package org.lotka.xenonx.presentation.util.error

sealed class PostDescriptionError :Error() {

    object FieldEmpty :  PostDescriptionError()

}