def call(){

    properties([
        parameters ([
            string(name: 'JSON_PAYLOAD', description: 'Generic body state'),
            string(name: 'JSON_QUERY', description: 'Generic query state'),
        ]),

        pipelineTriggers([[
            $class: 'GenericTrigger',
            genericVariables: [[key: 'JSON_PAYLOAD', value: '$']],
            genericRequestVariables: [[key: 'JSON_QUERY', value: '$']],
            causeString: 'PIPELINE',
            token: 'some-token-string',
            printContributedVariables: true,
            printPostContent: true
        ]])
    ]);

    // ENVIRONMENTS
    env.BUILD_JOB_NAME = env.BUILD_JOB_NAME ?: 'CI_4321';
    env.DEVOPS_CREDENTIALS_ID = env.DEVOPS_CREDENTIALS_ID ?: null;
    env.DEVOPS_HOST = env.DEVOPS_HOST ?: "https://DEVOPS-host.ru";
    env.BB_CREDENTIALS = env.BB_CREDENTIALS ?: null;
    env.SSH_CRED_ID = 'bitbucket-ssh';
    env.JENKINS_URL = 'https://some-jenkins-host.kek.ru';
    env.CONFIG_DIR = 'DEV';
    env.OSE_CLUSTER = 'cluster.local';
    env.PLAYBOOKS_NGINX = 'false, true, false';
    env.PLAYBOOKS_OSE = 'true, false, true';

    node("linux||builder||master"){
        def webhookPayload = readJSON text: env.JSON_PAYLOAD;
        def webhookQuery = readJSON text: env.JSON_QUERY;
        def ctx = init(webhookPayload);

        currentBuild.displayName = '#' + env.BUILD_NUMBER + "${ctx.appName}";

        // Определяем переменные для нотификации в BB
        def repositoryUri = new URI(ctx.gitUrl);
        def bitbucketStatus = 'IN PROGRESS';
        def buildResult = 'SUCCESS';

        stage('prepare vars'){
            if(ctx.event.contains("pr")){
                echo "VAR LIST APP, REPO, BRANCH, LATEST_COMMIT";
            }
        }

        stage('Check Jenkins file in repo'){
            def checkJenkinsFile = checkJenkinsFile(ctx);
        }

        try{
            stage('RUN BUILD'){
                jobName = env.BUILD_JOB_NAME;
                def build = runBuild(ctx, jobName, checkJenkinsFile);
            }
        }
    }
}