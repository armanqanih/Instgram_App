package org.lotka.xenonx.domain.util.error

sealed class PostDescriptionError :Error() {

    object FieldEmpty :  PostDescriptionError()

}