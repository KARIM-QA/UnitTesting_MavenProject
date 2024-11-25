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

        stage('Start SonarQube') {
            steps {
                script {
                    // stop containers if they exist
                    sh 'docker-compose -f ${DOCKER_COMPOSE_FILE} down -v || true'

                    // start SonarQube et PostgreSQL
                    sh 'docker-compose -f ${DOCKER_COMPOSE_FILE} up -d'

                    // wait sonarqube to completly charged
                    sh '''
                        echo "Waiting for SonarQube to start..."
                        timeout=300
                        while ! curl -s http://localhost:9000 >/dev/null; do
                            sleep 10
                            timeout=$((timeout - 10))
                            if [ $timeout -le 0 ]; then
                                echo "Timeout waiting for SonarQube to start"
                                exit 1
                            fi
                        done
                        echo "SonarQube is ready!"
                    '''
                }
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
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
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            script {
                // stop containers
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
}
