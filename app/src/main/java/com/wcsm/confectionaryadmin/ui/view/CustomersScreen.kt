package com.wcsm.confectionaryadmin.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.navigation.Screen
import com.wcsm.confectionaryadmin.ui.components.CustomersScreenFilter
import com.wcsm.confectionaryadmin.ui.components.MinimizedCustomerCard
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradient
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.viewmodel.CustomersViewModel

@Composable
fun CustomersScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    customersViewModel: CustomersViewModel
) {
    val customers by customersViewModel.customers.collectAsState()

    var searchInput by remember { mutableStateOf("") }
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(selectedCustomer) {
        if(selectedCustomer != null) {
            customersViewModel.updateSelectedCustomer(
                customer = selectedCustomer!!
            )
            navController.navigate(Screen.CustomerDetails.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .background(AppBackground)
                .fillMaxSize()
                .blur(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.screen_title_customers_screen),
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                style = TextStyle(
                    brush = AppTitleGradient
                ),
                modifier = Modifier.padding(top = 24.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomersScreenFilter(
                leadingIcon = if(searchInput.isEmpty()) Icons.Default.Search else Icons.Default.ArrowBack,
                trailingIcon = if(searchInput.isNotEmpty()) Icons.Default.Clear else null,
                value = searchInput,
                onLeadingIconClick = {
                    if(searchInput.isNotEmpty()) {
                        searchInput = ""
                        focusManager.clearFocus()
                    }
                },
                onTrailingIconClick = {
                    searchInput = ""
                },
                onValueChange = {
                    searchInput = it
                },
                modifier = Modifier.focusRequester(focusRequester)
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                modifier = Modifier.width(300.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                contentPadding = paddingValues
            ) {
                val customersToShow = if(searchInput.isEmpty()) {
                    customers
                } else {
                    customers.filter {
                        it.name.lowercase().contains(searchInput.lowercase())
                    }
                }
                items(customersToShow) {
                    MinimizedCustomerCard(
                        customer = it,
                        expandIcon = true
                    ) {
                        selectedCustomer = it
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomersScreenPreview(
    viewModel: CustomersViewModel = hiltViewModel()
) {
    ConfectionaryAdminTheme {
        val paddingValues = PaddingValues()
        val navController = rememberNavController()
        CustomersScreen(navController, paddingValues, viewModel)
    }
}