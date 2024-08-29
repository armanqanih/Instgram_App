package org.lotka.xenonx.presentation.screen.edit_profile


import android.net.Uri
import org.lotka.xenonx.domain.model.Skills
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.domain.util.state.StandardTextFieldState

data class EditProfileState(
    val isLoading : Boolean = false,
    val Error : String? = null,
    val profile : UserModel? = null,
    val selectedSkills : List<Skills> = emptyList(),
    val skills:List<Skills> = emptyList(),

    val bannerImageUri : Uri? = null,
    val profileImageUri: Uri? = null,

    val userNameState : StandardTextFieldState = StandardTextFieldState(),
    val githubTextState : StandardTextFieldState = StandardTextFieldState(),
    val instagramTextState : StandardTextFieldState = StandardTextFieldState(),
    val linkedInTextState : StandardTextFieldState = StandardTextFieldState(),
    val bioState : StandardTextFieldState = StandardTextFieldState(),



    )
