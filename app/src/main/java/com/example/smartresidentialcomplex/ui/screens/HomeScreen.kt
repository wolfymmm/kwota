package com.example.smartresidentialcomplex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartresidentialcomplex.R
import com.example.smartresidentialcomplex.ui.components.ProfileSwitch
import com.example.smartresidentialcomplex.ui.theme.AppTheme
import com.example.smartresidentialcomplex.ui.theme.SmartResidentialComplexTheme
import com.example.smartresidentialcomplex.ui.theme.WixMadeforDisplay

// Модель для пристрою
data class DeviceItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconRes: Int,
    val isEnabled: Boolean
)

@Composable
fun HomeScreen(
    onNotificationClick: () -> Unit = {},
    onAddDeviceClick: () -> Unit = {},
    onDeviceClick: (DeviceItem) -> Unit = {},
    onDeviceDeleteClick: (DeviceItem) -> Unit = {}
) {
    val colors = AppTheme.colors

    // Список фільтрів кімнат
    val rooms = listOf("Усі прилади", "Вітальня", "Кухня", "Спальня")
    var selectedRoom by remember { mutableStateOf("Усі прилади") }

    // Стан пристроїв
    var devices by remember {
        mutableStateOf(
            listOf(
                DeviceItem(
                    id = "1",
                    title = "Smart TV",
                    subtitle = "My Smart LG C2 LED",
                    iconRes = R.drawable.ic_edit,
                    isEnabled = true
                ),
                DeviceItem(
                    id = "2",
                    title = "Кондиціонер",
                    subtitle = "Cooper&Hunter CH-S07TXF2-NG",
                    iconRes = R.drawable.ic_edit,
                    isEnabled = true
                ),
                DeviceItem(
                    id = "3",
                    title = "Smart Lamp",
                    subtitle = "Smart LED Lamp",
                    iconRes = R.drawable.ic_edit,
                    isEnabled = true
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        // 1. Верхній блок: Користувач + Сповіщення
        HeaderSection(
            onNotificationClick = onNotificationClick,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 2. Статистика споживання енергії (2 картки)
        EnergyStatsRow(
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        // 3. Горизонтальний список категорій / кімнат
        RoomsFilterRow(
            rooms = rooms,
            selectedRoom = selectedRoom,
            onRoomSelected = { selectedRoom = it }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 4. Сітка приладів з кнопкою додавання
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 120.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Перший елемент — картка "+" для додавання нового девайса
            item {
                AddDeviceCard(onClick = onAddDeviceClick)
            }

            // Список активних пристроїв
            items(devices, key = { it.id }) { device ->
                DeviceCard(
                    device = device,
                    onClick = { onDeviceClick(device) },
                    onToggle = { isChecked ->
                        devices = devices.map {
                            if (it.id == device.id) it.copy(isEnabled = isChecked) else it
                        }
                    },
                    onDelete = { onDeviceDeleteClick(device) }
                )
            }
        }
    }
}

// -------------------------------------------------------------
// КОМПОНЕНТИ ЕКРАНА
// -------------------------------------------------------------

@Composable
private fun HeaderSection(
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Олена Коваль",
                fontFamily = WixMadeforDisplay,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = colors.textSecondary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Доброго ранку!",
                    fontFamily = WixMadeforDisplay,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "🌤️", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "квартира 22, під'їзд 1",
                fontFamily = WixMadeforDisplay,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = colors.textMuted
            )
        }

        Surface(
            modifier = Modifier
                .size(44.dp)
                .clickable { onNotificationClick() },
            shape = CircleShape,
            color = colors.cardBackground
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Сповіщення",
                    tint = colors.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun EnergyStatsRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            value = "4.2 кВт/год",
            label = "Сьогодні",
            iconRes = R.drawable.ic_edit
        )
        StatCard(
            modifier = Modifier.weight(1f),
            value = "125 кВт/год",
            label = "За місяць",
            iconRes = R.drawable.ic_energy
        )
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    iconRes: Int,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors

    Surface(
        modifier = modifier.height(130.dp),
        shape = RoundedCornerShape(24.dp),
        color = colors.cardBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(colors.actionButtonBg)
                    .align(Alignment.End),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = colors.textPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }

            Column {
                Text(
                    text = value,
                    fontFamily = WixMadeforDisplay,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = label,
                    fontFamily = WixMadeforDisplay,
                    fontSize = 11.sp,
                    color = colors.textMuted
                )
            }
        }
    }
}

@Composable
private fun RoomsFilterRow(
    rooms: List<String>,
    selectedRoom: String,
    onRoomSelected: (String) -> Unit
) {
    val colors = AppTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        rooms.forEach { room ->
            val isSelected = room == selectedRoom
            Surface(
                modifier = Modifier
                    .height(38.dp)
                    .clickable { onRoomSelected(room) },
                shape = RoundedCornerShape(19.dp),
                color = if (isSelected) colors.brandPurple else colors.cardBackground
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = room,
                        fontFamily = WixMadeforDisplay,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        color = if (isSelected) Color.White else colors.textPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun AddDeviceCard(onClick: () -> Unit) {
    val colors = AppTheme.colors

    Surface(
        modifier = Modifier
            .height(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        color = colors.cardBackground
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 36.sp,
                fontWeight = FontWeight.Light,
                color = colors.textPrimary
            )
        }
    }
}

@Composable
private fun DeviceCard(
    device: DeviceItem,
    onClick: () -> Unit,
    onToggle: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val colors = AppTheme.colors

    Surface(
        modifier = Modifier
            .height(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        color = colors.cardBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Верхня лінія: іконка приладу + кошик
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = device.iconRes),
                    contentDescription = null,
                    tint = colors.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Видалити",
                    tint = colors.textMuted.copy(alpha = 0.6f),
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onDelete() }
                )
            }

            // Назва та опис пристрою
            Column {
                Text(
                    text = device.title,
                    fontFamily = WixMadeforDisplay,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textPrimary,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = device.subtitle,
                    fontFamily = WixMadeforDisplay,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = colors.textMuted,
                    maxLines = 1
                )
            }

            // Перемикач живлення справа внизу
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                ProfileSwitch(
                    checked = device.isEnabled,
                    onCheckedChange = onToggle,
                    activeColor = colors.brandPurple,
                    width = 46.dp,
                    height = 26.dp,
                    thumbSize = 20.dp
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    SmartResidentialComplexTheme(darkTheme = false) {
        HomeScreen()
    }
}