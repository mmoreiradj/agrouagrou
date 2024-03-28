package fr.agrouagrou.mobileapp

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Card(
    @DrawableRes val imageRes: Int,
    @StringRes val imageDescriptionRes: Int,
    @StringRes val cardNameRes: Int
)
