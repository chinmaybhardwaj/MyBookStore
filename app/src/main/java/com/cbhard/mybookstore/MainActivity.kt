package com.cbhard.mybookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cbhard.mybookstore.ui.theme.MyBookStoreTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBookStoreTheme(darkTheme = true) {
                BookStoreApp()
            }
        }
    }
}

// --- Theme Colors ---
val AppBackground = Color(0xFF1E1E1E)
val SurfaceColor = Color(0xFF2D2D2D)
val TextPrimary = Color(0xFFE0E0E0)
val TextSecondary = Color(0xFFA0A0A0)
val AccentBlue = Color(0xFF64B5F6)

// --- Data Models ---
data class Book(
    val title: String,
    val author: String,
    val description: String,
    val format: String,
    val categories: List<String>,
    val price: Int,
    val deliveryInfo: String,
    val coverColor: Color
)

val sampleBooks = listOf(
    Book(
        "The Art of Focus", "Arjun Patel",
        "Practical guide to mastering focus & boosting productivity every day.",
        "Paperback", listOf("Non-fiction", "Self Help"),
        399, "Delivery by Mon, 21 Jul", Color(0xFFF5F5DC)
    ),
    Book(
        "The Art of Learning", "Raj Patel",
        "Master the mindset and methods for effective lifelong learning.",
        "Paperback", listOf("Non-fiction", "Self Help"),
        259, "Delivery by Mon, 21 Jul", Color(0xFFE53935)
    ),
    Book(
        "The Path to Success", "James Wright",
        "A practical guide to achieving goals with clarity and confidence.",
        "Paperback", listOf("Non-fiction", "Self Help"),
        359, "Delivery by Mon, 21 Jul", Color(0xFF0288D1)
    )
)

val bestSellers = listOf(
    Book(
        "The Midnight Hour", "James Adams",
        "Haunting tale of a man's journey & the shadows of a forgotten past.",
        "Paperback", listOf("Fiction", "Thriller", "Horror"),
        299, "Delivery by Mon, 21 Jul", Color(0xFF004D40)
    ),
    Book(
        "Beneath the Stars", "Jessica Martin",
        "A heartwarming tale, where two souls discover who you need.",
        "Hard Cover", listOf("Fiction", "Love", "Drama"),
        499, "Delivery by Mon, 21 Jul", Color(0xFF4527A0)
    ),
    Book(
        "The Final Frontier", "Laura Mitchell",
        "A mission to space secrets to change humanity forever.",
        "Paperback", listOf("Fiction", "Thriller"),
        359, "Delivery by Mon, 21 Jul", Color(0xFF006064)
    )
)

val categories = listOf(
    "All", "Romance", "Mystery", "Science Fiction", "Fantasy",
    "Historical", "Biography", "Self-help", "Memoir", "Travel",
    "Cooking", "Children's", "Young Adult", "Comics & Graphic Novels"
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BookStoreApp() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = SurfaceColor,
                drawerContentColor = TextPrimary
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "My Account",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                NavigationDrawerItem(
                    label = { Text("My Orders", color = TextPrimary) },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
                )
                NavigationDrawerItem(
                    label = { Text("My Wishlist", color = TextPrimary) },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
                )
                NavigationDrawerItem(
                    label = { Text("My Writers", color = TextPrimary) },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
                )
                HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(vertical = 8.dp))
                
                Text(
                    text = "Categories",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    categories.forEach { category ->
                        NavigationDrawerItem(
                            label = { Text(category, color = TextPrimary) },
                            selected = category == "All",
                            onClick = { scope.launch { drawerState.close() } },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent,
                                selectedContainerColor = Color.DarkGray
                            ),
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            containerColor = AppBackground,
            topBar = {
                TopAppBar(
                    title = { Text("Book Worm", fontWeight = FontWeight.Bold, color = TextPrimary) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = TextPrimary)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("cart") }) {
                            BadgedBox(
                                badge = { Badge { Text("2") } }
                            ) {
                                Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart", tint = TextPrimary)
                            }
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.Person, contentDescription = "Profile", tint = TextPrimary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AppBackground
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController, 
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    HomeScreenContent(
                        onBookClick = { title ->
                            navController.navigate("details/$title")
                        }
                    )
                }
                composable(
                    route = "details/{title}",
                    arguments = listOf(navArgument("title") { type = NavType.StringType })
                ) { backStackEntry ->
                    val title = backStackEntry.arguments?.getString("title")
                    val book = sampleBooks.find { it.title == title } 
                        ?: bestSellers.find { it.title == title } 
                        ?: sampleBooks[0]
                    ProductDetailScreen(
                        book = book,
                        onAddToCart = { navController.navigate("cart") }
                    )
                }
                composable("cart") {
                    CartScreen(
                        onContinueShopping = {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = false }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(onBookClick: (String) -> Unit = {}, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        // Search Bar Placeholder
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search you want to read here", color = TextSecondary) },
            trailingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = SurfaceColor,
                focusedContainerColor = SurfaceColor,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
            )
        )
        
        // Filters Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChipItem("Language", "All")
            FilterChipItem("Format", "All")
            FilterChipItem("Price Range", "All")
            FilterChipItem("Sort by", "Relevance")
        }

        BookSection(title = "Recommended for You", books = sampleBooks, onBookClick = onBookClick)
        Spacer(modifier = Modifier.height(24.dp))
        BookSection(title = "Bestsellers this Month", books = bestSellers, onBookClick = onBookClick)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun FilterChipItem(label: String, value: String) {
    Surface(
        color = SurfaceColor,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.clickable { /* TODO */ }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(text = label, color = TextSecondary, fontSize = 10.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = value, color = TextPrimary, fontSize = 14.sp)
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(16.dp).padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun BookSection(title: String, books: List<Book>, onBookClick: (String) -> Unit = {}) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(books) { book ->
                BookCard(book = book, onBookClick = onBookClick)
            }
        }
    }
}

@Composable
fun BookCard(book: Book, onBookClick: (String) -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clickable { onBookClick(book.title) }
    ) {
        // Book Cover Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(8.dp))
                .background(book.coverColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = book.title,
                color = if (book.coverColor == Color(0xFFF5F5DC)) Color.Black else Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = book.title,
            color = TextPrimary,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        val authorText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = TextSecondary)) {
                append("by ")
            }
            withStyle(style = SpanStyle(color = AccentBlue, textDecoration = TextDecoration.Underline)) {
                append(book.author)
            }
        }
        Text(text = authorText, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = book.description,
            color = TextSecondary,
            fontSize = 12.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = book.format,
            color = TextPrimary,
            fontSize = 12.sp
        )

        val categoryText = buildAnnotatedString {
            book.categories.forEachIndexed { index, category ->
                withStyle(style = SpanStyle(color = AccentBlue, textDecoration = TextDecoration.Underline)) {
                    append(category)
                }
                if (index < book.categories.size - 1) {
                    withStyle(style = SpanStyle(color = TextSecondary)) {
                        append(", ")
                    }
                }
            }
        }
        Text(text = categoryText, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "₹${book.price}",
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(
            text = book.deliveryInfo,
            color = TextSecondary,
            fontSize = 11.sp
        )
    }
}
