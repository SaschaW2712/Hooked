plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.saschaw.hooked.feature.favorites"
    compileSdk = 34
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigationCompose)
    implementation(libs.androidx.compose.m3)
    implementation(project(":core:designsystem"))
    implementation(libs.dagger.hilt.android)
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    testImplementation(libs.androidx.junit)
    implementation(libs.androidx.hilt.navigation.compose)

    ksp(libs.dagger.hilt.compiler)

    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
