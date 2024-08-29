package org.lotka.xenonx.presentation.screen.edit_profile

import android.net.Uri
import org.lotka.xenonx.domain.util.state.StandardTextFieldState

sealed class EditProfileEvent {

    data class UserNameChange(val userNameState : StandardTextFieldState) : EditProfileEvent()
    data class GithubTextChange(val githubTextState : StandardTextFieldState) : EditProfileEvent()
    data class InstagramTextChange(val instagramTextState : StandardTextFieldState) : EditProfileEvent()
    data class LinkedInTextChange(val linkedInTextState : StandardTextFieldState) : EditProfileEvent()
    data class BioTextChange(val bioTextState : StandardTextFieldState) : EditProfileEvent()

    data class CropProfilePicture(val uri : Uri) : EditProfileEvent()
    data class CropBannerImage(val uri : Uri) : EditProfileEvent()

    data class SetSkillSelected(val skill : String, val selected : Boolean) : EditProfileEvent()


    object UpdateProfile : EditProfileEvent()

}