def call (){

    node("builder") {
        def webhookpayload = readJSON text: env.JSON_PAYLOAD;
        echo "JSON: ${webhookpayload}"

        currentBuild.displayName =  '#' + env.BUILD_NUMBER;

        stage('Get code'){
            git branch: 'main',
            credentialsId: 'git-ssh',
            url: 'git@github.com:your-org/your-repo.git'
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