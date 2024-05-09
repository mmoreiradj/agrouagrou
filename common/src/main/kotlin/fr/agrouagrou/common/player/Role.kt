package fr.agrouagrou.common.player

sealed class Role {
    data object Villager : Role()

    data object Werewolf : Role()

    data class Witch(val deathPotion: Int = 1, val lifePotion: Int = 1) : Role()

    data object FortuneTeller : Role()
}
