def call(ctx, initPlaybook){
    if(ctx.event == 'pr:merged'){
        def params = [
            "PARAMS1=${env.CONFIG_DIR}",
            "PARAMS2=${env.SUBSYSTEM}",
            "PARAMS3=${env.DISTRIB_VERSION}",
            "PARAMS4=${env.OSE_CLUSTER}",
            "PARAMS5=${initPlaybook}",
        ]

        withCredentials([string(credentialsId: 'deploy_token', variable: 'API_TOKEN')]){
            ciJobHandler = triggerRemoteJob(
                job: "${env.JENKINS_URL}",
                auth: TokenAuth(apiToken: hudson.util.Secret.fromString(API_TOKEN), userName: 'userName'),
                blockBuildUntilComplete: true,
                pollInternal: 100,
                httpPostReadTimeout: 10000,
                parameters: params.join("\n"),
            )
        }
    }else{
        echo "SKIP DEPLOY"
    }
}