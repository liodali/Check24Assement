package mohamedali.hamza.shoppingc24.ui.component

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mohamedali.hamza.core.models.Price
import mohamedali.hamza.core.models.Product
import mohamedali.hamza.shoppingc24.commons.Filter
import java.time.LocalTime

@Composable
fun ProductsList(
    modifier: Modifier = Modifier,
    products: List<Product>,
    favs: List<Int> = emptyList(),
    filter: Filter,
    onProductClick: (Product) -> Unit,
    clickFooter: () -> Unit,
) {
    val list = when {
        filter == Filter.Alle -> products
        filter == Filter.AV -> products.filter { product -> product.available }
        else -> products.filter { product ->
            favs.contains(product.id)
        }
    }
    if (list.isEmpty()) {
        Center {
            Text(text = "no favorite products")
        }
    } else
        LazyColumn(modifier) {
            items(list) { product ->
                ProductItem(product, onProductClick)
            }
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    FooterProductOver(clickFooter = clickFooter)
                }
            }
        }
}

@Composable
fun ProductItem(product: Product, onClick: (Product) -> Unit) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(width = 1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .padding(6.dp)
            .clickable {
                onClick(product)
            }
    ) {
        when {
            product.available ->
                Row(modifier = Modifier.padding(12.dp)) {

                    AsyncImage(
                        model = product.imageURL,
                        contentDescription = "image product",
                        modifier = Modifier.size(56.dp)
                    )
                    SpacerWidth(width = 4.dp)
                    ProductItemDetail(product)
                }


            else -> Row(modifier = Modifier.padding(12.dp)) {
                ProductItemDetail(product, Modifier.weight(0.75f))
                SpacerWidth(width = 8.dp)
                AsyncImage(
                    model = product.imageURL,
                    contentDescription = product.imageURL,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(56.dp)
                )


            }
        }

    }
}

@Composable
fun ProductItemDetail(product: Product, modifier: Modifier = Modifier) {
    Column(modifier) {
        Row {
            Text(
                text = product.name, Modifier
                    .weight(0.65f)
            )
            if (product.available)
                Text(text = product.parseDate())
        }
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )

        Row {
            if (product.available) {
                PriceProduct(product.price, modifier = Modifier.weight(0.6f))
            }
            StarRatingBar(rating = product.rating.toFloat(), size = 6f)
        }
    }
}

@Composable
fun PriceProduct(price: Price, modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Preis: ")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.outline)) {
                append(" ${price.value} ${price.currency}")
            }
        }, modifier = modifier
    )
}

@Preview
@Composable
fun PreviProductItem() {
    ProductItem(
        product = Product(
            id = 1,
            name = "name",
            type = "",
            color = "",
            imageURL = "https://kredit.check24.de/konto-kredit/ratenkredit/nativeapps/imgs/08.png",
            colorCode = "",
            available = true,
            releaseDate = 1460629605,
            description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr,",
            longDescription = "long description",
            rating = 3.0,
            price = Price(value = 12.0, currency = "Eur"),
        ), onClick = {

        }
    )
}

@Preview
@Composable
fun PreviProductDetail() {
    ProductItemDetail(
        product = Product(
            id = 1,
            name = "name",
            type = "",
            color = "",
            imageURL = "",
            colorCode = "",
            available = true,
            releaseDate = 1460629605,
            description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr,",
            longDescription = "long description",
            rating = 3.0,
            price = Price(value = 12.0, currency = "Eur"),
        )
    )
}

@Preview
@Composable
fun PreviProductDetailNotAvailable() {
    ProductItemDetail(
        product = Product(
            id = 1,
            name = "name",
            type = "",
            color = "",
            imageURL = "",
            colorCode = "",
            available = false,
            releaseDate = 1460629605,
            description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr,",
            longDescription = "long description",
            rating = 3.0,
            price = Price(value = 12.0, currency = "Eur"),
        )
    )
}


@Preview
@Composable
fun PreviPriceProduct() {
    PriceProduct(Price(value = 12.0, currency = "Eur"))
}
