node {

    checkout scm

    stage("Read From file"){
        sh "pwd"
        sh "ls -la"
        def config = readJSON(file: 'config.json')
        echo "Version = ${config.version}"
    }

}