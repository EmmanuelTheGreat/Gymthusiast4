package com.emmanuel.gymthusiast4;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class stores the details of the User locally on the phone using shared preference.
 */
public class UserLocalStore {

    public static final String SP_Name = "userDetails";
    SharedPreferences userLocalDatabase;

    //Below is the constructor- i.e. the first thing that runs when a user local store is created.
    public UserLocalStore(Context context){

        userLocalDatabase = context.getSharedPreferences(SP_Name, 0); //SP_Name is the name of the file where the users details are coming from and 0 = a default value of zeron
    }

    //Below method stores user data
    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit(); //this method allows the shared preference folder to be edited
        spEditor.putString("Name", user.Name); //This line stores the attributes of the user which was passed to this method
        spEditor.putInt("Age", user.Age);
        spEditor.putString("Username", user.Username);
        spEditor.putString("Password", user.Password);
        spEditor.commit(); //This commits the changes to the shared preference.
           }

    //Below method retrieves the data of the User who is logged in. No use of "void" since it needs to return data.
    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name","");
        int age = userLocalDatabase.getInt("age", -1);
        String username = userLocalDatabase.getString("username","");
        String password = userLocalDatabase.getString("password","");

        //Now we create a new user with the attributes retrieved above

        User storedUser = new User(name, age, username, password);
        return storedUser;
    }

    //The below method sets a user as logged in given boolean flags are satisfied
    // If a user is logged in the below method will be set a as true. Else it will be set as false.
    public void setUserLoggedIn (boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    /*Below method tells us if a user is logged in or not
    * if the user is logged in then return true (false is se as default)
    * otherwise if not logged return false.
    */
    public boolean getUserLoggedIn(){

        if(userLocalDatabase.getBoolean("loggedIn", false)==true){
            return true;
        }else{
            return false;
        }
    }

    //Below method allows stored data to be cleared

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
