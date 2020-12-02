data class PasswordConstraint(
        val start : Int,
        val end : Int,
        val character : Char,
)

fun main() {
    val listOfPasswordConstraintAndPasswords = inputString.lines().map {
        parseLine(it)
    }

    val validCount = listOfPasswordConstraintAndPasswords.count { validatePasswordByCount(it.first, it.second) }
    println("$validCount of the passwords are valid by count of password constraint character")

    val validCount2 = listOfPasswordConstraintAndPasswords.count { validatePasswordByPosition(it.first, it.second) }
    println("$validCount2 of the passwords are valid by position of password constraint character")
}

fun validatePasswordByCount(pwConstraint: PasswordConstraint, password: String) : Boolean {
    val charCount = password.count { it == pwConstraint.character}
    return charCount >= pwConstraint.start && charCount <= pwConstraint.end
}

fun validatePasswordByPosition(pwConstraint: PasswordConstraint, password: String) : Boolean {
    var xor = 0
    if( password[pwConstraint.start] == pwConstraint.character ) xor++
    if( password[pwConstraint.end] == pwConstraint.character ) xor++
    return xor == 1
}

fun parseLine(line : String) : Pair<PasswordConstraint, String> {
    val splitLineOnColon = line.split(':')
    val splitPasswordConstraint = splitLineOnColon[0].split(' ')
    val splitCharMinMaxCount = splitPasswordConstraint[0].split('-')
    return PasswordConstraint(splitCharMinMaxCount[0].toInt(), splitCharMinMaxCount[1].toInt(), splitPasswordConstraint[1].first()) to splitLineOnColon[1]
}