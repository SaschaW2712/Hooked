plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.saschaw.hooked"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.saschaw.hooked"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.kotlin.serialization.json)
    implementation(libs.androidx.navigationCompose)
    implementation(libs.androidx.compose.m3)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.material)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
