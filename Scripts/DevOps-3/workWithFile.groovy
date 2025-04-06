node {

    checkout scm

    stage("Read From file"){
        sh "pwd"
        sh "ls -la"
        def config = readJSON(file: 'Scripts/DevOps-3/config.json')
        echo "Version = ${config.version}"
    }

}