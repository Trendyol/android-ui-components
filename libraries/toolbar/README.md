<img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/toolbar-1.png" width="240" alt:"Toolbar preview" />

$toolbarVersion = toolbar-2.2.0  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

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

:warning: Starting from the `toolbar-2.2.0` version, namings are updated from "left-right" to
"start-end" on both ToolbarViewState and resource attributes. Component will still support older
values but its highly recommended to update usages.

| Attribute                                                                       | Description                                                                                                               | Default Value                                               | Sample Usage                                           |
|---------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------|--------------------------------------------------------|
| `app:StartImageDrawable` ~~`app:leftImageDrawable`~~                            | Start image drawable resource.                                                                                            | ic_arrow_back                                               | `app:startImageDrawable="@drawable/ic_back"`           |
| `app:middleImageDrawable`                                                       | Middle image drawable resource.                                                                                           | 0                                                           | `app:middleImageDrawable="@drawable/ic_logo"`          |
| `app:endImageDrawable` ~~`app:rightImageDrawable`~~                             | End image drawable resource.                                                                                              | 0                                                           | `app:endImageDrawable="@drawable/ic_close"`            |
| `app:upperStartText` ~~`app:upperLeftText`~~                                    | Upper start text resource.                                                                                                | null                                                        | `app:upperStartText="@string/list_title"`              |
| `app:lowerStartText` ~~`app:lowerLeftText`~~                                    | Lower start text resource. If upper start text is set and this is not set, upper start text would be centered vertically. | null                                                        | `app:lowerStartText="@string/list_item_description"`   |
| `app:middleText`                                                                | Middle text resource.                                                                                                     | null                                                        | `app:middleText="@string/app_name"`                    |
| `app:upperEndText` ~~`app:upperRightText`~~                                     | Upper end text resource.                                                                                                  | null                                                        | `app:upperE dText="@string/action_select_all"`         |
| `app:lowerEndText` ~~`app:lowerRightText`~~                                     | Lower end text resource. If upper start text is set and this is not set, upper start text would be centered vertically.   | null                                                        | `app:lowerE dText="@string/action_clear"`              |
| `app:toolbarBackground`                                                         | Background color or drawable resource.                                                                                    | android.R.color.white - #FFFFFF                             | `app:toolbarBackground="@drawable/toolbar_background"` |
| `app:upperStartTextMarginStart` ~~`app:upperLeftTextMarginStart`~~              | Start margin for upper start text.                                                                                        | trendyol_uicomponents_toolbar_margin_start_side_text - 24dp | `app:upperStartTextMarginStart="@dimen/margin_sample"` |
| `app:lowerStartTextMarginStart` ~~`app:lowerLeftTextMarginStart`~~              | Start margin for lower start text.                                                                                        | trendyol_uicomponents_toolbar_margin_start_side_text - 24dp | `app:lowerStartTextMarginStart="24dp"`                 |
| `app:upperEndTextMarginEnd` ~~`app:upperRightTextMarginEnd`~~                   | End margin for upper end text.                                                                                            | trendyol_uicomponents_toolbar_margin_outer - 8dp            | `app:upperEndTextMarginEnd="@dimen/my_margin"`         |
| `app:lowerEndTextMarginEnd` ~~`app:lowerRightTextMarginEnd`~~                   | End margin for upper end text.                                                                                            | trendyol_uicomponents_toolbar_margin_outer - 8dp            | `app:lowerEndTextMarginEnd="16dp"`                     |
| `app:startImageDrawableMarginStart` ~~`app:leftImageDrawableMarginStart`~~      | Start margin for start drawable.                                                                                          | 0                                                           | `app:startImageDrawableMarginStart="@dimen/my_margin"` |
| `app:endImageDrawableMarginEnd` ~~`app:rightImageDrawableMarginEnd`~~           | End margin for start drawable.                                                                                            | 0                                                           | `app:endImageDrawableMarginEnd="32dp"`                 |
| `app:hideStartImage` ~~`app:hideLeftImage`~~                                    | Hide flag for start image.                                                                                                | false                                                       | `app:hideStartImage="true"`                            |
| `app:startImageContentDescription` ~~`app:leftImageContentDescription`~~        | Text for start Image of Talkback                                                                                          | ""                                                          | `app:startImageContentDescription="Back"`              |
| `app:endImageContentDescription` ~~`app:rightImageContentDescription`~~         | Text for end Image of Talkback                                                                                            | ""                                                          | `app:endImageContentDescription="Add"`                 |
| `app:endImageDrawableVerticalMargin` ~~`app:rightImageDrawableVerticalMargin`~~ | Vertical margin for end drawable                                                                                          | 0                                                           | `app:endImageDrawableVerticalMargin="12dp"`            |
| `app:enableDotPoint`                                                            | End drawable's dots point enabled status                                                                                  | false                                                       | `app:enableDotPoint="true"`                            |

Sample usage with attributes:

```xml

    <com.trendyol.uicomponents.toolbar.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        app:toolbarBackground="@color/background"
        app:middleText="@string/app_name"
        app:upperEndText="@string/clear_all" />

```

With `ToolbarViewState` you can also provide TextAppearance resources for all text fields in **Toolbar**.

You can assign every text field and image to click listeners.

To set click listener, you can use **Toolbar** instance fields like below.

```kotlin

    val toolbar: Toolbar = findViewById(R.id.toolbar)
    
    toolbar.startImageClickListener = { onBackPressed() }

```

Sample usage with `ToolbarViewState`:

```kotlin

    val toolbar: Toolbar = findViewById(R.id.toolbar)
    
    toolbar.viewState = ToolbarViewState(
        upperStartText = "<b>List</b>",
        startImageDrawableResId = R.drawable.ic_arrow_back,
        upperStartTextAppearance = R.style.MyTextStyle_Body_2
    )

```

# Contributors

This library is maintained mainly by Trendyol Android Team members. Everybody can contribute **Toolbar** or other UI Components with opening new PR's.

# License
    Copyright 2023 Trendyol.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
