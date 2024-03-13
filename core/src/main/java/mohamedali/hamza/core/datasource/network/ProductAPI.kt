package mohamedali.hamza.core.datasource.network

import mohamedali.hamza.core.datasource.model.ProductOverviewData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductAPI {

    @GET("products-test.json")
    suspend fun getListProduct(): Response<ProductOverviewData>

}