pipeline {
    agent any
            tools {
                jdk "openjdk15"
            }
            stages {
                 stage('Checkout') {
                        steps {
                        git branch: 'feature/core',
                            url: 'https://github.com/FedorSergeev/smartapp.git'
                            }
                }
            stage('Chmod for Gradle wrapper') {
                            steps {
                                script {
                                    sh 'chmod +x gradlew'
                                }
                            }
            }
            stage('Build executable') {
                steps {
                    script {
                        sh './gradlew clean build --no-daemon'
                    }
                }

            }

                stage('Build Docker image') {
                    steps {
                        script {
                            sh './gradlew  :application:docker --no-daemon'
                        }
                    }

                }
                stage('Push Docker image') {
                    steps {
                        script {
                            sh './gradlew  :application:dockerPush --no-daemon'
                        }
                    }

                }
                stage('Clean') {
                    steps {
                        script {
                            sh './gradlew  :application:dockerClean --no-daemon'
                        }
                    }

                }
        }
}