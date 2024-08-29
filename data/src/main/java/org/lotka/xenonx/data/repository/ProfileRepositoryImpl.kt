package org.lotka.xenonx.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

import org.lotka.xenonx.domain.model.Skills
import org.lotka.xenonx.domain.model.UserModel
import org.lotka.xenonx.domain.repository.profile.ProfileRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
): ProfileRepository {
    override suspend fun getProfile(userId: String): Flow<Resource<UserModel>> {
        return flow {
            emit(Resource.Loading()) // Emit loading state
            try {
                val document = firestore.collection("User").document(userId).get().await()
                if (document.exists()) {
                    val data = document.data

                    if (data != null) {
                        val profileResponse = UserModel(
                            userId = data["userId"] as String? ?: "0",
                            username = data["username"] as? String ?: "armanSherwanii",
                            profileImageUrl = data["profileImageUrl"] as? String ?: "",
                            bannerUrl = data["bannerUrl"] as? String ?: "",
                            bio = data["bio"] as? String ?: "",
                            topSkillsUrl = (data["topSkillsUrl"] as? List<Skills>) ?: emptyList(),
                            githubUrl = data["githubUrl"] as? String,
                            linkedInUrl = data["linkedInUrl"] as? String,
                            isOwenProfile = data["isOwenProfile"] as? Boolean ?: false,
                            isFollowing = data["isFollowing"] as? Boolean ?: false,
                            followingCount = (data["followingCount"] as? Long)?.toInt() ?: 0,
                            followerCount = (data["followerCount"] as? Long)?.toInt() ?: 0,
                            postCount = (data["postCount"] as? Long)?.toInt() ?: 0
                        )
                        emit(Resource.Success(profileResponse))
                    }

                } else {
                    emit(Resource.Error("Profile not found"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Error fetching profile: ${e.message}"))
            }
        }

    }

    override suspend fun getSkills(): Flow<Resource<List<Skills>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val snapshot = firestore.collection("skills").get().await()
                val skillsList = snapshot.documents.mapNotNull { document ->
                    document.toObject(Skills::class.java)
                }
                emit(Resource.Success(skillsList))
            } catch (e: Exception) {
                emit(Resource.Error("Error fetching skills: ${e.message}"))
            }
        }
    }

    override suspend fun upDateProfileData(
        updateProfileData: UserModel,
        bannerImageUri: Uri?,
        profileImageUri: Uri?,
    ): Flow<Resource<UserModel>> {
        return flow {
            emit(Resource.Loading())

            try {
                // Prepare variables for storing image URLs
                var profileImageUrl: String? = null
                var bannerUrl: String? = null

                // Upload profile image if present
                profileImageUri?.let {
                    val profileImageRef = FirebaseStorage.getInstance().reference.child("profileImages/${updateProfileData.userId}.jpg")
                    profileImageRef.putFile(it).await()
                    profileImageUrl = profileImageRef.downloadUrl.await().toString()
                }

                // Upload banner image if present
                bannerImageUri?.let {
                    val bannerImageRef = FirebaseStorage.getInstance().reference.child("bannerImages/${updateProfileData.userId}.jpg")
                    bannerImageRef.putFile(it).await()
                    bannerUrl = bannerImageRef.downloadUrl.await().toString()
                }

                // Prepare data to be updated in Firestore
                val profileData = hashMapOf(
                    "username" to updateProfileData.username,
                    "profileImageUrl" to (profileImageUrl ?: updateProfileData.profileImageUrl),
                    "bannerUrl" to (bannerUrl ?: updateProfileData.bannerUrl),
                    "bio" to updateProfileData.bio,
                    "topSkillsUrl" to updateProfileData.topSkillsUrl,
                    "githubUrl" to updateProfileData.githubUrl,
                    "linkedInUrl" to updateProfileData.linkedInUrl,
                    "isOwenProfile" to updateProfileData.isOwenProfile,
                    "isFollowing" to updateProfileData.isFollowing,
                    "followingCount" to updateProfileData.followingCount,
                    "followerCount" to updateProfileData.followerCount,
                    "postCount" to updateProfileData.postCount
                )

                // Update Firestore with new profile data
                val document = firestore.collection("User").document(updateProfileData.userId)
                document.set(profileData).await()

                // Emit success with the updated profile data
                emit(Resource.Success(updateProfileData.copy(
                    profileImageUrl = profileImageUrl ?: updateProfileData.profileImageUrl,
                    bannerUrl = bannerUrl ?: updateProfileData.bannerUrl
                )))
            } catch (e: Exception) {
                emit(Resource.Error("Error updating profile: ${e.message}"))
            }
        }
    }


}

