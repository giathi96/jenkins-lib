def call() {
    script {
        def input = "AhelloA"
        def pattern = /A(.*?)A/
        def matcher = (input =~ pattern)
        def result = "NO MATCH"

        if (matcher.find()) {
            result = matcher.group(1)
            println(result)
        } else {
            println("No match found")
        }
        return matcher
    }
}