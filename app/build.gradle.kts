plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidx.navigation.safe.args)
}

android {
    namespace = "com.annguyenhoang.tpiassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.annguyenhoang.tpiassignment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://www.travel.taipei/open-api/vi/\"")
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField("String", "BASE_URL", "\"https://www.travel.taipei/open-api/vi/\"")
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // coroutine
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.android.coroutine)

    // navigation
    implementation(libs.jetpack.navigation.fragment)
    implementation(libs.jetpack.navigation.ui)

    // retrofit2 + gson
    implementation(libs.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter)

    // koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.androidx.navigation)

    // coil
    implementation(libs.coil)

    // Logger
    implementation(libs.logger)
    implementation(libs.timber)

    // desugar for android backward compatibility
    coreLibraryDesugaring(libs.desugar)

    // skeleton loading
    implementation(libs.skeletonLoading)
}