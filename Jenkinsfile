pipeline {
    agent any

    tools {
        // FIXED: Changed 'scannerHome' to the correct keyword 'sonarScanner'
        // Make sure 'sonar-scanner' matches the name in Jenkins -> Tools
        sonarScanner 'sonar-scanner' 
    }

    environment {
        // 1. Server Configuration
        NEXUS_URL = 'nexus.imcc.com:9001' 
        SONAR_URL = 'http://sonarqube.imcc.com/'
        
        // 2. Project Info
        IMAGE_NAME = 'carbon-emission-web-app'
        TAG        = "${env.BUILD_NUMBER}"
        
        // 3. Credentials
        CRED_ID_SONAR   = 'sonar-cred-142'          
        CRED_ID_NEXUS   = 'nexus-cred-142'    
              
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/dikshita983/carbon-emission.git'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // We use the tool command to get the path safely
                    def scannerHome = tool name: 'sonar-scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                    
                    withSonarQubeEnv('SonarQube') { 
                        withCredentials([usernamePassword(credentialsId: CRED_ID_SONAR, usernameVariable: 'SONAR_USER', passwordVariable: 'SONAR_PASS')]) {
                            sh """
                            ${scannerHome}/bin/sonar-scanner \
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
                    sh "docker build -t ${NEXUS_URL}/${IMAGE_NAME}:${TAG} ."
                    
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
                    withKubeConfig([credentialsId: CRED_ID_K8S]) {
                        sh "kubectl apply -f kubernetes-deployment.yaml"
                        sh "kubectl set image deployment/carbon-app-deployment carbon-app-container=${NEXUS_URL}/${IMAGE_NAME}:${TAG}"
                        sh "kubectl rollout restart deployment/carbon-app-deployment"
                    }
                }
            }
        }
    }
}