package com.example.smartresidentialcomplex.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartresidentialcomplex.R
import com.example.smartresidentialcomplex.ui.theme.WixMadeforDisplay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val emailInteractionSource = remember { MutableInteractionSource() }
    val passwordInteractionSource = remember { MutableInteractionSource() }

    val scrollState = rememberScrollState()

    val brandPurple = Color(0xFF7E608F)
    val textDark = Color(0xFF1E232C)
    val textMuted = Color(0xFF8E8E93)
    val inputBackground = Color(0xFFF9F9FA)
    val inputBorderUnfocused = Color(0xFFECECEC)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFE9B2),
                        Color(0xFFFDF8FF)
                    ),
                    radius = 1010f
                )
            )
            .systemBarsPadding()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.kwota_logo),
                    contentDescription = "Kwota Logo",
                    modifier = Modifier.height(44.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(bottom = 28.dp),
                shape = RoundedCornerShape(40.dp),
                border = BorderStroke(1.dp, Color(0xFFF6F6F6)),
                color = Color.White,

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "З поверненням!⭐",
                        fontFamily = WixMadeforDisplay,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = textDark
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    // 1. Поле введення Email
                    BasicTextField(
                        value = email,
                        onValueChange = { email = it },
                        textStyle = LocalTextStyle.current.copy(
                            fontFamily = WixMadeforDisplay,
                            fontSize = 14.sp,
                            color = textDark
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        interactionSource = emailInteractionSource,
                        modifier = Modifier.fillMaxWidth()
                    ) { innerTextField ->
                        OutlinedTextFieldDefaults.DecorationBox(
                            value = email,
                            innerTextField = innerTextField,
                            enabled = true,
                            singleLine = true,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = emailInteractionSource,
                            placeholder = {
                                Text(
                                    text = "Введіть ваш email",
                                    fontFamily = WixMadeforDisplay,
                                    color = textMuted,
                                    fontSize = 14.sp
                                )
                            },
                            contentPadding = PaddingValues(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                            container = {
                                OutlinedTextFieldDefaults.Container(
                                    enabled = true,
                                    isError = false,
                                    interactionSource = emailInteractionSource,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = brandPurple,
                                        unfocusedBorderColor = inputBorderUnfocused
                                    ),
                                    shape = RoundedCornerShape(50)
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 2. Поле введення Пароля
                    BasicTextField(
                        value = password,
                        onValueChange = { password = it },
                        textStyle = LocalTextStyle.current.copy(
                            fontFamily = WixMadeforDisplay,
                            fontSize = 14.sp,
                            color = textDark
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        interactionSource = passwordInteractionSource,
                        modifier = Modifier.fillMaxWidth()
                    ) { innerTextField ->
                        OutlinedTextFieldDefaults.DecorationBox(
                            value = password,
                            innerTextField = innerTextField,
                            enabled = true,
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            interactionSource = passwordInteractionSource,
                            placeholder = {
                                Text(
                                    text = "Введіть ваш пароль",
                                    fontFamily = WixMadeforDisplay,
                                    color = textMuted,
                                    fontSize = 14.sp
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { passwordVisible = !passwordVisible },
                                    modifier = Modifier.padding(end = 12.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (passwordVisible) R.drawable.eye_icon else R.drawable.eye_closed_icon
                                        ),
                                        contentDescription = if (passwordVisible) "Сховати пароль" else "Показати пароль",
                                        tint = if (passwordVisible) brandPurple else textMuted,
                                        // Закрите око збільшуємо до 24.dp, а відкрите лишаємо 16.dp (або підберіть під свій файл):
                                        modifier = Modifier.size(if (passwordVisible) 15.dp else 44.dp)
                                    )
                                }
                            },
                            contentPadding = PaddingValues(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                            container = {
                                OutlinedTextFieldDefaults.Container(
                                    enabled = true,
                                    isError = false,
                                    interactionSource = passwordInteractionSource,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = brandPurple,
                                        unfocusedBorderColor = inputBorderUnfocused
                                    ),
                                    shape = RoundedCornerShape(50)
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3. Чекбокс «Запам'ятати мене» та посилання «Забули пароль?»
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = brandPurple,
                                    uncheckedColor = Color(0xFFC4C4C6)
                                ),
                                modifier = Modifier
                                    .scale(0.7f)
                                    .size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Запам'ятати мене",
                                fontFamily = WixMadeforDisplay,
                                fontSize = 12.sp,
                                color = textMuted
                            )
                        }

                        Text(
                            text = "Забули пароль?",
                            fontFamily = WixMadeforDisplay,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = brandPurple,
                            modifier = Modifier.clickable { onForgotPasswordClick() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4. Кнопка входу
                    Button(
                        onClick = { onLoginClick(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF141316),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Увійти",
                            fontFamily = WixMadeforDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 5. Текст розділювача
                    Text(
                        text = "або увійти за\nдопомогою",
                        textAlign = TextAlign.Center,
                        fontFamily = WixMadeforDisplay,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = textMuted,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // 6. Кнопки соцмереж
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SocialIconButton(iconRes = R.drawable.facebook, contentDesc = "Facebook")
                        SocialIconButton(iconRes = R.drawable.google, contentDesc = "Google")
                        SocialIconButton(iconRes = R.drawable.diia, contentDesc = "Дія")
                    }
                }
            }
        }
    }
}

// Компонент рамки для кнопок входу
@Composable
fun SocialIconButton(
    iconRes: Int,
    contentDesc: String,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .size(width = 68.dp, height = 54.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFEBEBEB)),
        color = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = contentDesc,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}