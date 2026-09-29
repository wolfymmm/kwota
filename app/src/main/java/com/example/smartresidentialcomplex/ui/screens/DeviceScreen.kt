package com.example.smartresidentialcomplex.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun DeviceScreen(
    initialDeviceName: String = "Кондиціонер",
    initialPowerCurrent: String = "0.72 кВт зараз",
    initialCoolingHeating: String = "3.5 / 3.8 кВт",
    initialTargetTemp: String = "22°C",
    initialMode: String = "Охолодження",
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val colors = AppTheme.colors
    // Світло-сіра рамка карток
    val cardBorder = BorderStroke(1.dp, colors.textMuted.copy(alpha = 0.25f))

    var isEnabled by remember { mutableStateOf(true) }
    var deviceName by remember { mutableStateOf(initialDeviceName) }
    var coolingHeating by remember { mutableStateOf(initialCoolingHeating) }
    var targetTemp by remember { mutableStateOf(initialTargetTemp) }
    var mode by remember { mutableStateOf(initialMode) }

    var isEditDialogOpen by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Прокручуваний контент
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp, bottom = 120.dp) // Запас під кнопку знизу
        ) {
            // Кнопка назад з оптично відцентрованим символом '‹'
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
                        modifier = Modifier.offset(x = (-1).dp, y = (-2).dp) // Компенсація провисання шрифту
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Назва пристрою
            Text(
                text = deviceName,
                fontFamily = WixMadeforDisplay,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.textPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Головна плашка поточного стану
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                color = colors.cardBackground,
                border = cardBorder
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        ProfileSwitch(
                            checked = isEnabled,
                            onCheckedChange = { isEnabled = it },
                            activeColor = colors.brandPurple,
                            width = 46.dp,
                            height = 26.dp,
                            thumbSize = 20.dp
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = if (isEnabled) initialPowerCurrent else "0.00 кВт",
                        fontFamily = WixMadeforDisplay,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Поточний стан",
                        fontFamily = WixMadeforDisplay,
                        fontSize = 13.sp,
                        color = colors.textPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Заголовок блоку показників та олівець
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Показники пристрою",
                    fontFamily = WixMadeforDisplay,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.sectionHeader
                )

                Surface(
                    modifier = Modifier
                        .size(38.dp)
                        .clickable { isEditDialogOpen = true },
                    shape = CircleShape,
                    color = colors.cardBackground,
                    border = cardBorder
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Редагувати",
                            tint = colors.textPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Збільшені плашки показників
            MetricRowCard(
                label = "Охолодження / нагрів",
                value = coolingHeating,
                border = cardBorder
            )

            Spacer(modifier = Modifier.height(12.dp))

            MetricRowCard(
                label = "Цільова температура",
                value = targetTemp,
                border = cardBorder
            )

            Spacer(modifier = Modifier.height(12.dp))

            MetricRowCard(
                label = "Режим",
                value = mode,
                border = cardBorder
            )
        }

        // 2. Фіолетове світіння: малюється ПІД кнопкою, але над фоном
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            colors.glowStart,
                            colors.glowEnd
                        )
                    )
                )
        )

        // 3. Кнопка "Видалити"
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .height(56.dp)
                .clickable { onDeleteClick() },
            shape = RoundedCornerShape(28.dp),
            color = colors.cardBackground,
            border = cardBorder,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Видалити",
                    fontFamily = WixMadeforDisplay,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary
                )
            }
        }
    }

    if (isEditDialogOpen) {
        EditMetricsDialog(
            currentCoolingHeating = coolingHeating,
            currentTargetTemp = targetTemp,
            currentMode = mode,
            onDismiss = { isEditDialogOpen = false },
            onConfirm = { newCooling, newTemp, newMode ->
                coolingHeating = newCooling
                targetTemp = newTemp
                mode = newMode
                isEditDialogOpen = false
            }
        )
    }
}

@Composable
private fun MetricRowCard(
    label: String,
    value: String,
    border: BorderStroke
) {
    val colors = AppTheme.colors

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp),
        shape = RoundedCornerShape(22.dp),
        color = colors.cardBackground,
        border = border
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontFamily = WixMadeforDisplay,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = colors.textPrimary
            )

            Text(
                text = value,
                fontFamily = WixMadeforDisplay,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.textPrimary
            )
        }
    }
}

@Composable
private fun EditMetricsDialog(
    currentCoolingHeating: String,
    currentTargetTemp: String,
    currentMode: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    val colors = AppTheme.colors

    var coolingInput by remember { mutableStateOf(currentCoolingHeating) }
    var tempInput by remember { mutableStateOf(currentTargetTemp) }
    var modeInput by remember { mutableStateOf(currentMode) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Редагувати показники",
                fontFamily = WixMadeforDisplay,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.textPrimary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = coolingInput,
                    onValueChange = { coolingInput = it },
                    label = { Text("Охолодження / нагрів", fontFamily = WixMadeforDisplay) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = tempInput,
                    onValueChange = { tempInput = it },
                    label = { Text("Цільова температура", fontFamily = WixMadeforDisplay) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = modeInput,
                    onValueChange = { modeInput = it },
                    label = { Text("Режим", fontFamily = WixMadeforDisplay) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(coolingInput, tempInput, modeInput) }
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
fun DeviceScreenPreview() {
    SmartResidentialComplexTheme(darkTheme = true) {
        AppBackground {
            DeviceScreen()
        }
    }
}