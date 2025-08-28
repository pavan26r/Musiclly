import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "com.example.musicuiapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.musicuiapp"
        minSdk = 25
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}


    dependencies {
        // camera permissions ->
        implementation("com.google.accompanist:accompanist-permissions:0.34.0")
        implementation("io.coil-kt:coil-compose:2.7.0")

        implementation(libs.camerax.core)
        implementation(libs.camerax.camera2)
        implementation(libs.camerax.lifecycle)
        implementation(libs.camerax.view)
        implementation(libs.retrofit)
        implementation(libs.retrofit.converter.gson)
        implementation(libs.okhttp.logging)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.activity.compose.v190)
        implementation(libs.firebase.storage.ktx)
        implementation(libs.androidx.media3.exoplayer)
        implementation(libs.androidx.media3.ui)
        // for storage ->
        implementation(platform(libs.firebase.bom.v3410))
 // Use the latest version
        // Add the dependency for the Firebase SDK for Google Analytics
        // Recommended to ensure a better Firebase experience
        implementation(libs.firebase.analytics)
        // Add the dependency for Firebase Cloud Storage
        // If you are using Kotlin, you might also want the KTX (Kotlin extensions) library
        // for Cloud Storage, which provides more idiomatic Kotlin APIs:
        implementation(libs.firebase.storage.ktx)
        implementation(libs.checkout)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.core.ktx.v120)
        implementation(libs.ui)
        implementation(libs.androidx.material)
        implementation(libs.ui.tooling.preview)
        implementation(libs.firebase.auth.ktx)
        implementation(libs.firebase.auth)
        implementation(libs.androidx.credentials)
        implementation(libs.androidx.credentials.play.services.auth)
        implementation(libs.googleid)
        implementation(platform(libs.firebase.bom))
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.compose.material3)
        implementation(libs.firebase.auth)
        implementation(libs.androidx.credentials)
        implementation(libs.androidx.credentials.play.services.auth)
        implementation(libs.googleid)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }
    // Firebase BoM (manages versions automatically)
