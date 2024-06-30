package fr.agrouagrou.mobileapp.ui.gamephases

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.ui.data.Card
import fr.agrouagrou.mobileapp.ui.data.VillagerCard
import fr.agrouagrou.mobileapp.ui.data.WitchCard
import fr.agrouagrou.mobileapp.ui.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun CardView(
    @PreviewParameter(CardPreviewProvider::class, 1) card: Card? = null,
    showMessage: Boolean = true
) {
    var isHidden by remember { mutableStateOf(true) }
    val rotation by animateFloatAsState(
        targetValue = if (isHidden) 0f else 180f,
        animationSpec = tween(500),
        label = "",
    )

    AppTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showMessage && card != null) Text(
                text = if (isHidden) stringResource(R.string.tap_to_reveal_your_role) else stringResource(
                    R.string.your_role_is
                ) + stringResource(id = card.cardNameRes),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            if (card != null) Image(
                painter = painterResource(id = if (rotation > 90f) card.imageRes else R.drawable.card_back),
                contentDescription = stringResource(id = card.imageDescriptionRes),
                Modifier
                    .clickable { isHidden = !isHidden }
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 8 * density
                    }
            ) else {
                Image(painter = painterResource(id = R.drawable.card_back), contentDescription = "Card back")
            }
        }
    }
}

class CardPreviewProvider : PreviewParameterProvider<Card> {
    override val values = sequenceOf(
        VillagerCard,
        WitchCard,
    )
}
