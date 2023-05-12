plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.mavenPublish)
}

android {
    namespace = "com.trendyol.uicomponents.timelineviewcompose"
    compileSdk = 33

    defaultConfig {
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion

        vectorDrawables.useSupportLibrary = true
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

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.kotlinCompilerExtensionVersion
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