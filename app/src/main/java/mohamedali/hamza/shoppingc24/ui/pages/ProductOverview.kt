package mohamedali.hamza.shoppingc24.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import com.example.compose.md_theme_light_outline
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mohamedali.hamza.core.models.MyResponse
import mohamedali.hamza.core.models.Product
import mohamedali.hamza.core.models.ProductOverviews
import mohamedali.hamza.shoppingc24.commons.Filter
import mohamedali.hamza.shoppingc24.ui.component.ErrorProduct
import mohamedali.hamza.shoppingc24.ui.component.Loading
import mohamedali.hamza.shoppingc24.ui.component.ProductsList
import mohamedali.hamza.shoppingc24.viewmodel.MainViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductOverviewPage(
    modifier: Modifier,
    viewModel: MainViewModel,
    onProductClick: (Product) -> Unit,
    clickFooter: () -> Unit
) {
    val isLoading = viewModel.isLoading
    var refreshing by remember {
        mutableStateOf(false)
    }
    val productStateData = viewModel.productsFlow.collectAsState()
    val response = productStateData.value
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            coroutineScope.launch {
                refreshing = true
                viewModel.retrieve()
                refreshing = false
            }

        }
    )
    when {
        isLoading || response is MyResponse.NoResponse<*> -> Loading()
        response is MyResponse.SuccessResponse<*> -> Box {
            ProductOverviewBody(
                response.data as ProductOverviews,
                onProductClick,
                filter = viewModel.filterToApply,
                clickFooter = clickFooter,
                alleCallback = {
                    viewModel.filter(Filter.Alle)
                },
                avCallback = {
                    viewModel.filter(Filter.AV)
                },
                favCallback = {
                    viewModel.filter(Filter.Fav)
                },

                modifier = modifier.pullRefresh(pullRefreshState),

                )
            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }

        else -> ErrorProduct(
            modifier = modifier,
            retryAgain = {
                viewModel.retrieve()
            })
    }

}

@Composable
fun ProductOverviewBody(
    productOverviews: ProductOverviews,
    onProductClick: (Product) -> Unit,
    filter: Filter,
    clickFooter: () -> Unit,
    alleCallback: () -> Unit,
    avCallback: () -> Unit,
    favCallback: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        FilterProduct(
            alleCallback = alleCallback,
            favCallback = favCallback,
            avCallback = avCallback ,
            filter = filter
        )
        HeaderProductOver(
            title = productOverviews.header.headerTitle,
            description = productOverviews.header.headerDescription
        )
        ProductsList(
            modifier = Modifier.weight(0.7f),
            products = productOverviews.products,
            onProductClick = onProductClick,
            clickFooter = clickFooter,
            filter = filter
        )

    }
}


@Composable
fun HeaderProductOver(title: String, description: String, modifier: Modifier = Modifier) {
    Column(
        Modifier
            .then(modifier)
            .height(64.dp)
            .padding(start = 6.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = description, style = MaterialTheme.typography.bodySmall.copy(
                color = md_theme_light_outline
            )
        )
    }
}


@Composable
fun FilterProduct(
    filter: Filter,
    alleCallback: () -> Unit,
    avCallback: () -> Unit,
    favCallback: () -> Unit,
) {
    Row(
        Modifier
            .height(48.dp)
            .fillMaxWidth()
    ) {
        TextButton(
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = when {
                    filter == Filter.Alle -> MaterialTheme.colorScheme.primary
                    else -> Color.Transparent
                }
            ),
            onClick = alleCallback,
            modifier = Modifier.weight(0.3f)
        ) {
            Text(text = "Alle", color = when {
                filter == Filter.Alle ->  Color.White
                else ->  MaterialTheme.colorScheme.onBackground
            })
        }
        TextButton(
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = when {
                    filter == Filter.AV -> MaterialTheme.colorScheme.primary
                    else ->  Color.Transparent
                }
            ),
            onClick = avCallback,
            modifier = Modifier.weight(0.3f)
        ) {
            Text(text = "Verfügbar", color = when {
                filter == Filter.AV -> Color.White
                else ->  MaterialTheme.colorScheme.onBackground
            })
        }
        TextButton(
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = when {
                    filter == Filter.Fav -> MaterialTheme.colorScheme.primary
                    else ->  Color.Transparent
                }
            ), onClick = favCallback, modifier = Modifier.weight(0.3f)
        ) {
            Text(text = "Vorgemerkt", color = when {
                filter == Filter.Fav -> Color.White
                else ->   MaterialTheme.colorScheme.onBackground
            })
        }
    }
}

@Preview
@Composable
fun PrevFilter() {
    FilterProduct(
        alleCallback = {

        },
        favCallback = {

        },
        avCallback = {

        },
        filter = Filter.Fav
    )
}

@Preview
@Composable
fun PrevHeader() {
    HeaderProductOver(title = "title 1", description = "Lorem ipsum dolor sit amet")
}