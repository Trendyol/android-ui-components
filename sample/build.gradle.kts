plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

android {
    compileSdkVersion(Configs.compileSdkVersion)
    buildToolsVersion(Configs.buildToolsVersion)

    defaultConfig {
        applicationId = Configs.applicationId
        minSdkVersion(Configs.minSdkVersion)
        targetSdkVersion(Configs.targetSdkVersion)
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

    dataBinding.isEnabled = true
}

dependencies {
    implementation(project(Components.ratingBar))
    implementation(project(Components.dialogs))
    implementation(project(Components.imageSlider))
    implementation(project(Components.phoneNumber))
    implementation(project(Components.toolbar))
    implementation(project(Components.suggestionInputView))
    implementation(project(Components.cardInputView))
    implementation(project(Components.quantityPickerView))
    implementation(project(Components.timelineView))
    implementation(project(Components.fitOptionMessageView))

    implementation(Dependencies.kotlinJDK)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.glide)
}
