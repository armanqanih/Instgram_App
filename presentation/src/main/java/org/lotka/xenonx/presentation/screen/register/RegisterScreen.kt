package org.lotka.xenonx.presentation.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardTextField
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation
import org.lotka.xenonx.presentation.util.Dimension.SpaceLarge
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.UiEvent
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnakeBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    androidx.compose.material.Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = SpaceLarge,
                    end = SpaceLarge,
                    top = SpaceLarge,
                    bottom = 50.dp
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(R.string.register),
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    value = state.email,
                    hint = stringResource(R.string.enter_you_email),
                    onValueChange = {
                        viewModel.onEvent(RegisterEvent.EnterEmail(it))
                    },
                    singleLine = true,
                    keyboardType = KeyboardType.Email,
                    error = state.emailError,
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    value = state.userName,
                    hint = stringResource(R.string.enter_user_name),
                    onValueChange = {
                        viewModel.onEvent(RegisterEvent.EnterUserName(it))
                    },
                    singleLine = true,
                    keyboardType = KeyboardType.Text,
                    error = state.userNameError,
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    value = state.password,
                    hint = stringResource(R.string.Password),
                    onValueChange = {
                        viewModel.onEvent(RegisterEvent.EnterPassword(it))
                    },
                    singleLine = true,
                    keyboardType = KeyboardType.Password,
                    error = state.passwordError,
                )



                state?.error?.let {
                    if (it.isNotEmpty()) {
                        Text(
                            text = it,
                            color = MaterialTheme.colors.error
                        )
                    }
                }
                Spacer(modifier = Modifier.height(SpaceMedium))
                Button(
                    onClick = {
                        viewModel.onEvent(RegisterEvent.Register)
                              navController.navigate(ScreensNavigation.LoginScreen.route)

                              },
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .align(Alignment.End)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colors.primary),
                    enabled = !state.isLoading && state.email.isNotEmpty() && state.userName.isNotEmpty() && state.password.isNotEmpty()
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        style = MaterialTheme.typography.body1,
                    )
                }

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }


            }

            val signUpText = buildAnnotatedString {
                append(stringResource(R.string.already_have_an_account))
                append(" ")
                withStyle(style = SpanStyle(MaterialTheme.colors.primary)) {
                    append(stringResource(R.string.Login))
                }
            }

            ClickableText(
                text = signUpText,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = SpaceMedium),
                onClick = {
                    navController.popBackStack()
                    navController.navigate(ScreensNavigation.LoginScreen.route)
                }
            )
        }
    }
}
