package chucknorris

import java.util.InputMismatchException

fun main() {
    while (true) {
        println()
        println("Please input operation (encode/decode/exit):")
        when(val choice = readln()) {
           "encode" -> {
               println("Input string:")
               val input = readln()
               println("Encoded string:")
               println(binStrToChuckStr(stringToBinStr(input)))
           }
           "decode" -> {
               println("Input encoded string:")
               val input = readln()
               try {
                   val binaryResult = binStrToString(chuckStrToBinStr(input))
                   println("Decoded string:")
                   println(binaryResult)
               } catch(ie: InputMismatchException) {
                   println("Encoded string is not valid.")
               }
           }
           "exit" -> {
               println("Bye!")
               return
           }
           else -> println("There is no '$choice' operation")
        }
    }
}

fun binStrToChuckStr(binStr: String): String {
    return binStr.split(Regex("(?<=(.))(?!\\1)"))
        .filter { it.isNotBlank() }.joinToString(" ") {
            if (it.first() == '1') "0 ${"0".repeat(it.length)}"
            else "00 ${"0".repeat(it.length)}"
        }
}

fun chuckStrToBinStr(chuckStr: String): String {
    val trimmedChuckStr = chuckStr.trim()
    if (trimmedChuckStr.contains("[^0\\s]".toRegex()))
        throw InputMismatchException("input contains characters other than 0 or space")

    val sequences = chuckStrToPairs(trimmedChuckStr)

    return sequencesToBinary(sequences)
}

private fun chuckStrToPairs(chuckStr: String): Sequence<Pair<String, String>> {
    val sequences = "0+\\s0+".toRegex().findAll(chuckStr).map { it.value }
    if (sequences.joinToString(" ") != chuckStr) throw InputMismatchException("odd pairing")
    val blocks = sequences.map { val pair = it.split(" "); pair.first() to pair.last() }

    blocks.forEach {
        if (!(it.first == "0" || it.first == "00"))
            throw InputMismatchException("each sequence must start with a 0 or 00")
    }

    return blocks
}

private fun sequencesToBinary(sequences: Sequence<Pair<String, String>>): String {
    val binary = sequences.map {
        if (it.first.length == 1) "1".repeat(it.second.length)
        else "0".repeat(it.second.length)
    }.joinToString("")

    if (binary.length % 7 != 0) throw InputMismatchException("binary string must be a multiple of 7")

    return binary
}


fun padBinStr(binStr: String) = "0000000$binStr".substring(binStr.length)

fun binStrToString(binStr: String) = binStr.chunked(7).map { it.toInt(2).toChar() }.joinToString("")

fun stringToBinStr(input: String) = input.map { padBinStr(Integer.toBinaryString(it.code)) }.joinToString("")
