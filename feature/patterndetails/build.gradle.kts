plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.saschaw.hooked.feature.patterndetails"
    compileSdk = 34
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    testImplementation(libs.androidx.junit)
    implementation(libs.androidx.compose.m3)
    implementation(libs.kotlinx.serialization.json)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.navigationCompose)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.material.navigation)
    implementation(libs.coil.compose)
    ksp(libs.dagger.hilt.compiler)
    implementation(project(":core:datastore"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:designsystem"))
}