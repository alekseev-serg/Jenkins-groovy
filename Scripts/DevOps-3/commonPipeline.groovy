node(label: 'builder'){
    stage('Example'){
        try{
            sh 'Hello World'
        }catch(e){
            currentBuild.result = 'FAILURE'
            echo "Build: ${currentBuild.result}"
        }
    }

    stage('def variables'){
        currentBuild.displayName = "#${BUILD_NUMBER} Test"
        currentBuild.description = "Build release"
    }
}