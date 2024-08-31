package org.lotka.xenonx.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.lotka.xenonx.data.repository.ProfileRepositoryImpl
import org.lotka.xenonx.domain.repository.profile.ProfileRepository
import org.lotka.xenonx.domain.usecase.profile.GetProfileUseCase
import org.lotka.xenonx.domain.usecase.profile.GetSkillsUseCase
import org.lotka.xenonx.domain.usecase.profile.ProfileUseCases
import org.lotka.xenonx.domain.usecase.profile.SetSkillsSelectedUseCase
import org.lotka.xenonx.domain.usecase.profile.UpdateProfileUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileRepository(
        firestore: FirebaseFirestore,
    ): ProfileRepository {
        return ProfileRepositoryImpl(firestore)
    }



    @Singleton
    @Provides
    fun provideProfileUseCases(
        profileRepository: ProfileRepository,
    ): ProfileUseCases {
        return ProfileUseCases(
            getProfile = GetProfileUseCase(profileRepository),
            getSkills = GetSkillsUseCase(profileRepository),
            updateProfile = UpdateProfileUseCase(profileRepository),
            setSkillsSelected = SetSkillsSelectedUseCase()
        )
    }

}