plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.saschaw.hooked.core.network"
    compileSdk = 34
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.transport.runtime)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.auth0.android.jwtDecode)
    implementation(libs.openId.appAuth)

    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}