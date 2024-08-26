package org.lotka.xenonx.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.lotka.xenonx.domain.model.ProfileResponse
import org.lotka.xenonx.domain.repository.profile.ProfileRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): ProfileRepository {
    override suspend fun getProfile(userId: String): Flow<Resource<ProfileResponse>> {
        return flow {
            try {
                val document = firestore.collection("profiles").document(userId).get().await()
                if (document.exists()) {
                    val data = document.data
                    val profileResponse = ProfileResponse(
                        id = data?.get("id") as String  ,
                        username = data["username"] as String  ,
                        profileImageUrl = data["profileImageUrl"] as String  ,
                        bannerUrl = data["bannerUrl"] as String  ,
                        bio = data["bio"] as String ,
                        topSkillsUrl = (data["topSkillsUrl"] as? List<String>) ?: emptyList(),
                        githubUrl = data["githubUrl"] as String?,
                        LinkedInUrl = data["LinkedInUrl"] as String?,
                        isOwenProfile = data["isOwenProfile"] as Boolean,
                        isFollowing = data["isFollowing"] as Boolean,
                        followingCount = (data["followingCount"] as? Long)?.toInt() ?: 0,
                        followerCount = (data["followerCount"] as? Long)?.toInt() ?: 0,
                        postCount = (data["postCount"] as? Long)?.toInt() ?: 0
                    )
                    Resource.Success(profileResponse)
                } else {
                    Resource.Error("Profile not found")
                }
            } catch (e: Exception) {
                Resource.Error("Error fetching profile: ${e.message}")
            }
        }

    }
}

