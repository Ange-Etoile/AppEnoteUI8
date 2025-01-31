// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    //======================================== service google
    id("com.google.gms.google-services") version "4.4.2" apply false

    //============================================= app distribution
    alias(libs.plugins.google.firebase.appdistribution) apply false
    //============================================= crashlitics
    alias(libs.plugins.google.firebase.crashlytics) apply false

    //modifier la version d'android




}