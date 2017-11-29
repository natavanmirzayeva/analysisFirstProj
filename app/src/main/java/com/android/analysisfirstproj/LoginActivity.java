package com.android.analysisfirstproj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity{
    EditText editTextEmail, editTextPassword,editTextUserName;
    Button buttonSignin,buttonSignInGoogle;
    TextView buttonSignup,buttonReset;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private FirebaseAuth mauth;
    FirebaseDatabase mFirebaseInstance;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    private static final String TAG1 = "Google Signin";
    private  static final int PC_SIGN_IN = 9001;
    Intent intent1;
    Intent intent3;
    Intent intent2;
   // User user1 = new User();

    private GoogleApiClient mGoogleApiClient;

    private boolean mGoogleIntentInProgress;


    private boolean mGoogleLoginClicked;


    private ConnectionResult mGoogleConnectionResult;
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        mauth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();



        firebaseAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(mAuthListener !=null){firebaseAuth.removeAuthStateListener(mAuthListener);}

    }

    @Override
    public void  onActivityResult(int requestCode,int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intent3 = getIntent();


        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        // editTextUserName = (EditText) findViewById(R.id.editTextUsername);


        buttonSignin = (Button) findViewById(R.id.btn_login);


        progressDialog = new ProgressDialog(this);




        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    intent1 = new Intent(LoginActivity.this,HomePageActivity.class);
                    startActivity(intent1);
                    updateUI(user);

                    Log.d(TAG1,"onAuthStateChanged:signed_in" + user.getUid());
                }
                else
                {
                    Log.d(TAG1,"onAuthStateChanged:signed_out");
                    updateUI(null);
                }

            }
        };






        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinDeneme();
            }
        });


    }


    public void signinDeneme()
    {

        String  email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        Log.d(TAG, "signIn:" + email);
        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) Toast.makeText(LoginActivity.this, "mail veya şifre eksik", Toast.LENGTH_LONG).show();

       else {
            final FirebaseUser prevUser = firebaseAuth.getCurrentUser();

            AuthCredential credential = EmailAuthProvider.getCredential(email, password);

            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "girmedi", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(LoginActivity.this, "giriş yapılmıştır", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                                startActivity(intent);
                                finish();


                            }
                        }
                    });
        }

    }

    private void sendEmailVerification()
    {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // Re-enable button

                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this,
                                    "Doğrulama Mesajı Gönderilmiştir: " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            firebaseAuth.signOut();
                            Intent intent1 = getIntent();
                            startActivity(intent1);

                            // FirebaseUser user = firebaseAuth.getCurrentUser();
                            finish();

                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    "Gönderilemedi",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    FirebaseUser user;
    private void checkIfEmailVerified()
    {
        user =  firebaseAuth.getCurrentUser();

        if (user.isEmailVerified())
        {

            Toast.makeText(LoginActivity.this, "Başarılı bir şekilde giriş yapılmıştır", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {

            user = null;
            FirebaseAuth.getInstance().signOut();
            Intent intent1 = getIntent();
            startActivity(intent1);
            finish();



        }
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {

        } else {


            findViewById(R.id.btn_login).setVisibility(View.VISIBLE);



        }
    }






}
