import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}
val properties = Properties().apply {
    load(File(rootProject.projectDir, "local.properties").inputStream())
}
android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true

    }

    buildTypes {
        debug {
            buildConfigField(
                "String", "BASE_URL",
                "\"${properties.getProperty("MOVIES_BASE_URL")}\""
            )
            buildConfigField("String", "API_KEY", "\"${properties.getProperty("MOVIES_API_KEY")}\"")
            buildConfigField(
                "String",
                "IMAGE_URL",
                "\"${properties.getProperty("MOVIES_IMAGE_URL")}\""
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String", "MOVIES_BASE_URL",
                "\"${properties.getProperty("MOVIES_BASE_URL")}\""
            )
            buildConfigField("String", "API_KEY", "\"${properties.getProperty("MOVIES_API_KEY")}\"")

            buildConfigField(
                "String",
                "IMAGE_URL",
                "\"${properties.getProperty("MOVIES_IMAGE_URL")}\""
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(project(":util"))
    implementation(project(":domain"))

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")


    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


    implementation ("com.squareup.okhttp3:okhttp:4.9.3")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1-native-mt")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

    // ViewModelScope
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    // LifecycleScope
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")


    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")


    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    //room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    implementation ("com.google.code.gson:gson:2.8.7")

    //test
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.room:room-testing:2.5.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")

    testImplementation ("io.mockk:mockk:1.13.2")
    testImplementation ("org.mockito:mockito-core:4.5.1")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation( "io.mockk:mockk-android:1.13.2")

}