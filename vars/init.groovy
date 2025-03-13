def call(webhookpayload){
    if (webhookpayload.pull_request != null){
        echo 'This is PullRequest'
    }
    else {
        echo 'This is not PR'
    }
}