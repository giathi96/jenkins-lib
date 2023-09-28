def call() {
    script {
        def input = "AhelloA"
        def pattern = /A(.*?)A/
        def matcher = (input =~ pattern)

        if (matcher.find()) {
            def result = matcher.group(1)
            println(result)
            return result
        } else {
            println("No match found")
            return "KHONG CO"
        }



        return matcher
    }
}