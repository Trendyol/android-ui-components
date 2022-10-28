plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

android {
    compileSdk = Configs.compileSdkVersion
    buildToolsVersion = Configs.buildToolsVersion

    defaultConfig {
        applicationId = Configs.applicationId
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion
        versionCode = 1
        versionName = "1.0"
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

    buildFeatures.dataBinding = true
}

dependencies {
    implementation(projects.libraries.ratingBar)
    implementation(projects.libraries.dialogs)
    implementation(projects.libraries.imageSlider)
    implementation(projects.libraries.phonenumber)
    implementation(projects.libraries.toolbar)
    implementation(projects.libraries.suggestionInputView)
    implementation(projects.libraries.cardInputView)
    implementation(projects.libraries.quantityPickerView)
    implementation(projects.libraries.timelineView)
    implementation(projects.libraries.fitOptionMessageView)

    implementation(Dependencies.kotlinJDK)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.glide)
}
