package org.lotka.xenonx.presentation.screen.activity


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.lazy.LazyColumn


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.font.FontWeight

import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.Post
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardToolBar


@Composable
fun  ActivityScreen(
    navController: NavController,
) {

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolBar(
            navController = navController,
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.your__feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
        ) {



            items(20) {

            }

        }


    }

}