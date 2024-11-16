pipeline {
    agent {
        label any
    }
    parameters {
        string(
            name: "OSH_API_SERVER_URL",
            description: "Example: https://api.dev-osh.server.ru:6443"
            defaultValue: params?.OSH_API_SERVER_URL ?: "",
            trim: true
        )
        string(
        )
    }
}