pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'gerturde/dockerlab:latest'
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
    stages {
        stage('Build') {
            steps {
                bat 'mvn clean compile'
                echo 'Maven build completed'
            }
        }

    stage('Test') {
        steps {
            script {
                def hasTests = sh(script: 'find src/test/java -name "*.java" | wc -l', returnStdout: true).trim()
                if (hasTests != "0") {
                    echo "Running tests..."
                    sh 'mvn test'
                } else {
                    echo "No tests found, skipping test stage"
                }
            }
        }
    }

    stage('Package') {
        steps {
            bat 'mvn package -DskipTests'
            echo 'Maven package completed'
        }
    }
//          stage('Build JAR') {
//                     steps {
//                         script {
//                             try {
//                                 // Build the JAR file
//                                 bat 'mvn clean package'
//                             } catch (Exception e) {
//                                 error "Maven build failed: ${e.message}"
//                             }
//                         }
//                     }
//                 }
//
//                 stage('Verify JAR File') {
//                     steps {
//                         script {
//                             try {
//                                 // Verify that the JAR file exists
//                                 bat 'dir target'
//                                 bat 'dir target\\DockerLab-0.0.1-SNAPSHOT.jar'
//                             } catch (Exception e) {
//                                 error "File verification failed: ${e.message}"
//                             }
//                         }
//                     }
//                 }

        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        // Build the Docker image (no nohup required on Windows)
                        bat 'docker build -t %DOCKER_IMAGE% .'


                    } catch (Exception e) {
                        error "Docker image build failed: ${e.message}"
                    }
                }
            }
        }

        // stage('Test Docker Image') {
        //     steps {
        //         script {
        //             try {
        //                 // Assuming your Docker image has tests (adjust command as per your tests)
        //                 bat 'docker run --rm %DOCKER_IMAGE% ./run-tests.sh'
        //             } catch (Exception e) {
        //                 error "Tests failed: ${e.message}"
        //             }
        //         }
        //     }
        // }

        stage('Push to Docker Hub') {
            steps {
                script {
                    try {
                        // Login to Docker Hub and push the image
                        withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS_ID}", usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                            bat """
                            docker login -u %DOCKERHUB_USERNAME% -p ${DOCKERHUB_PASSWORD}
                            docker tag %DOCKER_IMAGE% %DOCKERHUB_USERNAME%/${DOCKER_IMAGE}
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
                        // Stop and redeploy Docker container
                        sh """
                        docker stop \$(docker ps -q --filter ancestor=${DOCKER_IMAGE}) || true
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
