package org.lotka.xenonx.presentation.screen.create_post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.util.error.PostDescriptionError
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardTextField
import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation
import org.lotka.xenonx.presentation.util.CropActivityResultContract
import org.lotka.xenonx.presentation.util.Dimension.SpaceLarge
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall
import org.lotka.xenonx.presentation.util.PostContants.MAX_POST_LENGTH
import org.lotka.xenonx.presentation.util.UiEvent
import java.io.File
import java.util.UUID


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CreatePostScreen(
    onNavigate :(String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: CreatePostScreenViewModel = hiltViewModel(),
){
    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current



    val cropActivityResultLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(16f, 9f)
    ) { uri ->
        uri?.let {
            viewModel.onEvent(CreatePostEvent.CropImage(it))
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            cropActivityResultLauncher.launch(it)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnakeBar -> {
                    GlobalScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)

                    }
                }

                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }

                UiEvent.NavigateUp -> TODO()
            }
        }
    }





    Column(modifier = Modifier.fillMaxSize()) {

        StandardToolBar(
            modifier = Modifier
                .fillMaxWidth(),
            onNavigateUp = onNavigateUp,
            showBackArrow = false,
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
                .aspectRatio(16f / 9f)
                .clip(shape = MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = MaterialTheme.shapes.medium
                )
                .clickable {
                    imagePickerLauncher.launch("image/*")
                }
                ,
                contentAlignment = Alignment.Center

                ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_image),
                    tint = MaterialTheme.colors.onBackground
                )
              state.imageUri?.let {
                  Image(
                      painter = rememberImagePainter(
                          request = ImageRequest.Builder(LocalContext.current)
                              .data(state.imageUri)
                              .build()
                      ),
                      contentDescription = "Selected Image",
                      modifier = Modifier.fillMaxSize(),
                      contentScale = ContentScale.Crop
                  )

              }

                    // If image is selected, display it


            }

            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.description.text,
                hint = stringResource(R.string.your_description),
                maxLines = 5,
                maxLength = MAX_POST_LENGTH,
                error = when(state.description.error){
                    is PostDescriptionError.FieldEmpty -> {
                        stringResource(R.string.this_field_cant_be_empty)
                    }
                    else -> {
                        ""
                    }
                },
                singleLine = false,
                onValueChange ={
                   viewModel.onEvent(CreatePostEvent.EnteredDescription(it))
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(onClick = {
               viewModel.onEvent(CreatePostEvent.SendPost)
            },
                modifier = Modifier.align(Alignment.End),
                enabled = !state.isLoading

                ) {
                Text(text = "Post",
                    color = MaterialTheme.colors.onPrimary
                    )
                Spacer(modifier = Modifier.height(SpaceSmall))
                if (state.isLoading){
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.width(16.dp)
                            .scale(0.5f)

                    )
                }

            }


            }

        }



    }

