package com.example.pengingatku

import com.example.pengingatku.utils.ScreenNavigation
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.pengingatku.screen.stop_watch.StopWatchScreen
import com.example.pengingatku.screen.add_or_edit_alarm.AlarmEditScreen
import com.example.pengingatku.screen.alarm_list.AlarmListScreen
import com.example.pengingatku.ui.theme.PengingatkuTheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pengingatku.data.repository.StopwatchRepository
import com.example.pengingatku.data.repository.AlarmRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


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
    val navigationBottomBar = ScreenNavigation.bottomBarScreen.map { it.route }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val isBottomBarVisible by remember {
        derivedStateOf { navBackStackEntry?.destination?.hierarchy?.any { it.route in navigationBottomBar } == true }
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                title = {
                    Text(ScreenNavigation.getTitle(currentRoute))
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
                            //TODO: MORE VERTICAL MENU
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Add New Timer",
                        )
                    }
                })
        },
        bottomBar = {

            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar {
                    ScreenNavigation.bottomBarScreen.forEachIndexed { _, screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            label = { Text(screen.title) },
                            icon = { Icon(ImageVector.vectorResource(screen.icon ?: 0), null) },
                            onClick = {
                                navController.navigate(screen.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true

                                    }
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
                        navController.navigate(route = ScreenNavigation.AddTimer.route)
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


@OptIn(ExperimentalUuidApi::class)
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
        val alarmRepository = AlarmRepository()
        val stopWatchRepository = StopwatchRepository()
        // isi dengan NavGraphBuilder atau composable

        /* Kode Di Dalam Builder NavHost (lambda yang dibutuhkan dari NavHost)*/
        navigation(
            startDestination = ScreenNavigation.TimerList.route,
            route = "main_graph"
        ) {

            composable(ScreenNavigation.TimerList.route) {
                AlarmListScreen(
                    alarmRepository = alarmRepository,
                    onNavigate = { timerId ->
                        navController.navigate(
                            ScreenNavigation.EditTimer.createRoute(
                                timerId.toString()
                            )
                        )
                    }
                )
            }
            composable(ScreenNavigation.StopWatch.route) {
                StopWatchScreen(
                    stopWatchRepository = stopWatchRepository
                )
            }
        }

        composable(route = ScreenNavigation.AddTimer.route) {
            AlarmEditScreen(
                timerId = null,
                timerRepository = alarmRepository,
                onNavigateToTimerList = {
                    navController.navigate(ScreenNavigation.TimerList.route)
                }
            )
        }

        composable(
            route = ScreenNavigation.EditTimer.route, arguments = listOf(
                navArgument("uuidString") { type = NavType.StringType }
            )) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("uuidString")

            val uuid = id?.let { Uuid.parse(it) }

            id
            AlarmEditScreen(
                onNavigateToTimerList = {
                    navController.navigate(ScreenNavigation.TimerList.route)
                },
                timerRepository = alarmRepository,
                timerId = uuid

            )
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


