node(label: 'builder'){
    stage('Example'){
        try{
            sh 'exit 1'
        }catch(e){
            currentBuild.result = 'FAILURE'
            echo "Build: ${currentBuild.result}"
        }
    }
}