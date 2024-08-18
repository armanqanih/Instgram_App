package org.lotka.xenonx.presentation.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardTextField
import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.screen.edit_profile.EditProfileEvent
import org.lotka.xenonx.presentation.screen.search.compose.UserProfileItem
import org.lotka.xenonx.presentation.util.Dimension.SpaceLarge
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.presentation.util.state.StandardTextFieldState

@Composable
fun SearchScreen(
    navController: NavController ,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {

        StandardToolBar(
            modifier = Modifier
                .fillMaxWidth(),
            navController = navController,
            showBackArrow = true, title = {
                Text(text = stringResource(R.string.search_for_user),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground)


            },

        )
      Spacer(modifier = Modifier.height(SpaceMedium))


        Column(modifier = Modifier
            .fillMaxSize()
            .padding(SpaceLarge)
        ) {
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.searchMessage,
                hint = stringResource(R.string.search),
                leadingIcon = Icons.Default.Search,
                onValueChange ={
                    viewModel.onEvent(SearchEvent.SearchMessageChange(it))
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

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

}