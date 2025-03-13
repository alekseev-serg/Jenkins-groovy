def call(webhookpayload){
    if (webhookpayload.action != null){
        echo "This is event pullRequest: ${webhookpayload.action}"
    }
    else {
        echo 'This is not PR'
    }
}