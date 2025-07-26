package com.example.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.charts.model.ChartConfig
import com.example.charts.model.PieChartData
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Круговой график с анимацией и интерактивностью
 * @param data Список данных для отображения
 * @param config Конфигурация графика
 * @param modifier Модификатор
 * @param onSegmentClick Callback при клике на сегмент
 */
@Composable
fun PieChart(
    data: List<PieChartData>,
    config: ChartConfig = ChartConfig(),
    modifier: Modifier = Modifier,
    onSegmentClick: ((PieChartData) -> Unit)? = null
) {
    if (data.isEmpty()) return

    var selectedSegment by remember { mutableStateOf<PieChartData?>(null) }
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(data) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(config.animationDuration.toInt())
        )
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        config.title?.let { title ->
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = minOf(size.width, size.height) / 2 * 0.8f
                val strokeWidth = radius * 0.1f

                var startAngle = 0f
                val totalValue = data.sumOf { it.value }

                data.forEach { segment ->
                    val sweepAngle =
                        (segment.value / totalValue * 360f * animationProgress.value).toFloat()
                    val isSelected = selectedSegment == segment
                    val currentRadius = if (isSelected) radius * 1.05f else radius

                    drawArc(
                        color = segment.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(
                            center.x - currentRadius,
                            center.y - currentRadius
                        ),
                        size = Size(currentRadius * 2, currentRadius * 2)
                    )

                    drawArc(
                        color = Color.White,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(
                            center.x - currentRadius,
                            center.y - currentRadius
                        ),
                        size = Size(currentRadius * 2, currentRadius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    if (config.showValues && sweepAngle > 30f) {
                        val angleInRadians = (startAngle + sweepAngle / 2) * (PI / 180f)
                        val textRadius = currentRadius * 0.6f
                        val textOffset = Offset(
                            x = center.x + (cos(angleInRadians) * textRadius).toFloat(),
                            y = center.y + (sin(angleInRadians) * textRadius).toFloat()
                        )

                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "${String.format("%.1f", segment.percentage)}%",
                                textOffset.x,
                                textOffset.y,
                                android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE
                                    textSize = 12.sp.toPx()
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    isFakeBoldText = true
                                }
                            )
                        }
                    }

                    startAngle += sweepAngle
                }
            }

            if (onSegmentClick != null) {
                data.forEachIndexed { index, segment ->
                    val startAngle =
                        data.take(index).sumOf { it.value / data.sumOf { it.value } * 360f }
                    val sweepAngle = (segment.value / data.sumOf { it.value } * 360f).toFloat()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                selectedSegment = if (selectedSegment == segment) null else segment
                                onSegmentClick(segment)
                            }
                    )
                }
            }
        }

        if (config.showLegend) {
            PieChartLegend(
                data = data,
                selectedSegment = selectedSegment,
                onSegmentClick = { segment ->
                    selectedSegment = if (selectedSegment == segment) null else segment
                    onSegmentClick?.invoke(segment)
                }
            )
        }
    }
}

@Composable
private fun PieChartLegend(
    data: List<PieChartData>,
    selectedSegment: PieChartData?,
    onSegmentClick: (PieChartData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        data.forEach { segment ->
            val isSelected = selectedSegment == segment
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSegmentClick(segment) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (isSelected) 8.dp else 2.dp
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(segment.color)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    segment.icon?.let { icon ->
                        Text(
                            text = icon,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = segment.label,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                        Text(
                            text = "${String.format("%.1f", segment.percentage)}%",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = String.format("%.0f", segment.value),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
