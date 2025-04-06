node {

    stage("Read From file"){
        def config = readJSON(file: 'config.json')
        echo "Version = ${config.version}"
    }

}