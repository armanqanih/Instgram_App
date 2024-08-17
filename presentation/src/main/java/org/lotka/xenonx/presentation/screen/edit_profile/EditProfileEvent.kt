package org.lotka.xenonx.presentation.screen.edit_profile

import org.lotka.xenonx.presentation.util.state.StandardTextFieldState

sealed class EditProfileEvent {

    data class UserNameChange(val userNameState : StandardTextFieldState) : EditProfileEvent()
    data class GithubTextChange(val githubTextState : StandardTextFieldState) : EditProfileEvent()
    data class InstagramTextChange(val instagramTextState : StandardTextFieldState) : EditProfileEvent()
    data class LinkedInTextChange(val linkedInTextState : StandardTextFieldState) : EditProfileEvent()
    data class BioTextChange(val bioTextState : StandardTextFieldState) : EditProfileEvent()


}