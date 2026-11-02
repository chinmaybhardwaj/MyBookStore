package com.cbhard.mybookstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbhard.mybookstore.ui.theme.MyBookStoreTheme

@Composable
fun ProductDetailScreen(book: Book, onAddToCart: () -> Unit = {}, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Breadcrumb
        Text(
            text = "Home / Non-Fiction / Self Help",
            color = AccentBlue,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Book Details Card
        Surface(
            color = SurfaceColor,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    // Book Cover
                    Box(
                        modifier = Modifier
                            .weight(0.4f)
                            .aspectRatio(0.7f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(book.coverColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = book.title,
                            color = if (book.coverColor == Color(0xFFF5F5DC)) Color.Black else Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp),
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Book Info
                    Column(modifier = Modifier.weight(0.6f)) {
                        Text(text = book.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary, lineHeight = 28.sp)
                        Spacer(modifier = Modifier.height(8.dp))

                        val authorText = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = TextSecondary)) { append("by ") }
                            withStyle(style = SpanStyle(color = AccentBlue, textDecoration = TextDecoration.Underline)) { append(book.author) }
                        }
                        Text(text = authorText, fontSize = 16.sp)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = book.description, color = TextSecondary, fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "Published by: ", color = TextSecondary, fontSize = 12.sp)
                        Text(text = "ABC Publishers", color = AccentBlue, fontSize = 12.sp, textDecoration = TextDecoration.Underline)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = book.format, color = TextPrimary, fontSize = 14.sp)
                        val categoryText = buildAnnotatedString {
                            book.categories.forEachIndexed { index, category ->
                                withStyle(style = SpanStyle(color = AccentBlue, textDecoration = TextDecoration.Underline)) { append(category) }
                                if (index < book.categories.size - 1) {
                                    withStyle(style = SpanStyle(color = TextSecondary)) { append(", ") }
                                }
                            }
                        }
                        Text(text = categoryText, fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "₹${book.price}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(text = book.deliveryInfo, color = TextSecondary, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(Icons.Outlined.ShoppingCart, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add to Cart", color = Color.White)
                    }

                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("Add to Wishlist", color = TextPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Outlined.BookmarkBorder, contentDescription = null, tint = TextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Language", color = TextSecondary, fontSize = 12.sp)
                        Text("English", color = AccentBlue, fontSize = 14.sp, textDecoration = TextDecoration.Underline)
                    }
                    Column {
                        Text("Rating", color = TextSecondary, fontSize = 12.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(4) { Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp)) }
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                        }
                    }
                    Column {
                        Text("Sells", color = TextSecondary, fontSize = 12.sp)
                        Text("145 copies sold", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // About the Writer
        Text("About the writer", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            // Profile image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.DarkGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(book.author, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${book.author} is a writer, minimalist, and productivity coach based in San Francisco. With a passion for intentional living, ${book.author} has dedicated his career to helping individuals simplify their lives — one habit, one space, and one thought at a time.",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Reviews Section
        Text("Reviews", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Leave Your Review", color = TextSecondary, fontSize = 12.sp)
            Text("0/100", color = TextSecondary, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Placeholder text", color = TextSecondary) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = SurfaceColor,
                focusedContainerColor = SurfaceColor,
                unfocusedBorderColor = Color.DarkGray,
                focusedBorderColor = AccentBlue,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                repeat(5) { Icon(Icons.Default.Star, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(24.dp)) }
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Submit", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sample Review
        Surface(color = SurfaceColor, shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("John Smith", fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Text("The accordion component delivers large amounts of content in a small space through progressive disclosure. The user gets key details about the underlying content and can choose to expand that content within the constraints of the accordion.", color = TextSecondary, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    repeat(5) { Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp)) }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Related Reads (Reusing the BookSection from MainActivity)
        BookSection(title = "Related Reads", books = bestSellers)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    MyBookStoreTheme(darkTheme = true) {
        ProductDetailScreen(book = sampleBooks[0]) // Preview with "The Art of Focus"
    }
}