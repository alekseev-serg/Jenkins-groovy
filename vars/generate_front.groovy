@groovy.transform.Field def project = null;

def call(){
    node('some||worker||node'){
        if(!env.APP_NAME){
            stage("Реконфигурация джобы"){
                reconfigure();
            }

            return;
        }

        stage('Registry application'){
            front_register_app();
        }

        stage('Read app data'){
            project = front_register_app.getProject();
        }

        stage('Setup webhook git'){
            echo "TODO"
        }

        stage('Generate base project'){
            echo "TODO"
        }

        stage('Push to develop repo'){
            echo "TODO"
        }

        stage('Create PR'){
            echo "TODO"
        }

        stage('Print build info'){
            echo "TODO"
        }
    }
}

def reconfigure(){
    properties([
        parameters([
            string(name: "APP_NAME", description: "some description"),
            string(name: "APP_DESCRIPTION", description: "some description"),
            string(name: "COMMAND_CONF_LINK", description: "some description"),
            string(name: "GIT_SSH_URL", description: "some description"),
        ])
    ])
}