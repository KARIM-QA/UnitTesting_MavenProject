node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'maven';
    withSonarQubeEnv(installationName: 'sonar') {
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=demoSonarQube -Dsonar.projectName='demoSonarQube'"
    }
  }
}