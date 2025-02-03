
import cafe.adriel.voyager.core.screen.Screen
import com.boboor.speaking.ui.navigator.TransitionType

typealias AppScreen = Screen

interface AppNavigator {
    suspend fun back()
    suspend fun push(screen: AppScreen, transition: TransitionType = TransitionType.NO_TRANSITION)
    suspend fun popUntil(screen: AppScreen, transition: TransitionType = TransitionType.NO_TRANSITION)
    suspend fun replace(screen: AppScreen, transition: TransitionType = TransitionType.NO_TRANSITION)
    suspend fun replaceAll(screen: AppScreen, transition: TransitionType = TransitionType.NO_TRANSITION)
    suspend fun backToRoot()
}