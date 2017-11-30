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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.VISIBLE;

public class Login extends Fragment{

    private static final String TAG = "LoginFragment";

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

    private DatabaseReference mDatabase;

    public Login() {
        // Required empty public constructor
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

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inflate the layout for this fragment
        return view;
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
        void onFragmentInteraction();
        void onFragmentInteraction(View view);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));

            view.findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            view.findViewById(R.id.email_password_fields).setVisibility(View.GONE);
        } else {
            mStatusTextView.setText(R.string.signed_out);

            view.findViewById(R.id.email_password_buttons).setVisibility(VISIBLE);
            view.findViewById(R.id.email_password_fields).setVisibility(VISIBLE);
        }
    }

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
            if (mListener != null) {
                mLoadingBar.setVisibility(View.VISIBLE);

                mLoadingBar.setVisibility(View.VISIBLE);
                int i = view.getId();
                if (i == R.id.email_create_account_button) {
                    AccountTools tmpTools = AccountTools.getInstance();
                    tmpTools.createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                } else if (i == R.id.email_sign_in_button) {
                    AccountTools tmpTools = AccountTools.getInstance();
                    tmpTools.signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                }

            }
        }
    }

}
