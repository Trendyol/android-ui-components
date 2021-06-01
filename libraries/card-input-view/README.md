
<img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/card-input-view-1.png" width="240"/> <img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/card-input-view-2.png" width="240"/>
  
$cardInputViewVersion = card-input-view-1.1.2 [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
  
## CardInputView  
CardInputView is custom view for Master or Visa debit/credit card inputs. It can verify card number, expire date and CVV for you.
  
## Installation  
  - To implement **CardInputView** to your Android project via Gradle, you need to add JitPack repository to your root/project level build.gradle.  
```gradle  
allprojects {  
 repositories { ... maven { url 'https://jitpack.io' } }}  
```  
 - After adding JitPack repository, you can add **CardInputView** dependency to your app/module level build.gradle.
```gradle
dependencies {  
 implementation "com.github.Trendyol.android-ui-components:card-input-view:$cardInputViewVersion"}
```  
:warning: To use **CardInputView**, you have to enable dataBinding from your project's `build.gradle`.

## Usage
You can inflate **CardInputView** via XML or programmatically. View can be configurable either in XML or setting a ViewState.

To set programmatically, you can call `CardInputView.setViewState(CardInputViewState)`.

| Attribute |  Method | Description | Default Value |
| ------------- | ------------- | ------------- | ------------- |
| civ_cardNumberTitle | cardNumberTitle | Title to show above card number input field. | "" |
| civ_expiryTitle | expiryTitle |  Title to show above expire date fields. | "" |
| civ_expiryMonthTitle | expiryMonthTitle | Title to show on expire month field. | "" |
| civ_expiryYearTitle | expiryYearTitle | Title to show on expire year field. | "" |
| civ_cvvTitle | cvvTitle | Title to show above CVV input field. | "" |
| civ_titleTextColor | titleTextColor | cardNumberTitle text color. | Color.BLACK |
| civ_inputTextColor | inputTextColor | input field text color. | Color.DKGRAY |
| civ_inputBackground | inputBackgroundDrawable | input field background drawableResource/drawable. | shape_card_input_field_background |
| civ_inputErrorBackground | inputErrorBackgroundDrawable | input field background drawableResource/drawable on error. | shape_card_input_field_error_background |
| civ_cvvInfoColor | cvvInfoColor | Button on the right side of the CVV field, tint for this field. | Color.RED |
| civ_showCvvInfoButton | showCvvInfoButton | Show or hide cvvInfo. | true |
| civ_validationEnabled | validationEnabled | Enable or disable input validations. | false |

To validate inputs, call `CardInputView.validate()`. If you want to get created cardInformation, call `CardInputView.validateAndGet()` this will validate and return `CardInformation` if all fields are valid.

To reset all inputs, call `CardInputView.reset()`.

To focus and show soft keyboard on card number field call `CardInputView.focusToCardNumberField()`, to focus CVV field call `CardInputView.focusToCvvField()`.

For expire month and year selection, you need to open custom dialog or input field, we suggest you to use **Dialogs**, to more information about **Dialogs**, [click here](https://github.com/Trendyol/android-ui-components/tree/master/libraries/dialogs).

## Listeners
To get updates on **CardInputView** you need to set this listeners:

| Listener | Data | What to use for? |
| ------------- | ------------- | ------------- |
| onCardNumberChanged | cardNumber: String | To get latest input, you can set `CardInputView.setCardTypeLogoDrawable(Drawable)` or `CardInputView.setCardBankLogoDrawable(Drawable)`. |
| onCvvChanged | cvv: String | To get latest cvv, to focus next field on your activity/fragment. |
| onCvvInfoClicked |  | To open information dialog about CVV. |
| onCardNumberComplete | isValid: Boolean | If valid, open dialog for expire month dialog. |
| onCvvComplete | isValid: Boolean | To focus next field on your activity/fragment. |
| openMonthSelectionListener |  | To open dialog or input field for expire month. |
| openYearSelectionListener |  | To open dialog or input field for expire year. |

## Implementation

From XML, you can use attributes like below:

```xml
<com.trendyol.cardinputview.CardInputView
    android:id="@+id/card_input_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:civ_cardNumberTitle="Card Number"
    app:civ_cvvInfoColor="@android:color/holo_red_light"
    app:civ_cvvTitle="Cvv Number"
    app:civ_expiryMonthTitle="MM"
    app:civ_expiryTitle="Expire date"
    app:civ_expiryYearTitle="YY"
    app:civ_inputTextColor="@android:color/secondary_text_light"
    app:civ_titleTextColor="@android:color/primary_text_light"
    app:civ_validationEnabled="true" />
```

To set viewState programmatically:

```kotlin
val cardInputViewState = CardInputViewState(
    cardNumberTitle = "Card Number",
    expiryTitle = "Expiry Date",
    expiryMonthTitle = "MM",
    expiryYearTitle = "YY",
    cvvTitle = "CVV",
    validationEnabled = true,
    inputTextColor = Color.BLACK
)
findViewById<CardInputView>(R.id.card_input_view).setViewState(cardInputViewState)
```

## Contributors
This library is maintained mainly by Trendyol Android Team members but also other Android lovers contributes.

We developed this component for our needs, there is lots of improvements need to be implemented.

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

