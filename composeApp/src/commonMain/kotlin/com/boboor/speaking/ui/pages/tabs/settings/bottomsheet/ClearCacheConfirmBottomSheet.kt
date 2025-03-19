package com.boboor.speaking.ui.pages.tabs.settings.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/*
    Created by Boburjon Murodov 19/03/25 at 11:10
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClearCacheConfirmBottomSheet(
    onClickClear: () -> Unit,
    onDismiss: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch {
                bottomSheetState.hide()
                delay(300)
                onDismiss()
            }
        },
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Clear Cache?",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "This will restart the app. Are you sure?",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        delay(300)
                        onClickClear()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Yes, Clear Cache", color = MaterialTheme.colorScheme.error)
            }

            TextButton(
                onClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        delay(300)
                        onDismiss()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }

}