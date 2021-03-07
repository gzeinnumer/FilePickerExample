package com.gzeinnumer.filepickerexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.bu.utils.MBUtilsString;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int MY_RESULT_CODE_FILECHOOSER = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(v -> fileChosser());
    }

    private void fileChosser() {
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
                        filePath = MBUtilsString.getPath(getApplicationContext(), fileUri);

                        if (validateFileSize(filePath)) {
                            String fileName = MBUtilsString.getNameFromUrl(filePath);
                            String path = filePath;

                            ((TextView) findViewById(R.id.path)).setText("File Name :\n" + fileName + "\n\n File Path :\n" + path);
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

    //return false if file size more than 20 mB
    public boolean validateFileSize(String path) {
        File file = new File(path);

        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;

        return fileSizeInMB <= 20;
    }
}