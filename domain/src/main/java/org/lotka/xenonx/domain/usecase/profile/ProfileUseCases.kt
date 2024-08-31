package org.lotka.xenonx.domain.usecase.profile

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val getSkills: GetSkillsUseCase,
    val updateProfile: UpdateProfileUseCase,
    val setSkillsSelected: SetSkillsSelectedUseCase

)