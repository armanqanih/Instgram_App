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
import androidx.compose.ui.text.style.TextDecoration
import org.lotka.xenonx.domain.util.Constants.MIN_PASSWORD_LENGTH
import org.lotka.xenonx.domain.util.Constants.MIN_USERNAME_LENGTH
import org.lotka.xenonx.domain.util.error.AuthError
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardTextField
import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation

import org.lotka.xenonx.presentation.util.Dimension.SpaceLarge
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import org.lotka.xenonx.presentation.util.UiEvent


@Composable
fun RegisterScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel(),
) {


    val usernameState = viewModel.userNameState.collectAsState().value
    val passwordState = viewModel.passwordState.collectAsState().value
    val emailState = viewModel.emailState.collectAsState().value
    val state = viewModel.state.collectAsState().value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnakeBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }

                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }

                UiEvent.NavigateUp -> TODO()
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
                    value = emailState.text,
                    hint = stringResource(R.string.enter_you_email),
                    onValueChange = {
                        viewModel.onEvent(RegisterEvent.EnterEmail(it))
                    },
                    singleLine = true,
                    keyboardType = KeyboardType.Email,
                    error = when(emailState.error){
                       is AuthError.FieldEmpty -> {
                            stringResource(R.string.this_field_cant_be_empty)
                        }
                        is AuthError.InvalidEmail ->{
                            stringResource(R.string.not_a_vaild_email)
                        }
                        null -> ""
                        else -> {""}
                    }



                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    value = usernameState.text,
                    hint = stringResource(R.string.enter_user_name),
                    onValueChange = {
                        viewModel.onEvent(RegisterEvent.EnterUserName(it))
                    },
                    singleLine = true,
                    keyboardType = KeyboardType.Text,
                    error = when(usernameState.error){
                        is AuthError.FieldEmpty -> {
                            stringResource(R.string.this_field_cant_be_empty)
                        }
                        is AuthError.InputTooShort ->{
                            stringResource(R.string.input_too_short,MIN_USERNAME_LENGTH)
                        }
                        null -> ""
                        else -> {""}
                    },
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    value = passwordState.text,
                    hint = stringResource(R.string.Password),
                    onValueChange = {
                        viewModel.onEvent(RegisterEvent.EnterPassword(it))
                    },
                    singleLine = true,
                    keyboardType = KeyboardType.Password,
                    error = when(passwordState.error){
                        is AuthError.FieldEmpty -> {
                            stringResource(R.string.this_field_cant_be_empty)
                        }
                        is AuthError.InputTooShort ->{
                            stringResource(R.string.input_too_short,MIN_PASSWORD_LENGTH)
                        }
                        is AuthError.InvalidPassword -> {
                            stringResource(R.string.validate_password)
                        }
                        else -> ""
                    },
                    isPasswordVisible = passwordState.isPasswordVisible,
                    onPasswordToggleClick = {
                        viewModel.onEvent(RegisterEvent.IsPasswordVisibility)
                    },

                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                Button(
                    onClick = {
                        viewModel.onEvent(RegisterEvent.Register)
//                              navController.navigate(ScreensNavigation.LoginScreen.route)

                              },
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .align(Alignment.End)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colors.primary),
                    enabled =  emailState.text.isNotEmpty()
                               && usernameState.text.isNotEmpty()
                               && passwordState.text.isNotEmpty()
                ) {
                    val textColor = if (emailState.text.isNotEmpty() &&
                        usernameState.text.isNotEmpty() &&
                        passwordState.text.isNotEmpty()) {
                        Color.Black
                    } else {
                        Color.White
                    }
                    Text(
                        text = stringResource(R.string.register),
                        style = MaterialTheme.typography.body1,
                        color = textColor
                    )


                }

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(SpaceMedium)
                    )
                }


            }

            val signUpText = buildAnnotatedString {
                append(stringResource(R.string.already_have_an_account))
                append(" ")

                pushStringAnnotation(tag = "LOGIN", annotation = "Login")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        textDecoration = TextDecoration.Underline // optional for underlining
                    )
                ) {
                    append(stringResource(R.string.Login))
                }
                pop()
            }

            Box(modifier = Modifier.fillMaxSize()) {
                ClickableText(
                    text = signUpText,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp), // replace `SpaceMedium` with actual value like 16.dp
                    onClick = { offset ->
                        signUpText.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                            .firstOrNull()?.let {
                                onNavigate(ScreensNavigation.LoginScreen.route)
                            }
                    }
                )
            }
        }
    }
}
