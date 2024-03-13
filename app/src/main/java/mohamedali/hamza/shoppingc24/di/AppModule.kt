package mohamedali.hamza.shoppingc24.di

import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import mohamedali.hamza.core.datasource.network.ProductAPI
import mohamedali.hamza.core.repository.ProductRepository
import mohamedali.hamza.shoppingc24.R
import mohamedali.hamza.shoppingc24.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single(named("SERVER")) {
        androidContext().resources.getString(R.string.server)
    }
    single {
        MoshiConverterFactory
            .create(
                Moshi.Builder()
                    .build()
            )
    }
    single {
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("SERVER")))
            .client(get<OkHttpClient>())
            .addConverterFactory(get<MoshiConverterFactory>())

            .build()
    }
    single<ProductAPI> {
        get<Retrofit>().create(ProductAPI::class.java)
    }
    single(named("IODispatcher")) {
        Dispatchers.IO
    }
    single<ProductRepository> {
        ProductRepository(
            productAPI = get<ProductAPI>(),
            defaultDispatcherContext = get(named("IODispatcher"))
        )
    }
    viewModel {
        MainViewModel(get<ProductRepository>())
    }
}