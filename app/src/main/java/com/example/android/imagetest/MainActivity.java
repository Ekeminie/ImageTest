package com.example.android.imagetest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.imagetest.data.StudentDbHelper;

public class MainActivity extends AppCompatActivity {

    TextView signupButon, loginButon, forgotPasswordButton, teacherLogin;
    EditText emailEditText, passwordEditText;

    StudentDbHelper studentDbHelper;


    Integer  SELECT_FILE = 0;
    static final int REQUEST_IMAGE_CAPTURE = 10;
    public static final int CAMERA_REQUEST_CODE = 8675309;
    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signupButon = findViewById(R.id.signUpButton);
        loginButon = findViewById(R.id.logintext1);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        forgotPasswordButton = findViewById(R.id.fogotPassword);
            teacherLogin = findViewById(R.id.teacherLogin);

            teacherLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                }
            });
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            }
        });
        loginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginCheck();
            }
        });

        signupButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    public void loginCheck(){
        String email1 = emailEditText.getText().toString().trim();
        String password1 = passwordEditText.getText().toString().trim();

        studentDbHelper = new StudentDbHelper(this);


        if (studentDbHelper.findEmail(email1) && studentDbHelper.findPassword(password1)){
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
        } emailEditText.setError("Invalid Email/Password");


    }

    private void selectPicture() {


        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("select a profile picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera")) {
                    camerasetup();
                } else if (items[which].equals("Gallery")) {
                    getPictureName();
                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();

                }

            }
        });
        builder.show();
    }



    public void camerasetup() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent();
        } else {

            String[] permissionRequest = {android.Manifest.permission.CAMERA};
        //    requestPermissions(permissionRequest, CAMERA_REQUEST_CODE);
        }
    }

    public void getPictureName() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "select file"), SELECT_FILE);
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
