def call(ctx){
    def version = defineVersion(ctx);
}

def defineVersion(ctx) {
    def lastTag = sh(
        script: "git tag --merged ${ctx.commitHash} --sort=-creatordate | grep '^v[0-9]\\+\\.[0-9]\\+\\.[0-9]\\+\$' | head -n 1", 
        returnStdout: true
    ).trim();

    echo "Последний тег ${lastTag ?: 'не определён'}";
    
}