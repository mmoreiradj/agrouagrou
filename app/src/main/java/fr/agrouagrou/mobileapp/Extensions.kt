package fr.agrouagrou.mobileapp

import fr.agrouagrou.common.player.Role
import fr.agrouagrou.mobileapp.ui.data.Card
import fr.agrouagrou.mobileapp.ui.data.FortuneTellerCard
import fr.agrouagrou.mobileapp.ui.data.VillagerCard
import fr.agrouagrou.mobileapp.ui.data.WerewolfCard
import fr.agrouagrou.mobileapp.ui.data.WitchCard

fun Role.toCard(): Card =
    when (this) {
        Role.Villager -> VillagerCard
        Role.Werewolf -> WerewolfCard
        Role.FortuneTeller -> FortuneTellerCard
        is Role.Witch -> WitchCard
    }
