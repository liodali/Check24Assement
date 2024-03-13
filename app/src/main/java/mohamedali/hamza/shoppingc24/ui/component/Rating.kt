package mohamedali.hamza.shoppingc24.ui.component

import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import mohamedali.hamza.shoppingc24.R

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    size: Float = 12f
) {
    val density = LocalDensity.current.density
    val starSize = (size * density).dp
    val starSpacing = (0.5f * density).dp

    val fraction = when {
        rating - rating.toInt() > 0f -> 0.5f
        else -> 0f
    }
    val decimal = rating.toInt()
    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= decimal
            val icon = if (isSelected) Icons.Rounded.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else MaterialTheme.colorScheme.outline
            if (fraction > 0) {
                when {
                    i <= rating || i - decimal > 1 -> Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTintColor,
                        modifier = Modifier.size(starSize)
                    )

                    i - decimal == 1 -> {
                        Image(
                            painter = painterResource(R.drawable.baseline_star_half_24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color(0xFFFFC700)),
                            modifier = Modifier.size(starSize)
                        )
                    }
                }
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTintColor,
                    modifier = Modifier
                        .size(starSize)
                )
            }



            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}



@Preview
@Composable
fun StarRatingSample() {
    val rating by remember { mutableStateOf(2.2f) } //default rating will be 1

    StarRatingBar(
        maxStars = 5,
        rating = rating,
        size = 8f

    )
}
