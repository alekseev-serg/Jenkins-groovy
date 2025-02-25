def call (){

    node("builder") {
        def webhookpayload = readJSON text: env.JSON_PAYLOAD;
        echo "JSON: ${webhookpayload}"

        currentBuild.displayName =  '#' + env.BUILD_NUMBER;

        stage('Get code'){
            echo 'clone repo from webhook'
            // git branch: 'main',
            // credentialsId: 'git-ssh',
            // url: 'git@github.com:alekseev-serg/devops-wiki-frontend.git'
        }

        stage('Сборка'){
            sh "ls -la"
            echo "Build distrib"
        }

        stage('Deploy'){
            echo "Deploy"
        }
    }
}