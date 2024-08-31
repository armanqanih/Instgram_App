package org.lotka.xenonx.domain.usecase.profile

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lotka.xenonx.domain.model.SkillsModel
import org.lotka.xenonx.domain.util.ProfileContants.MAX_SELECTED_SKILL_COUNT
import org.lotka.xenonx.domain.util.Resource

class SetSkillsSelectedUseCase {

    operator fun invoke(
        selectedSkill: List<SkillsModel>,
        selectedToToggle: SkillsModel
    ): Resource<List<SkillsModel>> {
        val skillInList = selectedSkill.find { it.name == selectedToToggle.name }

         if (skillInList != null){

             Resource.Success(selectedSkill - skillInList)
         }
        return  if (selectedSkill.size >= MAX_SELECTED_SKILL_COUNT){
              (Resource.Error("You can't select more than $MAX_SELECTED_SKILL_COUNT skills"))
         }
         else{
              (Resource.Success(selectedSkill + selectedToToggle))
         }


     }



}