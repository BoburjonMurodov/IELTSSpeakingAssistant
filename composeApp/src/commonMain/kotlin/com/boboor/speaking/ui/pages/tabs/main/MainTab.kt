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

//        MainScreenContent(
//            snackBarHostState = snackBarHostState,
//            state = state,
//            onEventDispatcher = viewModel::onEventDispatcher
//        )

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
                    item {
                        Text(
                            text = "Home",
                            style = DuolingoTheme.typography.title.copy(color = DuolingoTheme.colors.textColor)
                        )
                    }

                    item {

                        BasicDuoLingoCard(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
//                                .height(150.dp)
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

//                            LinearProgressIndicator(
//                                strokeCap = StrokeCap.Round,
//                                progress = { 30 / 120f },
//                                modifier = Modifier.fillMaxWidth()
//                                    .height(8.dp),
//                                color = DuolingoTheme.colors.duoGreen,
//                                trackColor = duoGray100Color,
//                                gapSize = 0.dp
//                            )

                            DuolingoLinearIndicator(
                                modifier = Modifier.fillMaxWidth()
                                    .height(8.dp),
                                currentProgress = 30f / 120f,
                                color = DuolingoTheme.colors.duoGreen,
                                backgroundColor = duoGray300Color,
                            )


                            Row(modifier = Modifier.fillMaxWidth()) {
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

                        Spacer(Modifier.height(16.dp))

//                        PrimaryButton(
//                            modifier = Modifier.fillMaxWidth(),
//                            onClick = {},
//                            text = "Start practicing"
//                        )

                    }

                    item {
                        Text(
                            text = "Speaking Parts",
                            style = DuolingoTheme.typography.heading
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val screenWidth = getScreenWidth() - 32.dp

                            HomeCard(
                                image = Res.drawable.img_lily,
                                modifier = Modifier.width(screenWidth / 2 - 8.dp),
                                title = "Speaking\nPart 1",
                                progress = .3f
                            )
                            HomeCard(
                                image = Res.drawable.img_junior,
                                modifier = Modifier.width(screenWidth / 2 - 8.dp),
                                title = "Speaking\nPart 2",
                                progress = .6f,
                                backgroundColor = DuolingoTheme.colors.duoBlue,
                                lineColor = DuolingoTheme.colors.duoBlueHover
                            )
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
private fun MainScreenContent(
    snackBarHostState: SnackbarHostState,
    state: State<MainScreenContracts.UIState>,
    onEventDispatcher: (MainScreenContracts.Intent) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    OnExitBackPressHandler { snackBarHostState.showSnackbar("Click again to exit") }

    AppTheme {
        Scaffold(
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
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            contentAlignment = Alignment.CenterStart
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
                                                Brush.linearGradient(
                                                    colors = listOf(
                                                        Color(
                                                            0xff294081
                                                        ), Color(0xffbf63a7)
                                                    )
                                                ),
                                                blendMode = BlendMode.SrcAtop
                                            )
                                        }
                                    }.align(Alignment.CenterVertically),

                                painter = painterResource(Res.drawable.ic_ielts_envelope),
                                contentDescription = null,
                            )

                            Column(
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                ),
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
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .widthIn(max = 60.dp)
                    .aspectRatio(1f)
//                    .background(Color.White)
                ,
                contentScale = ContentScale.Crop,
                painter = painterResource(image),
                contentDescription = null
            )

            Text(
                text = title,
                style = DuolingoTheme.typography.subHeading.copy(textAlign = TextAlign.Center)
            )

            Text(
                text = "12 topics",
                style = DuolingoTheme.typography.body.copy(color = duoGray100Color)
            )

            DuolingoLinearIndicator(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .height(8.dp),
                currentProgress = progress,
                color = Color.White,
                backgroundColor = Color.White.copy(alpha = 0.5f),
            )

        }
    }
}
