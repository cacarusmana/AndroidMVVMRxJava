package com.rxjavaaac.example.model

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 * @author caca rusmana on 2019-09-26
 */

class Converters {

    @TypeConverter
    fun fromBigDecimalToString(value: BigDecimal): String {
        return value.toPlainString()
    }

    @TypeConverter
    fun fromStringToBigDecimal(value: String): BigDecimal {
        return BigDecimal(value)
    }
}