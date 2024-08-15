shimmerComposeVersion = **shimmer-compose-1.0.0** [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Shimmer Modifier

The `shimmer` modifier allows you to display a shimmer with a custom shape for loading state.

# Parameters

The `Shimmer` modifier accepts the following parameters:


| Parameter Name          | Type        | Description                    |
|-------------------------|-------------|--------------------------------|
| isVisible                    | Boolean      | It determines if the shimmer isVisible or not                   |
| isWipeOffAnimationEnabled               | Boolean   | It enables or disables wipe-off animation of the shimmer     |
| shape | Shape      | It determines the shimmer's shape |

# Usage

```kotlin
val title by mutableStateOf("")

Text(
   text = title,
   modifier = Modifier
      .fillMaxWidth()
      .shimmer(title == "")
)
```

# Installation

- To implement **Shimmer** to your Android project via Gradle, you need to add JitPack repository to your root build.gradle.

```groovy
 allprojects {
       repositories {
           // ... 

           maven { url 'https://jitpack.io' }
       }
   }
```

Open your module-level `build.gradle` file and add the `Shimmer` dependency:

```groovy
dependencies {    
  implementation "com.github.Trendyol.android-ui-components:shimmer-compose:$shimmerComposeVersion"
}  
```

# Contributors

This library is maintained mainly by Trendyol Android Team members but also other Android lovers contributes.

# License
--------
    Copyright 2023 Trendyol.com

    Copyright 2022 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
    Placeholder.kt file in androidx project has been renamed to Shimmer.kt and modified to fit our needs
    https://github.com/androidx/androidx/blob/8da8b8938ee44b72bd258b15eb623680b3fcbb29/wear/compose/compose-material/src/main/java/androidx/wear/compose/material/Placeholder.kt