package uz.gita.boburmobilebankingapp.ui.compontents

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp


/*
    Created by Boburjon Murodov 10/04/25 at 09:36
*/


@Composable
fun RowScope.HorizontalSpacer(width: Dp) = Spacer(Modifier.width(width))

@Composable
fun ColumnScope.VerticalSpacer(height: Dp) = Spacer(Modifier.height(height))

@Composable
fun RowScope.FillAvailableSpace(weight: Float = 1f) = Spacer(Modifier.weight(weight))

@Composable
fun ColumnScope.FillAvailableSpace(weight: Float = 1f) = Spacer(Modifier.weight(weight))