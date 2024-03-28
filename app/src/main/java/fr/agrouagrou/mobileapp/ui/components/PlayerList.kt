package fr.agrouagrou.mobileapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.twotone.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.Role
import fr.agrouagrou.mobileapp.roleToImage

@Composable
fun PlayerList(players: List<Player>, onClick: (Player) -> Unit = {}) {
    Column {
        players.forEach { player ->
            ListItem(
                headlineContent = {
                    Text(text = player.username)
                },
                supportingContent = if (player.role != null) {
                    {
                        Text(text = player.role.toString())
                    }
                } else {
                    null
                },
                leadingContent = {
                    Image(
                        painter = painterResource(id = roleToImage(player.role)),
                        contentDescription = player.role.toString(),
                        Modifier
                            .size(56.dp, 56.dp)
                    )
                },
                trailingContent = if (onClick != {}) {
                    {
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                            contentDescription = "Edit",
                            Modifier
                                .size(24.dp)
                                .clickable { onClick(player) }
                        )
                    }
                } else {
                    null
                }
            )
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
fun PlayerListPreview(@PreviewParameter(PlayerListPreviewProvider::class) players: List<Player>) {
    PlayerList(players)
}

class PlayerListPreviewProvider : PreviewParameterProvider<List<Player>> {
    override val values: Sequence<List<Player>> = sequenceOf(
        listOf(
            Player(username = "JohnDoe"),
            Player(username = "JaneDoe"),
            Player(username = "Alice"),
        ),
        listOf(
            Player(username = "Bob", role = Role.VILLAGER),
            Player(username = "Eve", role = Role.WEREWOLF),
            Player(username = "Matéo", role = Role.VILLAGER),
        ),
    )
}
