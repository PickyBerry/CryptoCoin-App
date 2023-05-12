package com.pickyberry.rtuitlab_recruit.presentation.coin_details


import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round
import kotlin.math.roundToInt
import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
/*
class Entry(
    override val x: Float,
    override val y: Float,
) : ChartEntry {
    override fun withY(y: Float) = Entry(x, y)
}




@Composable
fun HistoricalDataChart(chartEntryModelProducer: ChartEntryModelProducer) {
    ProvideChartStyle(rememberChartStyle(listOf(MaterialTheme.colors.secondary))) {
        Chart(
            chart = lineChart(),
            chartModelProducer = chartEntryModelProducer,
            startAxis = startAxis(tickLength = 2.dp),
            bottomAxis = bottomAxis(valueFormatter = { _, _ -> " "}, tickLength = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
} */


/*@Composable
fun LineChart(
    data: List<Pair<Float,Float>> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {
    val spacing = 100f
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }
    val upperValue = remember(data) {
        (data.maxOfOrNull { it.second }?.plus(1))?.roundToInt() ?: 0
    }
    val lowerValue = remember(data) {
        (data.minOfOrNull { it.second }?.toInt() ?: 0)
    }
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / data.size
        (0 until data.size - 1 step 2).forEach { i ->
            val info = data[i]
        }
        val priceStep = (upperValue - lowerValue) / 5f
        (0..5).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + priceStep * i).toString(),
                    30f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }
        var lastX = 0f
        val strokePath = androidx.compose.ui.graphics.Path().apply {
            val height = size.height
            for (i in data.indices) {
                val info = data[i]
                val nextInfo = data.getOrNull(i + 1) ?: data.last()
                val leftRatio = (info.second - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.second - lowerValue) / (upperValue - lowerValue)
                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (i + 1) * spacePerHour
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if (i == 0) moveTo(x1, y1)
                lastX=(x1+x2)/2f
                quadraticBezierTo(x1, y1, (x1 + x2) / 2f, (y1 + y2) / 2f)
            }
        }
        val fillPath = Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply{
                lineTo(lastX,size.height-spacing)
                lineTo(spacing,size.height-spacing)
                close()
            }
        drawPath(
            path=fillPath,
            brush= Brush.verticalGradient(
                colors=listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height-spacing
            )
        )
        drawPath(
            path=strokePath,
            color=graphColor,
            style= Stroke(
                width=3.dp.toPx(),
                cap= StrokeCap.Round
            )
        )

    }
} */

/*
@Composable
fun LinearTransactionsChart(
    modifier: Modifier = Modifier,
    data: List<Pair<Float,Float>>
) {
    val maxValue = remember(data){ data.maxOfOrNull { it.second }?.roundToInt() ?: 0 }
    Canvas(modifier = modifier) {
        // Total number of transactions.
        val totalRecords = data.size

        // Maximum distance between dots (transactions)
        val lineDistance = size.width / (totalRecords + 1)

        // Canvas height
        val cHeight = size.height

        // Add some kind of a "Padding" for the initial point where the line starts.
        var currentLineDistance = 0F + lineDistance

        data.forEachIndexed { index, listItem ->
            if (totalRecords >= index + 2) {
                drawLine(
                    start = Offset(
                        x = currentLineDistance,
                        y = calculateYCoordinate(
                            higherTransactionRateValue = maxValue,
                            currentTransactionRate = data[index].second,
                            canvasHeight = cHeight
                        )
                    ),
                    end = Offset(
                        x = currentLineDistance + lineDistance,
                        y = calculateYCoordinate(
                            higherTransactionRateValue = maxValue,
                            currentTransactionRate = data[index+1].second,
                            canvasHeight = cHeight
                        )
                    ),
                    color = Color(40, 193, 218),
                    strokeWidth = Stroke.DefaultMiter
                )
            }
            currentLineDistance += lineDistance
        }
    }
}

private fun calculateYCoordinate(
    higherTransactionRateValue: Int,
    currentTransactionRate: Float,
    canvasHeight: Float
): Float {
    val maxAndCurrentValueDifference = (higherTransactionRateValue - currentTransactionRate)
        .toFloat()
    val relativePercentageOfScreen = (canvasHeight / higherTransactionRateValue)
        .toFloat()
    return maxAndCurrentValueDifference * relativePercentageOfScreen
} */

