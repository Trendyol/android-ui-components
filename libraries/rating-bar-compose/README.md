ratingBarComposeVersion = **rating-bar-compose-1.0** [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# RatingBar

The `RatingBar` composable function is a custom rating bar implementation for Jetpack Compose. It allows you to display a rating bar with a specified number of rating items, custom item size, and optional spacing between items.

# Parameters

The `RatingBar` composable accepts the following parameters:

- `modifier`: Optional modifier for configuring the appearance or behavior of the rating bar.
- `rating`: The current rating value to be displayed.
- `painterRes`: The resource ID of the drawable used for the rating items (e.g., star icon).
- `emptyColor`: Optional color for the empty/unfilled rating items.
- `filledColor`: Color for the filled rating items.
- `spaceBetween`: The spacing between rating items.
- `itemCount`: The total number of rating items in the rating bar.
- `itemSize`: The size of each rating item.

# Usage

```kotlin
RatingBar(
    modifier = Modifier,
    rating = 3.5f,
    painterRes = R.drawable.star,
    emptyColor = Color.Gray,
    filledColor = Color.Yellow,
    spaceBetween = 4.dp,
    itemCount = 5,
    itemSize = 24.dp
)
```

# Installation

- To implement **RatingBar** to your Android project via Gradle, you need to add JitPack repository to your root build.gradle.

```groovy
 allprojects {
       repositories {
           // ... 

           maven { url 'https://jitpack.io' }
       }
   }
```

Open your module-level `build.gradle` file and add the `RatingBar` dependency:

```groovy
dependencies {    
  implementation "com.github.Trendyol.android-ui-components:rating-bar-compose:$ratingBarComposeVersion"
}  
```

# Contributors

This library is maintained mainly by Trendyol Android Team members but also other Android lovers contributes.

# License

```
Copyright 2023 Trendyol.com    Licensed under the Apache License, Version 2.0 (the "License");    you may not use this file except in compliance with the License.    You may obtain a copy of the License at    
   http://www.apache.org/licenses/LICENSE-2.0    
Unless required by applicable law or agreed to in writing, software    distributed under the License is distributed on an "AS IS" BASIS,    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.    See the License for the specific language governing permissions and    limitations under the License.
```