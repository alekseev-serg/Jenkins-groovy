node{
    stage('Get Env'){
        sh "env"
        echo "Building on node: ${env.NODE_NAME}"
    }

    stage('Get more env'){
        echo
    }
}