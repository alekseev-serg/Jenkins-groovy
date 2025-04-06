node {
    parallel(
        serviceA: {
            stage('Build A'){
                echo 'Build A'
            }
        }
        serviceB: {
            stage('Build B'){
                echo 'Build B'
            }
        }
    )
}