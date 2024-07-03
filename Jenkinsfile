pipeline {
    agent any
    tools {
        maven 'M3'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Build and Deploy Docker Image') {
            steps {
                script {
                    // Stop and remove existing container (if exists)
                    sh 'docker stop employee-retention-backend || true'
                    sh 'docker rm employee-retention-backend || true'

                    // Remove existing image (if exists)
                    sh 'docker rmi employee-retention/backend-employee-retain:0.0.1 || true'

                    // Build Docker image
                    sh 'docker buildx build --platform linux/amd64 -t employee-retention/backend-employee-retain:0.0.1 .'

                    // Run Docker container
                    sh 'docker run --name employee-retention-backend -d -p 8089:8089 -v /extra/backup/employee/upload:/app/upload employee-retention/backend-employee-retain:0.0.1'
                }
            }
        }
    }
}