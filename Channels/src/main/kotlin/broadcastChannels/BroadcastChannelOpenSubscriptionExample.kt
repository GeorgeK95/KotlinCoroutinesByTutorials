package broadcastChannels

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
fun main() {
    val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")
    val kotlinChannel = BroadcastChannel<String>(3)

    runBlocking {
        kotlinChannel.apply {
            send(fruitArray[0])
            send(fruitArray[1])
            send(fruitArray[2])
        }

        GlobalScope.launch {
            kotlinChannel.consumeEach { value ->
                println("Consumer 1: $value")
            }
        }
        GlobalScope.launch {
            kotlinChannel.consumeEach { value ->
                println("Consumer 2: $value")
            }
        }

        kotlinChannel.apply {
            send(fruitArray[3])
            send(fruitArray[4])
        }

        println("Press a key to exit...")
        readLine()

        kotlinChannel.close()
    }
}