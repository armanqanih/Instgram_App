package org.lotka.xenonx.domain.usecase.profile
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.domain.repository.profile.ProfileRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class UpdateProfileUseCase  @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(updateProfileData: UserModel, bannerImageUri: Uri?,
                                profileImageUri: Uri?): Flow<Resource<UserModel>>  {

        if (updateProfileData.username.isEmpty()) {
            return flow {
                emit(Resource.Error("Username cannot be empty"))
            }
        }

        return repository.upDateProfileData(updateProfileData, bannerImageUri, profileImageUri)
    }
}