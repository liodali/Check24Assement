package mohamedali.hamza.shoppingc24

import android.app.Application
import mohamedali.hamza.shoppingc24.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShoppingApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Reference Android context
            androidContext(this@ShoppingApplication)
            // Load modules
            modules(appModule)
        }
    }
}