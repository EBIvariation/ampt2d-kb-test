pipeline {
  agent {
    docker {
      image 'maven:3.5.2-jdk-8'
    }
  }
  stages {
    stage('Build') {
          steps {
            sh 'mvn clean test'
          }
       }
  }
}
