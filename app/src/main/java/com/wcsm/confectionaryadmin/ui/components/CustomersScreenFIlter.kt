package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.GrayColor
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary

@Composable
fun CustomersScreenFilter(
    leadingIcon: ImageVector,
    trailingIcon: ImageVector?,
    value: String,
    modifier: Modifier = Modifier,
    onLeadingIconClick: () -> Unit,
    onTrailingIconClick: () -> Unit,
    onValueChange: (newValue: String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        modifier = modifier.width(280.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Primary,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                Primary, Color.Transparent
            )
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_placeholder),
                color = GrayColor
            )
        },
        textStyle = TextStyle(
            color = GrayColor,
            fontFamily = InterFontFamily,
            fontSize = 18.sp
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = GrayColor,
                modifier = Modifier.clickable { onLeadingIconClick() }
            )
        },
        trailingIcon = {
            if(trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = GrayColor,
                    modifier = Modifier.clickable { onTrailingIconClick() }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun FilterCustomerPreview() {
    ConfectionaryAdminTheme {
        CustomersScreenFilter(
            leadingIcon = Icons.Default.Search,
            trailingIcon = null,
            value = "",
            onLeadingIconClick = {},
            onTrailingIconClick = {},
            onValueChange = {}
        )
    }
}