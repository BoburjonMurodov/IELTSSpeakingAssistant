package com.boboor.speaking.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.boboor.speaking.getScreenWidth
import com.boboor.speaking.presenter.main.MainScreenContracts
import com.boboor.speaking.utils.Section
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_ielts_envelope
import ieltsspeakingassistant.composeapp.generated.resources.ic_part_one
import ieltsspeakingassistant.composeapp.generated.resources.ic_part_three
import ieltsspeakingassistant.composeapp.generated.resources.ic_part_two
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


/*
    Created by Boburjon Murodov 19/12/24 at 22:43
*/

class MainScreen : Screen {
    override val key: ScreenKey get() = this.hashCode().toString()

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<MainScreenContracts.ViewModel>()

        val state = viewModel.container.stateFlow.collectAsState()


        MainScreenContent(state, viewModel::onEventDispatcher)
    }
}

fun Color.darken(factor: Float = 0.8f): Color {
    return Color(
        red = (red * factor).coerceIn(0f, 1f),
        green = (green * factor).coerceIn(0f, 1f),
        blue = (blue * factor).coerceIn(0f, 1f),
        alpha = alpha
    )
}

@Composable
private fun MainScreenContent(
    state: State<MainScreenContracts.UIState>,
    onEventDispatcher: (MainScreenContracts.Intent) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                        .shadow(
                            elevation = 40.dp, shape = RoundedCornerShape(24.dp),
                            ambientColor = Color(0xffc61531),
                            spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                        )
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { showToast("Coming Soon...") }
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
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            color = Color(0xffb5142d)
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
                .fillMaxWidth()
                .padding(horizontal = 24.dp),

            horizontalAlignment = Alignment.Start,
        ) {

            Spacer(Modifier.weight(1f))

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

            Spacer(Modifier.weight(5f))
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
            .appShadow(alpha = 0.5f)
            .width(if (takeFull) screenWidth + 24.dp else screenWidth / 2)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick.invoke() }
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
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun Modifier.appShadow(alpha: Float = 0.4f): Modifier {
    return shadow(
        elevation = 40.dp, shape = RoundedCornerShape(24.dp),
        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
    )
}

