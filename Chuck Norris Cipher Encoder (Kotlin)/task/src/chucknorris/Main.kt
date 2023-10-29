package chucknorris

fun main() {
    println("Input string:")
    val input = readln()
    println()
    println("The result:")
    println(binStrToString(chuckStrToBinStr(input)))
}

fun binStrToChuckStr(binStr: String): String {
    return binStr.split(Regex("(?<=(.))(?!\\1)"))
        .filter { it.isNotBlank() }.joinToString(" ") {
            if (it.first() == '1') "0 ${"0".repeat(it.length)}"
            else "00 ${"0".repeat(it.length)}"
        }
}

fun chuckStrToBinStr(chuckStr: String): String {
    return "0{1,2}\\s0+".toRegex().findAll(chuckStr)
        .map { it.value }
        .map { val pair = it.split(" "); pair.first() to pair.last() }
        .map {
            if (it.first.length == 1) "1".repeat(it.second.length)
            else "0".repeat(it.second.length)
        }.joinToString("")
}


fun padBinStr(binStr: String) = "0000000$binStr".substring(binStr.length)

fun binStrToString(binStr: String) = binStr.chunked(7).map { it.toInt(2).toChar() }.joinToString("")

fun stringToBinStr(input: String) = input.map { padBinStr(Integer.toBinaryString(it.code)) }.joinToString("")
