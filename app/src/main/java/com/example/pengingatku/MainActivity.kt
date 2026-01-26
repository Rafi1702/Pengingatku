package com.example.pengingatku

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.pengingatku.screen.StopWatch.StopWatchScreen
import com.example.pengingatku.screen.TimerEdit.TimerEditScreen
import com.example.pengingatku.screen.TimerList.TimerListScreen
import com.example.pengingatku.ui.theme.PengingatkuTheme
import com.example.pengingatku.utils.BaseNavigationScreen
import com.example.pengingatku.utils.BottomBarNavigationScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PengingatkuTheme {
                Main()
            }
        }
    }
}


val LocalModifier = staticCompositionLocalOf<Modifier> { Modifier }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
    val navController = rememberNavController()
    val navigationBottomBar = remember { BottomBarNavigationScreen.entries.map { it.route } }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val isBottomBarVisible by remember {
        derivedStateOf { navBackStackEntry?.destination?.hierarchy?.any { it.route in navigationBottomBar } == true }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Text("Alarm")
                },
                navigationIcon = {
                    if (!isBottomBarVisible) {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }, actions = {
                    IconButton(
                        onClick = {
                            // Panggil fungsi repository.getTimerDatas() atau navigasi di sini
                        },
                        modifier = Modifier.size(48.dp) // Ukuran standar touch target
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Add New Timer",
                            )
                    }
                })
        }, bottomBar = {

            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar {
                    BottomBarNavigationScreen.entries.forEachIndexed { _, screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            label = { Text(screen.route) },
                            icon = { Icon(screen.icon, null) },
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }

        },

        floatingActionButton = {

            if (isBottomBarVisible) {
                FloatingActionButton(
                    modifier = Modifier.offset(y = 40.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        navController.navigate(route = BaseNavigationScreen.AddTimer.route)
                    },
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }

        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val modifier = Modifier.padding(innerPadding)
            CompositionLocalProvider(LocalModifier provides modifier) {
                AppNavHost(
                    navController = navController,
                )

            }

        }

    }
}


@Composable
fun AppNavHost(
    navController: NavHostController,

    ) {
    NavHost(
        navController,
        startDestination = "main_graph",
        enterTransition = { fadeIn(animationSpec = tween(200)) },
        exitTransition = { fadeOut(animationSpec = tween(200)) }
    ) {
        val timerRepository = TimerRepository()
        // isi dengan NavGraphBuilder atau composable

        /* Kode Di Dalam Builder NavHost (lambda yang dibutuhkan dari NavHost)*/
        navigation(
            startDestination = BottomBarNavigationScreen.TIMER_LIST.route,
            route = "main_graph"
        ) {

            composable(BottomBarNavigationScreen.TIMER_LIST.route) {

                TimerListScreen(
                    timerRepository = timerRepository,
                    onNavigate = { navController.navigate(BaseNavigationScreen.EditTimer.route) }
                )
            }
            composable(BottomBarNavigationScreen.STOP_WATCH.route) { StopWatchScreen() }
        }

        composable(BaseNavigationScreen.EditTimer.route) {
            TimerEditScreen(onNavigateToTimerList = {
                navController.navigate(BottomBarNavigationScreen.TIMER_LIST.route)
            })
        }

//        composable(BaseNavigationScreen.AddTimer.route) {
//            AddTimerScreen(toTimerGrids = {
//                navController.navigate(BottomBarNavigationScreen.TIMER_LIST.route)
//            })
//        }

//        composable(
//            route = "detail/{timerId}",
//            arguments = listOf(
//                navArgument("timerId") { type = NavType.IntType }
//            )
//        ) { backStackEntry ->
//            val id = backStackEntry.arguments?.getInt("timerId") ?: 0
//            TimerDetailScreen(timerId = id, paddingValues = paddingValues)
//        }

//        composable<TimerInformation> { backStackEntry ->
//            val timerArgs = backStackEntry.toRoute<TimerInformation>()
//            TimerDetailScreen(timerId = timerArgs.id, paddingValues = paddingValues)
//        }

    }
}


