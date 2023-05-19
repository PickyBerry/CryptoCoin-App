package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity

@Composable
fun LinksComposable(links: Map<String, String>,context: Context) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = MaterialTheme.colors.primary,
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(2.dp, MaterialTheme.colors.onBackground),
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
        ) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))
                if (links["homepage"] != null && links["homepage"]!!.isNotEmpty())
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Home page",
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = links["homepage"]!!,
                            modifier = Modifier.weight(3f).clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(links["homepage"]!!))
                                context.startActivity(intent)
                            },
                            textAlign = TextAlign.End,
                            color = Color(0,0,238)
                        )
                    }

                if (links["blockchain_site"] != null && links["blockchain_site"]!!.isNotEmpty())
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Blockchain site",
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = links["blockchain_site"]!!,
                            modifier = Modifier.weight(3f).clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(links["blockchain_site"]!!))
                                context.startActivity(intent)
                            },
                            textAlign = TextAlign.End,
                            color = Color(0,0,238)
                        )
                    }

                Spacer(modifier = Modifier.height(5.dp))
            }

        }
    }
}

