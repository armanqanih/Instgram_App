package org.lotka.xenonx.domain.util

sealed class ActivityAction {

    object LikePost : ActivityAction()
    object CommentedOnPost : ActivityAction()

}