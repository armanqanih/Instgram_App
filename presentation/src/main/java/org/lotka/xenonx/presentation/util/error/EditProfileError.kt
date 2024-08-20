package org.lotka.xenonx.presentation.util.error

sealed class EditProfileError : Error() {
    object FieldEmpty :  EditProfileError()
}