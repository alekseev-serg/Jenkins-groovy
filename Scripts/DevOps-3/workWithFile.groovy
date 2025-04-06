node {

    stage("Read From file"){
        def config = readJSON('config.json')
        echo "Version = ${config.version}"
    }
    
}