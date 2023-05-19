package com.pickyberry.rtuitlab_recruit.presentation.coin_details


import android.content.Context
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.pickyberry.rtuitlab_recruit.R
import java.util.*


@Composable
fun HistoricalDataChart(
    modifier: Modifier = Modifier,
    data: List<Pair<Float, Float>> = emptyList(),
    context: Context
) {

    val spacing = 100f
    val max = remember(data) { data.maxOfOrNull { it.second }?.toFloat() ?: 1f }
    val min = remember(data) { data.minOfOrNull { it.second }?.toFloat() ?: 0f }
    val lineColor = MaterialTheme.colors.secondary
    val textColor = MaterialTheme.colors.onBackground.toArgb()
    val paintForHorizontalAxis = Paint().apply {
        color = textColor
        textAlign = Paint.Align.CENTER
        textSize = 20f
    }

    Canvas(modifier = modifier) {

        //Months at the bottom line
        val months = getMonthNames(data[0].first.toLong(),context)
        val spaceBetweenMonths = (size.width - spacing) / months.size
        months.forEachIndexed { index, it ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    it,
                    spacing + index * spaceBetweenMonths + spaceBetweenMonths / 2,
                    size.height,
                    paintForHorizontalAxis
                )
            }
        }


        //Prices at the left
        val step = (max - min) / 5f
        (0..5).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    String.format("%.3f", min + step * i),
                    60f,
                    size.height - spacing - i * size.height / 5f,
                    paintForHorizontalAxis
                )
            }
        }


        //The path for chart line
        val strokePath = Path().apply {
            val height = size.height
            for (i in data.indices) {
                val price = data[i]
                val nextInfo = data.getOrNull(i + 1) ?: data.last()
                val leftRatio = (price.second - min) / (max - min)
                val rightRatio = (nextInfo.second - min) / (max - min)
                val x1 = spacing + i * (size.width - spacing) / 91f
                val y1 = height - spacing - (leftRatio * height)
                val x2 = spacing + (i + 1) * (size.width - spacing) / 91f
                val y2 = height - spacing - (rightRatio * height)
                if (i == 0) moveTo(x1, y1)
                quadraticBezierTo(x1, y1, (x1 + x2) / 2f, (y1 + y2) / 2f)
            }
        }

        //Draw the line
        drawPath(
            path = strokePath,
            color = lineColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

    }
}

//Display four months starting from the first piece of data time
fun getMonthNames(timestamp: Long, context: Context): List<String> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    val monthNames = context.resources.getStringArray(R.array.months)
    val startIndex = calendar.get(Calendar.MONTH)
    val months = mutableListOf<String>()
    for (i in 0 until 4) {
        val index = (startIndex + i) % 12
        months.add(monthNames[index])
    }
    return months
}
