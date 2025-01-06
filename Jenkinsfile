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
        stage('Build') {
            steps {
                sh 'mvn clean package'
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
        //TODO: faire en sorte que ce stage ne se déclenche que si les deux dernière étapes ont fonctionnées.
        //TODO: faire en sorte de créer une pipeline qui permet d'attendre que le conteneur soit build avant de reup pour éviter un délai entre les deux déploiements.
        stage('Deploy') {
            steps {
                sh 'mvn spring-boot:start'
            }
        }
    }
}

