<img src="https://raw.githubusercontent.com/Trendyol/android-ui-components/master/images/timeline-view.png" width="240"/>

timelineViewVersion = timeline-view-1.0.0 [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## TimelineView
TimelineView creates a timeline and shows actions over time

# Installation
 - To implement **TimelineView** to your Android project via Gradle, you need to add JitPack repository to your root build.gradle.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
 - After adding JitPack repository, you can add **TimelineView** dependency to your app level build.gradle.
```gradle
dependencies {
    implementation "com.github.Trendyol.android-ui-components:timeline-view:$timelineViewViewVersion"
}
```
:warning: To use **TimelineView**, you have to enable dataBinding from your main project.
# Usage
To set width you can use `android:layout_width` attribute. To customize more you can use following attributes directly from your layout xml file, or call the functions programmatically.

| Attribute |  Method | Description | Default Value | Sample Usage |
| ------------- |-------------| ------------- |------------- |------------- |
| `app:tlv_dotSize` | `setDotSize(Float)` | size of each dot item | 16dp | app:tlv_dotSize="20dp"|
| `app:tlv_borderWidth` | `setBorderWidth(Float)` | width of each dot item's border | 3dp | app:tlv_borderWidth="3dp"|
| `app:tlv_textSize` | `setTextSize(Float)` | text size of each time line item | 10sp | app:tlv_textSize="12sp"|
| `app:tlv_lineWidth` | `setLineWidth(Float)` | width of each line between of dots | 25dp | app:tlv_lineWidth="50dp"|

# Public methods

| Method Name |  Parameter | Description |
| ------------- | ------------- | ------------- |
| setItems | List<TimelineItem> | To set timeline items |
| startTooltipAnimation | delay: Long, milliSecondsPerInch: Float | set animation delay, set animation speed |
| setDotSize | dotSize: Float | To set dotSize programmatically |
| setBorderWidth | borderWidth: Float | To set borderWidth programmatically |
| setTextSize | textSize: Float | To set setTextSize programmatically |
| setLineWidth | lineWidth: Float | To set setLineWidth programmatically |


# Contributors
This library is maintained mainly by Trendyol Android Team members but also other Android lovers contributes.

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
