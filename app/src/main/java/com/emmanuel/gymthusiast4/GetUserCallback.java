package com.emmanuel.gymthusiast4;

/**
 * THis class allows us to inform the activity which performed the server request,
 * when this server requested has been completed
 */

//Interface since this won't actually implement any method, it just tells the activity which method to use
interface GetUserCallback {

     public abstract void done(User returnedUser);

}
