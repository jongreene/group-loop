package ferocioushammerheads.grouploop;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountTools {
    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private DatabaseReference mDatabase;

    private UserProfile userProfile;

    private AccountToolsHelper ie;
    private boolean somethingHappened;

    AccountTools(){}

    public void setupTools(UserAccount event,FirebaseAuth mAuth, DatabaseReference mDatabase){
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;

        // Save the event object for later use.
        ie = event;
        // Nothing to report yet.
        somethingHappened = false;
    }

    public void setupTools(MainActivity event,FirebaseAuth mAuth, DatabaseReference mDatabase){
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;

        // Save the event object for later use.
        ie = event;
        // Nothing to report yet.
        somethingHappened = false;
    }

    public void setSomethingHappened(boolean somethingHappened){
        this.somethingHappened = somethingHappened;
    }

    public void doWork ()
    {
        // Check the predicate, which is set elsewhere.
        if (somethingHappened)
        {
            // Signal the even by invoking the interface's method.
            ie.loggedInEvent();
        }
    }

    public void toastUp(String toastText)
    {
        // Check the predicate, which is set elsewhere.
        if (somethingHappened)
        {
            ie.toastUp(toastText);
        }
    }


    public void createAccount(String email, String password) {
        final String username = email;
        if (!validateForm(email, password)) {
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
                            setSomethingHappened(true);
                            doWork();
                            setSomethingHappened(false);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(view.getCon, "Authentication failed. Password not long enough.",Toast.LENGTH_SHORT).show();
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
        if (!validateForm(email, password)) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            setSomethingHappened(true);
                            doWork();
                            toastUp("Sign in was a success");
                            setSomethingHappened(false);
                            loadProfile();
                        } else {
                            // If sign in fails, display a message to the user.
                            setSomethingHappened(true);
                            toastUp("Failed to sign in");
                            setSomethingHappened(false);
                        }
                    }
                });

        // [END sign_in_with_email]
    }

    public void signOut() {
        mAuth.signOut();
    }

    public void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        if (task.isSuccessful()) {
                            setSomethingHappened(true);
                            toastUp("Verification email sent successfully.");
                            setSomethingHappened(false);

                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (email.length()<4) {
            setSomethingHappened(true);
            toastUp("Failed to sign in");
            setSomethingHappened(false);

            valid = false;
        }

        if (password.length()<6) {
            setSomethingHappened(true);
            toastUp("Failed to sign in");
            setSomethingHappened(false);
            valid = false;
        }

        return valid;
    }

    private void writeNewUser(String userId, String name, String email) {
        userProfile = new UserProfile(userId, name, email);

        mDatabase.child("users").child(userId).setValue(userProfile);
    }

    public void updateUser(UserProfile userProfile){
        mDatabase.child("users").child(userProfile.getUserId()).setValue(userProfile);
    }

    private static AccountTools instance = null;

    private void AccountTools(){}

    public static AccountTools getInstance(){
        if(instance==null){
            instance = new AccountTools();
        }
        return instance;
    }

    private DatabaseReference mDatabaseRef;
    // Define the actual handler for the event.
    public void loadGroup(String groupName) {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        String userRefString = "/groups/" + groupName;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(userRefString);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserGroup tmpGroup = dataSnapshot.getValue(UserGroup.class);
                MainActivity.currentGroup = tmpGroup;
//                Log.d(TAG, "group creator:" + tmpGroup.getCreator());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabaseRef.addValueEventListener(postListener);
    }

    public void loadProfile() {
        String userRefString = "/users/" + MainActivity.user.getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(userRefString);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile tmpProfile = dataSnapshot.getValue(UserProfile.class);
                MainActivity.userProfile = tmpProfile;
                Log.d(TAG, "email from snapshot:" + tmpProfile.getEmail());
                if(tmpProfile.getGroupList().size()>0) {
                    loadGroup(tmpProfile.getCurrentGroup());

                    setSomethingHappened(true);
                    doWork();
                    setSomethingHappened(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabaseRef.addValueEventListener(postListener);
    }
}
