plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.saschaw.hooked.core.datastore"
    compileSdk = 34
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.dagger.hilt.android)
    implementation(libs.google.material)
    implementation(libs.openId.appAuth)
    implementation(libs.kotlinx.serialization.json)

    ksp(libs.dagger.hilt.compiler)

    implementation(project(":core:model"))
}