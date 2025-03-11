def cfg = [
        workDir: 'test',
        repository: [
                credentialsId: 'cred-from-jenkins',
                sshUrl: 'ssh://git@github.com/project/project_name.git',
                branch: 'master'
        ],
        subsystemPath: './DEV/subsystem.json'
];

pipeline {
    agent {
        label 'agentName'
    }

    parameters {
        string(name: 'APP_NAME', description: 'some_description', trim: true)
        text(name: 'APP_DESCRIPTION', description: 'some_description', trim: true)
        string(name: 'COMMAND_CONF_LINK', description: 'some_description', trim: true)
        string(name: 'GIT_SSH_URL', description: 'some_description', trim: true)
        booleanParam(name: 'ADD_TO_REGISTRY', description: 'some_description', trim: true)
    }

    stages {
        stage('Проверка имени приложения') {
            steps {
                script {
                    if (params.APP_NAME.size() > 17) throw new Exception("APP_NAME len > 17");
                    if (params.APP_NAME.startsWith("Z_")) throw new Exception("APP_NAME не должно начанаться с Z");
                    if (!params.GIT_SSH_URL.startsWith("ssh://")) throw new Exception("GIT_SSH_URL должно начинаться с ssh://");
                    if (params.APP_DESCRIPTION.size() == 0) throw new Exception("Обязательное поле");
                    env.FORMAT_APP_NAME = params.APP_NAME.toUpperCase()
                        .replaceAll("\\.", "_")
                        .replaceAll("-", "_")

                    currentBuild.displayName = "${env.FORMAT_APP_NAME}"
                    currentBuild.description = "Попытка зарезервировать имя ${env.FORMAT_APP_NAME}"
                }
            }
        }

        stage('Загрузка реестра') {
            steps {
                script {
                    dir(cfg.workDir) {
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: cfg.repository.branch]],
                            userRemoteConfigs: [[
                                url: cfg.repository.sshUrl,
                                credentialsId: cfg.repository.credentialsId
                            ]],
                            extensions: [[$class: 'SubmoduleOption',
                                disableSubmodules: false,
                                parentCredentials: true,
                                recursiveSubmodules: true,
                                reference: '',
                                trackingSubmodules: false]],
                        ])
                    }
                }
            }
        }

        stage('Проверка доступности имени') {
            steps {
                script {
                    dir(cfg.workDir){
                        def projects = readJSON file: cfg.subsystemPath

                        if(projects[env.FORMAT_APP_NAME] != null) {
                            def apps = project.keySet();
                            sh "echo \"Already existed apps ${app}\""
                            throw new Exception(params.APP_NAME + " already exists")
                        }
                    }
                }
            }
        }
    }
}



