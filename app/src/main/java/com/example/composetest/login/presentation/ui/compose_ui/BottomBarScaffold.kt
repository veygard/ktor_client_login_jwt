package com.example.composetest.login.presentation.ui.compose_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composetest.login.presentation.model.BottomNavigationItemInterface
import com.example.composetest.login.presentation.ui.theme.bottomBarTextStyle


@Composable
fun <T: BottomNavigationItemInterface> BottomBarElementNoneNav(
    modifier: Modifier = Modifier,
    route:String,
    items: List<T>,
    click:(String) -> Unit
){
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
        modifier = modifier
            .heightIn(min = 50.dp)
    ) {
        Column {
            Divider(color = MaterialTheme.colors.secondary, thickness = 0.5.dp)
            Row(verticalAlignment = Alignment.Top){
                items.forEach {
                    BottomNavigationItem(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background, shape = RectangleShape),
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = MaterialTheme.colors.secondary,
                        icon = {
                            Icon(painterResource(id =it.iconId ), contentDescription = stringResource(id = it.contentDescription))
                        },
                        label = {
                            Text(
                                text = it.label,
                                style = bottomBarTextStyle
                            )
                        },
                        selected = route == it.route,
                        onClick = {
                            click(it.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun <T: BottomNavigationItemInterface> BottomBarElement(
    modifier: Modifier = Modifier,
    navController: NavController,
    items: List<T>,
    click:(route: String) -> Unit
){
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
        modifier = modifier
            .border(0.2.dp, MaterialTheme.colors.secondary)
            .heightIn(min = 50.dp)
    ) {
        val navBackStackEntry by navController
            .currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach {
            BottomNavigationItem(
                modifier = Modifier
                    .padding(top = 9.dp, bottom = 1.dp)
                    .background(MaterialTheme.colors.background, shape = RectangleShape),
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.secondary,
                icon = {
                    Icon(painterResource(id =it.iconId ), contentDescription = stringResource(id = it.contentDescription))
                },
                label = {
                    Text(
                        text = it.label,
                        style = bottomBarTextStyle
                    )
                },
                selected = currentRoute == it.route,
                onClick = {
                    navController.popBackStack(
                        navController.graph.startDestinationId, false
                    )
                    if (currentRoute != it.route) {
                        if (currentRoute != null) {
                            click(it.route)
                        }
                    }
                }
            )
        }
    }
}

