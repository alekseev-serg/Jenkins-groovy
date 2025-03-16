def call(ctx, repositoryUri, buildResult){
    notifyBitbucket(
        commitSha1: ctx.commitHash,
        considerUnstableAsSuccess: true,
        credentialsId: "credentialsId",
        disableInprogressNotification: false,
        ignoreUnverifiedSSLPeer: true,
        includeBuildnumberInKey: false,
        prependParentProjectKey: false,
        buildStatus: buildResult,
        buildName: "[${JOB_NAME.split("/").last().trim()}]",
        projectKey: repositoryUri.path.tokenize("/")[0],
        stashServerBaseUrl: "https://" + repositoryUri.host
    )
}