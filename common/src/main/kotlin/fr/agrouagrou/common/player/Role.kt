package fr.agrouagrou.common.player

sealed class Role {
    data object Villager : Role()

    data object Werewolf : Role()

    data class Witch(var deathPotion: Int = 1, var lifePotion: Int = 1) : Role() {
        fun useDeathPotion() {
            if (deathPotion <= 0) {
                throw IllegalStateException("Witch has no more death potion")
            }

            deathPotion -= 1
        }

        fun useLifePotion() {
            if (lifePotion <= 0) {
                throw IllegalStateException("Witch has no more life potion")
            }

            lifePotion -= 1
        }
    }

    data object FortuneTeller : Role()
}
