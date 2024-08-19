package org.lotka.xenonx.presentation.screen.register

sealed class RegisterEvent {

     data class EnterUserName (val userName:String):RegisterEvent()
     data class EnterPassword (val password:String):RegisterEvent()
     data class EnterEmail (val email:String):RegisterEvent()
     object Register : RegisterEvent()
     data class ShowSnakeBar(val message:String):RegisterEvent()

}