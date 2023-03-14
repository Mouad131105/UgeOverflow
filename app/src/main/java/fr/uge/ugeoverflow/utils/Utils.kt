package fr.uge.ugeoverflow.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.uge.ugeoverflow.ui.components.ComponentSize
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyTag



@Composable
fun SearchableMultiSelect(
    options: List<String>,
    onSelectionChanged: (List<String>) -> Unit,
) {
    var searchText by remember { mutableStateOf("") }
    var selectedOptions by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(false) }

    val filteredOptions = options.filter {
        it.contains(searchText, ignoreCase = true)
    }

    Column {
        // Selected options as tags
        if (selectedOptions.isNotEmpty()) {
            Text(
                text = "Selected Tags:",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )



            Row(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(4.dp)
                ).fillMaxWidth(),
//                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//                contentPadding = PaddingValues(horizontal = 8.dp),
//                verticalArrangement = Arrangement.SpaceBetween,

            ) {
//                    items(selectedOptions) { option ->
//                        MyTag(text = option,
//                            componentSize = ComponentSize.Small,
//                            componentType = ComponentTypes.PrimaryOutline,
//                            onDismiss = {
//                                selectedOptions -= option
//                                onSelectionChanged(selectedOptions.toList())
//                            }
//                        )
//                    }

                selectedOptions.forEach { option ->
                    MyTag(text = option,
                        componentSize = ComponentSize.Small,
                        componentType = ComponentTypes.PrimaryOutline,
                        onDismiss = {
                            selectedOptions -= option
                            onSelectionChanged(selectedOptions.toList())
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                    )
//                    Tag(
//                        text = option,
//                        onDismiss = {
//                            selectedOptions -= option
//                            onSelectionChanged(selectedOptions.toList())
//                        }
//                    )
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 8.dp),
                label = { Text("Search Tags") }
            )

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Expand options"
                )
            }
        }

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredOptions.forEach { option ->
                DropdownMenuItem(onClick = {
                    selectedOptions += option
                    expanded = false
                    onSelectionChanged(selectedOptions.toList())
                }) {
                    Checkbox(
                        checked = selectedOptions.contains(option),
                        onCheckedChange = null
                    )

                    Text(
                        text = option,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Tag(text: String, onDismiss: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(50),
        elevation = 4.dp,
        color = MaterialTheme.colors.primary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary
            )

            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.clickable { onDismiss() }
            )
        }
    }
}