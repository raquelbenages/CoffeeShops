package com.example.coffeeshops

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun Portada(navController: NavHostController) {

    data class infousuarios(
        @DrawableRes var foto : Int,
        var nombre: String,
        var lugar: String,
    )

    fun getinfousuarios(): List<infousuarios> {
        return listOf(
            infousuarios(R.drawable.images, "Antico Caffe Greco", "St.Italy, Rome"),
            infousuarios(R.drawable.images1, "Coffe Room", "St.Germany, Berlin"),
            infousuarios(R.drawable.images2, "Coffe Ibiza", "St.Colón, Madrid"),
            infousuarios(R.drawable.images3, "Pudding Coffe Shop", "St.Diagonal, Barcelona"),
            infousuarios(R.drawable.images4, "L'Express", "St.Picadilly Circus, London"),
            infousuarios(R.drawable.images5, "Coffe Corner", "St.Angel Guimera, Valencia"),
            infousuarios(R.drawable.images6, "Antico Caffe Greco", "St.Italy, Rome"),
            )
    }

    val cafeterias = getinfousuarios()

    Scaffold(
        topBar = {
            MyTopAppBar()
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { /*  */ }) { Text("Inc") }
        },
        content = { innerPadding ->
            MyCard()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(){
    TopAppBar(
        title = { Text("CoffeShop") },
        navigationIcon = {
            IconButton(onClick = { /* */ }) {
                Icon(Icons.Filled.Menu, contentDescription = "")
            }
            IconButton(onClick = { /* */ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "" )
            }
        },
    )
}

@Composable
fun MyCard() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), elevation = 12.dp, shape = MaterialTheme.shapes.medium) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            LazyColumn {
                items(cafeterias) { cafeteria ->
                    MessageRow(message)
                }
            }
        }
    }
}

