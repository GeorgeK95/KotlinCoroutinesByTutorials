package broadcastChannels

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")
    val kotlinChannel = Channel<String>()

    runBlocking {
        GlobalScope.launch { kotlinChannel.send(fruitArray[0]) }

        GlobalScope.launch {
            kotlinChannel.consumeEach { value -> println("Consumer 1: $value") }
        }
        GlobalScope.launch {
            kotlinChannel.consumeEach { value -> println("Consumer 2: $value") }
        }

        println("Press a key to exit...")
        readLine()

        kotlinChannel.close()
    }
}