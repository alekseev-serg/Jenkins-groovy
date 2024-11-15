pipeline {
    agent any

    environment {
        OSE_URL = 'https://ose-some.domen.ru:6443'
        OSE_PROJECT = 'ose-project-dev'
        SECRET_NAME = 'test-secret'
    }

    stages {
        stage('Connect to OSE') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'ose_auth_token', variable: 'OSE_TOKEN')]) {
                        sh 'oc login --token=${OSE_TOKEN} --server=${OSE_URL}'
                        sh 'oc project ${OSE_PROJECT}'
                    }
                }
            }
        }
        stage('Get current OSE Token') {
            steps {
                script {

                }
            }
        }
    }
}
