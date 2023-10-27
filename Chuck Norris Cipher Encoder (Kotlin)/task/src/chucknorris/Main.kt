package chucknorris

fun main() {
    println("Input string:")
    val input = readln()

    val encoded = input.map { it to Integer.toBinaryString(it.code) }.toList()
    println()
    println("The result:")
    encoded.forEach { println("${it.first} = ${leadBinString(it.second)}") }
}

fun leadBinString(binStr: String) = "0000000$binStr".substring(binStr.length)