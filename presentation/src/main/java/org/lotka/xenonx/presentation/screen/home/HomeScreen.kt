package org.lotka.xenonx.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
){
    
    Column(modifier = Modifier.fillMaxSize()
    , horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Welcome To Home Screen", fontSize = 20.sp)
    }
    
}