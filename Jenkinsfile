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
        stage('Verify') {
            parallel {
                stage('Test') {
                    steps {
                        sh 'mvn test'
                    }
                }
                stage('Code Coverage') {
                    steps {
                        sh 'mvn verify'
                        archiveArtifacts artifacts: 'target/site/jacoco/*.html', allowEmptyArchive: true
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
            }
        }
        // Ensure this stage runs only if 'Verify' stages succeed
        stage('Deploy') {
            steps {
                echo 'deploying'
            }
        }
    }
}

