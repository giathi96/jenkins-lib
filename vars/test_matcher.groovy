def call() {
    script {
        def input = "AhelloA"
        def pattern = /A(.*?)A/
        def matcher = (input =~ pattern)

        if (matcher.find()) {
            def result = matcher.group(1)
            return result
            println(result)
        } else {
            println("No match found")
            return "KHONG CO"
        }

        return "XONG"
    }
}