package ar.com.tpfinal.rang_store.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.inject.Provider;

public final class DataBase extends FirebaseAuth{
    private static DataBase instance;

    public DataBase(@NonNull FirebaseApp firebaseApp, @NonNull Provider provider) {
        super(firebaseApp, provider);
    }


//    public static DataBase getInstance() {
//        if (instance == null) {
//
//            instance = DataBase();
//        }
//        return instance;
//    }
}