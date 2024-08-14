package org.lotka.xenonx.presentation.screen.activity


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.lazy.LazyColumn


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import org.lotka.xenonx.domain.model.ActivityModel
import org.lotka.xenonx.domain.util.ActivityAction
import org.lotka.xenonx.domain.util.DataFormater
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.composable.StandardToolBar
import org.lotka.xenonx.presentation.screen.activity.compose.ActivityItem
import org.lotka.xenonx.presentation.util.Dimension.SpaceMedium
import kotlin.random.Random


@Composable
fun  ActivityScreen(
    navController: NavController,
    viewModel: ActivityViewModel = hiltViewModel(),
) {

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolBar(
            navController = navController,
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            title = {
                Text(
                    text = stringResource(R.string.your_activity),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
            , contentPadding = PaddingValues(SpaceMedium)
        ) {
            items(20) {

                ActivityItem(activity = ActivityModel(
                    userName = "ArmanSherwanii",
                    actionType = if(Random.nextInt(2) == 0){
                        ActivityAction.LikePost
                    }else ActivityAction.CommentedOnPost,
                    formatTime = DataFormater.timeStampToFormatString(
                        timeStamp = System.currentTimeMillis(),
                        pattern = "MMM dd, HH:mm"
                    )
                ))
                if(it<19){
                Spacer(modifier = Modifier.height(SpaceMedium))
                }

            }

        }


    }

}