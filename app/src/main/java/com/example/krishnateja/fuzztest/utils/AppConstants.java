package com.example.krishnateja.fuzztest.utils;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class AppConstants {
    public static class ServerVariables{

        public static final String SCHEME="http";
        public static final String AUTHORITY="quizzes.fuzzstaging.com";
        public static final String[] PATHS={"quizzes","mobile","1","data.json"};

        public static final String METHOD = "GET";
    }

    public static class BundleExtras {
        public static final String TEXT = "text";
        public static final String IMAGE ="image";

        public static final String DATA_FLAG ="data_field" ;
        public static final String URL ="url" ;
    }

    public static class FLAGS {

        public static final int TEXT_FLAG =0;
        public static final int IMAGE_FLAG =1;
        public static final int ALL_FLAG=0;
    }
}
