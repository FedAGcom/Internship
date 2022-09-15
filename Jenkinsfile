#!groovy
properties([disableConcurrentBuilds()])

pipeline {
agent any
    options {
     buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
     timestamps()
    }

    stages {
        stage ("Docker-compose") {
            steps {
                echo " ============== start docker-compose =================="
                bat 'docker-compose up -d'
            }
        }
        stage('Build mvn project') {
            steps {
                echo " ============== build mvn project =================="
                sleep(time: 20, unit: "SECONDS")
                bat 'mvn clean install'
            }
        }
        stage("Docker login") {
            steps {
                echo " ============== docker login =================="
                withCredentials([usernamePassword(credentialsId: 'docker_hub_maxirage', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    bat """
                    docker login -u $USERNAME -p $PASSWORD
                    """
                }
            }
        }
        stage("Create docker image") {
            steps {
                echo " ============== start building image =================="
                	bat 'docker build -t maxirage/internship:latest . '
            }
        }
        stage("Docker push") {
            steps {
                echo " ============== start pushing image =================="
                bat 'docker push maxirage/internship:latest'
            }
        }
        stage("Run app") {
            steps {
                echo " ============== start app =================="
                bat 'docker-compose up -d --build'
            }
        }
    }
}