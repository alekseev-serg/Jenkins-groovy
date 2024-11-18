pipeline {
    agent {
        label any
    }
    parameters {
        string(
            name: "OSH_API_SERVER_URL",
            description: "Example: https://api.dev-osh.server.ru:6443"
            defaultValue: params?.OSH_API_SERVER_URL ?: "",
            trim: true
        )
        string(
            name: "OSH_TOKEN_CRED_ID",
            description: "Example: openshift-token",
            defaultValue: params?.OSH_TOKEN_CRED_ID ?: "",
            trim: true
        )
        string(
            name: "OSH_NAMESPACE",
            description: "Example: namespace-name",
            defaultValue: params?.OSH_NAMESPACE ?: "",
            trim: true
        )
        choice (
            name: "ACTION",
            choice: [
                'Показать список всех секретов в неймспейсе с их леблами',
                'Показать username, используемый в секрете SECRET_NAME',
                'Проверить совпадает ли password для пользователя USER_NAME в секрете SECRET_NAME с CRED_ID_FOR_NEW_PASSWORD',
                'Обновить password для пользователя USER_NAME в секрете SECRET_NAME на новый пароль из CRED_ID_FOR_NEW_PASSWORD',
                'Удалить и создать секрет SECRET_NAME с пользователем USER_NAME и паролем ищ CRED_ID_FOR_NEW_PASSWORD для реджести REGISTRY_LIST с лейблами LABELS_LIST',
                'Установить секрет SECRET_NAME как default для пула образов в неймспейсе'
            ]
        )
        string(
            name: 'SECRET_NAME',
            trim: true,
            defaultValue: params?.SECRET_NAME ?: '',
            description: "Use depends on action. Example: image-pull-test"
        )
        string(
            name: 'USER_NAME',
            trim: true,
            defaultValue: params?.USER_NAME ?: '',
            description: "Use depends on action. Principal name form(Example: username@domain.com)"
        )
        string(
            name: 'CRED_ID_FOR_NEW_PASSWORD',
            trim: true,
            description: "Use depends on action. Example: secman-sa-registry"
        )
        string(
            name: 'REGISTRY_LIST',
            trim: true,
            defaultValue: params?.REGISTRY_LIST ?: '',
            description: "Used for delete & create action, separate with ','. Example: registry1.domain.com,registry2.domain.com,registry3.domain.com"
        )
        string(
            name: 'LABELS_LIST',
            trim: true,
            description: "Used for delete & create action. Format: labelName1=value1,label-name2=value_2"
        )
    }

    options {
        timestamps()
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(logRotator(numToKeepStr: '50', daysToKeepStr: '7'))
    }

    environment {
        OC_HOME = tool name: 'oc-4.5.0', type: 'oc'
    }

    stages {
        stage ('Connect to cluster') {
            steps {
                script {
                    sh "${OC_HOME}/oc version"
                    sh "${OC_HOME}/oc config view > .kubeconfig"

                    withCredentials([vaultString(credentialsId: params.OSH_TOKEN_CRED_ID, variable: 'openshiftToken')]) {
                        sh "${OC_HOME}/oc login ${params.OSH_API_SERVER_URL} --insecure-skip-tls-verify=true --token=${env.openshiftToken} --kubeconfig='.kubeconfig' --namespace=${params.OSH_NAMESPACE}"
                    }

                    try {
                        if (params.ACTION == 'Показать список всех секретов в неймспейсе с их леблами'){
                            currentBuild.displayName = '#' + env.BUILD_NUMBER + ' list all secrets'
                            echo "\n"
                            echo "### List of all secrets in the namespace"
                            sh "${OC_HOME}/oc --kubeconfig\'.kubeconfig\' get secrets --show-labels"
                        }
                        else if (params.ACTION == 'Показать username, используемый в секрете SECRET_NAME'){
                            currentBuild.displayName = '#' + env.BUILD_NUMBER + ' list usernames'
                            sh "${OC_HOME}/oc --kubeconfig='.kubeconfig' get secrets ${params.SECRET_NAME} -o jsonpath='{.data.\\dockerconfigjson}' | base64 -d > secretFile"
                            def dockerConfig = readJSON file: "secretFile"
                            echo "\n"
                            echo "### Usernames used in the secret ${params.SECRET_NAME}"
                        }
                        else if (params.ACTION == 'Проверить совпадает ли password для пользователя USER_NAME в секрете SECRET_NAME с CRED_ID_FOR_NEW_PASSWORD') {
                            currentBuild.displayName = '#' + env.BUILD_NUMBER + ' check password'

                            def usernameFromCred
                            def passwordFromCred

                            withCredentials([usernamePassword(credentialsId: params.CRED_ID_FOR_NEW_PASSWORD, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                usernameFromCred = env.USERNAME
                                passwordFromCred = env.PASSWORD
                            }

                            if (usernameFromCred != params.USERNAME){
                                echo "\n [WARNING] username in CRED_ID_FOR_NEW_PASSWORD is " + usernameFromCred + " but USER_NAME is " + params.USER_NAME + "\n"
                            }

                            sh "${OC_HOME}/oc --kubeconfig='.kubeconfig' get secrets ${params.SECRET_NAME} -o jsonpath='{.data.\\dockerconfigjson}'| base64 -d > secret"
                            echo "\n"
                            echo "### Password usage: "
                            def dockerConfig.auth.each { k, v ->
                                if (v.username == params.USER_NAME){
                                    if (v.password == passwordFromCred) {
                                        echo "[OK] ${k} > username >" + v.username + ": password == password in credential"
                                    } else {
                                        echo "[WARNING] ${k} > username >" + v.username + ": password != password in credential !!!"
                                    }

                                    def authDecoded = new String(v.auth.decodeBase64()
                                    def authUser = authDecoded.substring(0, authDecoded.indexOf(':'))
                                    def authPassword = authDecoded.substring(0, authDecoded.indexOf(':') + 1)

                                    if (authUser == v.username) {
                                        echo "[OK] ${k} > username >" + v.username + " > auth (username) > " + authUser + ": username == username (auth)"
                                    } else {
                                        echo "[WARNING] ${k} > username >" + v.username + " > auth (username) > " + authUser + ": username != username (auth) !!!"
                                    }
                                    if (authPassword == passwordFromCred) {
                                        echo "[OK] ${k} > username >" + v.username + " > auth (username) > " + authUser + ": password == password in credential"
                                    } else {
                                        echo "[WARNING] ${k} > username >" + v.username + " > auth (username) > " + authUser + ": password != password in credential !!!"
                                    }
                                } else {
                                    echo "[INFO] ${k} > username > " + v.username ": (password not checked)"
                                }
                            }
                        }
                        else if () {

                        }
                        else if () {

                        }
                        else if () {

                        }
                    }
                }
            }
        }
    }
}