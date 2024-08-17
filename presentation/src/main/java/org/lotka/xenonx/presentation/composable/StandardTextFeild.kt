package org.lotka.xenonx.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.presentation.util.Dimension.IconSizeMedium

@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    maxLength : Int = 40,
    leadingIcon : ImageVector? = null,
    error: String = "",
    maxLines : Int = 1,
    textStyle : TextStyle = TextStyle(
        color = MaterialTheme.colors.onBackground
    ),
    leadingIconColor: Color = MaterialTheme.colors.onBackground,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed : Boolean = keyboardType == KeyboardType.Password,
    singleLine : Boolean = true,
    showPasswordToggle: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {}
) {



    Column(modifier = Modifier.fillMaxWidth()) {
    TextField(
         modifier = modifier.fillMaxWidth()
        ,
        value = value,
        onValueChange = {
            if(it.length < maxLength){
                onValueChange(it)
            }
        },
        maxLines = maxLines,
        textStyle = textStyle,
        isError = error != "",

        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.body1)
        },
         singleLine = singleLine,
        visualTransformation = if(!showPasswordToggle && isPasswordToggleDisplayed){
            PasswordVisualTransformation()
        }else{
            VisualTransformation.None
        }
      ,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        leadingIcon = {
            if(leadingIcon != null){
                val icon : @Composable () -> Unit = {
                    Icon(
                        imageVector = leadingIcon
                        , contentDescription = null ,
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                }
             icon()

            }else null
        },
        trailingIcon = {
            if (isPasswordToggleDisplayed) {
             IconButton(onClick = {
               onPasswordToggleClick(!showPasswordToggle)
             },
             modifier = Modifier
                 .semantics {
                 testTag = "password_toggle"
             }


             ) {
                 Icon(imageVector = if(showPasswordToggle){
                     Icons.Filled.Visibility
                 }else{
                     Icons.Filled.VisibilityOff

                 }, contentDescription ="password"
                 )
             }
            } else null
        }
    )

        if (error.isNotEmpty()){
            Text(
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}