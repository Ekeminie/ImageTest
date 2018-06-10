package com.example.android.imagetest.data;

import android.provider.BaseColumns;

/**
 * Created by Delight on 17/05/2018.
 */

public class StudentContract {



    public static final class StudentEntry implements BaseColumns{


        public static final String TABLE_NAME = "students";
        public static final String STUDENT_NAME= "name";
        public static final String STUDENT_EMAIL= "email";
        public static final String STUDENT_PHONE = "number";
        public static final String STUDENT_AGE = "age";
        public static final String STUDENT_PASSWORD = "password";


    }
}
