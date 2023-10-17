package chucknorris

fun main() {
    println("Input string:")
    val input = readln()

    val encoded = input.map { "$it" }.joinToString(" ")
    println()
    println(encoded)
}