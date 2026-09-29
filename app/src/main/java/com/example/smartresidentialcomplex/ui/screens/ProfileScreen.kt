package com.example.smartresidentialcomplex.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun ProfileScreen(
    isDarkTheme: Boolean = false,
    onThemeChanged: (Boolean) -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    var peakTariffs by remember { mutableStateOf(true) }
    var ecoTips by remember { mutableStateOf(true) }
    var monthlyReport by remember { mutableStateOf(true) }
    var communityNews by remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()
    val colors = AppTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(top = 28.dp, bottom = 100.dp)
        ) {
            // Заголовок сторінки
            Text(
                text = "Профіль",
                fontFamily = WixMadeforDisplay,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.textPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Головна картка профілю
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                color = colors.cardBackground
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Аватарка
                    Image(
                        painter = painterResource(id = R.drawable.avatar),
                        contentDescription = "Аватар користувача",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Рядок: Текстовий блок + Кнопка редагування по центру його висоти
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Олена Коваль",
                                fontFamily = WixMadeforDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.textPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "квартира 22, під'їзд 1",
                                fontFamily = WixMadeforDisplay,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = colors.textSecondary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "мешканець з вересня 2026",
                                fontFamily = WixMadeforDisplay,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = colors.textMuted
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Кнопка редагування (олівець)
                        Surface(
                            modifier = Modifier
                                .size(38.dp)
                                .clickable { onEditProfileClick() },
                            shape = CircleShape,
                            color = colors.actionButtonBg
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_edit),
                                    contentDescription = "Редагувати профіль",
                                    tint = colors.textPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Секція: Сповіщення
            Text(
                text = "Сповіщення",
                fontFamily = WixMadeforDisplay,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.sectionHeader
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Перемикачі сповіщень
            ProfileCardItem(
                title = "Пікові тарифи",
                trailingContent = {
                    ProfileSwitch(
                        checked = peakTariffs,
                        onCheckedChange = { peakTariffs = it },
                        activeColor = colors.brandPurple
                    )
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            ProfileCardItem(
                title = "Еко-поради",
                trailingContent = {
                    ProfileSwitch(
                        checked = ecoTips,
                        onCheckedChange = { ecoTips = it },
                        activeColor = colors.brandPurple
                    )
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            ProfileCardItem(
                title = "Місячний звіт",
                trailingContent = {
                    ProfileSwitch(
                        checked = monthlyReport,
                        onCheckedChange = { monthlyReport = it },
                        activeColor = colors.brandPurple
                    )
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            ProfileCardItem(
                title = "Новини спільноти",
                trailingContent = {
                    ProfileSwitch(
                        checked = communityNews,
                        onCheckedChange = { communityNews = it },
                        activeColor = colors.brandPurple
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Секція: Налаштування
            Text(
                text = "Налаштування",
                fontFamily = WixMadeforDisplay,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.sectionHeader
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Темна тема — викликає onThemeChanged для всього додатку
            ProfileCardItem(
                title = "Темна тема",
                trailingContent = {
                    ProfileSwitch(
                        checked = isDarkTheme,
                        onCheckedChange = { onThemeChanged(it) },
                        activeColor = colors.brandPurple
                    )
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Змінити пароль
            ProfileCardItem(
                title = "Змінити пароль",
                onClick = onChangePasswordClick
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Вийти з акаунту
            ProfileCardItem(
                title = "Вийти з акаунту",
                onClick = onLogoutClick
            )
        }

        // Нижнє градієнтне світіння
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(130.dp)
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

        // Нижня панель навігації
        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 18.dp)
        )
    }
}

// -------------------------------------------------------------
// ЄДИНИЙ ПЕРЕВИКОРИСТОВУВАНИЙ СТИЛЬ ДЛЯ БЛОКУ
// -------------------------------------------------------------
@Composable
fun ProfileCardItem(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.colors.cardBackground,
    onClick: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            ),
        shape = RoundedCornerShape(10.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontFamily = WixMadeforDisplay,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = AppTheme.colors.textPrimary
            )

            if (trailingContent != null) {
                trailingContent()
            }
        }
    }
}

// Нижня панель навігації
@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    val colors = AppTheme.colors

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp),
        shape = RoundedCornerShape(34.dp),
        color = colors.navBarBackground
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                title = "Дім",
                iconRes = R.drawable.ic_home,
                isSelected = false
            )
            BottomNavItem(
                title = "Енергія",
                iconRes = R.drawable.ic_energy,
                isSelected = false
            )
            BottomNavItem(
                title = "Спільнота",
                iconRes = R.drawable.ic_community,
                isSelected = false
            )
            BottomNavItem(
                title = "Профіль",
                iconRes = R.drawable.ic_profile,
                isSelected = true
            )
        }
    }
}

@Composable
fun BottomNavItem(
    title: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    val colors = AppTheme.colors

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                tint = if (isSelected) colors.navBarActive else colors.navBarInactive,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            fontFamily = WixMadeforDisplay,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) colors.navBarActive else colors.navBarInactive
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ProfileScreenLightPreview() {
    SmartResidentialComplexTheme(darkTheme = false) {
        ProfileScreen()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ProfileScreenDarkPreview() {
    SmartResidentialComplexTheme(darkTheme = true) {
        ProfileScreen(isDarkTheme = true)
    }
}