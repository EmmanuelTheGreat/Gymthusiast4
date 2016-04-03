package com.emmanuel.gymthusiast4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



/**
 * Created by user on 25/03/2016.
 */
public class SplashScreen extends AppCompatActivity {

    //On create is what the system refers to once the class is activated


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //Thread object
        Thread transition = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(2300); // sleep for 3000 milli seconds = how long the splash screen will be displayed before transitioning to the main screen
                    Intent StartMainScreen = new Intent(getApplicationContext(), Login.class); // Intent = intend to start a new activity. In this case main activity.
                    startActivity(StartMainScreen); // here the system is starting the Intended activity "StartMainScreen".
                    finish(); // this will finish the current activity "SplashScreen"
                } catch (InterruptedException e) {

                    e.printStackTrace(); // Try- Catch method above surrounds the "sleep" statement in case there is an error.


                }
            }

        };
        // below will start the thread we have just created above
        transition.start();
    }
}
