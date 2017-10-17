# Material-File-Picker

Material-File-Picker is a simple file picker for Android based on Material Design. The picker can be used to select files, directories or both. You can also decide what kind of files user will be able to select.

![device-2017-10-17-094708](https://user-images.githubusercontent.com/2614225/31652818-bfd7ca54-b320-11e7-9adf-a057ca59cdfa.png)
![device-2017-10-17-094544](https://user-images.githubusercontent.com/2614225/31652833-ca1539b6-b320-11e7-9a44-d007b8a5caf6.png)


## Features
* Material Design
* Ability to select files
* Ability to select directories
* Ability to select both (files and directories)
* Ability to select specific file types
* Ability to decide which root directory user can explore
```diff
-External storages in Android 5+ isn’t currently supported.
```

## How to use?
Make sure you have defined the **jcenter()** repository in project's **build.gradle** file:
```
allprojects {
    repositories {
        jcenter()
    }
}
```

Add the dependency to module's **build.gradle** file:
```
dependencies {
    compile 'com.applandeo:material-file-picker:1.0.0'
}
```

Add to your java code:
```java
new FilePicker.Builder(this, new OnSelectFileListener() {
      @Override
      public void onSelect(File file) {
         …
      }
});
```
The OnSelectFileListener returns standard Java File object.


### Hide files and view only directories:
```java
new FilePicker.Builder(this, listener)
      .hideFiles(true)
      .show();
```

### View only specific file types:
```java
new FilePicker.Builder(this, listener)
      .fileType(FileTypes.IMAGE)
      .show();
```

Other file types: `APK`, `ARCHIVE`, `BOOK`, `DOCUMENT`, `IMAGE`, `MUSIC`, `SHEET`, `PDF`, `PRESENTATION`, `TEXT` and `VIDEO`.

### Set default directory:
This method let you decide which directory user will see after picker opening:
```java
new FilePicker.Builder(this, listener)
      .directory("/storage/sdcard/MyFiles)
      .show();
```
If you don't set this parameter, picker automatically view Download directory.

### Set main directory:
This method let you decide how far user can go up in the directories tree:
```java
new FilePicker.Builder(this, listener)
      .mainDirectory("/storage")
      .show();
```
If you don't set this parameter, picker automatically set the main internal storage.


## Handle the permissions request response (Android 6+)
Use `FilePicker.STORAGE_PERMISSIONS` to check if user granted storage permissions for the picker:
```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && requestCode == FilePicker.STORAGE_PERMISSIONS) {
                ...
        }
}
```

You don't have to add permissions tag to your AndroidManifest.xml.


## Customization
Simply use standard material color tags in your XML file where you store your colours. Picker automatically use them to adjust to your application.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
   <color name="colorPrimary">#3F51B5</color>
   <color name="colorPrimaryDark">#303F9F</color>
   <color name="colorAccent">#FF4081</color>
</resources>
```


## Changelog
#### Version 1.0.0:
* Initial build


## Get in touch
It would be great if you decide to use our component in your project. It’s open source, feel free. Write to us at hi@applandeo.com if you want to be listed and we will include your app in our repo. If you have any questions or suggestions just let us know.


## License
```
Copyright 2017, Applandeo sp. z o.o.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
