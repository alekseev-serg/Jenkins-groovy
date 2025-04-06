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

def greetUser(String name){
    echo "Hello, ${name}"
}

node{
    checkout scm
    buildApp();
    runTests();
    greetUser("Serg!")
}