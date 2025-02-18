buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20") // Kotlin Gradle Plugin
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

plugins {
    kotlin("jvm") version "2.0.20" // Apply Kotlin plugin
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20") // Kotlin Standard Library
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // Coroutines Core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // Coroutines for Android
}