package com.example.a.projecttwo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {
    Button btn_login;
    TextView txt_taotaikhoan;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;
    LoginButton loginButton;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private ProgressDialog mProgressDialog;
    private CallbackManager mCallbackManager;
    private EditText edt_email;
    private EditText edt_pass;
    private ImageButton btn_google_login;
    private ImageButton btn_facebook_login;
    String email_text = "";
    String password_text = "";
    private Dialog dialog;
    private String URL_CALL_API_GET_DATA = "http://namtnps06077.hol.es/crud.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        initControl();
        initData();
        initEvent();
        initGoogle();
        initFacebook();
        initEmail();
        //Logout Google when run app
        revokeAccess();
        //Logout Facebook when run app
        signOut();

    }


    private void initEvent() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.my_statusbar_color));
        email_text = edt_email.getText().toString();
        password_text = edt_pass.getText().toString();

    }

    private void initData() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_email != null || edt_pass != null) {
                    signInEmail();
                } else {
                    Toast.makeText(getApplicationContext(), "null value", Toast.LENGTH_LONG).show();
                }

            }
        });
        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        btn_facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();

            }
        });
        txt_taotaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initControl() {
        btn_login = (Button) findViewById(R.id.btn_login_login);
        txt_taotaikhoan = (TextView) findViewById(R.id.txt_newaccount_login);
        edt_email = (EditText) findViewById(R.id.ext_user_login);
        edt_pass = (EditText) findViewById(R.id.ext_password_login);
        btn_google_login = (ImageButton) findViewById(R.id.img_gg_login);
        btn_facebook_login = (ImageButton) findViewById(R.id.img_fb_login);

    }

    //Login Google
    private void initGoogle() {

//        mStatusTextView = findViewById(R.id.status);
//        mDetailTextView = findViewById(R.id.detail);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
//        findViewById(R.id.sign_out_button).setOnClickListener(this);
//        findViewById(R.id.disconnect_button).setOnClickListener(this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
    }


    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            String userName = acct.getDisplayName().toString();
                            String idUser = acct.getId().toString();
                            String imageUser = acct.getPhotoUrl().toString();
                            updateUI(user, userName, idUser, imageUser);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.activity_login), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null, null, null, null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOutFb() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null, null, null, null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null, null, null, null);
                    }
                });
    }

    private void updateUI(FirebaseUser user, String name, String id, String image) {
        hideProgressDialog();
        if (user != null) {
//            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            showQuestionDialog(name, id, image);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    //Login Facebook
    private void initFacebook() {
//        mStatusTextView = findViewById(R.id.status);
//        mDetailTextView = findViewById(R.id.detail);
//        findViewById(R.id.button_facebook_signout).setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth

        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUIFb(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUIFb(null);
                // [END_EXCLUDE]
            }
        });
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                parseJson(object);
                JSONObject object1 = response.getJSONObject();

                Log.d("successed", response.toString());
                String name_facebook_get_data = null;
                String id_facebook_get_data = null;
                String url_facebook_get_data = null;
                try {
                    name_facebook_get_data = object1.getString("first_name") + " " + object.getString("last_name");
                    id_facebook_get_data = object1.getString("id");

                    url_facebook_get_data = "https://graph.facebook.com/" + object1.getString("id") + "" + "/picture?type=large";
//                    email_facebook_get_data=object1.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //
                if (name_facebook_get_data != null) {
                    showQuestionDialog(name_facebook_get_data, id_facebook_get_data, url_facebook_get_data);
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,last_name,first_name,picture.type(large),updated_time");
        request.setParameters(parameters);
        request.executeAsync();
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUIFb(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            updateUIFb(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    public void parseJson(JSONObject response) {

        try {

            JSONObject json = response;

            String id = json.getString("id");
            String email = json.getString("email");
            String first_name = json.getString("first_name");
            String last_name = json.getString("last_name");
            // show(true,first_name);
//            UserImageUrl = "https://graph.facebook.com/" + IdProfileFb + "/picture?type=large";
        } catch (JSONException e) {

        }

    }

    private void updateUIFb(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
//            mStatusTextView.setText(getString(R.string.facebook_status_fmt, user.getDisplayName()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.button_facebook_login).setVisibility(View.GONE);
//            findViewById(R.id.button_facebook_signout).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);

            findViewById(R.id.button_facebook_login).setVisibility(View.GONE);
//            findViewById(R.id.button_facebook_signout).setVisibility(View.GONE);
        }
    }

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUIFb(null);
    }

    private void initEmail() {
        mAuth = FirebaseAuth.getInstance();
        email_text = edt_email.getText().toString();
        password_text = edt_pass.getText().toString();

    }

    private void signInEmail() {
        Log.d(TAG, "signIn:" + edt_email.getText().toString());
        if (!validateForm()) {
            return;
        }
        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(edt_email.getText().toString(), edt_pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String image = "https://developers.google.com/experts/img/user/user-default.png";
                            updateUIEmail(user, edt_email.getText().toString(), id, image);

                        } else {
                            // If sign in fails, display a message to the user.
                            showQuestionDialogError(task.getException().toString());
                            updateUIEmail(null, null, null, null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {

                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = edt_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            edt_email.setError("Required.");
            valid = false;
        } else {
            edt_email.setError(null);
        }

        String password = edt_pass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            edt_pass.setError("Required.");
            valid = false;
        } else {
            edt_pass.setError(null);
        }

        return valid;
    }

    private void updateUIEmail(FirebaseUser user, String email, String id, String image) {
        hideProgressDialog();
        if (user != null) {
            if (email.equals("admin@gmail.com")) {
                showQuestionDialogAdmin(email, id, image);
            } else {
                showQuestionDialog(email, id, image);
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, null, null, null);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        } else if (i == R.id.btn_login_login) {

        }

//        } else if (i == R.id.sign_out_button) {
//            signOut();
//        } else if (i == R.id.disconnect_button) {
//            revokeAccess();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null, null, null, null);
                // [END_EXCLUDE]
            }
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void showQuestionDialog(final String NameUser, final String id_guest, final String image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("App");
        builder.setMessage("Bạn có muốn đăng nhập với tài khoản: " + NameUser + "\nID: " + id_guest + " không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                upData("id" + id_guest);
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id_guest", "id" + id_guest + "");
                editor.putString("username", NameUser + "");
                editor.putString("image", image + "");
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void showQuestionDialogAdmin(final String NameUser, final String id_guest, final String image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("App");
        builder.setMessage("Admin Login?"+"\nID: " + id_guest );
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                upData("id" + id_guest);
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id_guest", "id" + id_guest + "");
                editor.putString("username", NameUser + "");
                editor.putString("image", image + "");
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void showQuestionDialogError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("App");
        builder.setMessage("" + error + "");
        builder.setCancelable(false);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void upData(final String id_guest) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CALL_API_GET_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(getApplicationContext(), "Tạo Bảng Thành Công", Toast.LENGTH_SHORT).show();
//
//                initUpdateUI();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
//               Bitmap bitmap = ((BitmapDr awable) img_view_photo_nhaphang.getDrawable()).getBitmap();
////                Bitmap image_fb = BitmapFactory.decodeStream(url_fb.openConnection().getInputStream());
//                String image = decodeImage(bitmap);
                stringMap.put("id_guest", id_guest + "");
                stringMap.put("select", "2");

                return stringMap;

            }

        };
        requestQueue.add(stringRequest);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
