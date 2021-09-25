package com.example.bmi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;

import java.util.Timer;
import java.util.TimerTask;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "HuaweiIdActivity";
    private AccountAuthService mAuthManager;
    private AccountAuthParams mAuthParam;
    private Button start_ibm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        findViewById(R.id.account_signin).setOnClickListener(this);
        findViewById(R.id.account_signout).setOnClickListener(this);
        findViewById(R.id.account_signInCode).setOnClickListener(this);
        findViewById(R.id.account_silent_signin).setOnClickListener(this);
        findViewById(R.id.cancel_authorization).setOnClickListener(this);
        start_ibm = findViewById(R.id.ibm);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Codelab Code
     * Pull up the authorization interface by getSignInIntent
     */
    private void signIn() {
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setAccessToken()
                .createParams();
        mAuthManager = AccountAuthManager.getService(AccountActivity.this, mAuthParam);
        startActivityForResult(mAuthManager.getSignInIntent(), Constant.REQUEST_SIGN_IN_LOGIN);
//        start_ibm.setEnabled(true);
        Toast myToast = Toast.makeText(AccountActivity.this, "Signing in, please wait" , Toast.LENGTH_LONG);
        myToast.show();
        enable_btn();
    }

    private void signInCode() {
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setProfile()
                .setAuthorizationCode()
                .createParams();
        mAuthManager = AccountAuthManager.getService(AccountActivity.this, mAuthParam);
        startActivityForResult(mAuthManager.getSignInIntent(), Constant.REQUEST_SIGN_IN_LOGIN_CODE);
//        start_ibm.setEnabled(true);
        Toast myToast = Toast.makeText(AccountActivity.this, "Signing in, please wait" , Toast.LENGTH_LONG);
        myToast.show();
        enable_btn();
    }

    /**
     * Codelab Code
     * sign Out by signOut
     */
    private void signOut() {
        Task<Void> signOutTask = mAuthManager.signOut();
        signOutTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "signOut Success");
                Toast myToast = Toast.makeText(AccountActivity.this, "Signed out" , Toast.LENGTH_LONG);
                myToast.show();
                start_ibm.setEnabled(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "signOut fail");
                Toast myToast = Toast.makeText(AccountActivity.this, "Sign out failed" , Toast.LENGTH_LONG);
                myToast.show();
//                start_ibm.setEnabled(true);
            }
        });
    }

    /**
     * Codelab Code
     * Silent SignIn by silentSignIn
     */
    private void silentSignIn() {
        Task<AuthAccount> task = mAuthManager.silentSignIn();
        task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
            @Override
            public void onSuccess(AuthAccount authAccount) {
                Log.i(TAG, "silentSignIn success");
                Toast myToast = Toast.makeText(AccountActivity.this, "Silent Sign in success" , Toast.LENGTH_LONG);
                myToast.show();
//                start_ibm.setEnabled(true);
                enable_btn();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                //if Failed use getSignInIntent
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    signIn();
                    Toast myToast = Toast.makeText(AccountActivity.this, "Silent Sign in success" , Toast.LENGTH_LONG);
                    myToast.show();
//                    start_ibm.setEnabled(true);
                    enable_btn();
                }
            }
        });
//        start_ibm.setEnabled(true);
        enable_btn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_signin:
                signIn();
                break;
            case R.id.account_signout:
                signOut();
                break;
            case R.id.account_signInCode:
                signInCode();
                break;
            case R.id.account_silent_signin:
                silentSignIn();
                break;
            case R.id.cancel_authorization:
                cancelAuthorization();
                break;
            default:
                break;
        }
    }

    private void cancelAuthorization() {
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setProfile()
                .setAuthorizationCode()
                .createParams();
        mAuthManager = AccountAuthManager.getService(AccountActivity.this, mAuthParam);
        Task<Void> task = mAuthManager.cancelAuthorization();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "cancelAuthorization success");
                Toast myToast = Toast.makeText(AccountActivity.this, "Cancel Authorization success" , Toast.LENGTH_SHORT);
                myToast.show();
                start_ibm.setEnabled(false);
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "cancelAuthorization failure：" + e.getClass().getSimpleName());
                Toast myToast = Toast.makeText(AccountActivity.this, "Cancel Authorization failure" , Toast.LENGTH_SHORT);
                myToast.show();
//                start_ibm.setEnabled(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN) {
            //login success
            //get user message by parseAuthResultFromIntent
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                AuthAccount authAccount = authAccountTask.getResult();
                Log.i(TAG, authAccount.getDisplayName() + " signIn success ");
                Log.i(TAG, "AccessToken: " + authAccount.getAccessToken());
            } else {
                Log.i(TAG, "signIn failed: " + ((ApiException) authAccountTask.getException()).getStatusCode());
            }
        }
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN_CODE) {
            //login success
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                AuthAccount authAccount = authAccountTask.getResult();
                Log.i(TAG, "signIn get code success.");
                Log.i(TAG, "ServerAuthCode: " + authAccount.getAuthorizationCode());

                /**** english doc:For security reasons, the operation of changing the code to an AT must be performed on your server. The code is only an example and cannot be run. ****/
                /**********************************************************************************************/
            } else {
                Log.i(TAG, "signIn get code failed: " + ((ApiException) authAccountTask.getException()).getStatusCode());
            }
        }
    }

    private void enable_btn() {
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        start_ibm.setEnabled(true);
                    }
                });
            }
        }, 5000);
    }

    public void main_actvt(View view) {
        Intent intent = new Intent(this, BmiActivity.class);
//        intent.putExtra(EXTRA_MESSAGE, resultTra);
        startActivity(intent);
    }
}