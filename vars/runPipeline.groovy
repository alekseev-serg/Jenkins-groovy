def call (){

    node("builder") {

        currentBuild.displayName =  '#' + env.BUILD_NUMBER;
        echo "---------------------"
        def webhookpayload = env.JSON_PAYLOAD;
        echo "Read JSON: ${webhookpayload}";
        echo "Repository: ${webhookpayload.repository.full_name}"


        stage('Get code'){
            echo 'clone repo from webhook'
            git branch: 'main',
            credentialsId: 'git-ssh',
            url: 'git@github.com:alekseev-serg/devops-wiki-backend.git'
        }

        stage('Сборка'){
            sh "ls -la"
            echo "Build distrib"
        }

        stage('Deploy'){
            echo "Deploy"
        }
    }

    post {
        always {
            script {
                cleanWs disableDeferredWipeout: true, deleteDirs: true
            }
        }
    }
}