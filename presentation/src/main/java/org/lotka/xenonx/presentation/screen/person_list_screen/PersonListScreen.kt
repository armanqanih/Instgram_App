package org.lotka.xenonx.presentation.screen.person_list_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.composable.item.UserProfileItem

import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium

@Composable
fun PersonListScreen(
    navController: NavController ,

) {

    Column(modifier = Modifier.fillMaxSize()) {

        StandardToolBar(
            modifier = Modifier
                .fillMaxWidth(),
            navController = navController,
            showBackArrow = true, title = {
                Text(text = stringResource(R.string.like_by),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground)


            },

        )

            Spacer(modifier = Modifier.height(SpaceMedium))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceMedium)

            ) {

                items(10){
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    UserProfileItem(user = UserModel(
                        "",
                        userName = "ArmanSHerwanii",
                        description = "hellow my name is arman how are" +
                                " you every one"
                    , followingCount = 12,
                        followerCount = 13,
                        postCount = 0
                    )
                        , actionItem = {

                            Icon(imageVector =Icons.Default.PersonAdd ,
                                tint = Color.White,
                                contentDescription = "")

                        }
                        , onCardClick = {

                        },
                        onActionItemClick = {

                        },
                        modifier = Modifier.fillMaxWidth()

                    )
                }

            }
        }




    }
