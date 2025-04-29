package com.boboor.speaking.ui.pages.tabs.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.boboor.speaking.getScreenWidth
import com.boboor.speaking.ui.components.AppCard
import com.boboor.speaking.ui.components.BasicDuoLingoCard
import com.boboor.speaking.ui.components.DuoLingoCard
import com.boboor.speaking.ui.components.DuolingoLinearIndicator
import com.boboor.speaking.ui.theme.AppTheme
import com.boboor.speaking.ui.theme.DuolingoTheme
import com.boboor.speaking.ui.theme.duoGray100Color
import com.boboor.speaking.ui.theme.duoGray300Color
import com.boboor.speaking.ui.theme.duoWhiteColor
import com.boboor.speaking.utils.OnExitBackPressHandler
import com.boboor.speaking.utils.collectAsState
import com.boboor.speaking.utils.enums.Section
import com.materialkolor.ktx.darken
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_ielts_envelope
import ieltsspeakingassistant.composeapp.generated.resources.ic_part_one
import ieltsspeakingassistant.composeapp.generated.resources.ic_part_three
import ieltsspeakingassistant.composeapp.generated.resources.ic_part_two
import ieltsspeakingassistant.composeapp.generated.resources.img_junior
import ieltsspeakingassistant.composeapp.generated.resources.img_lily
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import uz.gita.boburmobilebankingapp.ui.compontents.FillAvailableSpace
import uz.gita.boburmobilebankingapp.ui.compontents.VerticalSpacer


/*
    Created by Boburjon Murodov 19/12/24 at 22:43
*/

object MainTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(0u, "Home", icon = rememberVectorPainter(Icons.Default.Home))


    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val snackBarHostState = remember { SnackbarHostState() }
        val viewModel = koinScreenModel<MainScreenContracts.ViewModel>()
        val state = viewModel.collectAsState()

        LifecycleEffectOnce { viewModel.init() }

        LaunchedEffect(Unit) {
            viewModel.container.sideEffectFlow.collect {
                when (it) {
                    is MainScreenContracts.SideEffect.Error -> snackBarHostState.showSnackbar(it.message)
                    is MainScreenContracts.SideEffect.Message -> snackBarHostState.showSnackbar(it.message)
                }
            }
        }

        ScreenContent()
    }

    @Composable
    private fun ScreenContent() {

        AppTheme {
            Scaffold(
                containerColor = DuolingoTheme.colors.background
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding() + 16.dp,
                        bottom = innerPadding.calculateBottomPadding() + 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    item { TitleText("Home") }
                    item { Banner() }
                    item { TitleText(text = "Speaking Parts") }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                HomeCard(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    image = Res.drawable.img_lily,
                                    title = "Card 1",
                                    progress = 0.5f
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                HomeCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    image = Res.drawable.img_junior,
                                    title = "Card 2",
                                    progress = 0.7f,
                                    backgroundColor = DuolingoTheme.colors.duoBlue,
                                    lineColor = DuolingoTheme.colors.duoBlueHover
                                )
                            }
                        }

                    }

                    item {
                        val screenWidth = getScreenWidth() - 32.dp

                        HomeCard(
                            image = Res.drawable.img_junior,
                            modifier = Modifier.width(screenWidth),
                            title = "Speaking\nPart 3",
                            progress = .6f,
                            backgroundColor = DuolingoTheme.colors.duoPurple,
                            lineColor = DuolingoTheme.colors.duoPurpleHover
                        )
                    }


                }
            }
        }
    }
}


@Composable
fun Feed(
    modifier: Modifier = Modifier,
    value: String,
    title: String,
    backgroundColor: Color = DuolingoTheme.colors.duoGreen
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .heightIn(max = 60.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(backgroundColor)
//                .padding(12.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = DuolingoTheme.typography.heading.copy(color = Color.White)
            )
        }

        Text(
            text = title,
            style = DuolingoTheme.typography.body.copy(color = DuolingoTheme.colors.textColor)
        )
    }
}

@Composable
private fun TitleText(
    text: String
) {
    Text(
        text = text,
        style = DuolingoTheme.typography.title.copy(color = DuolingoTheme.colors.textColor)
    )
}

@Composable
private fun Banner() {
    BasicDuoLingoCard(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = {},
    ) {
        Text(
            text = "Your Progress",
            style = DuolingoTheme.typography.subHeading.copy(color = DuolingoTheme.colors.textColor)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Questions practised",
                style = DuolingoTheme.typography.bodyLarge.copy(color = DuolingoTheme.colors.textColor)
            )

            Text(
                text = "30 / 120",
                style = DuolingoTheme.typography.bodyLarge.copy(color = DuolingoTheme.colors.textColor)
            )
        }

        DuolingoLinearIndicator(
            modifier = Modifier.fillMaxWidth()
                .height(8.dp),
            currentProgress = 30f / 120f,
            color = DuolingoTheme.colors.duoGreen,
            backgroundColor = duoGray300Color,
        )


        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Feed(value = "7", title = "Streak")
            FillAvailableSpace()
            Feed(
                value = "12",
                title = "Questions\npractised",
                backgroundColor = DuolingoTheme.colors.duoBlue
            )
            FillAvailableSpace()
            Feed(
                value = "8",
                title = "Mastered",
                backgroundColor = DuolingoTheme.colors.duoPurple
            )

        }
    }
}


@Composable
private fun HomeCard(
    image: DrawableResource,
    modifier: Modifier = Modifier,
    title: String,
    progress: Float,
    backgroundColor: Color = DuolingoTheme.colors.duoGreen,
    lineColor: Color = DuolingoTheme.colors.duoGreenHover
) {
    DuoLingoCard(
        modifier = modifier,
        lineHeight = 8.dp,
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = Color.White,
            disabledContainerColor = backgroundColor,
            disabledContentColor = Color.White
        ),
        lineColor = lineColor
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(80.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(image),
                contentDescription = null
            )

            Text(
                text = title,
                style = DuolingoTheme.typography.subHeading.copy(
                    textAlign = TextAlign.Center,
                    color = duoWhiteColor
                )
            )

            Text(
                text = "12 topics",
                style = DuolingoTheme.typography.body.copy(color = duoWhiteColor)
            )

            DuolingoLinearIndicator(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(8.dp),
                currentProgress = progress,
                color = Color.White,
                backgroundColor = Color.White.copy(alpha = 0.5f),
            )
        }
    }
}