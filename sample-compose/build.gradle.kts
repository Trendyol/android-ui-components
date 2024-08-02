plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.compose) version "2.0.0"
}

kotlin.jvmToolchain(17)

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
}
