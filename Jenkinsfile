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
        //TODO: faire en sorte que ce stage ne se déclenche que si les deux dernière étapes ont fonctionnées.
        //TODO: faire en sorte de créer une pipeline qui permet d'attendre que le conteneur soit build avant de reup pour éviter un délai entre les deux déploiements.
        stage('Docker build and deploy') {
            steps {
                sh 'docker stop digilearning'
                sh 'docker rm digilearning'
                sh 'docker build digilearning'
                sh 'docker compose down'
                sh 'docker compose up'
            }
        }
    }
}

