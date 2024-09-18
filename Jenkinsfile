// pipeline {
//     agent any
//
//     stages {
//         stage('Checkout') {
//             steps {
//                 git 'https://github.com/your-repo/java-app.git'
//             }
//         }
//
//         stage('Build') {
//             steps {
//                 sh 'mvn clean package'
//             }
//         }
//
//         stage('Test') {
//             steps {
//                 sh 'mvn test'
//             }
//         }
//
//         stage('Build Docker Image') {
//             steps {
//                 script {
//                     dockerImage = docker.build('your-dockerhub-username/java-app')
//                 }
//             }
//         }
//
//         stage('Push Docker Image') {
//             steps {
//                 script {
//                     docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials-id') {
//                         dockerImage.push('latest')
//                     }
//                 }
//             }
//         }
//
//         stage('Deploy') {
//             steps {
//                 sh 'docker pull your-dockerhub-username/java-app:latest'
//                 sh 'docker run -d -p 8080:8080 your-dockerhub-username/java-app:latest'
//             }
//         }
//     }
// }

//
//
// pipeline {
//     agent any
//     environment {
//         DOCKER_IMAGE = 'kety016/sortingalgorithms:latest'
//         REGISTRY_CREDENTIALS_ID = 'dockerhub-credentials-id' // Docker Hub credentials ID
//         DEPLOY_SERVER = 'user@192.168.1.10' // Replace with your server's IP or hostname
//         GIT_REPO_URL = 'https://github.com/Magnifique67/docker.git'
//         GIT_BRANCH = 'main' // Change 'main' to your branch if different
//     }
//     stages {
//         stage('Checkout') {
//             steps {
//                 script {
//                     // Checkout code from SCM
//                     checkout([$class: 'GitSCM',
//                         branches: [[name: "*/${GIT_BRANCH}"]],
//                         doGenerateSubmoduleConfigurations: false,
//                         extensions: [],
//                         userRemoteConfigs: [[url: "${GIT_REPO_URL}"]]
//                     ])
//                 }
//             }
//         }
//         stage('Build') {
//             steps {
//                 script {
//                     // Build the Java application using Maven
//                     try {
//                         sh 'mvn clean package'
//                     } catch (Exception e) {
//                         error "Build failed: ${e.message}"
//                     }
//                 }
//             }
//         }
//         stage('Test') {
//             steps {
//                 script {
//                     // Run tests
//                     try {
//                         sh 'mvn test'
//                     } catch (Exception e) {
//                         error "Tests failed: ${e.message}"
//                     }
//                 }
//             }
//         }
//         stage('Deploy') {
//             steps {
//                 script {
//                     // Deploy the Docker image on the deployment server
//                     try {
//                         sshagent([REGISTRY_CREDENTIALS_ID]) {
//                             // Commands to run on the remote server
//                             sh """
//                             ssh ${DEPLOY_SERVER} << EOF
//                                 docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}
//                                 docker pull ${DOCKER_IMAGE}
//                                 docker run -d ${DOCKER_IMAGE}
//                             EOF
//                             """
//                         }
//                     } catch (Exception e) {
//                         error "Deployment failed: ${e.message}"
//                     }
//                 }
//             }
//         }
//     }
//     post {
//         always {
//             // Actions that should be performed after all stages
//             cleanWs() // Clean workspace
//         }
//         success {
//             echo 'Pipeline succeeded!'
//         }
//         failure {
//             echo 'Pipeline failed!'
//         }
//     }
// }


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
