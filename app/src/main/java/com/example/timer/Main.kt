package com.example.timer

import kotlinx.coroutines.delay

suspend fun main(){
    for(i in 0..5){
        delay(400L)
        println(i)
    }

    println("Hello Coroutines")
}