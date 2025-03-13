def call(webhookpayload){
    def event;

    if (webhookpayload.action != null){
        echo "This is event pullRequest: ${webhookpayload.action}"
    }
    else {
        echo 'This is not PR'
    }

    return [
        event: webhookpayload.action,
    ];
}