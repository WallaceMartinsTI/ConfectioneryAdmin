package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.GrayColor

@Composable
fun OrdersFilterContainer(
    text: String,
    modifier: Modifier = Modifier,
    onClickFilterContainer: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .clickable { onClickFilterContainer() }
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.FilterAlt,
            contentDescription = null,
            tint = GrayColor
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text.ifEmpty { stringResource(id = R.string.filter_field_description) },
            color = GrayColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OrdersFilterContainerPreview() {
    ConfectionaryAdminTheme {
        OrdersFilterContainer(text = "") {}
    }
}