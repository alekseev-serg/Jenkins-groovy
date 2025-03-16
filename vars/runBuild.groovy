def call(ctx, jobName, checkJenkinsFile){

    if (checkJenkinsFile.jenkinsFile == 'True'){
        echo "jenkinsFile is True";
        def runJob = build job: jobName, parameters: [
                        string(name: 'CONFIG_REPO', value: "${ctx.gitUrl}"),
                        string(name: 'CONFIG_BRANCH', value: "${ctx.branch}"),
        ], propagate: false;

        def artifactVersion = runJob.getBuildVariables().VERSION;
        echo "DISTRIBUTIVE VERSION: ${artifactVersion}";

        return [
            artifactVersion: artifactVersion
        ];
    } else {
        echo "jenkinsFile is false";
        echo "build with common pipeline"
        def runJob = build job: jobName, parameters: [
                        string(name: 'APP_NAME', value: "${ctx.appName}"),
                        string(name: 'APP_REPO', value: "${ctx.gitUrl}"),
                        string(name: 'APP_BRANCH', value: "${ctx.branch}"),
        ], propagate: false;
        def artifactVersion = runJob.getBuildVariables().VERSION;

        echo "Build done: ${runJob.result}";
        return [
            artifactVersion: artifactVersion
        ];
    }
}