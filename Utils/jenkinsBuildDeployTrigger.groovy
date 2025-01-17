pipeline {
    agent{
        label any
    }

    environment {
        SSH_CREDENTIALS_ID = 'bitbucket-ssh'
        BUILD_JOB_NAME = 'CI_JOB_NAME'
        JENKINS_URL = 'https://my-home-jenkins.local/ci/job/DEPLOY_JOB'
        CONFIG_DIR = 'DEV'
        OSE_CLUSTER_DEV = 'dev-gen.cluster.local'
        OSE_CLUSTER_PROD = 'prod-gen.cluster.loca'
        PLAYBOOKS_OSE_NGINX_DEPLOY = 'false,false,false,true,false,false,true,'
        PLAYBOOKS_NGINX_DEPLOY = 'false,false,false,true,false,false,true,'
    }

    stages{
        stage('Получение перменных для запуска'){
            steps {
                script {
                    sh "env"
                    dev webhookPayload = readJSON text: env.JSON_PAYLOAD
                }
            }
        }
    }
}