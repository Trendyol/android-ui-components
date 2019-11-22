
<img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/dialogs-1.png" width="240"/><img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/dialogs-2.png" width="240"/>  
  
[![](https://jitpack.io/v/Trendyol/android-ui-components.svg)](https://jitpack.io/#Trendyol/android-ui-components) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)  
  
## Dialogs  
Dialogs is a bunch of BottomSheetDialogs to use in app to show user an information, agreement or list.  
  
# Installation  
  - To implement **Dialogs** to your Android project via Gradle, you need to add JitPack repository to your root build.gradle.  
```gradle  
allprojects {  
 repositories { ... maven { url 'https://jitpack.io' } }}  
```  
 - After adding JitPack repository, you can add **Dialogs** dependency to your app level build.gradle.  
```gradle  
dependencies {  
 implementation "com.trendyol.ui-components:dialogs:$dialogsVersion"}  
```  
:warning: To use **Dialogs**, you have to enable dataBinding from your main project, and implement material library into app level `build.gradle`.  
:warning: **Dialogs** can only usable via Kotlin.  
  
# Usage  
You can configure your dialog with given extensions with Kotlin DSL syntax.

To show the dialog you just need to call `show(FragmentManager)` function.
  
* Info Dialog:

Simple dialog to show information, error or text.
  
| Field | Type | Description | Default Value |  
| ------------- |-------------|-------------| ------------- |  
| `title` | String |Title of the dialog | "" |  
|  `showCloseButton` | Boolean | Close button visibility | false |  
| `closeButtonListener` | (Dialog) -> Unit |Listener for close button. When clicked, dialog will dismiss and listener vill be invoked with dialog. | { } |
| `content` | CharSequence | Content of a dialog | "" |
| `showContentAsHtml` | Boolean | If you provided `content` as Html and set this flag as true, content will be parsed as HTML. | false |
| `contentImage` | Int | Drawable resource id of an visual, will be shown on top of `content` | 0 |
  
Sample usage:
 ```kotlin 
 infoDialog { 
    title = "Info Title"
	showCloseButton = true	
	closeButtonListener = { onInfoDialogClosed(it) }
	content = SpannableString.valueOf(getSpannableString())  
	contentImage = android.R.drawable.btn_plus 
}.show(supportFragmentManager)
```

* Agreement Dialog:

Dialog with buttons on bottom. You can show 2 buttons at the bottom or just one button.
All arguments plus these arguments will be applicable to show agreement dialogs.

| Field | Type | Description | Default Value |  
| ------------- |-------------|-------------| ------------- |  
| `rightButtonText` | String | Text of the right button. Will not be shown if given string is empty. | "" | 
| `leftButtonText` | String | Text of the left button. Will not be shown if given string is empty. | "" |
| `rightButtonClickListener` | (Dialog) -> Unit | Listener for right button's click events. Will be invoked with dialog instance. | { } |
| `leftButtonClickListener` | (Dialog) -> Unit | Listener for left button's click events. Will be invoked with dialog instance. | { } |

Sample usage:
```kotlin
agreementDialog {
    title = "Agreement Dialog Title"
    leftButtonText = "Cancel"
    rightButtonText = "Agree"
    content = getHtmlString()
    showContentAsHtml = true
    rightButtonClickListener = {
        it.dismiss()
        onRightButtonClicked()
    }
    leftButtonClickListener = {
        it.dismiss()
    }
}.showDialog(supportFragmentManager)
```
# TODOs
* Implement ListDialog. 
* Implement SelectionDialog
    * Implement search line.
    * Implement multiple selectable type.
* Provide theme for more styling.
* Update builder for Java.

# Contributors
This library is maintained mainly by Trendyol Android Team members but also other Android lovers contributes.

# License
    Copyright 2019 Trendyol.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
