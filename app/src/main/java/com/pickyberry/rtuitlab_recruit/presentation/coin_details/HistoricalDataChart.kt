package com.pickyberry.rtuitlab_recruit.presentation.coin_details



import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import java.util.*
import kotlin.math.round



@Composable
fun HistoricalDataChart(
    modifier: Modifier = Modifier,
    data: List<Pair<Float,Float>> = emptyList()
) {
    val spacing = 100f

    val max = remember(data) { data.maxOfOrNull { it.second }?.toFloat() ?: 1f }
    val min = remember(data) { data.minOfOrNull { it.second }?.toFloat() ?: 0f }
    val lineColor = MaterialTheme.colors.secondary

    val paintForHorizontalAxis = Paint().apply {
        color = android.graphics.Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = 20f
    }

    Canvas(modifier = modifier) {


        val months = getMonthAbbreviations(data[0].first.toLong())
        val spaceBetweenMonths = (size.width - spacing) / months.size
        months.forEachIndexed{index,it ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    it,
                    spacing + index * spaceBetweenMonths+spaceBetweenMonths/2,
                    size.height,
                    paintForHorizontalAxis
                )
            }
        }


        val step = (max - min) / 5f
        (0..5).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(min + step * i).toString(),
                    30f,
                    size.height - spacing - i * size.height / 5f,
                    Paint()
                )
            }
        }


        val strokePath = Path().apply {
            val height = size.height
            for (i in data.indices) {
                val price = data[i]
                val nextInfo = data.getOrNull(i + 1) ?: data.last()
                val leftRatio = (price.second - min) / (max - min)
                val rightRatio = (nextInfo.second - min) / (max - min)
                val x1 = spacing + i * (size.width - spacing)/91f
                val y1 = height - spacing - (leftRatio * height)
                val x2 = spacing + (i + 1) * (size.width - spacing)/91f
                val y2 = height - spacing - (rightRatio * height)
                if (i == 0) moveTo(x1, y1)
                quadraticBezierTo(x1, y1, (x1 + x2) / 2f, (y1 + y2) / 2f)
            }
        }

        drawPath(
            path=strokePath,
            color=lineColor,
            style=Stroke(
                width=3.dp.toPx(),
                cap=StrokeCap.Round
            )
        )

    }
}

fun getMonthAbbreviations(timestamp: Long): List<String> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    val monthNames = arrayOf("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER")
    val startIndex = calendar.get(Calendar.MONTH)
    val months = mutableListOf<String>()
    for (i in 0 until 4) {
        val index = (startIndex + i) % 12
        months.add(monthNames[index])
    }
    return months
}
