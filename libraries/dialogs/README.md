
<img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/dialogs-1.png" width="280"/> <img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/dialogs-2.png" width="280"/> <img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/dialogs-3.png" width="280"/> <img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/dialogs-4.png" width="280"/> <img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/dialogs-5.png" width="280"/>
  
$dialogsVersion = dialogs-1.4.5 [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
  
## Dialogs  
Dialogs is a bunch of BottomSheetDialogs to use in app to show user an information, agreement or list.  
  
## Installation  
  - To implement **Dialogs** to your Android project via Gradle, you need to add JitPack repository to your root build.gradle.  
```gradle  
allprojects {  
 repositories { ... maven { url 'https://jitpack.io' } }}  
```  
 - After adding JitPack repository, you can add **Dialogs** dependency to your app level build.gradle.  
```gradle  
dependencies {  
 implementation "com.github.Trendyol.android-ui-components:dialogs:$dialogsVersion"}  
```  
:warning: **Dialogs** can only usable via Kotlin.  
  
## Usage  
You can configure your dialog with given extensions with Kotlin DSL syntax.

To show the dialog you just need to call `showDialog(FragmentManager)` function.

You can call DialogFragment.findFragment(supportFragmentManager) method inorder to retrieve DialogFragment's instance and use that instance to set click listeners after configuration change. 

* Info Dialog:

Simple dialog to show information, error or text.
  
| Field                           | Type                     | Description                                                                                                               | Default Value |
|---------------------------------|--------------------------|---------------------------------------------------------------------------------------------------------------------------|---------------|
| `title`                         | CharSequence             | Title of the dialog                                                                                                       | ""            |
| `showCloseButton`               | Boolean                  | Close button visibility                                                                                                   | false         |
| `closeButtonColor`              | Int                      | Close button color                                                                                                        | false         |
| `closeButtonDrawable`           | Int                      | Close button drawable                                                                                                     | false         |
| `animateCornerRadiusWhenExpand` | Boolean                  | Corner radius will be removed with an animation when set true.                                                            | false         |  
| `cornerRadius`                  | Float                    | Corner radius will be applied.                                                                                            | 16dp          |
| `horizontalPadding`             | Float                    | Horizontal padding will be applied.                                                                                       | 16dp          |
| `verticalPadding`               | Float                    | Vertical padding will be applied.                                                                                         | 0dp           |
| `closeButtonListener`           | (DialogFragment) -> Unit | Listener for close button. When clicked, dialog will dismiss and listener will be invoked with dialog.                    | { }           |
| `content`                       | CharSequence             | Content of a dialog                                                                                                       | ""            |
| `showContentAsHtml`             | Boolean                  | If you provided `content` as Html and set this flag as true, content will be parsed as HTML.                              | false         |
| `contentImage`                  | Int                      | Drawable resource id of an visual, will be shown on top of `content`                                                      | 0             |
| `webViewContent`                | WebViewContent           | If you provide a webview content such as Html data content or url , that will be shown in the webview.                    | null          |
  
Sample usage:
 ```kotlin 
 infoDialog {
    title = "Info Title"
    showCloseButton = true
    closeButtonColor = R.color.sample_color
    closeButtonDrawable = R.drawable.sample_drawable
    closeButtonListener = { onInfoDialogClosed(it) }
    content = SpannableString.valueOf(getSpannableString())
    contentImage = android.R.drawable.btn_plus
}.show(supportFragmentManager)
```

** Configuring WebView:**
To configure the WebView instance of the dialog, you can call the DialogFragment's function named show,
which takes WebViewDownloadConfigurator and WebViewConfigurator objects as parameters. 
These objects must extend Fragments.

These passed objects will be used to configure the webview instance of the dialog.
See the sample usage below:
```kotlin
infoDialog {
  title = "Info Dialog with WebView Download Listener"
  webViewContent = WebViewContent.UrlContent("https://github.com/Trendyol")
  showContentAsHtml = true
  titleTextColor = CardInputViewR.color.civ_error_stroke
  showCloseButton = true
}.showDialog(
  fragmentManager = supportFragmentManager,
  webViewConfigurator = WebViewJavascriptEnabler(),
  downloadConfigurator = null
)

class WebViewJavascriptEnabler : WebViewConfigurator, Fragment() {

  override fun configureWebView(webView: WebView) {
    webView.settings.javaScriptEnabled = true
  }
}
```
```kotlin

* Agreement Dialog:

Dialog with buttons on bottom. You can show 2 buttons at the bottom or just one button.
All arguments plus these arguments will be applicable to show agreement dialogs.

| Field                      | Type                     | Description                                                                     | Default Value |  
|----------------------------|--------------------------|---------------------------------------------------------------------------------|---------------|
| `rightButtonText`          | String                   | Text of the right button. Will not be shown if given string is empty.           | ""            | 
| `leftButtonText`           | String                   | Text of the left button. Will not be shown if given string is empty.            | ""            |
| `rightButtonClickListener` | (DialogFragment) -> Unit | Listener for right button's click events. Will be invoked with dialog instance. | { }           |
| `leftButtonClickListener`  | (DialogFragment) -> Unit | Listener for left button's click events. Will be invoked with dialog instance.  | { }           |

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

* Selection Dialog:

Dialog with list that user can select. There will be checkBox on the left side of each line and user can change the selection. 
All **Info Dialog** arguments plus these arguments will be applicable to show selection dialogs.

| Field                           | Type                          | Description                                                    | Default Value |
|---------------------------------|-------------------------------|----------------------------------------------------------------|---------------|
| `items`                         | List<Pair<Boolean, String>>   | Item list that will be listed on dialog.                       | null          | 
| `showItemsAsHtml`               | Boolean                       | Item texts will be parsed as Html if this flag set as true.    | false         |
| `onItemSelectedListener`        | (DialogFragment, Int) -> Unit | Listener to notify selected index.                             | null          |
| `onDismissListener`             | (DialogFragment) -> Unit      | Listener to notify when dialog is dismiss.                     | null          |
| `enableSearch`                  | Boolean                       | Enables search function in given `items`                       | false         |
| `showClearSearchButton`         | Boolean                       | Shows clean button on the right of the search input line.      | false         |
| `searchHint`                    | String                        | Hint to show on search input line.                             | ""            |
| `selectedItemDrawable`          | Int                           | Drawable resource for selected item icon.                      | null          |
| `selectedTextColor`             | Int                           | Color resource for selected item text color.                   | null          |
| `showRadioButton`               | Boolean                       | Radio button visibility                                        | false         |
| `titleTextColor`                | Int                           | Color resource for title text color.                           | null          |
| `animateCornerRadiusWhenExpand` | Boolean                       | Corner radius will be removed with an animation when set true. | false         |

Sample usage:
```kotlin
selectionDialog {
    title = "Selection Dialog Title"
    content = getContent()
    items = getItemsAsHtml()
    showItemsAsHtml = true
    titleTextColor = R.color.colorPrimary
    animateCornerRadiusWhenExpand = true
    selectedItemDrawable = R.drawable.ic_check
    selectedTextColor = R.color.colorPrimary
    showRadioButton = true
    onItemSelectedListener = { dialog, index -> 
        dialog.dismiss()
        onItemSelected(index)
    }
}
```

Sample usage with search:
```kotlin
selectionDialog {
    title = "Selection Dialog with Search Title"
    content = getContent()
    items = getItemsAsHtml()
    showItemsAsHtml = true
    onItemSelectedListener = { dialog, index ->
        onItemSelected(index)
    }
    enableSearch = true
    showClearSearchButton = true
    searchHint = "Hint for search"
    selectedItemDrawable = R.drawable.ic_check
    selectedTextColor = R.color.colorPrimary

}
```

* Info List Dialog:

Dialog with the list. There are two textViews that are showing key-value pairs.
All **Info Dialog** arguments plus these arguments will be applicable to show info list dialogs.

| Field           | Type                                   | Description                              | Default Value |
|-----------------|----------------------------------------|------------------------------------------|---------------|
| `infoListItems` | List<Pair<CharSequence, CharSequence>> | Item list that will be listed on dialog. | null          |
| `itemDividers`  | List<ItemDivider>                      | Wrapper that will decorate recyclerView  | emptyList     |

Sample usage:
```kotlin
infoListDialog {
    title = SpannableStringBuilder().color(Color.RED) { append("Info List Dialog Sample") }
    showCloseButton = true
    cornerRadius = 0F
    closeButtonListener = infoDialogClosed
    infoListItems = getInfoListItems()
    itemDividers =  listOf(
        ItemDivider.MarginDivider(56, listOf(ItemDivider.MarginDivider.MarginDirection.TOP, ItemDivider.MarginDivider.MarginDirection.BOTTOM)),
        ItemDivider.DrawableDivider(R.drawable.shape_info_list_dialog_divider)
    )
}
```

## TODOs
* Implement ListDialog. 
* ~~Implement SelectionDialog~~
    * ~~Implement search line.~~
    * Implement multiple selectable type.
* Provide theme for more styling.
* Update builder for Java.
* ~~Stop using DialogFragment's constructor inorder to build DialogFragment~~


## Contributors
This library is maintained mainly by Trendyol Android Team members but also other Android lovers contributes.

## License
    Copyright 2022 Trendyol.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
