package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import fr.uge.ugeoverflow.filters.QuestionFilterType


@Composable
fun DropdownMenuFilter(onOptionSelected: (String) -> Unit) {
    val options = listOf(QuestionFilterType.ALL.name,QuestionFilterType.NEWEST.name,
        QuestionFilterType.OLDEST.name,QuestionFilterType.UNANSWERED.name,QuestionFilterType.ANSWERED.name)
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        TextButton(onClick = { expanded = true }) {
            Text(selectedOption)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    selectedOption = option
                    expanded = false
                    onOptionSelected(option)
                }) {
                    Text(option)
                }
            }
        }
    }
}