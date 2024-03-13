package mohamedali.hamza.core.models

import mohamedali.hamza.core.common.simpleDateFormat
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import java.util.SimpleTimeZone

data class ProductOverviews(
    val header: Header,
    val products:List<Product>,
)

data class Header(
    val headerTitle: String,
    val headerDescription: String
)

data class Product(
    val id: Int,
    var name: String,
    var type: String,
    val color: String,
    val imageURL: String,
    val colorCode: String,
    val available: Boolean,
    val releaseDate: Long,
    val description: String,
    val longDescription: String,
    val rating: Double,
    val price: Price,
){
    fun parseDate():String {
       val calendar = Calendar.getInstance()
        calendar.timeInMillis = releaseDate
        return simpleDateFormat.format(calendar.time)
    }
}

data class Price(
    val value: Double,
    val currency: String,
)