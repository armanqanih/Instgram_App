package org.lotka.xenonx.presentation.screen.create_post

import android.net.Uri
import org.lotka.xenonx.domain.util.state.StandardTextFieldState

data class CreatePostState(
    val isLoading: Boolean = false,
    val description: StandardTextFieldState =StandardTextFieldState(),
    val error: String? = null,
    val result: Unit? = null,
    val imageUri : Uri? = null
)