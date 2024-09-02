package org.lotka.xenonx.domain.usecase

import android.content.SharedPreferences
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lotka.xenonx.domain.model.PostModel
import org.lotka.xenonx.domain.repository.post.PostRepository
import org.lotka.xenonx.domain.util.Constants.KEY_USERID
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class GetOwnUserIdUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
      operator fun invoke():String  {

     return sharedPreferences.getString(KEY_USERID,"")?:""

    }

}
