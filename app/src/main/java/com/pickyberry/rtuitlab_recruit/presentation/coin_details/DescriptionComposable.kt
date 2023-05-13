package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionComposable(text:String) {
    Surface(
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(3.dp, MaterialTheme.colors.onBackground),
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 14.sp,
            softWrap = true
        )
    }
}