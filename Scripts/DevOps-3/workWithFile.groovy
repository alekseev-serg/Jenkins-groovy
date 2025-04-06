node {

    stage("Read From file"){
        sh "pwd"
        def config = readJSON(file: 'config.json')
        echo "Version = ${config.version}"
    }

}