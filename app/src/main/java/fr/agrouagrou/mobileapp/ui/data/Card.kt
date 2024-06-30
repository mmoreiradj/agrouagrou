package fr.agrouagrou.mobileapp.ui.data;

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import fr.agrouagrou.grpc_server.R

open class Card(
    @DrawableRes val imageRes: Int,
    @StringRes val imageDescriptionRes: Int,
    @StringRes val cardNameRes: Int
)

object VillagerCard : Card(
    imageRes = R.drawable.simple_villager,
    imageDescriptionRes = R.string.simple_villager_description,
    cardNameRes = R.string.simple_villager_name
)

object WerewolfCard : Card(
    imageRes = R.drawable.simple_werewolf,
    imageDescriptionRes = R.string.werewolf_description,
    cardNameRes = R.string.werewolf_name
)

object WitchCard : Card(
    imageRes = R.drawable.witch,
    imageDescriptionRes = R.string.witch_description,
    cardNameRes = R.string.witch_name
)

object FortuneTellerCard : Card(
    imageRes = R.drawable.fortune_teller,
    imageDescriptionRes = R.string.fortune_teller_description,
    cardNameRes = R.string.fortune_teller_name
)
