plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "mohamedali.hamza.shoppingc24"
    compileSdk = 34

    defaultConfig {
        applicationId = "mohamedali.hamza.shoppingc24"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)
    //dataStore
    implementation (libs.androidx.datastore.preferences)
    implementation (libs.androidx.preference.ktx)

    // optional - Jetpack Compose integration
    implementation (libs.androidx.paging.compose)
    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation(libs.logging.interceptor)

    //coil
    implementation(libs.coil.compose)

    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)


    // ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation( libs.androidx.lifecycle.viewmodel.compose)
    implementation( libs.androidx.lifecycle.service)
    //rating
    //implementation ("com.github.SmartToolFactory:Compose-RatingBar:2.1.1")

    //Koin
    implementation (libs.koin.android)
    // Jetpack WorkManager
    implementation (libs.koin.androidx.workmanager)
    // Navigation Graph
    implementation (libs.koin.androidx.navigation)
    // jetpack compose
    implementation (libs.koin.androidx.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("androidx.compose.material:material:1.6.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    implementation( project(":core"))
}