package com.example.coffeeshops

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCafeteria(navController: NavHostController, nombre: String) {

    // Datos base
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

    // ---- ESTADO SearchBar ----
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val filteredComentarios by remember(query) {
        derivedStateOf {
            if (query.isBlank()) comentarios
            else comentarios.filter { it.contains(query, ignoreCase = true) }
        }
    }

    val rvState: LazyStaggeredGridState = rememberLazyStaggeredGridState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(170.dp)
                    .background(Color(0xFFFF80AB)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF80AB),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "CoffeeShops", fontSize = 20.sp,
                            style = MaterialTheme.typography.headlineMedium)
                        Text(text = nombre, fontFamily = FontFamily(Font(R.font.aliviaregular)),
                            fontSize = 30.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Close, contentDescription = "Atrás")
                    }
                },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(text = { Text("Compartir") }, onClick = { showMenu = false })
                        DropdownMenuItem(text = { Text("Álbum") }, onClick = { showMenu = false })
                    }
                }
            )
        }
    ) { inner ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {

            // -------- SearchBar --------
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Buscar comentarios…") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = ""; active = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Limpiar")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Sugerencias mientras la barra está activa
                val sugerencias = comentarios
                    .distinct()
                    .filter { it.contains(query, ignoreCase = true) }
                    .take(6)

                sugerencias.forEach { s ->
                    ListItem(
                        headlineContent = { Text(s) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                query = s
                                active = false
                            }
                    )
                    Divider()
                }
            }

            // -------- Lista filtrada --------
            Box(Modifier.fillMaxSize()) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(180.dp),
                    verticalItemSpacing = 12.dp,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    state = rvState,
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredComentarios) { comentario ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Text(text = comentario, modifier = Modifier.padding(16.dp))
                        }
                    }
                }

                val showButton by remember { derivedStateOf { rvState.firstVisibleItemIndex > 0 } }

                this@Column.AnimatedVisibility(
                    visible = showButton,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    FloatingActionButton(
                        onClick = { /* TODO: acción para añadir comentario */ },
                        containerColor = Color(0xFFFF80AB),
                        contentColor = Color.White,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.height(64.dp).width(280.dp)
                    ) {
                        Text(
                            text = "Añadir un nuevo comentario",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
