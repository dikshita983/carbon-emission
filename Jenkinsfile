pipeline {
    agent {
        kubernetes {
            // We define a custom pod with 2GB memory for the agent (jnlp) so Sonar doesn't crash it.
            // We also add a 'dind' container for building Docker images.
            yaml '''
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: jnlp
    image: jenkins/inbound-agent:3283.v92c105e0f819-7
    resources:
      limits:
        memory: "2Gi"
        cpu: "1"
      requests:
        memory: "1Gi"
        cpu: "500m"
  - name: dind
    image: docker:dind
    securityContext:
      privileged: true
    env:
      - name: DOCKER_TLS_CERTDIR
        value: ""
'''
        }
    }

    environment {
        // --- SERVER INFO ---
        NEXUS_URL = 'nexus.imcc.com:9001'
        SONAR_URL = 'http://sonarqube.imcc.com/'
        
        // --- PROJECT INFO ---
        IMAGE_NAME = 'carbon-emission-web-app'
        TAG        = "${env.BUILD_NUMBER}"
        
        // --- CREDENTIALS ---
        CRED_ID_SONAR   = 'sonar-creds-142'          
        CRED_ID_NEXUS   = 'nexus-cred-142'    
        CRED_ID_K8S     = 'k8s-kubeconfig'       
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
                    // Define tool inside the stage to avoid "Invalid tool type" error
                    def scannerHome = tool name: 'sonar-scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                    
                    // Use the correct server name from your screenshot
                    withSonarQubeEnv('sonar-imcc-2401060') { 
                        withCredentials([usernamePassword(credentialsId: CRED_ID_SONAR, usernameVariable: 'SONAR_USER', passwordVariable: 'SONAR_PASS')]) {
                            // We limit the scanner heap to 1GB to be safe inside our 2GB container
                            sh """
                            export SONAR_SCANNER_OPTS="-Xmx1024m"
                            "${scannerHome}/bin/sonar-scanner" \
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
                // Run this inside the 'dind' container which has the 'docker' command
                container('dind') {
                    script {
                        // Build
                        sh "docker build -t ${NEXUS_URL}/${IMAGE_NAME}:${TAG} ."
                        
                        // Login & Push
                        withCredentials([usernamePassword(credentialsId: CRED_ID_NEXUS, usernameVariable: 'N_USER', passwordVariable: 'N_PASS')]) {
                            // Note: We log in to the registry URL specifically
                            sh "echo $N_PASS | docker login -u $N_USER --password-stdin ${NEXUS_URL}"
                            sh "docker push ${NEXUS_URL}/${IMAGE_NAME}:${TAG}"
                        }
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                // K8s commands run from the agent (jnlp), which has kubectl configured by the plugin
                script {
                    withKubeConfig([credentialsId: CRED_ID_K8S]) {
                        sh "kubectl apply -f kubernetes-deployment.yaml"
                        
                        // Update image
                        sh "kubectl set image deployment/carbon-app-deployment carbon-app-container=${NEXUS_URL}/${IMAGE_NAME}:${TAG}"
                        
                        // Restart
                        sh "kubectl rollout restart deployment/carbon-app-deployment"
                    }
                }
            }
        }
    }
}