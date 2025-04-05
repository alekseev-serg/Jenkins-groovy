node{
    stage('Get Env'){
        sh "env"
        echo "Building on node: ${env.NODE_NAME}"
    }

    stage('Get more env'){
        echo "Job Name: ${env.JOB_NAME}"
        echo "Build Number: ${env.BUILD_NUMBER}"
        echo "Workspace: ${env.WORKSPACE}"
    }
}