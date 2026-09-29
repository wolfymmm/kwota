package com.example.smartresidentialcomplex.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartresidentialcomplex.R
import com.example.smartresidentialcomplex.ui.theme.AppBackground
import com.example.smartresidentialcomplex.ui.theme.AppTheme
import com.example.smartresidentialcomplex.ui.theme.SmartResidentialComplexTheme
import com.example.smartresidentialcomplex.ui.theme.WixMadeforDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeviceScreen(
    onBackClick: () -> Unit = {},
    onDeviceAdded: (room: String, name: String, brand: String, model: String, power: String) -> Unit = { _, _, _, _, _ -> }
) {
    val colors = AppTheme.colors

    var selectedCategory by remember { mutableStateOf("Інший") }
    var selectedRoom by remember { mutableStateOf("") }
    var isRoomDropdownExpanded by remember { mutableStateOf(false) }

    var deviceName by remember { mutableStateOf("") }
    var brandName by remember { mutableStateOf("") }
    var modelName by remember { mutableStateOf("") }
    var nominalPower by remember { mutableStateOf("") }

    val categories = listOf("Smart TV", "Кондиціонер", "Інший")
    val rooms = listOf("Кухня", "Ванна", "Вітальня", "Спальня")

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 32.dp)
            .imePadding()
    ) {
        // Кнопка назад "‹"
        Surface(
            modifier = Modifier
                .size(42.dp)
                .clickable { onBackClick() },
            shape = CircleShape,
            color = colors.cardBackground
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "‹",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Normal,
                    color = colors.textPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Головний заголовок
        Text(
            text = "Новий пристрій",
            fontFamily = WixMadeforDisplay,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.textPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Підзаголовок
        Text(
            text = "Оберіть пристрій із переліку або\nдодайте власний.",
            fontFamily = WixMadeforDisplay,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.sp,
            color = colors.textSecondary
        )

        Spacer(modifier = Modifier.height(22.dp))

        // Рядок горизонтального вибору категорій
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            categories.forEach { category ->
                val isSelected = category == selectedCategory
                Surface(
                    modifier = Modifier
                        .height(44.dp)
                        .clickable { selectedCategory = category },
                    shape = RoundedCornerShape(22.dp),
                    color = if (isSelected) colors.brandPurple else colors.cardBackground
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = category,
                            fontFamily = WixMadeforDisplay,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) Color.White else colors.textPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Біла картка форми
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = colors.cardBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Випадаючий список кімнати
                ExposedDropdownMenuBox(
                    expanded = isRoomDropdownExpanded,
                    onExpandedChange = { isRoomDropdownExpanded = !isRoomDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedRoom,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = {
                            Text(
                                text = "Кімната",
                                fontFamily = WixMadeforDisplay,
                                fontSize = 14.sp,
                                color = colors.textMuted
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_down),
                                contentDescription = "Обрати кімнату",
                                tint = colors.textMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        shape = RoundedCornerShape(22.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.brandPurple,
                            unfocusedBorderColor = colors.textMuted.copy(alpha = 0.25f),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isRoomDropdownExpanded,
                        onDismissRequest = { isRoomDropdownExpanded = false }
                    ) {
                        rooms.forEach { room ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = room,
                                        fontFamily = WixMadeforDisplay,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedRoom = room
                                    isRoomDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Поле: Назва пристрою
                DeviceInputField(
                    value = deviceName,
                    onValueChange = { deviceName = it },
                    placeholder = "Назва пристрою"
                )

                // Поле: Виробник, назва бренду
                DeviceInputField(
                    value = brandName,
                    onValueChange = { brandName = it },
                    placeholder = "Виробник, назва бренду"
                )

                // Поле: Модель пристрою
                DeviceInputField(
                    value = modelName,
                    onValueChange = { modelName = it },
                    placeholder = "Модель пристрою"
                )

                // Поле: Номінальна потужність
                DeviceInputField(
                    value = nominalPower,
                    onValueChange = { nominalPower = it },
                    placeholder = "Номінальна потужність",
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Кнопка: Додати пристрій
                Button(
                    onClick = {
                        onDeviceAdded(
                            selectedRoom,
                            deviceName,
                            brandName,
                            modelName,
                            nominalPower
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(27.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF141316)
                    )
                ) {
                    Text(
                        text = "Додати пристрій",
                        fontFamily = WixMadeforDisplay,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun DeviceInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val colors = AppTheme.colors

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = WixMadeforDisplay,
                fontSize = 14.sp,
                color = colors.textMuted
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(22.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colors.brandPurple,
            unfocusedBorderColor = colors.textMuted.copy(alpha = 0.25f),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = colors.textPrimary,
            unfocusedTextColor = colors.textPrimary
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun AddDeviceScreenPreview() {
    SmartResidentialComplexTheme(darkTheme = false) {
        AppBackground {
            AddDeviceScreen()
        }
    }
}