def call (){

    node("builder") {

        currentBuild.displayName =  '#' + env.BUILD_NUMBER;
        echo "Raw JSON: ${env.JSON_PAYLOAD}"

        echo "---------------------"

        def webhookpayload = readJSON text: env.JSON_PAYLOAD;
        echo "Read JSON: ${webhookpayload}";


        if (webhookpayload instanceof List) {
            echo "JSON пришёл в виде массива! Количество элементов: ${webhookpayload.size()}"
            // Например, обработаем первый элемент:
            def firstItem = webhookpayload[0]
            echo "Первый элемент: ${firstItem}"
        } else {
            echo "JSON пришёл в виде объекта!"
            echo "Repository: ${webhookpayload.repository.full_name}"
        }

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