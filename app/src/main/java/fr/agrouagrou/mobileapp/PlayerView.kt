package fr.agrouagrou.mobileapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import fr.agrouagrou.mobileapp.ui.theme.MobileAppTheme

@Preview(showBackground = true)
@Composable
fun PlayerNightView(@PreviewParameter(CardPreviewProvider::class, 1) card: Card) {
    MobileAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.moon), contentDescription = "Moon",
                    Modifier
                        .height(100.dp)
                )
                Text(
                    text = "It's night time, please turn your phone upside down!",
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp),
                    textAlign = TextAlign.Center,
                )
                CardView(card = card)
            }
        }
    }
}