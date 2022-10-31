plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.mavenPublish)
}

android {
    compileSdk = Configs.compileSdkVersion
    buildToolsVersion = Configs.buildToolsVersion

    defaultConfig {
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion
        vectorDrawables.useSupportLibrary = true

        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        getByName<com.android.build.gradle.internal.dsl.BuildType>("release") {
            isMinifyEnabled = false
            setProguardFiles(mutableListOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"))
        }
    }

    buildFeatures.dataBinding = true
}
