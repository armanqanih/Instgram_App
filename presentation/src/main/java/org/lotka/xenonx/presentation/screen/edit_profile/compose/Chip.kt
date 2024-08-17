package org.lotka.xenonx.presentation.screen.edit_profile.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.presentation.util.Dimension.IconSizeMedium
import org.lotka.xenonx.presentation.util.Dimension.SpaceSmall

@Composable
fun Chip (
    modifier: Modifier = Modifier,
    text:String,
    isSelected:Boolean = false,
    selectedColor : Color = MaterialTheme.colors.primary,
    unSelectedColor : Color = MaterialTheme.colors.onSurface,
    onChipClick:()-> Unit ={},
) {

    Text(text = text,
        modifier = modifier
            .clip(shape = RoundedCornerShape(50.dp))
            .border(width = 1.dp,
                color = if(isSelected) selectedColor else unSelectedColor
                 , shape = RoundedCornerShape(50.dp)
            )
            .clickable {onChipClick() }
            .padding(SpaceSmall)
        , color = if(isSelected) selectedColor else unSelectedColor
        )


}