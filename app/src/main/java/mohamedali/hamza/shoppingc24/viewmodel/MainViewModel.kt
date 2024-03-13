package mohamedali.hamza.shoppingc24.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mohamedali.hamza.core.models.IResponse
import mohamedali.hamza.core.models.MyResponse
import mohamedali.hamza.core.models.Product
import mohamedali.hamza.core.repository.ProductRepository
import mohamedali.hamza.shoppingc24.commons.Filter

class MainViewModel(private val repository: ProductRepository) : ViewModel() {
    var filterToApply: Filter by mutableStateOf(Filter.Alle)
    private var mutableFavorites: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    val produtcsFavs: StateFlow<List<Int>> = mutableFavorites
    private var mutableStateFlow: MutableStateFlow<IResponse> =
        MutableStateFlow(MyResponse.NoResponse<Any>())

    val productsFlow: StateFlow<IResponse> = mutableStateFlow
    private var mutableStateFlowSelectedProduct: MutableStateFlow<Product?> =
        MutableStateFlow(null)
    val productFlowSelected: StateFlow<Product?> = mutableStateFlowSelectedProduct
    var isLoading: Boolean by mutableStateOf(false)
    private val errorMax = 3
    private var currentError = 0

    fun retrieve() {
        isLoading = true
        viewModelScope.launch {

            when (currentError == errorMax) {
                true -> {
                    delay(1200)
                    mutableStateFlow.value = MyResponse.ErrorResponse<Any>(error = "No Data Found")
                    currentError = -1
                }

                else -> {
                    val response = repository.retrieveProducts()
                    delay(1000)
                    mutableStateFlow.value = response
                    currentError++
                }
            }
            isLoading = false

        }
    }

    fun addToFav(product: Product) {
        val list = mutableFavorites.value.toMutableList()
        list.add(product.id)
        mutableFavorites.value = list
    }

    fun removeToFav(product: Product) {
        val list = mutableFavorites.value.toMutableList()
        list.remove(product.id)
        mutableFavorites.value = list
    }

    fun isFav(productId: Int) = mutableFavorites.value.contains(productId)


    fun selectProduct(product: Product) {
        mutableStateFlowSelectedProduct.value = product
    }

    fun filter(filter: Filter) {
        filterToApply = filter
    }
}