package org.lotka.xenonx.presentation.screen.login

import androidx.compose.ui.focus.FocusState
import org.lotka.xenonx.presentation.screen.register.RegisterEvent

sealed class LoginEvent {

     data class EnterEmail (val userName:String):LoginEvent()
     data class EnterPassword (val password:String):LoginEvent()
     object IsPasswordVisibility : LoginEvent()
     object Login : LoginEvent()
     data class ShowSnakeBar(val message:String):LoginEvent()

}