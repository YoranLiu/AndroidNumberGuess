package com.example.numberguess

import java.util.*

class SecretNumber {
    var secret = Random().nextInt(10) + 1
    var count = 0

    fun check_num(num: Int): Int {
        count++
        return num - secret
    }

    fun reset() {
        secret = Random().nextInt(10) + 1
        count = 0
    }
}

fun main() {
    val secretNumber = SecretNumber()
    var given_num = 5

    println(secretNumber.secret)
    println("Difference between secret and given number: ${secretNumber.check_num(given_num)}, " +
            "Count: ${secretNumber.count}" )
}