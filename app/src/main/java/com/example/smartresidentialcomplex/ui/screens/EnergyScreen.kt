package com.example.smartresidentialcomplex.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartresidentialcomplex.R
import com.example.smartresidentialcomplex.ui.components.ProfileSwitch
import com.example.smartresidentialcomplex.ui.theme.AppBackground
import com.example.smartresidentialcomplex.ui.theme.AppTheme
import com.example.smartresidentialcomplex.ui.theme.SmartResidentialComplexTheme
import com.example.smartresidentialcomplex.ui.theme.WixMadeforDisplay
import java.util.Locale

data class EditableEnergyDevice(
    val id: String,
    val name: String,
    val powerWatt: Double,
    val hoursPerDay: Double,
    val isEnabled: Boolean = true,
    val iconRes: Int? = null,
    val dailyDistribution: List<Float>
) {
    val consumptionKwh: Double
        get() = if (isEnabled) (powerWatt * hoursPerDay) / 1000.0 else 0.0
}

@Composable
fun EnergyScreen(
    onBackClick: () -> Unit = {}
) {
    val colors = AppTheme.colors
    val cardBorder = BorderStroke(1.dp, colors.textMuted.copy(alpha = 0.22f))

    val periods = listOf("День", "Тиждень", "Місяць", "Рік")
    var selectedPeriod by remember { mutableStateOf("День") }

    // Стан списку приладів
    var devices by remember {
        mutableStateOf(
            listOf(
                EditableEnergyDevice(
                    id = "1",
                    name = "Smart TV",
                    powerWatt = 200.0,
                    hoursPerDay = 5.0,
                    isEnabled = true,
                    iconRes = R.drawable.ic_edit,
                    dailyDistribution = listOf(0.0f, 0.1f, 0.2f, 0.3f, 0.7f, 1.0f, 0.4f)
                ),
                EditableEnergyDevice(
                    id = "2",
                    name = "Air Conditioner",
                    powerWatt = 1500.0,
                    hoursPerDay = 6.5,
                    isEnabled = true,
                    iconRes = R.drawable.ic_energy,
                    dailyDistribution = listOf(0.2f, 0.1f, 0.6f, 0.9f, 0.8f, 0.5f, 0.2f)
                ),
                EditableEnergyDevice(
                    id = "3",
                    name = "Smart Lamp",
                    powerWatt = 40.0,
                    hoursPerDay = 7.0,
                    isEnabled = true,
                    iconRes = R.drawable.ic_edit,
                    dailyDistribution = listOf(0.2f, 0.6f, 0.0f, 0.0f, 0.4f, 1.0f, 0.8f)
                ),
                EditableEnergyDevice(
                    id = "4",
                    name = "Device",
                    powerWatt = 800.0,
                    hoursPerDay = 2.0,
                    isEnabled = false,
                    iconRes = null,
                    dailyDistribution = listOf(0.1f, 0.2f, 0.5f, 0.8f, 0.4f, 0.2f, 0.0f)
                )
            )
        )
    }

    var editingDevice by remember { mutableStateOf<EditableEnergyDevice?>(null) }
    val intervalsCount = 7
    val barValues = remember(devices) {
        val totalDailyLoads = FloatArray(intervalsCount) { 0f }

        devices.filter { it.isEnabled }.forEach { device ->
            val powerKw = (device.powerWatt / 1000.0).toFloat()
            for (i in 0 until intervalsCount) {
                val weight = device.dailyDistribution.getOrElse(i) { 0.4f }
                totalDailyLoads[i] += powerKw * weight
            }
        }

        val maxVal = totalDailyLoads.maxOrNull()?.takeIf { it > 0f } ?: 1f
        totalDailyLoads.map { load ->
            ((load / maxVal) * 0.82f).coerceIn(0.10f, 0.92f)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(44.dp)
                    .clickable { onBackClick() },
                shape = CircleShape,
                color = colors.cardBackground,
                border = cardBorder
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "‹",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Light,
                        color = colors.textPrimary,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            lineHeight = 28.sp,
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.Both
                            )
                        ),
                        modifier = Modifier.offset(x = (-1).dp, y = (-2).dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Моє споживання",
                fontFamily = WixMadeforDisplay,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.textPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Перемикач періоду ("День", "Тиждень", "Місяць", "Рік")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                color = colors.cardBackground,
                border = cardBorder
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    periods.forEach { period ->
                        val isSelected = period == selectedPeriod
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { selectedPeriod = period }
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = period,
                                fontFamily = WixMadeforDisplay,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) colors.textPrimary else colors.textMuted
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(36.dp)
                                    .then(if (isSelected) Modifier.padding(horizontal = 2.dp) else Modifier)
                            ) {
                                if (isSelected) {
                                    Surface(
                                        modifier = Modifier.fillMaxSize(),
                                        shape = RoundedCornerShape(1.dp),
                                        color = colors.brandPurple
                                    ) {}
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Графік, що динамічно реагує на зміну карток
            EnergyBarChartCard(
                barValues = barValues,
                barColor = colors.brandPurple.copy(alpha = 0.65f),
                gridColor = colors.textMuted.copy(alpha = 0.25f),
                border = cardBorder
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Сітка карток приладів
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 120.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(devices, key = { it.id }) { device ->
                    EnergyDeviceCard(
                        device = device,
                        border = cardBorder,
                        onClick = { editingDevice = device } // Відкрити редагування
                    )
                }
            }
        }

        // Діалогове вікно швидкого редагування картки приладу
        editingDevice?.let { targetDevice ->
            EditDeviceMetricsDialog(
                device = targetDevice,
                onDismiss = { editingDevice = null },
                onSave = { updatedDevice ->
                    devices = devices.map { if (it.id == updatedDevice.id) updatedDevice else it }
                    editingDevice = null
                }
            )
        }
    }
}


@Composable
private fun EnergyBarChartCard(
    barValues: List<Float>,
    barColor: Color,
    gridColor: Color,
    border: BorderStroke
) {
    val colors = AppTheme.colors

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        color = colors.cardBackground,
        border = border
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Пунктирні лінії осей
                val gridLines = 5
                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)

                for (i in 0 until gridLines) {
                    val y = (canvasHeight / (gridLines - 1)) * i
                    drawLine(
                        color = gridColor,
                        start = Offset(0f, y),
                        end = Offset(canvasWidth, y),
                        strokeWidth = 1.dp.toPx(),
                        pathEffect = pathEffect
                    )
                }

                // Стовпчики
                val barCount = barValues.size
                val barSpacing = 10.dp.toPx()
                val totalSpacing = barSpacing * (barCount - 1)
                val barWidth = (canvasWidth - totalSpacing) / barCount

                barValues.forEachIndexed { index, value ->
                    val barHeight = canvasHeight * value
                    val left = index * (barWidth + barSpacing)
                    val top = canvasHeight - barHeight

                    drawRoundRect(
                        color = barColor,
                        topLeft = Offset(left, top),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                    )
                }
            }
        }
    }
}

@Composable
private fun EnergyDeviceCard(
    device: EditableEnergyDevice,
    border: BorderStroke,
    onClick: () -> Unit
) {
    val colors = AppTheme.colors

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        color = colors.cardBackground,
        border = border
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape,
                    color = colors.actionButtonBg
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (device.iconRes != null) {
                            Icon(
                                painter = painterResource(id = device.iconRes),
                                contentDescription = device.name,
                                tint = colors.textPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Text(
                    text = if (device.isEnabled) "${device.hoursPerDay.toInt()} год/д" else "Вимкн.",
                    fontSize = 10.sp,
                    color = if (device.isEnabled) colors.brandPurple else colors.textMuted
                )
            }

            Column {
                Text(
                    text = device.name,
                    fontFamily = WixMadeforDisplay,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.textMuted
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${String.format(Locale.US, "%.2f", device.consumptionKwh)} кВт/год",
                    fontFamily = WixMadeforDisplay,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }
        }
    }
}


@Composable
private fun EditDeviceMetricsDialog(
    device: EditableEnergyDevice,
    onDismiss: () -> Unit,
    onSave: (EditableEnergyDevice) -> Unit
) {
    val colors = AppTheme.colors

    var nameInput by remember { mutableStateOf(device.name) }
    var powerInput by remember { mutableStateOf(device.powerWatt.toInt().toString()) }
    var hoursInput by remember { mutableStateOf(device.hoursPerDay.toString()) }
    var isEnabledState by remember { mutableStateOf(device.isEnabled) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Налаштування приладу",
                fontFamily = WixMadeforDisplay,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = colors.textPrimary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Увімкнення / вимкнення пристрою
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Враховувати у розрахунках",
                        fontFamily = WixMadeforDisplay,
                        fontSize = 13.sp,
                        color = colors.textPrimary
                    )
                    ProfileSwitch(
                        checked = isEnabledState,
                        onCheckedChange = { isEnabledState = it },
                        activeColor = colors.brandPurple
                    )
                }

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Назва пристрою", fontFamily = WixMadeforDisplay) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                OutlinedTextField(
                    value = powerInput,
                    onValueChange = { powerInput = it },
                    label = { Text("Потужність (Вт)", fontFamily = WixMadeforDisplay) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                OutlinedTextField(
                    value = hoursInput,
                    onValueChange = { hoursInput = it },
                    label = { Text("Годин роботи на день", fontFamily = WixMadeforDisplay) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val pWatt = powerInput.toDoubleOrNull() ?: device.powerWatt
                    val hrs = hoursInput.toDoubleOrNull() ?: device.hoursPerDay
                    onSave(
                        device.copy(
                            name = nameInput,
                            powerWatt = pWatt,
                            hoursPerDay = hrs.coerceIn(0.0, 24.0),
                            isEnabled = isEnabledState
                        )
                    )
                }
            ) {
                Text(
                    text = "Зберегти",
                    fontFamily = WixMadeforDisplay,
                    color = colors.brandPurple,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Скасувати",
                    fontFamily = WixMadeforDisplay,
                    color = colors.textMuted
                )
            }
        },
        containerColor = colors.cardBackground,
        shape = RoundedCornerShape(22.dp)
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun EnergyScreenPreview() {
    SmartResidentialComplexTheme(darkTheme = false) {
        AppBackground {
            EnergyScreen()
        }
    }
}