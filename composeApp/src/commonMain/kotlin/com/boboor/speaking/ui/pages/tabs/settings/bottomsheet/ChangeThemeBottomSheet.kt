package com.boboor.speaking.ui.pages.tabs.settings.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.boboor.speaking.getScreenWidth
import com.boboor.speaking.ui.pages.tabs.settings.gradient
import com.boboor.speaking.ui.pages.tabs.settings.seedColors
import com.boboor.speaking.ui.theme.getColor
import kotlinx.coroutines.launch
import kotlin.math.abs


/*
    Created by Boburjon Murodov 12/03/25 at 14:25
*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeThemeBottomSheet(
    onClick: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismiss) {
        val pagerState = rememberPagerState(
            pageCount = { seedColors.size },
            initialPage = seedColors.indexOf(getColor())
        )

        val screenWidth = getScreenWidth()
        val itemSize = screenWidth / 5 - 6.dp
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = Modifier.navigationBarsPadding()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Choose your color palette",
                style = MaterialTheme.typography.bodySmall
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentPadding = PaddingValues(horizontal = itemSize * 2 + 6.dp),
            ) { page ->
                val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                val scale = 1f - (0.2f * abs(pageOffset))
                val alpha = 1f - (0.2f * abs(pageOffset))

                Box(
                    modifier = Modifier
                        .size(itemSize)
                        .scale(scale)
                        .alpha(alpha)
                        .clip(RoundedCornerShape(12.dp))
                        .background(seedColors[page].gradient())
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page)
                            }
                        }
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    onClick(seedColors[pagerState.currentPage])
                }) {
                    Text("Save")
                }
            }


        }

    }

}
