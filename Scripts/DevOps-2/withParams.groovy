pipeline {
    agent any

    parameters{
        string(name: 'USERNAME', defaultValue: 'devops', description: 'Введите имя пользователя:')
    }

    stages{
        stage('Print Params'){
            echo "Hello ${env.USERNAME}"
        }
    }
}