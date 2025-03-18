package com.boboor.speaking.ui.pages.screens.cueCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.boboor.speaking.data.remote.models.PartTwoResponse
import com.boboor.speaking.ui.components.AppBar
import com.boboor.speaking.ui.components.ScalableHorizontalPager


/*
    Created by Boburjon Murodov 17/03/25 at 21:52
*/

class CueCardScreen(
    private val questions: List<PartTwoResponse.PartTwoQuestion>,
    private val index: Int
) : Screen {

    @Composable
    override fun Content() {
        val pagerState = rememberPagerState(pageCount = { questions.size }, initialPage = index.coerceIn(0, questions.size - 1))

        ScalableHorizontalPager(
            modifier = Modifier,
            pagerState = pagerState
        ) { page ->
            CueCardScreenContent(questions[page])
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CueCardScreenContent(question: PartTwoResponse.PartTwoQuestion) {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                AppBar(
                    title = "Part 2",
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow),
                    onClickBack = { navigator.pop() },
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = innerPadding.calculateTopPadding() + 16.dp)
            ) {

            }
        }
    }

}