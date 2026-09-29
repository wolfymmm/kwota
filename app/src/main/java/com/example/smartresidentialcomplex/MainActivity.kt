package com.example.smartresidentialcomplex

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartresidentialcomplex.ui.components.AppDestination
import com.example.smartresidentialcomplex.ui.components.Navbar
import com.example.smartresidentialcomplex.ui.screens.AddDeviceScreen
import com.example.smartresidentialcomplex.ui.screens.DeviceItem
import com.example.smartresidentialcomplex.ui.screens.DeviceScreen
import com.example.smartresidentialcomplex.ui.screens.HomeScreen
import com.example.smartresidentialcomplex.ui.screens.EnergyScreen
import com.example.smartresidentialcomplex.ui.screens.LoginScreen
import com.example.smartresidentialcomplex.ui.screens.ProfileScreen
import com.example.smartresidentialcomplex.ui.theme.AppBackground
import com.example.smartresidentialcomplex.ui.theme.AppTheme
import com.example.smartresidentialcomplex.ui.theme.SmartResidentialComplexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            SmartResidentialComplexTheme(darkTheme = isDarkTheme) {
                AppBackground {
                    val colors = AppTheme.colors
                    // null позначає екран Login (де навбар не потрібен), а AppDestination — основні вкладки
                    var currentDestination by remember { mutableStateOf<AppDestination?>(AppDestination.HOME) }

                    // Стани для відкриття форми додавання та деталей пристрою
                    var isAddingDevice by remember { mutableStateOf(false) }
                    var selectedDevice by remember { mutableStateOf<DeviceItem?>(null) }

                    Box(modifier = Modifier.fillMaxSize()) {
                        // Контент активного екрана
                        when {
                            // 1. Екран додавання нового пристрою
                            isAddingDevice -> {
                                AddDeviceScreen(
                                    onBackClick = { isAddingDevice = false },
                                    onDeviceAdded = { room, name, brand, model, power ->
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Пристрій '$name' додано до $room",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        isAddingDevice = false
                                    }
                                )
                            }

                            // 2. Екран обраного приладу з редагуванням показників
                            selectedDevice != null -> {
                                DeviceScreen(
                                    initialDeviceName = selectedDevice!!.title,
                                    onBackClick = { selectedDevice = null },
                                    onDeleteClick = {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Пристрій видалено",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        selectedDevice = null
                                    }
                                )
                            }

                            // 3. Основні екрани з навбаром
                            currentDestination == AppDestination.HOME -> {
                                HomeScreen(
                                    onNotificationClick = {
                                        Toast.makeText(this@MainActivity, "Сповіщення", Toast.LENGTH_SHORT).show()
                                    },
                                    onAddDeviceClick = {
                                        isAddingDevice = true
                                    },
                                    onDeviceClick = { device ->
                                        selectedDevice = device
                                    }
                                )
                            }

                            currentDestination == AppDestination.PROFILE -> {
                                ProfileScreen(
                                    isDarkTheme = isDarkTheme,
                                    onThemeChanged = { isDarkTheme = it },
                                    onLogoutClick = {
                                        currentDestination = null // Перехід на екран входу
                                    }
                                )
                            }

                            currentDestination == AppDestination.ENERGY -> {
                                EnergyScreen(
                                    onBackClick = {
                                        currentDestination = AppDestination.HOME // Повернення на головний екран
                                    }
                                )
                            }

                            currentDestination == null -> {
                                LoginScreen(
                                    onLoginClick = { _, _ ->
                                        currentDestination = AppDestination.HOME
                                    },
                                    onForgotPasswordClick = {
                                        Toast.makeText(this@MainActivity, "Відновлення пароля", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }

                        // Нижня підсвітка та Navbar відображаються лише на головних вкладках
                        val showMainBottomBar = currentDestination != null && !isAddingDevice && selectedDevice == null
                        if (showMainBottomBar) {
                            // Градієнт підсвічування під навбаром
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

                            // Єдина нижня панель навігації
                            Navbar(
                                currentDestination = currentDestination!!,
                                onDestinationSelected = { destination ->
                                    currentDestination = destination
                                },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(horizontal = 24.dp, vertical = 18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}