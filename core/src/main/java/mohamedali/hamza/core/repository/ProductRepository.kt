package mohamedali.hamza.core.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import mohamedali.hamza.core.common.data
import mohamedali.hamza.core.datasource.network.ProductAPI
import mohamedali.hamza.core.models.IResponse
import mohamedali.hamza.core.models.Product
import mohamedali.hamza.core.models.ProductOverviews

class ProductRepository(
    private val productAPI: ProductAPI,
    private val defaultDispatcherContext: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend fun retrieveProducts(): IResponse{
        return runBlocking(defaultDispatcherContext) {
            productAPI.getListProduct().data { data->
                ProductOverviews(
                    header = data.header,
                    products = data.products,
                    )
            }
        }
    }
}