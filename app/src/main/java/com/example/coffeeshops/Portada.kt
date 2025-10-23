package com.example.coffeeshops

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// ---------- Datos ----------
data class infousuarios(
    @DrawableRes val foto: Int,
    val nombre: String,
    val lugar: String,
)

fun getinfousuarios(): List<infousuarios> = listOf(
    infousuarios(R.drawable.images,  "Antico Caffe Greco",  "St.Italy, Rome"),
    infousuarios(R.drawable.images1, "Coffee Room",         "St.Germany, Berlin"),
    infousuarios(R.drawable.images2, "Coffee Ibiza",        "St.Colón, Madrid"),
    infousuarios(R.drawable.images3, "Pudding Coffee Shop", "St.Diagonal, Barcelona"),
    infousuarios(R.drawable.images4, "L'Express",           "St.Piccadilly Circus, London"),
    infousuarios(R.drawable.images5, "Coffee Corner",       "St.Angel Guimerà, Valencia"),
    infousuarios(R.drawable.images6, "Antico Caffe Greco",  "St.Italy, Rome"),
)

// ---------- App + Nav ----------
@Composable
fun CoffeeShopsApp() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "portada") {
        composable("portada") { Portada(nav) }
        composable("detalle/{nombre}") { backStack ->
            val nombre = backStack.arguments?.getString("nombre") ?: ""
            DetalleCafeteria(nav, nombre) // ← SOLO declarada en DetallesCafeteria.kt
        }
    }
}

// ---------- UI común ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(title: String) {
    var show by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { /* no-op */ }) { Icon(Icons.Filled.Menu, null) }
        },
        actions = {
            IconButton(onClick = { show = !show }) { Icon(Icons.Filled.MoreVert, null) }
            DropdownMenu(expanded = show, onDismissRequest = { show = false }) {
                DropdownMenuItem(text = { Text("Settings") }, onClick = { show = false })
                DropdownMenuItem(text = { Text("Help") }, onClick = { show = false })
            }
        }
    )
}

// ---------- Pantalla lista ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Portada(navController: NavHostController) {
    val cafeterias = remember { getinfousuarios() }

    Scaffold(
        topBar = { MyTopAppBar("CoffeeShop") },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { /* no-op */ }) { Text("Inc") }
        }
    ) { inner ->
        LazyColumn(
            contentPadding = inner,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(cafeterias) { c ->
                CafeCardSimple(
                    foto = c.foto,
                    nombre = c.nombre,
                    lugar = c.lugar,
                    onClick = { navController.navigate("detalle/${Uri.encode(c.nombre)}") }
                )
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

// ---------- Item + Rating ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CafeCardSimple(
    foto: Int,
    nombre: String,
    lugar: String,
    onClick: () -> Unit
) {
    var rating by remember { mutableStateOf(0) }

    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column {
            Image(
                painter = painterResource(foto),
                contentDescription = nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(16.dp)) {
                Text(nombre, fontFamily = FontFamily(Font(R.font.aliviaregular)),fontSize = 30.sp,)
                Text(lugar, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                RatingBarSimple(value = rating, onChange = { rating = it })
                Spacer(Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))
                Button(onClick = { /* reservar */ }, modifier = Modifier.align(Alignment.End)) {
                    Text("Reserve")
                }
            }
        }
    }
}

@Composable
fun RatingBarSimple(value: Int, onChange: (Int) -> Unit, max: Int = 5) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(max) { i ->
            val pos = i + 1
            Icon(
                imageVector = if (value >= pos) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onChange(pos) }
            )
        }
        Spacer(Modifier.width(8.dp))
        Text("$value/$max")
    }
}
