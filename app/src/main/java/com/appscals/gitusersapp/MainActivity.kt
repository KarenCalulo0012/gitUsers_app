package com.appscals.gitusersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.appscals.gitusersapp.model.User
import com.appscals.gitusersapp.ui.theme.JetpackComposeMVVMRetrofitAndRecyclerviewTheme
import com.appscals.gitusersapp.viewmodel.UsersVM

class MainActivity : ComponentActivity() {

    private val usersViewModel by viewModels<UsersVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeMVVMRetrofitAndRecyclerviewTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    UserList(userList = usersViewModel.userListResponse)
                    usersViewModel.getUserList()
                }
            }
        }
    }

    @Composable
    fun UserList(userList: List<User>) {
        var selectedIndex by remember { mutableStateOf(-1) }
        LazyColumn {
            itemsIndexed(items = userList) { index, item ->
                UserItem(user = item, index, selectedIndex) { i ->
                    selectedIndex = i
                }
            }
        }
    }

    @Composable
    private fun UserItem(user: User, index: Int, selectedIndex: Any, onClick: (Int) -> Unit) {
        val backgroundColor =
            if (index == selectedIndex) MaterialTheme.colors.primary else MaterialTheme.colors.background
        Card(
            modifier = Modifier
                .padding(8.dp, 4.dp)
                .fillMaxWidth()
                .clickable {
                    onClick(index)
                    usersViewModel.getUserInfo(user.login)
                }
                .height(110.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
        ) {
            Surface(color = backgroundColor) {
                Row(
                    Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = user.avatarUrl,

                            builder = {
                                scale(Scale.FILL)
                                placeholder(coil.base.R.drawable.abc_vector_test)
                                transformations(CircleCropTransformation())

                            }
                        ),
                        contentDescription = user.login,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.2f)
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxHeight()
                            .weight(0.8f)
                    ) {
                        Text(
                            text = user.login,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = user.type,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier
                                .background(
                                    Color.LightGray
                                )
                                .padding(4.dp)
                        )
                        Text(
                            text = user.url,
                            style = MaterialTheme.typography.subtitle2,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}