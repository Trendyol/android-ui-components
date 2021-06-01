<img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/rating-bar-1.png" width="240"/>

[![](https://jitpack.io/v/Trendyol/android-ui-components.svg)](https://jitpack.io/#Trendyol/android-ui-components) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Rating Bar
RatingBarView is alternative implementation of RatingBar on Android.

# Installation
 - To implement **RatingBar** to your Android project via Gradle, you need to add JitPack repository to your root build.gradle.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
 - After adding JitPack repository, you can add **RatingBar** dependency to your app level build.gradle.
```gradle
dependencies {
    implementation "com.github.Trendyol.android-ui-components:rating-bar:$ratingBarVersion"
}
```
:warning: To use **RatingBar**, you have to enable dataBinding from your main project.
# Usage
To set width you can use `android:layout_width` attribute. To customize more you can use following attributes directly from your layout xml file, or call the functions programmatically.

| Attribute |  Method | Description | Default Value |
| ------------- |-------------| ------------- |------------- |
| `app:starCount` | `setStarCount(Int)` | Rating over 5 | 0 |
| `app:starDefaultColor` | `setDefaultStarColor(Color)` | color of star | #e6e6e6 |
| `app:starHighlightColor` | `setHighlightColor(Color)` | color of highlighted color | #ffc000 |

# Contributors
This library is maintained mainly by Trendyol Android Team members but also other Android lovers contributes.

We would like to specially thanks to following contributors;

* [burhanaras](https://github.com/burhanaras)

# License
    Copyright 2021 Trendyol.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
