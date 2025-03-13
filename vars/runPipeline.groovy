def call (){

    node("builder") {

        currentBuild.displayName =  '#' + env.BUILD_NUMBER;
        echo "Raw JSON: ${env.JSON_PAYLOAD}"
        echo "---------------------------------------------------------------"

        def webhookpayload = readJSON text: env.JSON_PAYLOAD;
        def ctx = init(webhookpayload);

        echo "event ${ctx.event}";

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
}