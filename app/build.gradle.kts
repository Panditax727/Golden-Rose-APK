plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.golden_rose_apk"
    compileSdk = 34  // Corregido: usa número directamente

    defaultConfig {
        applicationId = "com.example.golden_rose_apk"
        minSdk = 24
        targetSdk = 34  // Actualizado a 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"  // Agregar esto
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
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // DEPENDENCIAS NECESARIAS PARA TU PROYECTO:

    // 1. Navigation Compose (esencial)
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // 2. ViewModel (para manejar estado)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // 3. Material Icons (para los iconos)
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    // 4. Si quieres usar Retrofit para API (OPCIONAL - elimina si no lo necesitas)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // 5. Coil para cargar imágenes (si vas a usar imágenes de internet)
    implementation("io.coil-kt:coil-compose:2.5.0")

    // ELIMINA estas líneas duplicadas (ya están en el BOM):
    // implementation("androidx.compose.material3:material3:1.2.1")
    // implementation("androidx.compose.ui:ui:1.7.5")
    // implementation("androidx.compose.ui:ui-tooling-preview:1.7.5")
    // implementation("androidx.activity:activity-compose:1.9.2")
    // implementation("androidx.compose.material:material-icons-core:1.7.5")
    // implementation("androidx.compose.material:material-icons-extended:1.7.5")

    // ELIMINA estas si no las necesitas:
    //implementation(libs.play.services.maps)        // Si no usas Google Maps
    //implementation(libs.play.services.analytics.impl) // Si no usas Analytics
    //implementation(libs.androidx.tv.material)      // Si no es app para TV

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}