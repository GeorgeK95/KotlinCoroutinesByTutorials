import kotlinx.coroutines.*

fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    val job = GlobalScope.launch(exceptionHandler) {
        throw AssertionError("My Custom Assertion Error!")
    }

    val deferred = GlobalScope.async(exceptionHandler) {
        // Nothing will be printed,
        // relying on user to call deferred.await()
        throw ArithmeticException()
    }

    // This suspends current coroutine until all given jobs are complete.
    joinAll(job, deferred)
}