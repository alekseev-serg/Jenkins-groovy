node{
    checkout scm
    buildApp();
    runTests();
}

def buildApp() {
    stage('Build'){
        echo "Сборка проекта"
    }
}

def runTests(){
    stage('Tests'){
        echo "Run Test"
    }
}