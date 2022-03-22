package produsersAndActors

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
fun main() {
    val numbers = Array<String>(200) { "$it" }
    var index = 0

    val producer = GlobalScope.produce(capacity = 10) {
        while (isActive) {
            if (!isClosedForSend) {
//                val number = Random.nextInt(0, 20)
                val current = numbers[++index]
                send(current)
                println("$current sent")
                /*if (offer(current)) {
                    println("$current sent")
                } else {
                    println("$current discarded")
                }*/
            }
        }
    }

    runBlocking {
        producer.consumeEach { number ->
            println("$number received")
        }
    }

//    Thread.sleep(30L)
    /*while (!producer.isClosedForReceive) {
        val number = producer.poll()

        if (number != null) {
            println("$number received")
        }
    }*/


}