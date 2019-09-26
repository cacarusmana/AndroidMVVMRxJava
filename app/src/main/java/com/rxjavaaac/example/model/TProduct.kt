package com.rxjavaaac.example.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

/**
 * @author caca rusmana on 2019-09-26
 */

@Entity
@Parcelize
data class TProduct(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var productName: String,
    var price: BigDecimal
) : Parcelable