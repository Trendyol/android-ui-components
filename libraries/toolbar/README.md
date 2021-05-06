<img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/toolbar-1.png" width="240"/>

$toolbarVersion = toolbar-2.0.4  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Toolbar
Toolbar is alternative implementation of Toolbar component on Android.

# Installation
 - To implement **Toolbar** to your Android project via Gradle, you need to add JitPack repository to your root build.gradle.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
 - After adding JitPack repository, you can add **Toolbar** dependency to your app level build.gradle.
```gradle
dependencies {
    implementation "com.github.Trendyol.android-ui-components:toolbar:$toolbarVersion"
}
```

# Usage

To customize **Toolbar** you can set [ToolbarViewState](src/main/java/com/trendyol/uicomponents/toolbar/ToolbarViewState.kt) via calling `Toolbar.setViewState` or use attributes listed below. All given text attributes will be formatted as [HTML](https://developer.android.com/reference/android/text/Html).

| Attribute | Description | Default Value | Sample Usage |
| ------------- | ------------- | ------------- | ------------- |
| `app:leftImageDrawable` | Left image drawable resource. | ic_arrow_back | `app:leftImageDrawable="@drawable/ic_back"` |
| `app:middleImageDrawable` | Middle image drawable resource. | 0 | `app:middleImageDrawable="@drawable/ic_logo"` |
| `app:rightImageDrawable` | Right image drawable resource. | 0 | `app:rightImageDrawable="@drawable/ic_close"` |
| `app:upperLeftText` | Upper left text resource. | null | `app:upperLeftText="@string/list_title"` |
| `app:lowerLeftText` | Lower left text resource. If upper left text is set and this is not set, upper left text would be centered vertically. | null | `app:lowerLeftText="@string/list_item_description"` |
| `app:middleText` | Middle text resource. | null | `app:middleText="@string/app_name"` |
| `app:upperRightText` | Upper right text resource. | null | `app:upperRightText="@string/action_select_all"` |
| `app:lowerRightText` | Lower right text resource. If upper left text is set and this is not set, upper left text would be centered vertically. | null | `app:lowerRightText="@string/action_clear"` |
| `app:toolbarBackground` | Background color or drawable resource. | android.R.color.white | `app:toolbarBackground="@drawable/toolbar_background"` |
| `app:upperLeftTextMarginStart` | Start margin for upper left text. | trendyol_uicomponents_toolbar_margin_left_side_text | `app:upperLeftTextMarginStart="@dimen/trendyol_uicomponents_toolbar_margin_left_side_text"` |
| `app:lowerLeftTextMarginStart` | Start margin for lower left text. | trendyol_uicomponents_toolbar_margin_left_side_text | `app:lowerLeftTextMarginStart="@dimen/trendyol_uicomponents_toolbar_margin_left_side_text"` |
| `app:upperRightTextMarginEnd` | End margin for upper right text. | trendyol_uicomponents_toolbar_margin_outer | `app:upperLeftTextMarginStart="@dimen/trendyol_uicomponents_toolbar_margin_left_side_text"` |
| `app:lowerRightTextMarginEnd` | End margin for upper right text. | trendyol_uicomponents_toolbar_margin_outer | `app:upperLeftTextMarginStart="@dimen/trendyol_uicomponents_toolbar_margin_left_side_text"` |
| `app:leftImageDrawableMarginStart` | Start margin for left drawable. | 0 | `app:upperLeftTextMarginStart="@dimen/trendyol_uicomponents_toolbar_margin_left_side_text"` |
| `app:rightImageDrawableMarginEnd` | End margin for left drawable. | 0 | `app:rightImageDrawableMarginEnd="@dimen/trendyol_uicomponents_toolbar_margin_right_side_icon"` |
| `app:hideLeftImage` | Hide flag for left image. | false | `app:hideLeftImage="true"` |

Sample usage with attributes:

```xml

    <com.trendyol.uicomponents.toolbar.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        app:toolbarBackground="@color/background"
        app:middleText="@string/app_name"
        app:upperRightText="@string/clear_all" />

```

With `ToolbarViewState` you can also provide TextAppearance resources for all text fields in **Toolbar**.

You can assign every text field and image to click listeners.

To set click listener, you can use **Toolbar** instance fields like below.

```kotlin

    val toolbar: Toolbar = findViewById(R.id.toolbar)
    
    toolbar.leftImageClickListener = { onBackPressed() }

```

Sample usage with `ToolbarViewState`:

```kotlin

    val toolbar: Toolbar = findViewById(R.id.toolbar)
    
    toolbar.viewState = ToolbarViewState(
        upperLeftText = "<b>List</b>",
        leftImageDrawableResId = R.drawable.ic_arrow_back,
        upperLeftTextAppearance = R.style.MyTextStyle_Body_2
    )

```

# Contributors

This library is maintained mainly by Trendyol Android Team members. Everybody can contribute **Toolbar** or other UI Components with opening new PR's.

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
