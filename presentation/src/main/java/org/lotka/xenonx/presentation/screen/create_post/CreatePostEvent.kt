package org.lotka.xenonx.presentation.screen.create_post

import android.net.Uri
import org.lotka.xenonx.domain.util.error.AuthError

sealed class CreatePostEvent {
    data class EnteredDescription(val description: String) : CreatePostEvent()
    data class CropImage(val cropUri : Uri?):CreatePostEvent()
    object SendPost : CreatePostEvent()

}