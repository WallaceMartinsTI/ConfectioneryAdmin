package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary

@Composable
fun CustomRadioButton(
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onSelectedChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Primary, RoundedCornerShape(15.dp))
            .width(250.dp)
            .padding(horizontal = 12.dp)
            .clickable { onSelectedChange() }
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = Primary,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        
        Icon(
            imageVector = if(isSelected) Icons.Default.RadioButtonChecked
            else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = Primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomRadioButtonPreview() {
    ConfectionaryAdminTheme {
        Column {
            CustomRadioButton(text = "DATA", isSelected = false) {}
            Spacer(modifier = Modifier.height(8.dp))
            CustomRadioButton(text = "STATUS", isSelected = true) {}
        }
    }
}