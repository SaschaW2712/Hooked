plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.saschaw.hooked.core.designsystem"
    compileSdk = 34
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)

    api(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.material.icons.core.android)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.material3.adaptive.navigation.suite.android)
    implementation(libs.androidx.ui.tooling.preview.android)
    api(libs.androidx.compose.ui.googleFonts)
    implementation(libs.coil.compose)
    implementation(project(":core:model"))

    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
}
