package edu.bluejack22_2.Caddo.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var categoryName : String? = null
) : Parcelable