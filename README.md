# FilePickerExample

- `gradle`
```gradle
//maven { url 'https://jitpack.io' }

implementation 'com.github.gzeinnumer:BaseUtils:1.0.0'
```

- `MainActivity.java`
```java
private static final int MY_RESULT_CODE_FILECHOOSER = 2000;

private void actionBtnUpload() {
    Intent chooseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
    chooseFileIntent.setType("*/*");
    // Only return URIs that can be opened with ContentResolver
    chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE);

    chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file");
    startActivityForResult(chooseFileIntent, MY_RESULT_CODE_FILECHOOSER);
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == MY_RESULT_CODE_FILECHOOSER) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri fileUri = data.getData();

                String filePath = null;
                try {
                    filePath = FileUtils.getPath(getApplicationContext(), fileUri);

                    if (validateFileSize(filePath)) {
                        String fileName = MBUtilsString.getNameFromUrl(filePath);
                        String path = filePath;
                    } else {
                        Toast.makeText(this, "Ukuran file melebihi 20 MB", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    super.onActivityResult(requestCode, resultCode, data);
}

public boolean validateFileSize(String path) {
    File file = new File(path);

    long fileSizeInBytes = file.length();
    long fileSizeInKB = fileSizeInBytes / 1024;
    long fileSizeInMB = fileSizeInKB / 1024;

    return fileSizeInMB <= 20;
}
```

---

```
Copyright 2021 M. Fadli Zein
```