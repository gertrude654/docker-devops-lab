pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'dockerlab:latest'
        GIT_REPO_URL = 'https://github.com/gertrude654/docker-devops-lab.git'
        GIT_BRANCH = 'master'
        DOCKERHUB_CREDENTIALS_ID = '425775b7-629b-4c2f-b757-74f5dc26fbe4' // Replace with your Docker Hub credentials in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                git branch: "${GIT_BRANCH}", url: "${GIT_REPO_URL}"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        // Build the Docker image
                        sh "docker build -t ${DOCKER_IMAGE} ."
                    } catch (Exception e) {
                        error "Docker image build failed: ${e.message}"
                    }
                }
            }
        }

        stage('Test Docker Image') {
            steps {
                script {
                    try {
                        // Run tests inside Docker container (if any test suite is included)
                        // Assuming that your Docker image includes some tests, this command can vary based on your project setup
                        sh "docker run --rm ${DOCKER_IMAGE} ./run-tests.sh" // Adjust this based on your test script
                    } catch (Exception e) {
                        error "Tests failed: ${e.message}"
                    }
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    try {
                        // Login to Docker Hub and push the image
                        withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS_ID}", usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                            sh """
                            docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}
                            docker tag ${DOCKER_IMAGE} ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}
                            docker push ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}
                            """
                        }
                    } catch (Exception e) {
                        error "Docker push failed: ${e.message}"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    try {
                        // Assuming you want to deploy locally
                        // Stop any existing containers and run the new image
                        sh """
                        docker stop $(docker ps -q --filter ancestor=${DOCKER_IMAGE}) || true
                        docker run -d ${DOCKER_IMAGE}
                        """
                    } catch (Exception e) {
                        error "Deployment failed: ${e.message}"
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs() // Clean the workspace after the job
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
