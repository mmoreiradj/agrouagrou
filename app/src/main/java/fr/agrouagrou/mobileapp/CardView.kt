package fr.agrouagrou.mobileapp

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import fr.agrouagrou.mobileapp.ui.theme.MobileAppTheme

@Preview(showBackground = true)
@Composable
fun CardView(
    @PreviewParameter(CardPreviewProvider::class, 1) card: Card
) {
    var isHidden by remember { mutableStateOf(true) }
    val rotation by animateFloatAsState(
        targetValue = if (isHidden) 0f else 180f,
        animationSpec = tween(500),
        label = ""
    )
    MobileAppTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isHidden) "Tap to reveal your role" else "Your role is " + stringResource(id = card.cardNameRes),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Image(
                painter = painterResource(id = if (rotation > 90f) card.imageRes else R.drawable.card_back),
                contentDescription = stringResource(id = card.imageDescriptionRes),
                Modifier
                    .clickable { isHidden = !isHidden }
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 8 * density
                    }
            )
        }
    }
}

class CardPreviewProvider : PreviewParameterProvider<Card> {
    override val values = sequenceOf(
        Card(
            imageRes = R.drawable.simple_villager,
            imageDescriptionRes = R.string.simple_villager_description,
            cardNameRes = R.string.simple_villager_name
        )
    )
}
