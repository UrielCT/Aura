package com.aura.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource

@Composable
fun <T>AuraDropdownMenu(
    items: List<T> = emptyList(),
    labelRes:Int,
    onSelect:(T) -> Unit
){
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var selectedItem by remember { mutableStateOf<T?>(null) }
    var isExpanded by remember { mutableStateOf(false) }
    Box{
        OutlinedButton(
            onClick = {
                focusManager.clearFocus(force = true)
                keyboardController?.hide()
                isExpanded = !isExpanded
            }
        ) {
            Text(text = selectedItem?.toString() ?: stringResource(labelRes))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false}) {
            items.forEach{
                DropdownMenuItem(
                    onClick = {
                        isExpanded =false
                        selectedItem = it
                        onSelect(it)
                    },
                    text = { Text( text = it.toString() ) }
                )
            }
        }
    }
}