plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.secrets)
}

android {
    namespace = "com.saschaw.hooked"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.saschaw.hooked"
        minSdk = 27
        multiDexEnabled = true
        targetSdk = 35
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["appAuthRedirectScheme"] = "com.saschaw.hooked"

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
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigationCompose)
    implementation(libs.androidx.compose.m3)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.google.material)
    implementation(libs.kotlinx.datetime)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.material3.adaptive.navigation.suite.android)
    implementation(project(":core:data"))
    ksp(libs.dagger.hilt.compiler)
    implementation(project(":core:designsystem"))
    implementation(project(":core:authentication"))
    implementation(project(":feature:onboarding"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    implementation(libs.auth0.android.jwtDecode)
    implementation(libs.openId.appAuth)

    implementation(project(":feature:discover"))
    implementation(project(":feature:favorites"))

    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.material.navigation)
    implementation(libs.androidx.adaptive.android)
    implementation(libs.androidx.core.splashScreen)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
