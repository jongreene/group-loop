package ferocioushammerheads.grouploop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class AccountTools {
//    private OnSignInListener mSignIn;

    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private DatabaseReference mDatabase;

    private View view;


    AccountTools(FirebaseAuth mAuth, DatabaseReference mDatabase){
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
    }

    AccountTools(FirebaseAuth mAuth, DatabaseReference mDatabase, View view){
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
        this.view = view;

//        if (this instanceof OnSignInListener) {
//            mSignIn = (OnSignInListener) view.getContext();
//        } else {
//            throw new RuntimeException(view.getContext().toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    private void createAccount(String email, String password) {
        final String username = email;
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            generates a folder under users for the user
                            writeNewUser(user.getUid(), username, username);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(view.getContext(), "Authentication failed. Password not long enough.",Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]


    }

    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            hideProgressDialog();
//                            mSignIn.onSignIn();
                            if(user.isEmailVerified()){
//                                finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(view.getContext(),"Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                            hideProgressDialog();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        // [END_EXCLUDE]


                    }


                });

        // [END sign_in_with_email]
    }

    public void signOut() {
        mAuth.signOut();
    }

    public void sendEmailVerification() {
        // Disable button
//        view.findViewById(R.id.verify_email_button).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
//                        view.findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(view.getContext(),
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
//                            hideProgressDialog();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(view.getContext(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
//                            hideProgressDialog();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm() {
        boolean valid = true;
//
//        String email = mEmailField.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            mEmailField.setError("Required.");
//            valid = false;
//        } else {
//            mEmailField.setError(null);
//        }
//
//        String password = mPasswordField.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            mPasswordField.setError("Required.");
//            valid = false;
//        } else {
//            mPasswordField.setError(null);
//        }

        return valid;
    }

    private void writeNewUser(String userId, String name, String email) {
        UserProfile userProfile = new UserProfile(userId, name, email);

        mDatabase.child("users").child(userId).setValue(userProfile);
    }
}
