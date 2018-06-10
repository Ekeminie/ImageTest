package com.example.android.imagetest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.android.imagetest.data.StudentDbHelper;
import com.google.common.collect.Range;

import static android.provider.BaseColumns._ID;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_AGE;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_EMAIL;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_NAME;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_PASSWORD;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_PHONE;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.TABLE_NAME;

public class SignUpActivity extends AppCompatActivity {

    StudentDbHelper mDbHelper = new StudentDbHelper(this);
    private EditText name;

    private EditText email;
    private EditText confirmEmail;

    private EditText password;
    private EditText confirmPassword;

    private EditText phone;
    private EditText age;

    private Button submit;
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
//      awesomeValidation = new AwesomeValidation(COLORATION);
//      awesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set
//// or
//      awesomeValidation = new AwesomeValidation(UNDERLABEL);
//      awesomeValidation.setContext(this);  // mandatory for UNDERLABEL style
//// or
//      AwesomeValidation mAwesomeValidation = new AwesomeValidation(TEXT_INPUT_LAYOUT);

        initView();
    }

    private void initView() {
        name =  findViewById(R.id.etName);
        email =  findViewById(R.id.etEmail);
        confirmEmail =  findViewById(R.id.etConfirmEmail);
        password = findViewById(R.id.etPassword);
        confirmPassword =  findViewById(R.id.etConfirmPassword);
        phone =  findViewById(R.id.etPhone);
        age =  findViewById(R.id.etAge);

        submit =  findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.submit:
                        submitForm();
                        break;
                }

            }
        });

        addValidationToViews();
    }

    private void addValidationToViews() {

        awesomeValidation.addValidation(this, R.id.etName, RegexTemplate.NOT_EMPTY, R.string.invalid_name);

        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.etConfirmEmail, R.id.etEmail, R.string.invalid_confirm_email);

        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this, R.id.etPassword, regexPassword, R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.etConfirmPassword, R.id.etPassword, R.string.invalid_confirm_password);

        awesomeValidation.addValidation(this, R.id.etPhone, "^[+]?[0-9]{10,13}$", R.string.invalid_phone);
        awesomeValidation.addValidation(this, R.id.etAge, Range.closed(12, 60), R.string.invalid_age);
    }

    private void submitForm() {
        // Validate the form...
        if (awesomeValidation.validate()) {
            String sendName = name.getText().toString().trim();
            String sendEmail = email.getText().toString().trim();
            String getPassword = password.getText().toString().trim();
            String getPhone = phone.getText().toString().trim();
            String getAge =  age.getText().toString().trim();

            int agge = Integer.parseInt(getAge);

            if (!nameExists(sendName) && !phoneExists(getPhone) && !emailExists(sendEmail)){



            mDbHelper.addStudent(sendName, sendEmail, getPassword, getPhone, agge);
            Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
            startActivity(intent);
            // Here, we are sure that form is successfully validated. So, do your stuffs now...
            Toast.makeText(this, "Form Validated Successfully", Toast.LENGTH_LONG).show();
        }
        else {
                Toast.makeText(getApplicationContext(), "Name, Email or Phone Number Already Exists", Toast.LENGTH_LONG).show();
            }
        }

    }



    public boolean nameExists(String studentName){
        String [] projection = {
                _ID,
                STUDENT_NAME,
                STUDENT_EMAIL,
                STUDENT_PASSWORD,
                STUDENT_PHONE,
                STUDENT_AGE
        };
        String selection = STUDENT_NAME + " =?";
        String[] selectionArgs = { studentName };
        String limit = "1";

        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = mDbHelper.getWritableDatabase();


        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0 );
        cursor.close();
        return exists;
    }

    public boolean emailExists(String studentEmail){
        String [] projection = {
                _ID,
                STUDENT_NAME,
                STUDENT_EMAIL,
                STUDENT_PASSWORD,
                STUDENT_PHONE,
                STUDENT_AGE
        };
        String selection = STUDENT_EMAIL + " =?";
        String[] selectionArgs = { studentEmail };
        String limit = "1";

        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = mDbHelper.getWritableDatabase();


        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0 );
        cursor.close();
        return exists;
    }

    public boolean phoneExists(String studentPhone){
        String [] projection = {
                _ID,
                STUDENT_NAME,
                STUDENT_EMAIL,
                STUDENT_PASSWORD,
                STUDENT_PHONE,
                STUDENT_AGE
        };
        String selection = STUDENT_PHONE + " =?";
        String[] selectionArgs = { studentPhone };
        String limit = "1";

        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = mDbHelper.getWritableDatabase();


        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0 );
        cursor.close();
        return exists;
    }

}

