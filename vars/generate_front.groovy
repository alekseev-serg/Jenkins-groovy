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

        stage('Read metadata'){
            project = front_register_app.getProject();
        }

        stage('Setup develop repo - Webhook'){
            dir('scripts'){
                checkout([
                    $class: 'GitSCM',
                    branches: [scm.branches[0]],
                    useRemoteConfigs: [[
                        url: scm.userRemoteConfigs?.get(0).url,
                        credentialsId: scm.userRemoteConfigs?.get(0)?.credentialsId
                    ]]
                ])
            }
            dir('scripts/frontend/ansible'){
                def uri = new URI(project.mets.sshUrl)
                def bitbucket_workspace = uri.path.split("/")[1];
                def bitbucket_repo_slug = uri.path.split.("/")[2].replace(".git", "");
                def bitbucket_segment = uri.host.contains.("delta") ? "delta" : "sigma";
                
                withCredentials([usernamePassowrd(credentialsId: 'ad_password', usernameVariable: 'bitbucket_username', passwordVariable: 'bitbucket_token')]){
                    withCredentials([usernamePassowrd(credentialsId: 'dpm_token', usernameVariable: 'dpm_username', passwordVariable: 'dpm_password')]){
                        ansiblePlaybook(
                            playbook: 'configurate-repository.yml',
                            installation: 'ansible-latest',
                            desableHostKeyChecking:  true,
                            become: 'yes',
                            extraVars: [
                                subsystem: env.SUBSYSTEM,
                                bitbucket_workspace: bitbucket_workspace,
                                bitbucket_repo_slug: bitbucket_repo_slug,
                                bitbucket_segment: bitbucket_segment,

                                application_name: project.fpi_name,
                                application_key: project.meta.dpmApplicationKey,
                                service_name: project.fpi_name,
                                service_key: project.meta.dpmServiceKey,
                                qg_service_key: project.meta.dpmQgServiceKey,
                                nexus_name: project.fpi_name,
                                nexus_group_id: project.groupId,

                                bitbucket_username: "${bitbucket_username},"
                                bitbucket_token: "${bitbucket_token},"
                                dpm_username: "${dpm_username},"
                                dpm_password: "${dpm_password},"
                            ]
                        )
                    }
                }
            }
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