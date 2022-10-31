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
            setProguardFiles(
                mutableListOf(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }

    buildFeatures.viewBinding = true
    namespace = "com.trendyol.uicomponents.quantitypickerview"
}

dependencies {
    implementation(Dependencies.kotlinJDK)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.lifecycleExtensions)
    kapt(Dependencies.lifecycleCompiler)
}
