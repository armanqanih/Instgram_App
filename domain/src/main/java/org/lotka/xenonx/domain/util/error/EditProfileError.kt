package org.lotka.xenonx.domain.util.error

sealed class EditProfileError : Error() {
    object FieldEmpty :  EditProfileError()
}