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
        stage('Build') {
            steps {
                sh 'mvn clean package -Dskiptests'
            }
        }
        stage('Archive Build') {
            steps {
                archiveArtifacts artifacts: 'target/digi-learning-0.0.1-SNAPSHOT.jar', allowEmptyArchive: true
            }
        }
        stage('Deploy') {
            steps {
                sshagent(['SSH']) {
                    sshagent(credentials: ['SSH']) {
                        sh '''
                            ssh-keyscan -H 10.99.215.34 >> ~/.ssh/known_hosts
                            scp target/digi-learning-0.0.1-SNAPSHOT.jar hijokaidan@10.99.215.34:/home/hijokaidan/sites/digi-learning/
                            ssh hijokaidan@10.99.215.34 ./home/hijokaidan/sites/digi-learning/setup.sh
                        '''
}
                }
            }
        }
    }
}
