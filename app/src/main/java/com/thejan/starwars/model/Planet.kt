package com.thejan.starwars.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Thejan Thrimanna on 11/13/21.
 */
@Parcelize
data class Planet(
    val name: String,
    val climate: String,
    val orbital_period: String,
    val gravity: String
): Parcelable