// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.apply {
        set("lifecycle_version", "2.6.1")
        set("room_version", "2.5.2")
        set("dokka_version", "1.9.10")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath( "org.jetbrains.dokka:dokka-gradle-plugin:${rootProject.extra["dokka_version"]}")
    }
}

plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
   // id("org.jetbrains.dokka") version "1.9.10"
}


