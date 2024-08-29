package org.lotka.xenonx.domain.repository.profile

import android.net.Uri
import kotlinx.coroutines.flow.Flow

import org.lotka.xenonx.domain.model.Skills
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.domain.util.Resource

interface ProfileRepository {

    suspend fun getProfile(userId: String): Flow<Resource<UserModel>>

    suspend fun getSkills(): Flow<Resource<List<Skills>>>

    suspend fun upDateProfileData(updateProfileData: UserModel, bannerImageUri: Uri?,
        profileImageUri: Uri?): Flow<Resource<UserModel>>


}