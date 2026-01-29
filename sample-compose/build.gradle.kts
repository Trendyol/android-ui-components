plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.compose) version "2.1.21"
}

kotlin.jvmToolchain(21)

android {
    compileSdk = Configs.compileSdkVersion
    buildToolsVersion = Configs.buildToolsVersion
    namespace = "com.trendyol.uicomponents.sample.compose"

    defaultConfig {
        applicationId = Configs.applicationId
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion
        versionCode = 1
        versionName = "1.0"
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
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeCoil)
    implementation(Dependencies.composeConstraintLayout)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composeRuntime)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeActivity)
    implementation(Dependencies.composeNavigation)

    implementation(projects.libraries.timelineViewCompose)
    implementation(projects.libraries.ratingBarCompose)
    implementation(projects.libraries.quantityPickerCompose)
}
