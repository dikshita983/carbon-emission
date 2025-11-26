pipeline {
    agent any

    tools {
        // Make sure this name matches what you set in Jenkins -> Tools
        scannerHome 'sonar-scanner' 
    }

    environment {
        // --- YOUR SERVER INFO ---
        NEXUS_URL = 'nexus.imcc.com:9001'
        SONAR_URL = 'http://sonarqube.imcc.com/'
        
        // --- PROJECT INFO ---
        IMAGE_NAME = 'carbon-emission-web-app'
        TAG        = "${env.BUILD_NUMBER}"
        
        // --- CREDENTIAL IDs ---
        // We use the IDs you created in Jenkins
        CRED_ID_SONAR   = 'sonar-creds'          // Username & Password for Sonar
        CRED_ID_NEXUS   = 'nexus-credentials'    // Username & Password for Nexus
        CRED_ID_K8S     = 'k8s-kubeconfig'       // Secret File for Kubernetes
    }

    stages {
        stage('Checkout Code') {
            steps {
                // YOUR GitHub Repository
                git branch: 'main', url: 'https://github.com/dikshita983/carbon-emission.git'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Use the scanner tool (for non-Maven projects)
                    withSonarQubeEnv('SonarQube') { 
                        // Login using Username and Password
                        withCredentials([usernamePassword(credentialsId: CRED_ID_SONAR, usernameVariable: 'SONAR_USER', passwordVariable: 'SONAR_PASS')]) {
                            sh """
                            $scannerHome/bin/sonar-scanner \
                            -Dsonar.projectKey=CarbonEmissionWebApp \
                            -Dsonar.projectName="Carbon Emission Web App" \
                            -Dsonar.projectVersion=1.0.${BUILD_NUMBER} \
                            -Dsonar.sources=src/main/java \
                            -Dsonar.java.binaries=build/classes \
                            -Dsonar.host.url=${SONAR_URL} \
                            -Dsonar.login=$SONAR_USER \
                            -Dsonar.password=$SONAR_PASS
                            """
                        }
                    }
                }
            }
        }

        stage('Build & Push to Nexus') {
            steps {
                script {
                    // 1. Build Docker Image (Uses Dockerfile & ROOT.war from your repo)
                    sh "docker build -t ${NEXUS_URL}/${IMAGE_NAME}:${TAG} ."
                    
                    // 2. Login & Push using Nexus Credentials
                    withCredentials([usernamePassword(credentialsId: CRED_ID_NEXUS, usernameVariable: 'N_USER', passwordVariable: 'N_PASS')]) {
                        sh "echo $N_PASS | docker login -u $N_USER --password-stdin http://${NEXUS_URL}"
                        sh "docker push ${NEXUS_URL}/${IMAGE_NAME}:${TAG}"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Deploy using the kubeconfig secret file
                    withKubeConfig([credentialsId: CRED_ID_K8S]) {
                        // Apply the yaml configuration
                        sh "kubectl apply -f kubernetes-deployment.yaml"
                        
                        // Force the deployment to use the new image we just built
                        sh "kubectl set image deployment/carbon-app-deployment carbon-app-container=${NEXUS_URL}/${IMAGE_NAME}:${TAG}"
                        
                        // Restart the deployment to load the new version
                        sh "kubectl rollout restart deployment/carbon-app-deployment"
                    }
                }
            }
        }
    }
}