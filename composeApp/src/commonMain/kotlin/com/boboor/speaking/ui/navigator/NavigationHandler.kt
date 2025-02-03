

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.boboor.speaking.ui.navigator.TransitionType
import kotlinx.coroutines.flow.SharedFlow


typealias NavigationArgs =  Navigator.() -> Unit

interface NavigationHandler {
    val screenState: SharedFlow<NavigationArgs>
}