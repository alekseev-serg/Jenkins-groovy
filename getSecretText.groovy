pipeline {
    agent any

    stages {
        stage('stage_name') {
            steps {
                script {
                    def kek = "get it"
                    withCredentials([string(credentialsId:"key", variable:"Token")]) {
                        kek = sh (returnStdout: true, script: 'echo ${Token}')
                    }
                    println kek
                }
            }
        }
    }
}
