package mohamedali.hamza.shoppingc24.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mohamedali.hamza.core.models.Product
import mohamedali.hamza.shoppingc24.ui.component.FooterProductOver
import mohamedali.hamza.shoppingc24.ui.component.PriceProduct
import mohamedali.hamza.shoppingc24.ui.component.SpacerWidth
import mohamedali.hamza.shoppingc24.ui.component.StarRatingBar
import mohamedali.hamza.shoppingc24.viewmodel.MainViewModel

@Composable
fun ProductDetailPage(modifier: Modifier, viewModel: MainViewModel, clickFooter: () -> Unit) {
    val product = viewModel.productFlowSelected.collectAsState().value!!
    ProductBody(product, {}, clickFooter, modifier)
}

@Composable
fun ProductBody(
    product: Product,
    onFavUnFavClick: () -> Unit,
    clickFooter: () -> Unit,
    modifier: Modifier
) {
    Column(

        modifier = Modifier
            .then(modifier)
            .padding(6.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(96.dp)
        ) {
            AsyncImage(
                model = product.imageURL,
                contentDescription = "image product",
                modifier = Modifier.size(56.dp)
            )
            SpacerWidth(width = 4.dp)
            ProductDetail(product)
        }
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )
        Button(
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(2.dp),
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Vormerken")
        }
        Box(Modifier.weight(0.5f)) {
            LongDescription(product.longDescription)
        }
        FooterProductOver(clickFooter = clickFooter)
    }
}

@Composable
fun ProductDetail(product: Product) {
    Column {
        Row {
            Text(
                text = product.name, Modifier
                    .weight(0.65f)
            )

        }
        if (product.available) {
            PriceProduct(product.price)
        }
        Row {
            Box(Modifier.weight(0.7f)) {
                StarRatingBar(rating = product.rating.toFloat(), size = 6f)
            }
            if (product.available)
                Text(text = product.parseDate())
        }
    }
}

@Composable
fun LongDescription(description: String) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(text = "Beschreibung", style = MaterialTheme.typography.bodyLarge)
        Text(text = description, style = MaterialTheme.typography.bodyMedium)
    }
}