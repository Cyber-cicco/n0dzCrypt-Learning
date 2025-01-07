pipeline {
    agent any
    tools {
        maven 'Maven 3.9.9'
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Cyber-cicco/n0dzCrypt-Learning.git'
            }
        }
        stage('Deploy') {
            steps {
                sshagent(['SSH-1']) {
                    sh 'git push -u destination master'
                }
            }
        }
    }
}

