plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.mavenPublish)
    id(Plugins.compose) version "2.1.21"
}

kotlin.jvmToolchain(21)

android {
    namespace = "com.trendyol.ratingbarcompose"
    compileSdk = Configs.compileSdkVersion

    defaultConfig {
        minSdk = Configs.minSdkVersion
        vectorDrawables.useSupportLibrary = true
    }

    lint {
        disable.add("NullSafeMutableLiveData")
    }

    buildTypes {
        getByName<com.android.build.gradle.internal.dsl.BuildType>("release") {
            isMinifyEnabled = false
            setProguardFiles(
                mutableListOf(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    val composeBom = platform(Dependencies.composeBom)
    implementation(composeBom)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeCoil)
    implementation(Dependencies.composeRuntime)
    implementation(Dependencies.composeConstraintLayout)
}