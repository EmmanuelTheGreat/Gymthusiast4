package com.emmanuel.gymthusiast4;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

//import org.apache.http.NameValuePair;
import org.apache.commons.io.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**

 */
public class ServerRequests {
    //Progress dialog shows a loading bar when server requests are being executed
    ProgressDialog progressDialog;
    //How long the connection should persist for before timing out
    public static final int CONNECTION_TIMEOUT = 1000 * 5;
    public static final String SERVER_ADDRESS = "http//:192.168.0.3/android_login_api/Register.php";

    //Below are the constructs to be run when this sever request is created
    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false); //Cancelable is set to false so a user  cannot cancel a progress dialog when it is loading
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("This will only take a few seconds. I promise :)");
    }

    //Below method accesses the data in the background
    public void storeUserDataInBackground(User user, GetUserCallback callback) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, callback).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback callback) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, callback).execute();
    }

    //AsyncTask = background task.
    //Arguments 1st- return void since we send no info to this task but rather the constructor.
    //2nd void - Since we don't need to receive the progress from the async task. Instead we just start the progress dialog
    //3rd void - This is what the we want the async task to return
    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback callBack) {
            this.user = user;
            this.userCallback = callBack;

        }

        //Below method will be run in the background when the StoreUserDataAsyncTask is run
     /*   @Override
        protected Void doInBackground(Void... params) {
            //Accessing the server


            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("Name", user.Name));
            dataToSend.add(new BasicNameValuePair("age", user.Age + ""));
            dataToSend.add(new BasicNameValuePair("username", user.Username));
            dataToSend.add(new BasicNameValuePair("password", user.Password));


            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT); // Is the time we want to wait to receive anything for the server before timing out

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");



            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
*/

        @Override
        protected Void doInBackground(Void... params) {
            try {

                URL url= new URL(SERVER_ADDRESS + "Register.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("name", user.Name)
                        .appendQueryParameter("age", user.Age + "")
                        .appendQueryParameter("username", user.Username)
                        .appendQueryParameter("password", user.Password);

                String query          = builder.build().getEncodedQuery();
                OutputStream os       = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            //Dismiss progress dialog from loading
            progressDialog.dismiss();
            userCallback.done(null); //So the user activity knows the background (async task is finished)

            super.onPostExecute(aVoid);
        }
    }

     public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallback userCallback;

        public fetchUserDataAsyncTask(User user, GetUserCallback callBack) {
            this.user = user;
            this.userCallback = callBack;
        }

     /*   @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.Username));
            dataToSend.add(new BasicNameValuePair("password", user.Password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT); // Is the time we want to wait to receive anything for the server before timing out

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                //This decodes the sent file
                JSONObject jObject = new JSONObject(result);

                if(jObject.length() ==0){
                    returnedUser = null;
                }else{
                    String name = jObject.getString("name");
                    int age = jObject.getInt("age");
                    returnedUser = new User(name, age, user.Username, user.Password);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedUser;
        }

*/
    @Override
    protected User doInBackground(Void... params) {
        User returnedUser;
        try {

            URL url               = new URL(SERVER_ADDRESS + "FetchUserData.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", user.Username)
                    .appendQueryParameter("password", user.Password);

            String query          = builder.build().getEncodedQuery();
            OutputStream os       = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            InputStream in = new BufferedInputStream(conn.getInputStream());

            String response = IOUtils.toString(in, "UTF-8");
            JSONObject jResponse = new JSONObject(response);

            if (jResponse.length() == 0) {
                returnedUser = null;
            } else {
                String name = jResponse.getString("name");
                int age     = jResponse.getInt("age");

                returnedUser = new User(name, age, user.Username, user.Password);


            }

            return returnedUser;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);

            super.onPostExecute(returnedUser);
        }
    }
}

