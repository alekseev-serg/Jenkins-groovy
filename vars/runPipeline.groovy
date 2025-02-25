def call (){

    node("builder") {
        def webhookpayload = readJSON text: env.JSON_PAYLOAD;
        echo "JSON: ${webhookpayload}"

        currentBuild.displayName =  '#' + env.BUILD_NUMBER;

        stage('Get code'){
            checkout scm;
        }

        stage('Сборка'){
            echo "Build distrib"
        }

        stage('Deploy'){
            echo "Deploy"
        }
    }
}