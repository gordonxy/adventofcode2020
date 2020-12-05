import java.lang.NumberFormatException

fun main() {
    val validPassportsPartOne = countValidPassports(inputString)
    println(validPassportsPartOne)

    val eclConstraint = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val constraints = mapOf<String, (value : String) -> Boolean>(
            "byr" to { key ->
                try {
                    key.toInt() in 1920..2002
                } catch (e: NumberFormatException) {
                    false
                }
            },
            "iyr" to { key ->
                try {
                    key.toInt() in 2010..2020
                } catch (e: NumberFormatException) {
                    false
                }
            },
            "eyr" to { key ->
                try {
                    key.toInt() in 2020..2030
                } catch (e: NumberFormatException) {
                    false
                }
            },
            "hgt" to { key ->
                try {
                    val unitIndex = key.length - 2
                    val digit = key.substring(0, unitIndex).toInt()
                    when (key.substring(unitIndex)) {
                        "cm" -> digit in 150..193
                        "in" -> digit in 59..76
                        else -> false
                    }
                } catch (e: NumberFormatException) {
                    false
                }
            },
            "hcl" to { key ->
                Regex("""#[0-9a-f]{6}""") matches key
            },
            "ecl" to { key ->
                key in eclConstraint
            },
            "pid" to { key ->
                Regex("""[0-9]{9}""") matches key
            },
            "cid" to { true },
    )
    val validPassportsPartTwo = countValidPassportsWithConstraints(inputString, constraints)
    println(validPassportsPartTwo)
}

fun countValidPassports(input : String) : Int {
    var validPassportCount = 0

    val mustHaveKeys = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    var currentPassportStringAcc = ""
    input.lines().forEach { line ->
        if(line.isEmpty()) {
            if(currentPassportStringAcc.split(" ", ":").filterIndexed { index, _ -> index%2 == 0 }.containsAll(mustHaveKeys))
                validPassportCount++

            currentPassportStringAcc = ""
        } else {
            if(currentPassportStringAcc.isNotEmpty()) currentPassportStringAcc += " "
            currentPassportStringAcc += line
        }
    }

    return validPassportCount
}

fun countValidPassportsWithConstraints(input : String, constraints : Map<String, (value : String) -> Boolean>) : Int {
    var validPassportCount = 0

    val mustHaveKeys = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    var currentPassportStringAcc = ""
    input.lines().forEach { line ->
        if(line.isEmpty()) {
            if(currentPassportStringAcc.split(" ", ":")
                            .chunked(2).map { (key, value) -> constraints[key]?.invoke(value) == true  }.reduce { acc, res -> acc && res}
                    && currentPassportStringAcc.split(" ", ":").containsAll(mustHaveKeys))
                validPassportCount++

            currentPassportStringAcc = ""
        } else {
            if(currentPassportStringAcc.isNotEmpty()) currentPassportStringAcc += " "
            currentPassportStringAcc += line
        }
    }

    return validPassportCount
}

