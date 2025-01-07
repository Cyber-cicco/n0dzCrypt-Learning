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
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Code Coverage') {
            steps {
                sh 'mvn verify'
            }
        }
        stage('Archive Coverage Report') {
            steps {
                // Archive the JaCoCo coverage report
                archiveArtifacts artifacts: 'target/site/jacoco/*.html', allowEmptyArchive: true
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
        stage('SonarQube Analysis') {
            steps {
                 script {
                     def mvnHome = tool 'Maven 3.9.9' 
                     withSonarQubeEnv('SonarQ') {
                         sh "${mvnHome}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey='Digilearning' -Dsonar.projectName='Digilearning'"
                     }
                 }
             }
        }
        stage('Deploy') {
            steps {
                    sshagent(credentials: ['SSH-1']) {
                        sh '''
                            ssh-keyscan -H 192.168.1.110 >> ~/.ssh/known_hosts
                            scp target/digi-learning-0.0.1-SNAPSHOT.jar hijokaidan@10.99.215.34:/home/hijokaidan/sites/digi-learning/
                            ssh hijokaidan@192.168.1.110 ./home/hijokaidan/sites/digi-learning/setup.sh
                        '''
                }
            }
        }
    }
}
