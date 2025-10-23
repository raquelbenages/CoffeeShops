package com.example.coffeeshops

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCafeteria(navController: NavHostController, nombre: String) {

    val comentarios = remember {
        val base = listOf(
            "Buen café",
            "Me encantó",
            "Súper satisfactorio y de calidad",
            "100% recomendado, café de calidad y buen ambiente",
            "Gran variedad para elegir",
            "Buen ambiente",
        )
        List(30) { base[it % base.size] }
    }


    val context = LocalContext.current
    val rvState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
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
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(180.dp),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                state = rvState,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
                modifier = Modifier.fillMaxSize()
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
            }

            val showButton by remember {
                derivedStateOf { rvState.firstVisibleItemIndex > 0 }
            }

            AnimatedVisibility(
                visible = showButton,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                FloatingActionButton(onClick = {

                }) {
                    Text("Añadir un nuevo comentario")
                }
            }
        }
    }
}