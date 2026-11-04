package com.nishanth.drivermovement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ReceiptLanguage { ENGLISH, TAMIL }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PerLineDemoScreen()
                }
            }
        }
    }
}

@Composable
fun PerLineDemoScreen(
    initialLanguage: ReceiptLanguage = ReceiptLanguage.ENGLISH
) {
    var language by rememberSaveable { mutableStateOf(initialLanguage) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Compose 1.13: Per-Line Typography",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Choose a language, then compare Fixed vs PerLine with identical text and styles.",
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { language = ReceiptLanguage.ENGLISH },
                enabled = language != ReceiptLanguage.ENGLISH,
                modifier = Modifier.weight(1f)
            ) { Text("English Layout") }
            Button(
                onClick = { language = ReceiptLanguage.TAMIL },
                enabled = language != ReceiptLanguage.TAMIL,
                modifier = Modifier.weight(1f)
            ) { Text("Tamil Layout") }
        }
        Text(
            text = if (language == ReceiptLanguage.ENGLISH) "English payment receipt" else "தமிழ் கட்டண ரசீது",
            style = MaterialTheme.typography.titleMedium
        )
        ReceiptCard(language, LineHeightStyle.Mode.Fixed, "BEFORE · Fixed")
        ReceiptCard(language, LineHeightStyle.Mode.PerLine, "AFTER · PerLine")
        Text(
            text = "Note: The difference is most visible when a paragraph mixes font sizes. " +
                "PerLine is not a general-purpose fix for every Tamil font or clipping issue.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ReceiptCard(
    language: ReceiptLanguage,
    mode: LineHeightStyle.Mode,
    title: String
) {
    val receipt = remember(language) {
        buildAnnotatedString {
            withStyle(SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)) {
                append(if (language == ReceiptLanguage.ENGLISH) "Payment Successful\n" else "பணம் செலுத்தப்பட்டது\n")
            }
            withStyle(SpanStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold)) {
                append("₹25,000\n")
            }
            withStyle(SpanStyle(fontSize = 19.sp)) {
                append(
                    if (language == ReceiptLanguage.ENGLISH)
                        "Your transfer was completed successfully\n"
                    else
                        "பணம் வெற்றிகரமாக அனுப்பப்பட்டது\n"
                )
            }
            withStyle(SpanStyle(fontSize = 13.sp)) {
                append(
                    if (language == ReceiptLanguage.ENGLISH)
                        "Transaction ID: TXN-2026-1042"
                    else
                        "பரிவர்த்தனை எண்: TXN-2026-1042"
                )
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = receipt,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 24.sp,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Proportional,
                        trim = LineHeightStyle.Trim.None,
                        mode = mode
                    )
                )
            )
        }
    }
}

@Preview(name = "English comparison", showBackground = true, widthDp = 393, heightDp = 850)
@Composable
private fun EnglishPreview() {
    MaterialTheme { PerLineDemoScreen(initialLanguage = ReceiptLanguage.ENGLISH) }
}

@Preview(name = "Tamil comparison", showBackground = true, widthDp = 393, heightDp = 850)
@Composable
private fun TamilPreview() {
    MaterialTheme { PerLineDemoScreen(initialLanguage = ReceiptLanguage.TAMIL) }
}

@Preview(name = "Large font - Tamil", showBackground = true, widthDp = 393, heightDp = 850, fontScale = 1.4f)
@Composable
private fun TamilLargeFontPreview() {
    MaterialTheme { PerLineDemoScreen(initialLanguage = ReceiptLanguage.TAMIL) }
}
