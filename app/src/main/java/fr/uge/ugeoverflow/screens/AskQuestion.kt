package fr.uge.ugeoverflow.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*;
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import fr.uge.ugeoverflow.api.QuestionRequest
import fr.uge.ugeoverflow.api.UserSession
import fr.uge.ugeoverflow.model.Tag
import fr.uge.ugeoverflow.services.UgeOverflowApiService
import kotlinx.coroutines.launch



