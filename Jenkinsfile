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
                sshagent(['SSH-1']) {
                    sh 'git remote remove destination'
                    sh 'git remote add destination git@10.99.215.34:/home/git/digi-learning'
                    sh 'git push -u destination master'
                }
            }
        }
    }
}

