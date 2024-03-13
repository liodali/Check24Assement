package mohamedali.hamza.core.datasource.model

import mohamedali.hamza.core.models.Header
import mohamedali.hamza.core.models.Price
import mohamedali.hamza.core.models.Product

data class ProductOverviewData(
    val header: Header,
    val filters: List<String>,
    val products:List<Product>,
)

