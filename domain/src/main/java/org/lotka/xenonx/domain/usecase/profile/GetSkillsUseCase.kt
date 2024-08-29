package org.lotka.xenonx.domain.usecase.profile
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.model.Skills
import org.lotka.xenonx.domain.repository.profile.ProfileRepository
import org.lotka.xenonx.domain.util.Resource
import javax.inject.Inject

class GetSkillsUseCase  @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Skills>>>  {
        return repository.getSkills()
    }
}