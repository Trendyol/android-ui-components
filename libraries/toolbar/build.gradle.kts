plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.mavenPublish)
}

kotlin.jvmToolchain(21)

android {
    compileSdk = Configs.compileSdkVersion
    buildToolsVersion = Configs.buildToolsVersion

    defaultConfig {
        minSdk = Configs.minSdkVersion
        vectorDrawables.useSupportLibrary = true

        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures.viewBinding = true
    buildTypes {
        getByName<com.android.build.gradle.internal.dsl.BuildType>("release") {
            isMinifyEnabled = false
            setProguardFiles(mutableListOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"))
        }
    }

    publishing {
        this.singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
    }

    namespace = "com.trendyol.uicomponents.toolbar"
}

dependencies {
    implementation(Dependencies.appCompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.constraintLayout)
}
