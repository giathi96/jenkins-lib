def call() {
    script {
        def inputString = "shdajs dbs <sofware>hello</sofware> sdja sdhbshd"

        // Find the starting and ending indices of the <sofware> and </sofware> tags
        def startIndex = inputString.indexOf("<sofware>")
        def endIndex = inputString.indexOf("</sofware>")

        // Check if both tags are present in the input string
        if (startIndex >= 0 && endIndex >= 0) {
            // Extract the value between the tags
            def extractedValue = inputString.substring(startIndex + "<sofware>".length(), endIndex)
            println(extractedValue)
        } else {
            println("Tags not found in the input string.")
        }
    }
}