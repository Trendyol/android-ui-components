plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.mavenPublish)
}

kotlin.jvmToolchain(17)

android {
    compileSdk = Configs.compileSdkVersion
    buildToolsVersion = Configs.buildToolsVersion

    defaultConfig {
        minSdk = Configs.minSdkVersion
        vectorDrawables.useSupportLibrary = true

        consumerProguardFiles("consumer-rules.pro")
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

    namespace = "com.trendyol.timelineview"
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(Dependencies.appCompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.recyclerView)
    implementation(Dependencies.constraintLayout)
}
