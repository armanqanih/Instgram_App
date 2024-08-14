package org.lotka.xenonx.domain.model

import org.lotka.xenonx.domain.util.ActivityAction

data class ActivityModel(
   val userName: String,
   val formatTime: String ,
   val actionType : ActivityAction
)
