/**
 * Top-level build file for ftc_app project.
 *
 * It is extraordinarily rare that you will ever need to edit this file.
 */

buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        // Note for FTC Teams: Do not modify this yourself.
        classpath("com.android.tools.build:gradle:8.7.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21") // Kotlin Gradle Plugin
    }
}

// This is now required because aapt2 has to be downloaded from the
// google() repository beginning with version 3.2 of the Android Gradle Plugin
allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.9.21" // Apply Kotlin plugin
    java // Apply Java plugin
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.21") // Kotlin Standard Library
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // Coroutines Core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // Coroutines for Android
    implementation("org.apache.commons:commons-math3:3.6.1") // Apache Commons Math
}
