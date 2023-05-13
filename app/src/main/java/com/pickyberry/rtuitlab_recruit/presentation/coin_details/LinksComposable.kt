package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LinksComposable(links: Map<String, String>) {
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
                if (links["homepage"] != null)
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
                            modifier = Modifier.weight(3f),
                            textAlign = TextAlign.End
                        )
                    }

                if (links["blockchain_site"] != null)
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
                            modifier = Modifier.weight(3f),
                            textAlign = TextAlign.End
                        )
                    }

                Spacer(modifier = Modifier.height(5.dp))
            }

        }
    }
}

