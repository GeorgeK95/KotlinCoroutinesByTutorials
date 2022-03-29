package testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainView(
    private val presenter: MainPresenter,
    private val contextProvider: CoroutineContextProvider,
    private val scope: CoroutineScope
) {

    var userData: User? = null

    fun fetchUserData() {
        scope.launch(contextProvider.context()) {
            userData = presenter.getUser("101")
        }
    }

    fun printUserData() {
        println(userData)
    }
}