package com.example.monprofil.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monprofil.ActeursDestination
import com.example.monprofil.FilmsDestination
import com.example.monprofil.ProfilDestination
import com.example.monprofil.SeriesDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmScreen(navController: NavController, viewModel: MainViewModel, onClick: (id: String) -> Unit) {
    // Observer les films dans le ViewModel
    var selectedTab by remember { mutableStateOf("film") } // Onglet sélectionné
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // Texte de recherche
    val movies by viewModel.movies.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFADD8E6)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Movie, contentDescription = "Film") },
                    label = { Text("Film") },
                    selected = selectedTab == "film",
                    onClick = {
                        selectedTab = "film"
                        navController.navigate(FilmsDestination())
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Tv, contentDescription = "Series") },
                    label = { Text("Series") },
                    selected = selectedTab == "series",
                    onClick = {
                        selectedTab = "series"
                        navController.navigate(SeriesDestination())
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Acteur") },
                    label = { Text("Acteur") },
                    selected = selectedTab == "acteur",
                    onClick = {
                        selectedTab = "acteur"
                        navController.navigate(ActeursDestination())
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
                    label = { Text("Profil") },
                    selected = selectedTab == "profil",
                    onClick = {
                        selectedTab = "profil"
                        navController.navigate(ProfilDestination())
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Ligne avec texte à gauche et barre de recherche à droite
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // Aligner verticalement au centre
                horizontalArrangement = Arrangement.SpaceBetween // Espacer les éléments
            ) {
                // Texte à gauche
                Text(
                    text = "FavAPP",
                    fontSize = 32.sp, // Taille du texte
                    fontWeight = FontWeight.Bold, // Texte en gras
                    color = Color.Black, // Couleur du texte
                    modifier = Modifier.weight(2f) // Prendre de l'espace à gauche
                )

                var searchText by remember { mutableStateOf(TextFieldValue("")) }
                val keyboardController = LocalSoftwareKeyboardController.current

                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Rechercher film ...") },
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.searchMovies(searchText.text) // Déclenche la recherche avec le texte saisi
                            keyboardController?.hide() // Masque le clavier après la recherche
                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
            // Grille des films (responsive)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp), // Responsive grid
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(movies) { movie ->
                    FilmCard(movie = movie, onClick)
                }
            }
        }
    }


}






