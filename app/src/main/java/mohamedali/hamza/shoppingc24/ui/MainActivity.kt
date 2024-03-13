package mohamedali.hamza.shoppingc24.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.ShoppingC24Theme
import kotlinx.coroutines.launch
import mohamedali.hamza.shoppingc24.R
import mohamedali.hamza.shoppingc24.ui.component.WebViewScreen
import mohamedali.hamza.shoppingc24.ui.pages.ProductDetailPage
import mohamedali.hamza.shoppingc24.ui.pages.ProductOverviewPage
import mohamedali.hamza.shoppingc24.viewmodel.MainViewModel
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            KoinAndroidContext {
                App(
                    //mainViewModel = mainViewModel,
                    navController = navController,
                )
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                mainViewModel.retrieve()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun App(
        navController: NavHostController,
    ) {

        ShoppingC24Theme {
            NavHost(navController = navController, startDestination = "products") {
                composable("products") {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(stringResource(id = R.string.Home))
                                }
                            )
                        },
                    ) { innerPadding ->
                        ProductOverviewPage(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = mainViewModel,
                            onProductClick = { product ->
                                mainViewModel.selectProduct(product)
                                navController.navigate("detail/${product.id}")
                            }, clickFooter = {
                                navController.navigate("webpage")
                            }
                        )
                    }

                }
                composable(
                    "detail/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    KoinContext {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(stringResource(id = R.string.detail))
                                    }
                                )
                            },
                        ) { innerPadding ->
                            ProductDetailPage(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = mainViewModel,
                                clickFooter = {
                                    navController.navigate("webpage")
                                }
                            )
                        }

                    }
                }
                composable("webpage") { backStackEntry ->
                    KoinContext {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(stringResource(id = R.string.web))
                                    }
                                )
                            },
                        ) { innerPadding ->
                            WebViewScreen(
                                url = "https://m.check24.de/rechtliche-hinweise/?deviceoutput=app",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                    }
                }
            }
        }
    }
}

