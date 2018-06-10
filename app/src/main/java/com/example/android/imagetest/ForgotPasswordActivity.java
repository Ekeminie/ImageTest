package com.example.android.imagetest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.imagetest.data.StudentDbHelper;

import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_EMAIL;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_PASSWORD;

public class ForgotPasswordActivity extends AppCompatActivity {

    StudentDbHelper mDbHelper = new StudentDbHelper(this);
    TextView forgotPasswordTextView;
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPasswordTextView= findViewById(R.id.password);
        editText =findViewById(R.id.edit_t);



        button = findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editText.getText().toString().trim();
                forgotPassword(email, forgotPasswordTextView);
            }
        });





    }

    public String forgotPassword(String email, TextView displayText){

        String query = "SELECT " + STUDENT_PASSWORD + " WHERE " + STUDENT_EMAIL + " =  \"" + email + "\"";

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //Product product = new Product();
        String succes = null;

        if (cursor.moveToFirst()) {

            succes = cursor.getString(cursor.getColumnIndex(STUDENT_PASSWORD));
            displayText.setText(succes);
            displayText.setVisibility(View.VISIBLE);



        }else {
                displayText.setText(R.string.Email_does_not_exist);
            displayText.setVisibility(View.VISIBLE);
        }
        db.close();
        return succes;



    }
}
