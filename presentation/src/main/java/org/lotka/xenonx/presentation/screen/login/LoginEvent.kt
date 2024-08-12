package org.lotka.xenonx.presentation.screen.login

import androidx.compose.ui.focus.FocusState

sealed class LoginEvent {

     data class EnterUserName (val userName:String):LoginEvent()
     data class EnterPassword (val password:String):LoginEvent()
     data class ShowPassword ( val showPassword : Boolean ):LoginEvent()
     data class PasswordError ( val passwordError : String ):LoginEvent()
     data class UserNameError ( val userNameError : String ):LoginEvent()
     object Login : LoginEvent()
     data class ShowSnakeBar(val message:String):LoginEvent()

}