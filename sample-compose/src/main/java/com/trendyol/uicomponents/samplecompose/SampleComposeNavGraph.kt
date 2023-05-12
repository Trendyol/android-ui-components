package com.trendyol.uicomponents.samplecompose

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SampleComposeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        modifier = modifier.statusBarsPadding(),
        navController = navController,
        startDestination = Route.Components.destination
    ) {
        composable(Route.Components.destination) {
            ComponentsScreen {
                navController.navigate(it.destination)
            }
        }

        composable(Route.TimelineView.destination) {
            TimelineViewScreen()
        }
    }
}