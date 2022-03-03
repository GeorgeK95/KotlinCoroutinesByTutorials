import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class CustomScope : CoroutineScope {

  private val parentJob = Job()

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + parentJob

  fun onStart() {
    parentJob.start()
  }

  fun onStop() {
    parentJob.cancel()
  }

}