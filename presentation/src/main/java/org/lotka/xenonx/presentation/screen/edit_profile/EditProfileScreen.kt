package org.lotka.xenonx.presentation.screen.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardTextField
import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.screen.edit_profile.compose.BannerEditSection
import org.lotka.xenonx.presentation.screen.edit_profile.compose.Chip
import org.lotka.xenonx.presentation.util.Dimension.SpaceLarge
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.Dimension.profilePictureSizeLarge
import org.lotka.xenonx.domain.util.error.EditProfileError
import org.lotka.xenonx.domain.util.state.StandardTextFieldState
import org.lotka.xenonx.presentation.screen.create_post.CreatePostEvent
import org.lotka.xenonx.presentation.util.CropActivityResultContract
import org.lotka.xenonx.presentation.util.UiEvent
import kotlin.random.Random


@Composable
fun  EditProfileScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: EditProfileViewModel = hiltViewModel(),
    profilePictureSize : Dp = profilePictureSizeLarge
) {
     val state = viewModel.state.collectAsState().value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {

        viewModel.eventFlow.collect { event ->
            when(event) {
                is UiEvent.ShowSnakeBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

            }
        }
    }

    val cropProfileImageLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f,1f)
    ) { uri ->
        uri?.let {
            viewModel.onEvent(EditProfileEvent.CropProfilePicture(it))
        }
    }
    val cropBannerImageLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(5f,2f)
    ) { uri ->
        uri?.let {
            viewModel.onEvent(EditProfileEvent.CropBannerImage(it))
        }
    }

    val ProfilePictuerGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            cropProfileImageLauncher.launch(it)
        }
    }

    val BannerPictuerGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            cropBannerImageLauncher.launch(it)
        }
    }





    Column(modifier = Modifier.fillMaxSize()) {

        StandardToolBar(
             modifier = Modifier
                .fillMaxWidth(),
            onNavigateUp = onNavigateUp,
            showBackArrow = true
            , title = {
                Text(text =  stringResource(R.string.edit_profile)
                    , fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            navAction = {
                IconButton(onClick = {
                    viewModel.onEvent(EditProfileEvent.UpdateProfile)
                }) {
                    Icon(imageVector = Icons.Outlined.Check
                        , contentDescription = stringResource(R.string.save_changes),
                        tint = MaterialTheme.colors.onBackground
                    )

                }
            }


        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {

            BannerEditSection(
                banner = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(
                        data = state.profile?.bannerUrl ?:  state.profile?.bannerUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                profileImage = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = state.profile?.profileImageUrl ?: state.profile?.profileImageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                )
                , profilePictureSize = profilePictureSize,
                onProfileImageClick = {
                    ProfilePictuerGalleryLauncher.launch("image/*")
                },
                onBannerImageClick = {
                    BannerPictuerGalleryLauncher.launch("image/*")
                }

            )
            Spacer(modifier = Modifier.height(SpaceMedium))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge)
            ){
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.userNameState.text,
                    leadingIcon = Icons.Default.Person,
                    hint = stringResource(R.string.user_name),
                    error = state.userNameState.error.toString(),
                    onValueChange ={
                        viewModel.onEvent(EditProfileEvent.UserNameChange(
                            StandardTextFieldState(text = it)
                        ))
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.instagramTextState.text,
                    hint = stringResource(R.string.instagram_profile_url),
                    error = state.instagramTextState.error.toString(),
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.instagram)
                    ,
                    onValueChange ={
                        viewModel.onEvent(EditProfileEvent.InstagramTextChange(
                            StandardTextFieldState(text = it)
                        ))
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.linkedInTextState.text,
                    hint = stringResource(R.string.linkedin_profile_url),
                    error = when(state.linkedInTextState.error){
                        is EditProfileError.FieldEmpty -> {
                            stringResource(R.string.this_field_cant_be_empty)
                        }

                        else -> {""}
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.linkedin)
                    ,
                    onValueChange ={
                        viewModel.onEvent(EditProfileEvent.LinkedInTextChange(
                            StandardTextFieldState(text = it)
                        ))
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.githubTextState.text,
                    hint = stringResource(R.string.github_profile_url),
                    error = when(state.githubTextState.error){
                        is EditProfileError.FieldEmpty -> {
                            stringResource(R.string.this_field_cant_be_empty)
                        }

                        else -> {""}
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.github)
                    ,
                    onValueChange ={
                        viewModel.onEvent(EditProfileEvent.GithubTextChange(
                            StandardTextFieldState(text = it)
                        ))
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.bioState.text,
                    hint = "bio",
                    leadingIcon = Icons.Default.Description,
                    error = when(state.bioState.error){
                        is EditProfileError.FieldEmpty -> {
                            stringResource(R.string.this_field_cant_be_empty)
                        }
                        else -> {""}
                    },
                    maxLines = 3,
                    singleLine = false,
                    onValueChange ={
                        viewModel.onEvent(EditProfileEvent.BioTextChange(
                            StandardTextFieldState(text = it)
                        ))
                    }
                )

                Spacer(modifier = Modifier.height(SpaceMedium))

                Text(text = "Select Top 3 Skills",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(CenterHorizontally))

                Spacer(modifier = Modifier.height(SpaceMedium))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisAlignment = MainAxisAlignment.Center,
                    mainAxisSpacing = SpaceMedium,
                    crossAxisSpacing = SpaceMedium
                ) {

                  state.skills.forEach {skill->
                       Chip(
                           text = skill.name
                       ,   isSelected = state.selectedSkills.any { it.name == skill.name },
                           onChipClick = {
                               viewModel.onEvent(EditProfileEvent.SetSkillSelected(skill))
                           }
                       )
                    }

                }

            }




        }
    }



}