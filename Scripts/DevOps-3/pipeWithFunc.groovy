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

def deploy(){
    echo "This is Deploy func"
}

node{
    checkout scm
    buildApp();
    runTests();
    stage('Hello User'){
        greetUser("Serg!");
    }

    stage('Deploy'){
        deploy();
    }
}