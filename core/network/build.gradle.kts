plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.secrets)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.saschaw.hooked.core.network"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.transport.runtime)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.auth0.android.jwtDecode)
    implementation(libs.openId.appAuth)
    implementation(libs.kotlinx.datetime)
    implementation(libs.dagger.hilt.android)
    implementation(libs.okhttp)
    implementation(project(":core:authentication"))
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    ksp(libs.dagger.hilt.compiler)

    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
