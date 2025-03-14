def call(ctx){
    def jenkinsFile

    dir('source'){
        sshagent([env.SSH_CRED_ID]){
            try {
                sh "git clone ${ctx.gitUrl} ./"
                sh "ls -la"
                def fileNames = ['Jenkinsfile', 'Jenkinsfile.groovy', 'jenkinsfile']

                for (jenkinsFileName in fileNames){
                    def jenkinsfileExist = fileExists('./distrib/${jenkinsFileName}')
                    if (jenkinsfileExist){
                        jenkinsFile = 'True'
                    }
                }
            } catch(Exception e) {
                echo e;
            } finally {
                cleanWs(disableDeferredWipeout: true, deleteDirs: true)
            }
        }
    }
    return [
        jenkinsFile: jenkinsFile;
    ];
}