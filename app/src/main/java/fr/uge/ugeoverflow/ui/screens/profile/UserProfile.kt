package fr.uge.ugeoverflow.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.uge.ugeoverflow.api.UserProfileDTO
import fr.uge.ugeoverflow.services.ProfileService
import fr.uge.ugeoverflow.ui.components.ComponentSize
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyTag
import fr.uge.ugeoverflow.ui.routes.Routes

//@Composable
//fun UserProfilePage(userProfileDTO: UserProfileDTO,navController: NavController) {
//    Scaffold(
//        floatingActionButton = {
//            if (isMe) {
//                FloatingActionButton(
//                    onClick = {
//                        showEditProfile.value = true
//                    },
//                    backgroundColor = ComponentTypes.Secondary.color,
//                    contentColor = ComponentTypes.Secondary.contentColor,
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .size(56.dp),
//                    shape = CircleShape
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Edit,
//                        contentDescription = null
//                    )
//                }
//            } else {
//                if (!isFollowing) {
//                    FloatingActionButton(
//                        onClick = {
//                            ProfileService.followUser(
//                                username = user.username,
//                                onSuccess = { user ->
//                                    isFollowing = true
//                                    userProfile.value = user
//                                },
//                                onError = {
//                                    isFollowing = false
//                                }
//                            )
//                        },
//                        backgroundColor = ComponentTypes.Success.color,
//                        contentColor = ComponentTypes.Success.contentColor,
//                        modifier = Modifier
//                            .padding(8.dp),
////                        .size(80.dp, 40.dp),
//                        shape = RoundedCornerShape(20)
//                    ) {
//                        Text(
//                            text = "Follow",
//                            modifier = Modifier
//                                .padding(4.dp, 12.dp)
//                                .padding(12.dp, 4.dp)
//                        )
//                    }
//
//                } else {
//                    FloatingActionButton(
//                        onClick = {
//                            ProfileService.unfollowUser(
//                                username = user.username,
//                                onSuccess = { user ->
//                                    isFollowing = false
//                                    userProfile.value = user
//                                },
//                                onError = {
//                                    isFollowing = true
//                                }
//                            )
//                        },
//                        backgroundColor = ComponentTypes.Danger.color,
//                        contentColor = ComponentTypes.Danger.contentColor,
//                        modifier = Modifier
//                            .padding(8.dp),
//                        shape = RoundedCornerShape(20)
//                    ) {
//                        Text(
//                            text = "Unfollow",
//                            modifier = Modifier
//                                .padding(4.dp, 12.dp)
//                                .padding(12.dp, 4.dp)
//                        )
//                    }
//                }
//            }
//
//        },
//        floatingActionButtonPosition = FabPosition.Center,
//
//        content = {
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                contentPadding = PaddingValues(horizontal = 16.dp),
//            ) {
//                item {
//
//                    Column {
//                        Row(
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .fillMaxWidth()
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .padding(8.dp)
//                                    .fillMaxWidth(0.5f)
//                            ) {
//
//                                imageData.value?.let {
//                                    Image(
//                                        bitmap = it,
//                                        contentDescription = "Profile",
//                                        modifier = Modifier
//                                            .size(80.dp)
//                                            .clip(CircleShape)
//                                            .align(Alignment.Start),
//                                        contentScale = ContentScale.Crop
//                                    )
//                                }
//                                Text(
//                                    text = user.username,
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 24.sp,
//                                    modifier = Modifier
//                                        .padding(top = 8.dp)
//                                        .align(Alignment.Start)
//                                )
//                                user.bio?.let { it1 ->
//                                    Text(
//                                        text = it1,
//                                        modifier = Modifier.padding(top = 8.dp)
//                                    )
//                                }
//                                Text(
//                                    text = user.address.toString(),
//                                    modifier = Modifier.padding(top = 8.dp)
//                                )
//
//                            }
//                        }
//                        UserFollowReputationSection(
//                            user = userProfile,
//                            navController = navController,
//                            isMe = isMe,
//                            isFollowing = isFollowing
//                        )
//                    }
//                }
//
//
//                item {
//                    Text(
//                        text = "Tags",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                    LazyRow {
//                        items(tagList?.size ?: 0) { tag ->
//                            Column {
//                                MyTag(
//                                    text = tagList?.get(tag) ?: "",
//                                    componentSize = ComponentSize.Small,
//                                    componentType = ComponentTypes.Secondary
//                                )
//                            }
//                        }
//                    }
//                }
//                item {
//
//
//                    //Questions and Answers Tabs
//
//                    val tabs = listOf("Questions", "Answers")
//
//                    var selectedTabIndex by remember { mutableStateOf(0) }
//
//                    Column {
//                        // Create TabRow with tabs
//                        TabRow(
//                            selectedTabIndex = selectedTabIndex,
//                            backgroundColor = MaterialTheme.colors.background
//                        ) {
//                            tabs.forEachIndexed { index, text ->
//                                Tab(
//                                    selected = selectedTabIndex == index,
//                                    onClick = { selectedTabIndex = index }
//                                ) {
//                                    Text(
//                                        text = text,
//                                        modifier = Modifier.padding(
//                                            vertical = 8.dp,
//                                            horizontal = 16.dp
//                                        )
//                                    )
//                                }
//                            }
//                        }
//
//                        if (selectedTabIndex == 0) {
//                            user.questions?.forEach { question ->
//                                QuestionItem(
//                                    question = question,
//                                    onClick = {
//                                        navController.navigate(
//                                            "${Routes.OneQuestion.route}/${question.id}"
//                                        )
//                                    }
//
//                                )
//                            }
//                        } else {
//                            user.answers?.forEach { answer ->
//                                AnswerItem(
//                                    answer = answer,
////                                    onClick = {
////                                        navController.navigate(
////                                            "question/${answer.id}"
////                                        )
////                                    }
//                                )
//                            }
//                        }
//                    }
//
//
//                }
//            }
//
//        }
//    )
//}