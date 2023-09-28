def inputString = """lsda"sdas"?;dsda ddsa dsa'd ssads'dda "192.168.0.xxx"sdasdsadsa sdasdasd sdasdsad"""

// Split the input string into words
def words = inputString.split()

// Iterate through the words and check if they match the IP address format
def ipAddressList = []
for (word in words) {
    // Remove any non-alphanumeric characters to ensure an accurate match
    def cleanedWord = word.replaceAll("[^\\d.]", "")
    
    // Check if the cleaned word matches the desired format
    if (cleanedWord.startsWith("192.168.") && cleanedWord.length() >= 11) {
        ipAddressList.add(cleanedWord)
    }
}

// Print the found IP addresses
if (ipAddressList.isEmpty()) {
    println("No IP addresses in the specified format found.")
} else {
    println("Found IP addresses:")
    ipAddressList.each { println(it) }
}