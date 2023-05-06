package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.runtime.Composable
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.time.LocalDate


class Entry(
    val localDate: LocalDate,
    override val x: Float,
    override val y: Float,
) : ChartEntry {
    override fun withY(y: Float) = Entry(localDate, x, y)
}

val axisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, chartValues ->
    (chartValues.chartEntryModel.entries.first().getOrNull(value.toInt()) as? Entry)
        ?.localDate
        ?.run { "$dayOfMonth" }
        .orEmpty()
}


@Composable
fun myChart(chartEntryModelProducer: ChartEntryModelProducer) {
    Chart(
        chart = lineChart(),
        chartModelProducer = chartEntryModelProducer,
        startAxis = startAxis(),
        bottomAxis = bottomAxis(valueFormatter = axisValueFormatter),
    )
}