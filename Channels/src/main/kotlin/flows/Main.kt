package flows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun main() {
    val flowOfStrings = flow {
        emit("")

        for (number in 0..100) {
            emit("Emitting: $number")
        }
    }

    GlobalScope.launch {
        flowOfStrings
            .map { it.split(" ") }
            .map { it[1] }
            .catch {
                it.printStackTrace()
                // send the fallback value or values
                emit("Fallback")
            }
            .flowOn(Dispatchers.Default)
            .collect { println(it) }

        println("The code still works!")
    }

    /*GlobalScope.launch {
        flowOfStrings
            .map { it.split(" ") }
            .map { it[1] }
            .catch { it.printStackTrace() }
            .flowOn(Dispatchers.Default)
            .collect { println(it) }
        println("The code still works!")
    }*/

    Thread.sleep(3_000)

    /*GlobalScope.launch {
        flowOfStrings.collect {
            println(it)
        }
    }

    Thread.sleep(3_000)*/

    /*GlobalScope.launch {
        flowOfStrings
            .map { it.split(" ") }
            .map { it.last() }
            .flowOn(Dispatchers.IO)
            .delayEach(100)
            .flowOn(Dispatchers.Default)
            .collect { value ->
                println(value)
            }
    }

    Thread.sleep(3_000)*/

    /*GlobalScope.launch {
        flowOfStrings
            .map { it.split(" ") }
            .map { it.last() }
            .onEach {
                delay(100)
            }
            .collect { value ->
                println(value)
            }
    }
    Thread.sleep(3_000)*/
}