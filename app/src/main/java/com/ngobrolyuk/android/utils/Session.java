package com.ngobrolyuk.android.utils;

/**
 * Created by MIP on 1/6/2018.
 */


import android.app.Activity;
import android.content.Intent;

import com.ngobrolyuk.android.LoginActivity;
import com.ngobrolyuk.android.models.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Session {
    // define single instance
    private static Session instance;
    // define realm
    private Realm realm;

    // Session constructor
    private Session() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfig);
    }

    // get singletone from session
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // get new Instance (new Object) from this class
    public static Session newInstance() {
        return new Session();
    }

    // login user take user and add it to realm
    public void loginUser(final User user) {

        if (realm.where(User.class).findFirst() == null) {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(user);
                }
            });

        } else {
            logout();
            loginUser(user);
        }


    }

    // logout
    public void logout() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(User.class);
            }
        });
    }

    public boolean isUserLoggedIn() {
        return realm.where(User.class).findFirst() != null;
    }

    public User getUser() {
        return realm.where(User.class).findFirst();
    }

    public void logoutAndGoToLogin(Activity activity) {
        logout();
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }


}
