package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.components.SyncDialog
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradient
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.util.showToastMessage
import com.wcsm.confectionaryadmin.ui.viewmodel.CustomersViewModel
import com.wcsm.confectionaryadmin.ui.viewmodel.InfoViewModel
import com.wcsm.confectionaryadmin.ui.viewmodel.LoginViewModel
import com.wcsm.confectionaryadmin.ui.viewmodel.OrdersViewModel

@Composable
fun InfoScreen(
    paddingValues: PaddingValues,
    customersViewModel: CustomersViewModel,
    ordersViewModel: OrdersViewModel,
    loginViewModel: LoginViewModel,
    infoViewModel: InfoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isSyncSuccess by infoViewModel.isSyncSuccess.collectAsState()
    val isSyncLoading by infoViewModel.isSyncLoading.collectAsState()

    val confirmSyncDownDialogPreference by loginViewModel.showConfirmSyncDownDialog.collectAsState()
    val isConnected by loginViewModel.isConnected.collectAsState()

    val clipboardManager = LocalClipboardManager.current
    val uriHandler = LocalUriHandler.current

    val devEmail = "wallace159santos@hotmail.com"
    val devLinkedin = "https://www.linkedin.com/in/wallace-martins-ti/"

    var syncText by rememberSaveable { mutableStateOf("") }

    var isSincronized by rememberSaveable { mutableStateOf(false) }

    var showSyncDownConfirmDialog by remember { mutableStateOf(false) }


    LaunchedEffect(true) {
        loginViewModel.checkConnection()
    }

    LaunchedEffect(confirmSyncDownDialogPreference) {
        loginViewModel.checkShowSyncDownConfirmDialog()
    }

    LaunchedEffect(ordersViewModel.ordersWithCustomer, customersViewModel.customers) {
        isSincronized = false
    }

    LaunchedEffect(isSyncSuccess) {
        if(isSyncSuccess) {
            showToastMessage(context, "Dados buscados da nuvem com sucesso!")
            customersViewModel.getAllCustomers()
            ordersViewModel.getAllOrders()
            isSincronized = true
        }
    }

    LaunchedEffect(isSyncLoading) {
        syncText = if(isSyncLoading) "SINCRONIZANDO..." else "SINCRONIZAR"
    }

    Column(
        modifier = Modifier
            .background(AppBackground)
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.screen_title_info_screen),
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            style = TextStyle(
                brush = AppTitleGradient
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTitle(
                    text = stringResource(id = R.string.info_screen_about_app)
                )

                Spacer(modifier = Modifier.height(4.dp))

                CustomContainer {
                    Text(
                        text = stringResource(id = R.string.info_screen_about_app_description),
                        fontFamily = InterFontFamily,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTitle(
                    text = stringResource(id = R.string.info_screen_already_used_our_app)
                )

                Spacer(modifier = Modifier.height(4.dp))

                CustomContainer {
                    Text(
                        text = stringResource(id = R.string.info_screen_already_used_description),
                        fontFamily = InterFontFamily,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PrimaryButton(
                        text = if(isSincronized) "ATUALIZADO" else syncText,
                        textColor = Color.White,
                        icon = Icons.Default.CloudSync
                    ) {
                        loginViewModel.checkConnection()

                        if(!isSincronized) {
                            loginViewModel.checkShowSyncDownConfirmDialog()

                            if(confirmSyncDownDialogPreference != true) {
                                showSyncDownConfirmDialog = true
                            } else {
                                if (isConnected) {
                                    loginViewModel.checkConnection()
                                    infoViewModel.fetchAllUserData()
                                } else {
                                    showToastMessage(context, "Sem conexão no momento")
                                }
                            }

                        }
                    }
                }

                if(showSyncDownConfirmDialog) {
                    SyncDialog(
                        isSendingFromRoomToFirestore = false,
                        onDontShowAgain = {
                            loginViewModel.changeSyncDownConfirmDialogPreference(it)
                        },
                        onDissmiss = { showSyncDownConfirmDialog = false }
                    ) {
                        loginViewModel.checkConnection()
                        if (isConnected) {
                            loginViewModel.checkConnection()
                            infoViewModel.fetchAllUserData()
                        } else {
                            showToastMessage(context, "Sem conexão no momento")
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTitle(
                    text = stringResource(id = R.string.info_screen_about_me)
                )

                Spacer(modifier = Modifier.height(4.dp))

                CustomContainer {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.me),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = "Wallace Martins\n25 Anos\nDesenvolvedor Android",
                            color = Color.White,
                            fontFamily = InterFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    HorizontalDivider(
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    ContactContainer(
                        text = "COPIAR E-MAIL",
                        vectorIcon = Icons.Default.Email,
                        trailingIcon = Icons.Default.ContentCopy
                    ) {
                        clipboardManager.setText(AnnotatedString(devEmail))
                        showToastMessage(context, "E-mail copiado!")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    ContactContainer(
                        text = "ABRIR LINKEDIN",
                        painterIcon = painterResource(id = R.drawable.linkedin_icon),
                        trailingIcon = Icons.Default.OpenInBrowser
                    ) {
                        uriHandler.openUri(devLinkedin)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CustomContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .width(300.dp)
            .border(1.dp, Color.White, RoundedCornerShape(15.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
private fun CustomTitle(text: String) {
    Text(
        text = text,
        color = Primary,
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    )
}

@Composable
fun ContactContainer(
    text: String,
    vectorIcon: ImageVector? = null,
    painterIcon: Painter? = null,
    trailingIcon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable { onClick() }
            .fillMaxWidth()
            .border(1.dp, Primary, RoundedCornerShape(15.dp))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if(vectorIcon != null) {
            Icon(
                imageVector = vectorIcon,
                contentDescription = null,
                tint = Primary
            )
        } else if(painterIcon != null) {
            Icon(
                painter = painterIcon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = text,
            color = Primary,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        )
        Icon(
            imageVector = trailingIcon,
            contentDescription = null,
            tint = Primary
        )
    }
}