package com.nishanth.learnapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/*
    Required Dependencies
// Compose Foundation 1.13 undo API
    implementation(
        "androidx.compose.foundation:foundation:1.13.0-beta02"
    )

    // State-based Material 3 text field
    implementation(
        "androidx.compose.material3:material3:1.5.0-beta02"
    )

    // Activity Compose
    implementation(
        "androidx.activity:activity-compose:1.12.0"
    )

    // Android Studio previews
    implementation(
        "androidx.compose.ui:ui-tooling-preview:1.13.0-beta02"
    )

    debugImplementation(
        "androidx.compose.ui:ui-tooling:1.13.0-beta02"
    )
    
*/




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PaymentRemarksScreen()
                }
            }
        }
    }
}

@Composable
fun PaymentRemarksScreen() {
    val remarks = rememberTextFieldState(
        initialText = "Rent payment for November"
    )

    val history = remarks.undoState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Payment Remarks",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Edit the remarks, then try Undo and Redo.",
            style = MaterialTheme.typography.bodyMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Transaction: ₹25,000",
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedTextField(
                    state = remarks,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Remarks") },
                    lineLimits = TextFieldLineLimits.MultiLine(
                        minHeightInLines = 3,
                        maxHeightInLines = 6
                    )
                )

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { history.undo() },
                        enabled = history.canUndo
                    ) {
                        Text("Undo")
                    }

                    Button(
                        onClick = { history.redo() },
                        enabled = history.canRedo
                    ) {
                        Text("Redo")
                    }
                }

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = {
                            remarks.clearText()
                        }
                    ) {
                        Text("Clear Text")
                    }

                    TextButton(
                        onClick = {
                            history.clearHistory()
                        }
                    ) {
                        Text("Clear History")
                    }
                }
            }
        }

        HorizontalDivider()

        Text(
            text = "Current Editor State",
            style = MaterialTheme.typography.titleMedium
        )

        Text("Can Undo: ${history.canUndo}")
        Text("Can Redo: ${history.canRedo}")
        Text("Characters: ${remarks.text.length}")

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Current remarks:",
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = remarks.text.toString().ifEmpty {
                "(Empty)"
            }
        )
    }
}

@Preview(
    name = "Payment Remarks - Light",
    showBackground = true
)
@Composable
private fun PaymentRemarksLightPreview() {
    MaterialTheme {
        PaymentRemarksScreen()
    }
}

@Preview(
    name = "Payment Remarks - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PaymentRemarksDarkPreview() {
    MaterialTheme {
        PaymentRemarksScreen()
    }
}
