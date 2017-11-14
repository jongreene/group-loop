package ferocioushammerheads.grouploop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    private static final String TAG = "EmailPassword";

    private TextView mStatusTextView;
    private EditText mEmailField;
    private EditText mPasswordField;
    private ProgressBar mLoadingBar;
    private View view;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private OnFragmentInteractionListener mListener;
    private ButtonClickListener mButtonClickListener;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);


        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }
        // Views
        mStatusTextView = view.findViewById(R.id.status);
        mEmailField = view.findViewById(R.id.field_email);
        mPasswordField = view.findViewById(R.id.field_password);
        mLoadingBar = view.findViewById(R.id.login_loading_bar);

        // Buttons
        view.findViewById(R.id.email_sign_in_button).setOnClickListener(mButtonClickListener);
        view.findViewById(R.id.email_create_account_button).setOnClickListener(mButtonClickListener);
        view.findViewById(R.id.sign_out_button).setOnClickListener(mButtonClickListener);
        view.findViewById(R.id.verify_email_button).setOnClickListener(mButtonClickListener);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        mAuth.getCurrentUser();

//        // Auto logs out signed in users who have verified email accounts when activity is started
//        if(mAuth.getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
////            TODO: add signOut();
//            //            signOut();
//        }

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
        void onFragmentInteraction(View view);
    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        TODO: add update UI
//        updateUI(currentUser);
    }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password);
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(this, "Authentication failed. Password not long enough.",Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // [START_EXCLUDE]
//                        hideProgressDialog();
//                        // [END_EXCLUDE]
//                    }
//                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
//        mAuth.signInWithEmailAndPassword(email, password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            hideProgressDialog();
                            if(user.isEmailVerified()){
//                                finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(view.getContext(),"Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            hideProgressDialog();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        // [END_EXCLUDE]


                    }


                });

        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        // Disable button
        view.findViewById(R.id.verify_email_button).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
//        user.sendEmailVerification();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        view.findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(view.getContext(),
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(view.getContext(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));

            view.findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            view.findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            view.findViewById(R.id.signed_in_buttons).setVisibility(VISIBLE);

            view.findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());

        } else {
            mStatusTextView.setText(R.string.signed_out);

            view.findViewById(R.id.email_password_buttons).setVisibility(VISIBLE);
            view.findViewById(R.id.email_password_fields).setVisibility(VISIBLE);
            view.findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }

//    @Override
//    public void onClick(View v) {
//
//        view.findViewById(R.id.login_loading_bar).setVisibility(View.VISIBLE);
//
//        int i = v.getId();
//        if (i == R.id.email_create_account_button) {
//            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
//        } else if (i == R.id.email_sign_in_button) {
//            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
//        } else if (i == R.id.sign_out_button) {
//            signOut();
//        } else if (i == R.id.verify_email_button) {
//            sendEmailVerification();
//        }
//
//    }

    /**    show/hide progress bar for logging in/out/creating account/sending email */
    public void showProgressDialog() {
        view.findViewById(R.id.login_loading_bar).setVisibility(VISIBLE);
    }

    public void hideProgressDialog() {
        view.findViewById(R.id.login_loading_bar).setVisibility(View.GONE);
    }

    private class ButtonClickListener implements View.OnClickListener {
        ButtonClickListener() {}

        @Override
        public void onClick(View view) {
            int clickedId = view.getId();
            if (mListener != null) {
//                mListener.onFragmentInteraction(view);

                mLoadingBar.setVisibility(View.VISIBLE);

//                view.findViewById(R.id.login_loading_bar);
                int i = view.getId();
                if (i == R.id.email_create_account_button) {
                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                } else if (i == R.id.email_sign_in_button) {
                    signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                } else if (i == R.id.sign_out_button) {
                    signOut();
                } else if (i == R.id.verify_email_button) {
                    sendEmailVerification();
                }

            }
        }
    }

}
