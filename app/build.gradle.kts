plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.firebase.crashlytics)

}

android {
    namespace = "com.example.appenote"
    compileSdk = 35



    defaultConfig {
        applicationId = "com.example.appenote"
        minSdk = 24
        targetSdk = 34
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
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.crashlytics)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    //========================== firestore
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")

    // ViewModel et LiveData (Lifecycle components)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

    //======================= Si tu veux utiliser des coroutines dans ViewModel
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    //===========================================================GSON
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.google.firebase:firebase-crashlytics:19.4.0")
    implementation("com.google.firebase:firebase-analytics:22.2.0")

    //update version kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
    // ADD the API-only library to all variants
    implementation("com.google.firebase:firebase-appdistribution-api:16.0.0-beta14")
    //=============================






}

