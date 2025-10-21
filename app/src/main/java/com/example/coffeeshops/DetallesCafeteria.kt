package com.example.coffeeshops

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCafeteria(navController: NavHostController, nombre: String) {

    val comentarios = remember {
        List(30) { i -> "Comentario $i · Muy buen café ☕" }
    }

    val gridState = rememberLazyStaggeredGridState()
    var mostrarFab by remember { mutableStateOf(true) }

    // Ocultar FAB al bajar, mostrar al subir
    LaunchedEffect(gridState) {
        var lastIndex = 0
        var lastOffset = 0
        snapshotFlow { gridState.firstVisibleItemIndex to gridState.firstVisibleItemScrollOffset }
            .collect { (idx, off) ->
                val subiendo = idx < lastIndex || (idx == lastIndex && off < lastOffset)
                mostrarFab = subiendo
                lastIndex = idx
                lastOffset = off
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(nombre) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Atrás")
                    }
                },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = { showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Help") },
                            onClick = { showMenu = false }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = mostrarFab) {
                ExtendedFloatingActionButton(onClick = { /* agregar comentario */ }) {
                    Text("Add new comment")
                }
            }
        }
    ) { inner ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(180.dp),
            contentPadding = inner,
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            state = gridState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(comentarios) { comentario ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = comentario,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}
