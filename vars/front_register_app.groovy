@groovy.transform.Field def cfg = [
    workDir: 'folder',
    repository: [
        sshUrl: 'ssh://git....some.url.git',
        branch: 'main',
        credentialsId: 'frontend_registry_ssh'
    ],
    subsystemPath: './ALPHA/DEV/subsystem.json'
];

def call(){
    validate();

    dir(cfg.workDir){

        checkout([
            $class: 'GitSCM',
            branches: [[name: cfg.repository.branch]],
            userRemoteConfigs: [[
                url: cfg.repository.sshUrl,
                credentialsId: cfg.repository.credentialsId,
            ]]
        ])

        def projects = readJSON file cfg.subsystemPath;

        if(projects[env.FORMAT_APP_NAME] != null){
            echo "APP is always exist";
            return;
        }

        echo "APP not exist. CREATE APP";

        projects.remove("__default");
        def uri = new URI(params.GIT_SSH_URL);

        projects[env.FORMAT_APP_NAME] = [
            name: "value",
            name: "value",
            name: "value",
            name: "value",
            name: "value",
            name: "value",
            meta: [
                name: "value",
                name: "value",
                name: "value",
                name: "value",
                name: "value",
            ]
        ]

        //Записываем данные проекта в файл
        ['SEGMENT1', 'SEGMENT2'].each{segment ->
            ['DEV', 'STABLE', 'PREPROD', 'PROD'].each{stand ->
                try{
                    def rawProject = readJSON file: "${segment}/${stand}/subsystem.json";
                    def jsonProjects = new groovy.json.JsonBuilder([__default: rawProject["__default"]] + projects).toPrettyString();
                    writeFile(file: '${segment}/${stand/subsystem.json', text: jsonProjects, encoding: "UTF-8");
                }catch(Exception e){
                    echo "Error ${e.message}";
                }
            }
        }
        
        sshagent([cfg.repository.credentialsID]){
            wrap([$class: 'BuildUser']){
                sh """ 
                    git config --global user.email "${BUILD_USER_EMAIL}"
                    git config --global user.name "${BUILD_USER}"
                    git add .
                    git commit -n -m \"add app: \"${FORMAT_APP_NAME}\" by ${BUILD_USER} - ${BUILD_USER_ID}\"
                    git push origin HEAD:main
                """
            }
        }
    }
}

def getProject(){
    validate();

    def repoExist = fileExists cfg.workDir + "README.md";
    def project = null;

    dir(cfg.workDir){

        if(!repoExists){
            checkout([
                $class: 'GitSCM',
                branches: [[name: cfg.repository.branch]],
                userRemoteConfigs: [[
                    url: cfg.repository.sshUrl,
                    credentialsId: cfg.repository.credentialsId,
                ]]
            ])
        }

        def projects = readJSON file cfg.subsystemPath;

        project = projects[env.FORMAT_APP_NAME];
    }

    return project;
}

def validate(){
    if (params.APP_NAME.size() > 17) throw new Exception("APP_NAME max size 17");
    if (params.APP_NAME.startWith("Z_")) throw new Exception("APP_NAME must not contains Z_");
    if (!params.GIT_SSH_URL.startWith("ssh://")) throw new Exception("GIT_SSH_URL must contains ssh://");
    if (params.APP_DESCRIPTION.size() == 0) throw new Exception("write APP_DESCRIPTION");

    env.FORMAT_APP_NAME = params.APP_NAME.toUpperCase().replace("\\","_").replace("-","_");

    currentBuild.displayName = "${env.FORMAT_APP_NAME}";
    currentBuild.description = "Try to registry new app";
}