package org.lotka.xenonx.presentation.composable

import android.text.style.LineBackgroundSpan.Standard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lotka.xenonx.presentation.theme.HintGray

@Composable
fun RowScope.StandardBottomNavigationItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    alertCount: Int? = null,
    selectedColor: Color = MaterialTheme.colors.primary,
    unselectedColor: Color = HintGray,
    selected: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit,

    ){
//    alert count is not null me use this require for checking
    alertCount?.let {
    require(alertCount >= 0)
    }

    BottomNavigationItem(
        selected = selected,
        onClick = onClick,
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        modifier = modifier,
        enabled = enabled,
        icon = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .drawBehind {
                    if (selected) {
                        drawLine(
                            color = if (selected) selectedColor
                            else unselectedColor,
                            start = Offset(
                                size.width /2 -15.dp.toPx(),
                                size.height),
                            end = Offset(size.width /2 +15.dp.toPx(),
                                size.height),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }


                }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    modifier = Modifier.
                    align(Alignment.Center),
                    tint =  if (selected) selectedColor else unselectedColor

                )
                if (alertCount != null) {
                    val alertText = if (alertCount > 99) {
                        "99+"
                    } else {
                        alertCount.toString()
                    }

                    Text(
                        text = alertText,
                        color = MaterialTheme.colors.onPrimary,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier
                                .align(Alignment.TopCenter)
                                .offset(10.dp)
                                .size(15.dp)
                                .clip(CircleShape)
                                .background(color = MaterialTheme.colors.primary)
                    )



                }

            }
        })

}