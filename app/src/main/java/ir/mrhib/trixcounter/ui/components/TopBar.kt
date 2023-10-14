package ir.mrhib.trixcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.mrhib.trixcounter.ui.theme.vazir

@Preview
@Composable
fun TopBarPreview() {
    TopBar(title = "Games History")
}

@Composable
fun TopBar(title: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 5.dp)
            .background(Color(0xFFFF9000), RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            fontFamily = vazir,
            fontSize = 23.sp,
            text = title,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}