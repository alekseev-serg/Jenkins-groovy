def config = [
    workDir : 'test'
    source: [nexusUrl: "https://nexus-url.local", groupId: 'group-id/app/app-name', artifactId: 'artifact-id', version: 'app-version', repository: 'repo-distr'],
    distribution: [groupId: 'group-distrib-id.your-namespace.app-name', artifactId: 'your-artifact-id', version: 'app-version'],
    load: [nexusUrl: "https://nexus-url.local", credentialsId: 'JenkinsCred', repository: 'repo-distr-dev']
];


pipeline {
    agent {
        label 'Any'
    }

    stages {
        stage('Download config'){
            steps {
                script {
                    dir(cfg.WorkDir){
                        withCredentials ([
                            usernamePassword (
                                credentialsId: cfg.load.credentialsId,
                                usernameVariable: 'nexusUser',
                                passwordVariable: 'nexusPwd'
                            )
                        ])
                        {
                            def request = 'curl -u $nexusUser:$nexusPwd ' +
                                            cfg.source.nexusUrl + '/repository/' +
                                            cfg.source.repository + '/' +
                                            cfg.source.groupId + '/' +
                                            cfg.source.artifactId + '/' +
                                            cfg.source.version + '/' +
                                            cfg.source.artifactId + '/' +
                                            cfg.source.version + '-distrib.zip \
                                            --output ' + 'distrib-number.zip'
                        }
                    }
                }
            }
        }
        stage('searchFile') {
            steps {
                script {
                    dir(cfg.workDir){
                        sh "ls -la"
                    }
                }
            }
        }
    }
}