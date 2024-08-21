package org.lotka.xenonx.presentation.screen.create_post

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardTextField
import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.util.Dimension.SpaceLarge
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.domain.util.error.PostDescriptionError

@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: CreatePostScreenViewModel = hiltViewModel(),
){
    val descriptionState = viewModel.descriptionState.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {

        StandardToolBar(
            modifier = Modifier
                .fillMaxWidth(),
            navController = navController,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.create_a_new_post),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )


            },


            )
        Spacer(modifier = Modifier.height(SpaceMedium))

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(SpaceLarge)
        ){

            Box(modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f/9f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = MaterialTheme.shapes.medium
                )
                .clickable {

                }
                ,
                contentAlignment = Alignment.Center

                ){
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_image),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.align(Alignment.Center)
                    )

            }

            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                modifier = Modifier.fillMaxWidth(),
                value = descriptionState.text,
                hint = stringResource(R.string.your_description),
                maxLines = 5,
                error = when(descriptionState.error){
                    is PostDescriptionError.FieldEmpty -> {
                        stringResource(R.string.this_field_cant_be_empty)
                    }
                    else -> {
                        ""
                    }
                },
                singleLine = false,
                onValueChange ={
                   viewModel.setDescription(it)
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(onClick = {  },
                modifier = Modifier.align(Alignment.End)
                ) {
                Text(text = "Post",
                    color = MaterialTheme.colors.onPrimary
                    )
                Spacer(modifier = Modifier.width(SpaceSmall))
                Icon(imageVector = Icons.Default.Send ,
                    contentDescription = "Send" )

            }

        }



    }

    }