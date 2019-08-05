package com.vbstaj.bizimyerimiz.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vbstaj.bizimyerimiz.model.User;

public class Utils {
    public static User loggedUser;
    public static FirebaseFirestore databaseFirestore;
    public static FirebaseAuth fbaseAuth;
}
