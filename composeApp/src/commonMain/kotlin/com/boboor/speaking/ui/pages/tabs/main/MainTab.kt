package com.boboor.speaking.ui.pages.tabs.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.boboor.speaking.getScreenWidth
import com.boboor.speaking.ui.theme.AppTheme
import com.boboor.speaking.ui.theme.getColor
import com.boboor.speaking.ui.theme.setColor
import com.boboor.speaking.utils.OnExitBackPressHandler
import com.boboor.speaking.utils.Section
import com.boboor.speaking.utils.debounceClickable
import com.materialkolor.ktx.darken
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_ielts_envelope
import ieltsspeakingassistant.composeapp.generated.resources.ic_part_one
import ieltsspeakingassistant.composeapp.generated.resources.ic_part_three
import ieltsspeakingassistant.composeapp.generated.resources.ic_part_two
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


/*
    Created by Boburjon Murodov 19/12/24 at 22:43
*/

@OptIn(ExperimentalVoyagerApi::class)
object MainTab : Tab{

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<MainScreenContracts.ViewModel>()

        val state = viewModel.collectAsState()
        MainScreenContent(state, viewModel::onEventDispatcher)
    }

    override val options: TabOptions
        @Composable
        get() = TabOptions(0u, "Home", icon = rememberVectorPainter(Icons.Default.Home))

}



@Composable
private fun MainScreenContent(
    state: State<MainScreenContracts.UIState>,
    onEventDispatcher: (MainScreenContracts.Intent) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    OnExitBackPressHandler { snackBarHostState.showSnackbar("Click again to exit") }

    AppTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            snackbarHost = {
                SnackbarHost(
                    snackBarHostState,
                    snackbar = {
                        Box(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12))
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                .padding(horizontal = 16.dp, vertical = 12.dp), contentAlignment = Alignment.CenterStart
                        ) {

                            Text(
                                it.visuals.message,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(225.dp)
                            .clip(RoundedCornerShape(bottomEnd = 36.dp, bottomStart = 36.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.primary.darken(0.5f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier.align(Alignment.BottomCenter)
                            .padding(horizontal = 32.dp)
                    ) {
                        Text(
                            "IDP",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Row(
                            modifier = Modifier.padding(vertical = 12.dp)
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                .clickable {
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            "Coming soon...",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                                .padding(24.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(72.dp)
                                    .graphicsLayer(alpha = 0.99f)
                                    .drawWithCache {
                                        onDrawWithContent {
                                            drawContent()
                                            drawRect(
                                                Brush.linearGradient(colors = listOf(Color(0xff294081), Color(0xffbf63a7))),
                                                blendMode = BlendMode.SrcAtop
                                            )
                                        }
                                    }.align(Alignment.CenterVertically),

                                painter = painterResource(Res.drawable.ic_ielts_envelope),
                                contentDescription = null,
                            )

                            Column(
                                modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 8.dp),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = "Check IELTS result",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    text = "If you have taken IELTS, check your IELTS results",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                        }
                    }
                }

                Column(
                    modifier = Modifier.weight(1f)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.Start,
                ) {

//                Spacer(Modifier.weight(1f))

                    Text(
                        "Speaking",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        AppCard("Part 1", Res.drawable.ic_part_one) {
                            onEventDispatcher(MainScreenContracts.Intent.OnClickPart(section = Section.PART_ONE))
                        }
                        Spacer(Modifier.weight(1f))
                        AppCard("Part 2", Res.drawable.ic_part_two) {
                            onEventDispatcher(MainScreenContracts.Intent.OnClickPart(section = Section.PART_TWO))
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    AppCard("Part 3", Res.drawable.ic_part_three, true) {
                        onEventDispatcher(MainScreenContracts.Intent.OnClickPart(section = Section.PART_THREE))
                    }

                }


            }
        }

    }
}


@Composable
fun AppCard(
    title: String,
    image: DrawableResource,
    takeFull: Boolean = false,
    onClick: () -> Unit = {}
) {
    val screenWidth = getScreenWidth() - 72.dp


    Column(
        modifier = Modifier
            .width(if (takeFull) screenWidth + 24.dp else screenWidth / 2)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .debounceClickable { onClick.invoke() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(image),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}