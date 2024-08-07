package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackground
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.view.customersMock

@Composable
fun MinimizedCustomerCard(
    customer: Customer,
    expandIcon: Boolean = false,
    onCardClick: () -> Unit
) {
    val customerIcon = when(customer.gender) {
        "Masculino" -> painterResource(id = R.drawable.male)
        "Feminino" -> painterResource(id = R.drawable.female)
        else -> painterResource(id = R.drawable.others)
    }

    Row(
        modifier = Modifier
            .width(300.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(brush = InvertedAppBackground)
            .border(1.dp, Primary, RoundedCornerShape(15.dp))
            .padding(horizontal = 16.dp)
            .clickable { onCardClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = customerIcon,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )

        Text(
            text = customer.name,
            color = Primary,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        if(expandIcon) {
            Icon(
                painter = painterResource(id = R.drawable.expand_icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MinimizedCustomerCardPreview() {
    ConfectionaryAdminTheme {
        MinimizedCustomerCard(customersMock[0]) {}
    }
}