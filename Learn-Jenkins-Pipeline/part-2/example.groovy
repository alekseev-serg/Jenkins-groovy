pipeline {
    agent any

    options {
        timeout(time: 300, unit: 'SECONDS')
    }

    stages {

        stage("Checkout Git") {
            steps {
                git branch: 'main', url: 'https://github.com/elestopadov/java-example-apps.git'
            }
        }

        stage("Unit Test") {
            steps {
                echo 'Running unit-tests'
                sleep 5
            }
        }

        stage("Security Test") {
            steps {
                echo 'Running unit-tests'
                sleep 5
            }
        }

        stage("Quality Test") {
            steps {
                echo 'Running quality tests'
                sleep 5
            }
        }

        stage("Build") {
            steps {
                echo 'Building artifact'
                sleep 10
                sh 'ls -lha'
            }
        }

        stage("Deploy") {
            steps {
                echo 'Deploying artifact'
                sleep 10
            }
        }

        stage("Acceptance Tests") {
            steps {
                echo 'Running Acceptance Tests'
                
                script {
                    def browsers = ["Edge", "Firefox", "Chromium"]
                    for (int i = 0; i < browsers.size(); i++) {
                        echo "Acceptance testing in ${browsers[i]} browser"
                        sleep 2
                    }
                } 
            }
        }   
    }
    
    post {
        success {
            mail to: 'team-company@example.ru',
            subject: "Completed Pipeline: ${currentBuild.fullDisplayName}",
            body: "Your build completed, please check: ${env.BUILD_URL}"
        }
        failure {
            mail to: 'team-company@example.ru',
            subject: "Failure project - Jenkins Pipeline: ${currentBuild.fullDisplayName}",
            body: "Your build FAILED, please check: ${env.BUILD_URL}"
        }
    }
}