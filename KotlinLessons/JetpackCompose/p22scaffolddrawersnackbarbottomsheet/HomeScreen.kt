package ru.korobeynikov.p22scaffolddrawersnackbarbottomsheet

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    //ModalBottomSheet
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    Text("Content", modifier = Modifier.clickable {
        scope.launch {
            showBottomSheet = true
        }
    })
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            repeat(10) {
                Text("Item $it", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenBottomSheetScaffold() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            repeat(5) {
                Text(text = "Item $it", modifier = Modifier.padding(16.dp))
            }
        }
    ) {
        Text("Content", modifier = Modifier.clickable {
            scope.launch {
                scaffoldState.bottomSheetState.run {
                    if (currentValue == SheetValue.Expanded) partialExpand() else expand()
                }
            }
        })
    }
}

@Composable
fun HomeScreenDrawer() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.White,
        drawerContent = {
            Column {
                Text("Item 1")
                Text("Item 2")
                Text("Item 3")
            }
        }
    ) {
        Scaffold { scaffoldPadding ->
            Column(modifier = Modifier.padding(scaffoldPadding)) {
                Text(text = "Content", modifier = Modifier.clickable {
                    scope.launch {
                        drawerState.open()
                    }
                })
            }
        }
    }
}

@Composable
fun HomeScreenSnackbarWithAction() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        })
    { scaffoldPadding ->
        Column(modifier = Modifier.padding(scaffoldPadding)) {
            Text("Content", modifier = Modifier.clickable {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        "Snackbar message",
                        actionLabel = "Action",
                        duration = SnackbarDuration.Short
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> Toast.makeText(
                            context,
                            "Snackbar dismissed",
                            Toast.LENGTH_SHORT
                        ).show()

                        SnackbarResult.ActionPerformed -> Toast.makeText(
                            context,
                            "Snackbar action performed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }
}

@Composable
fun HomeScreenSnackbar() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        })
    { scaffoldPadding ->
        Column(modifier = Modifier.padding(scaffoldPadding)) {
            Text("Content", modifier = Modifier.clickable {
                scope.launch {
                    snackbarHostState.showSnackbar("Snackbar message")
                }
            })
        }
    }
}

@Composable
fun HomeScreenScaffoldBottomNavigation() {
    Scaffold(
        content = { scaffoldPadding ->
            Column(modifier = Modifier.padding(scaffoldPadding)) {
                Text("Content")
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = null)
                    },
                    label = {
                        Text("Home")
                    },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = {
                        Icon(Icons.Filled.Call, contentDescription = null)
                    },
                    label = {
                        Text("Call")
                    },
                    selected = false,
                    onClick = {}
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenScaffold() {
    ModalNavigationDrawer(
        scrimColor = Color.White,
        drawerContent = {
            Column {
                Text("Drawer item 1", modifier = Modifier.padding(8.dp))
                Text("Drawer item 2", modifier = Modifier.padding(8.dp))
                Text("Drawer item 3", modifier = Modifier.padding(8.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Top bar")
                    }
                )
            },
            bottomBar = {
                BottomAppBar {
                    Text(text = "Bottom bar")
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
                }
            }
        ) { scaffoldPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Content")
            }
        }
    }
}

@Composable
fun HomeScreenSimpleScaffold() {
    ModalNavigationDrawer(
        scrimColor = Color.White,
        drawerContent = {
            Text("Drawer content")
        }
    ) {
        Scaffold(
            topBar = {
                Text(text = "Top bar")
            },
            bottomBar = {
                Text(text = "Bottom bar")
            },
            floatingActionButton = {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
        ) { scaffoldPadding ->
            Column(modifier = Modifier.padding(scaffoldPadding)) {
                Text("Content")
            }
        }
    }
}

@Composable
fun HomeScreenFABContentSlot() {
    ExtendedFloatingActionButton(
        text = {
            Text("Some text")
        },
        icon = {
            Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
        },
        onClick = {}
    )
}

@Composable
fun HomeScreenButtonContentSlot() {
    Button(onClick = {}) {
        Text("Some text")
        Spacer(modifier = Modifier.width(8.dp))
        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
    }
}