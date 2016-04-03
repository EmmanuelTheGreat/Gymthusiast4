package com.emmanuel.gymthusiast4;

/**
 * Created by user on 26/03/2016.
 */
public class User {

    String Username, Password, Name;
    int Age;

    public User (String Name, int Age, String Username, String Password){

        this.Name =Name;
        this.Age = Age;
        this.Username = Username;
        this.Password = Password;

    }

    public User(String Name,String Password){

        this.Name =Name;
        this.Age = -1;
        this.Username = "";
        this.Password = Password;
    }

}
