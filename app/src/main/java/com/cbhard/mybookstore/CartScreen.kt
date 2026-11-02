package com.cbhard.mybookstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun CartScreen(onContinueShopping: () -> Unit = {}, modifier: Modifier = Modifier) {
    var showPaymentSheet by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    if (showPaymentSheet) {
        PaymentBottomSheet(
            onDismiss = { showPaymentSheet = false },
            onPaySuccess = {
                showPaymentSheet = false
                showSuccessDialog = true
            }
        )
    }

    if (showSuccessDialog) {
        PaymentSuccessDialog(
            purchasedBooks = listOf(sampleBooks[0], sampleBooks[2]),
            onContinueShopping = {
                showSuccessDialog = false
                onContinueShopping()
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Shopping Cart",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Cart Items
        CartItem(book = sampleBooks[0]) // The Art of Focus
        Spacer(modifier = Modifier.height(16.dp))
        CartItem(book = sampleBooks[2]) // The Path to Success

        Spacer(modifier = Modifier.height(24.dp))

        AddressForm()

        Spacer(modifier = Modifier.height(24.dp))

        OrderSummary(onPayClicked = { showPaymentSheet = true })
    }
}

@Composable
fun CartItem(book: Book) {
    Surface(
        color = SurfaceColor,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book Cover
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(book.coverColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = book.title,
                    color = if (book.coverColor == Color(0xFFF5F5DC)) Color.Black else Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(text = book.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                val authorText = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = TextSecondary)) { append("by ") }
                    withStyle(style = SpanStyle(color = AccentBlue, textDecoration = TextDecoration.Underline)) { append(book.author) }
                }
                Text(text = authorText, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = book.format, color = TextSecondary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "₹${book.price}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(text = book.deliveryInfo, color = TextSecondary, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(12.dp))

                // Quantity Selector
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = TextPrimary)
                    }
                    Text(text = "1", color = TextPrimary, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 8.dp))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase", tint = TextPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun AddressForm() {
    Surface(
        color = SurfaceColor,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Address", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(
                        checkedColor = AccentBlue,
                        uncheckedColor = TextSecondary
                    )
                )
                Text("Use Saved Address", color = TextPrimary)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DarkOutlinedTextField(label = "First Name", modifier = Modifier.weight(1f))
                DarkOutlinedTextField(label = "Last Name", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            DarkOutlinedTextField(label = "Address Line 2", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            DarkOutlinedTextField(label = "e-mail", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DarkOutlinedTextField(label = "City", modifier = Modifier.weight(1f))
                DarkOutlinedTextField(label = "Pin", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DarkOutlinedTextField(label = "Phone Number", modifier = Modifier.weight(1f))
                DarkOutlinedTextField(label = "State", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            DarkOutlinedTextField(label = "Country", modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun DarkOutlinedTextField(label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text(label, color = TextSecondary) },
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = AppBackground,
            focusedContainerColor = AppBackground,
            unfocusedBorderColor = Color.DarkGray,
            focusedBorderColor = AccentBlue,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            unfocusedLabelColor = TextSecondary,
            focusedLabelColor = AccentBlue
        )
    )
}

@Composable
fun OrderSummary(onPayClicked: () -> Unit = {}) {
    Surface(
        color = SurfaceColor,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Grand Total", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))

            SummaryRow("Price (2 items)", "₹508.00")
            Spacer(modifier = Modifier.height(8.dp))
            SummaryRow("Tax", "₹62.00")
            Spacer(modifier = Modifier.height(8.dp))
            SummaryRow("Delivery Charges", "Free")

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Apply Coupon", color = TextSecondary) },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = AppBackground,
                        focusedContainerColor = AppBackground,
                        unfocusedBorderColor = Color.DarkGray,
                        focusedBorderColor = AccentBlue,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                    modifier = Modifier.height(52.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Apply", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SummaryRow("Discount", "₹100")

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total Amount", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("₹470", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onPayClicked,
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Pay Now", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextSecondary)
        Text(text = value, color = TextPrimary)
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    MyBookStoreTheme(darkTheme = true) {
        CartScreen()
    }
}