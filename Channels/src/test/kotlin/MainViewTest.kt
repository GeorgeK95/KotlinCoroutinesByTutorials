import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import testing.CoroutineContextProviderImpl
import testing.MainPresenter
import testing.MainView

class MainViewTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
    private val contextProvider by lazy { CoroutineContextProviderImpl(testCoroutineDispatcher) }

    private val mainPresenter by lazy { MainPresenter() }
    private val mainView by lazy {
        MainView(
            mainPresenter,
            contextProvider,
            testCoroutineScope
        )
    }

    @Test
    fun testFetchUserData() = testCoroutineScope.runBlockingTest {
        // initial state
        assertNull(mainView.userData)

        // updating the state
        mainView.fetchUserData()

        advanceTimeBy(1_000)

        // checking the new state, and printing it out
        assertEquals("Joe", mainView.userData?.name)
        mainView.printUserData()
    }
}
