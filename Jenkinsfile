pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }


        stage('SonarQube Analysis') {
            steps {
                script {
                    def mvn = tool 'maven'
                    withSonarQubeEnv(installationName: 'sonar') {
                        sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=demoSonarQube -Dsonar.projectName='demoSonarQube'"
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
/*
    post {
        always {
            script {
                 stop containers
                sh 'docker-compose -f ${DOCKER_COMPOSE_FILE} down -v'
                cleanWs()
            }
        }
        failure {
            script {
                sh 'docker-compose -f ${DOCKER_COMPOSE_FILE} down -v'
            }
        }
    }
    */
}
