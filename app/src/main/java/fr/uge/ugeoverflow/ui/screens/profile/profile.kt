package fr.uge.ugeoverflow.ui.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.services.ProfileService
import fr.uge.ugeoverflow.ui.components.ComponentSize
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.Loading.LoadingScreen
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.components.MyError.ErrorScreen
import fr.uge.ugeoverflow.ui.components.MyTag
import java.util.*


@SuppressLint("UnrememberedMutableState")
@Composable
fun UserProfileScreen(
    navController: NavController,
    username: String? = null
) {
    val userProfileState = remember { mutableStateOf<UserProfileDTO?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }

    if (isLoading.value) {
        LoadingScreen()
    } else {
        if (userProfileState.value != null)
            UserProfilePage(
                userProfile = mutableStateOf(userProfileState.value!!),
                navController = navController
            )
    }

    LaunchedEffect(Unit) {
        isLoading.value = true
        try {
            ProfileService.getProfile(
                username = username,
                onSuccess = { userProfile ->
                    userProfileState.value = userProfile
                    isLoading.value = false
                },
                onError = {
                    isLoading.value = false
                    isError.value = true
                }
            )
        } catch (e: Exception) {
            isLoading.value = false
            isError.value = true

        } finally {
            isLoading.value = false
        }
    }
    if (isError.value) {
        ErrorScreen(
            errorMessage = "Unknown error"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
//@Preview(showBackground = true)
@Composable
fun UserProfilePage(
    userProfile: MutableState<UserProfileDTO>,
//    = UserProfileDTO(
//        "firstname",
//        "lastname",
//        "username",
//        "email",
//        "bio ozeigmzoeing moinzegzneg mozieng mozeing moinoùezng ",
//        "street, city, country, zipCode",
//        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.fr%2Fpin%2F687201000000000000%",
//        6,
//        "98234034",
//        null,
//        null,
//        listOf(
//            QuestionResponse(
//                UUID.randomUUID(),
//                "title",
//                "content",
//                listOf("JAVA", "KOTLIN"),
//                UserBoxResponse(
//                    UUID.randomUUID(),
//                    "user",
//                    "email",
//                    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.fr%2Fpin%2F687201000000000000%",
//                ),
//                "2021-05-05T00:00:00.000+00:00",
//                MyLocation(1.0, 1.0)
//            ),
//            QuestionResponse(
//                UUID.randomUUID(),
//                "title",
//                "content",
//                listOf("JAVA", "KOTLIN"),
//                UserBoxResponse(
//                    UUID.randomUUID(),
//                    "user",
//                    "email",
//                    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.fr%2Fpin%2F687201000000000000%",
//                ),
//                "2021-05-05T00:00:00.000+00:00",
//                MyLocation(1.0, 1.0)
//            ), QuestionResponse(
//                UUID.randomUUID(),
//                "title",
//                "content",
//                listOf("JAVA", "KOTLIN"),
//                UserBoxResponse(
//                    UUID.randomUUID(),
//                    "user",
//                    "email",
//                    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.fr%2Fpin%2F687201000000000000%",
//                ),
//                "2021-05-05T00:00:00.000+00:00",
//                MyLocation(1.0, 1.0)
//            ),
//            QuestionResponse(
//                UUID.randomUUID(),
//                "title",
//                "content",
//                listOf("JAVA", "KOTLIN"),
//                UserBoxResponse(
//                    UUID.randomUUID(),
//                    "user",
//                    "email",
//                    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.fr%2Fpin%2F687201000000000000%",
//                ),
//                "2021-05-05T00:00:00.000+00:00",
//                MyLocation(1.0, 1.0)
//            )
//        ),
//        null,
//        mapOf(
//            Pair("JAVA", 1),
//            Pair("KOTLIN", 2),
//            Pair("C#", 3),
//            Pair("C++", 4),
//            Pair("C", 5),
//            Pair("PYTHON", 6),
//        )
//    ),
//    isMe: Boolean = false,
    navController: NavController
) {
    val user = userProfile.value ?: return

    val isMe = user.username == ProfileService.currentUsername

    var isFollowing by remember {
        mutableStateOf(
            user.followers?.map { follower -> follower.username }?.contains(
                ProfileService.currentUsername
            ) ?: false
        )
    }

    val tagList: List<String>? = user.tags?.map { tag -> tag.key }
    Scaffold(
        floatingActionButton = {
            if (isMe) {
                FloatingActionButton(
                    onClick = {

                    },
                    backgroundColor = ComponentTypes.Secondary.color,
                    contentColor = ComponentTypes.Secondary.contentColor,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(56.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                }
            } else {
                if (!isFollowing) {
                    FloatingActionButton(
                        onClick = {
                            ProfileService.followUser(
                                username = user.username,
                                onSuccess = { user ->
                                    isFollowing = true
                                    userProfile.value = user
                                },
                                onError = {
                                    isFollowing = false
                                }
                            )
                        },
                        backgroundColor = ComponentTypes.Success.color,
                        contentColor = ComponentTypes.Success.contentColor,
                        modifier = Modifier
                            .padding(8.dp),
//                        .size(80.dp, 40.dp),
                        shape = RoundedCornerShape(20)
                    ) {
                        Text(
                            text = "Follow",
                            modifier = Modifier
                                .padding(4.dp, 12.dp)
                                .padding(12.dp, 4.dp)
                        )
                    }

                } else {
                    FloatingActionButton(
                        onClick = {
                            ProfileService.unfollowUser(
                                username = user.username,
                                onSuccess = { user ->
                                    isFollowing = false
                                    userProfile.value = user
                                },
                                onError = {
                                    isFollowing = true
                                }
                            )
                        },
                        backgroundColor = ComponentTypes.Danger.color,
                        contentColor = ComponentTypes.Danger.contentColor,
                        modifier = Modifier
                            .padding(8.dp),
                        shape = RoundedCornerShape(20)
                    ) {
                        Text(
                            text = "Unfollow",
                            modifier = Modifier
                                .padding(4.dp, 12.dp)
                                .padding(12.dp, 4.dp)
                        )
                    }
                }
            }

        },
        floatingActionButtonPosition = FabPosition.Center,

        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                item {

                    Column {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(0.5f)
                            ) {

                                Image(
//                            painter = rememberImagePainter(user.profilePicture),
                                    painter = rememberImagePainter(R.drawable.user2),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .align(Alignment.Start)
                                )
                                Text(
                                    text = user.username,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .align(Alignment.Start)
                                )
                                user.bio?.let { it1 ->
                                    Text(
                                        text = it1,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                                Text(
                                    text = user.address.toString(),
                                    modifier = Modifier.padding(top = 8.dp)
                                )

                            }
                        }
                        UserFollowReputationSection(
                            user = userProfile,
                            navController = navController,
                            isMe = isMe,
                            isFollowing = isFollowing
                        )
                    }
                }


                item{
                    Text(
                        text = "Tags",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow{
                        items(tagList?.size ?: 0) { tag ->
                            Column{
                                MyTag(
                                    text = tagList?.get(tag) ?: "",
                                    componentSize = ComponentSize.Small,
                                    componentType = ComponentTypes.Secondary
                                )
                            }
                        }
                    }
                }
                item {


                    //Questions and Answers Tabs

                    val tabs = listOf("Questions", "Answers")

                    var selectedTabIndex by remember { mutableStateOf(0) }

                    Column {
                        // Create TabRow with tabs
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                            backgroundColor = MaterialTheme.colors.background
                        ) {
                            tabs.forEachIndexed { index, text ->
                                Tab(
                                    selected = selectedTabIndex == index,
                                    onClick = { selectedTabIndex = index }
                                ) {
                                    Text(
                                        text = text,
                                        modifier = Modifier.padding(
                                            vertical = 8.dp,
                                            horizontal = 16.dp
                                        )
                                    )
                                }
                            }
                        }

                        if (selectedTabIndex == 0) {
                            user.questions?.forEach { question ->
                                QuestionItem(question = question)
                            }
                        } else {
                            user.answers?.forEach { answer ->
                                AnswerItem(answer = answer)
                            }
                        }
                    }


                }
            }

        }
    )
}

@Composable
fun QuestionItem(question: QuestionResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .height(100.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = question.body,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Asked by ${question.user.username}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun AnswerItem(answer: AnswerDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = answer.body,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Answered by ${answer.user.username}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}


@Composable
fun UserFollowReputationSection(
    user: MutableState<UserProfileDTO>,
    isMe: Boolean = false,
    isFollowing: Boolean = false,
    navController: NavController
) {

    val showFollowerModel = remember { mutableStateOf(false) }
    val showFollowingModel = remember { mutableStateOf(false) }
    val showReputationModel = remember { mutableStateOf(false) }

    if (showFollowerModel.value) {
        FollowsModal(
            name = "Follower",
            follow = user.value.followers ?: listOf(),
            onClose = {
                showFollowerModel.value = false
            },
            navController = navController
        )
    }

    if (showFollowingModel.value) {
        FollowsModal(
            name = "Following",
            follow = user.value.followed ?: listOf(),
            onClose = {
                showFollowingModel.value = false
            },
            navController = navController
        )
    }

    if (showReputationModel.value) {
        ReputationModal(
            user,
            onDismiss = {
                showReputationModel.value = false
            }
        )
    }

    Divider(
        color = Color(0xFFDDDDDD),
        thickness = 1.dp,
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .padding(start = 16.dp, end = 16.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        //if the user.followers is null, then it will be 0
        ColumnContent(
            number = user.value.followers?.size ?: 0,
            text = "Followers",
            onClick = {
                showFollowerModel.value = true
            }
        )
        Spacer(Modifier.width(12.dp))
        ColumnContent(
            number = user.value.followed?.size ?: 0,
            text = "Following",
            onClick = {
                showFollowingModel.value = true
            }
        )
        if (!isMe && isFollowing) {
            Spacer(Modifier.width(12.dp))
            ColumnContent(
                number = user.value.reputation,
                text = "Reputation",
                onClick = {
                    showReputationModel.value = true
                }
            )
        }
    }
    Divider(
        color = Color(0xFFDDDDDD),
        thickness = 1.dp,
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun ColumnContent(
    number: Number = 0,
    text: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.clickable {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = number.toString(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = text)
    }
}


//@OptIn(ExperimentalFoundationApi::class)
//@Preview
//@Composable
//fun GridPreview() {
//    val items = listOf(
//        "Item 1",
//        "Item 2",
//        "Item 3",
//        "Item 4",
//        "Item 5",
//        "Item 6",
//        "Item 7",
//        "Item 8",
//        "Item 9",
//        "Item 10"
//    )
//    LazyVerticalGrid(
//        cells = GridCells.Adaptive(100.dp),
//        content = {
//            items(items.size) { index ->
//                Text(text = items[index], modifier = Modifier.padding(16.dp))
//            }
//        }
//
//    )
//}