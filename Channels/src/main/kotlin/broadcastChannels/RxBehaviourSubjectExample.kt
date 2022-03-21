package broadcastChannels

import io.reactivex.subjects.BehaviorSubject


fun main() {
    val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")
    val subject = BehaviorSubject.create<String>()

    subject.apply {
        onNext(fruitArray[0])
        onNext(fruitArray[1])
        onNext(fruitArray[2])
    }

    subject.subscribe { println("Consumer 1: $it") }

    subject.subscribe { println("Consumer 2: $it") }

    subject.apply {
        onNext(fruitArray[3])
        onNext(fruitArray[4])
    }

    println("Press a key to exit...")
    readLine()

    subject.onComplete()
}