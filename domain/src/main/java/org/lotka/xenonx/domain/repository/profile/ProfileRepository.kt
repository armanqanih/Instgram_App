package org.lotka.xenonx.domain.repository.profile

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.model.PostModel

import org.lotka.xenonx.domain.model.SkillsModel
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.domain.util.Resource

interface ProfileRepository {

    fun getPostsPage(userId: String): Flow<PagingData<PostModel>>

    suspend fun getProfile(userId: String): Flow<Resource<UserModel>>

    suspend fun getSkills(): Flow<Resource<List<SkillsModel>>>

    suspend fun upDateProfileData(updateProfileData: UserModel, bannerImageUri: Uri?,
        profileImageUri: Uri?): Flow<Resource<UserModel>>


}