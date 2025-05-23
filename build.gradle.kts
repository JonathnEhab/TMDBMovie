// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies {


        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")



    }

}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    id ("com.google.dagger.hilt.android") version "2.50" apply false
}