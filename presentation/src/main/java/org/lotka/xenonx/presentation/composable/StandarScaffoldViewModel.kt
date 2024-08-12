package org.lotka.xenonx.presentation.composable

import androidx.collection.mutableIntSetOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StandardScaffoldViewModel @Inject constructor (

): ViewModel(){

    private val _selectBottomNavItem = mutableStateOf(0)
    var selectBottomNavItem :  State<Int> = _selectBottomNavItem

    fun SetselectBottomNavItem(index : Int){
        _selectBottomNavItem.value = index
    }

}