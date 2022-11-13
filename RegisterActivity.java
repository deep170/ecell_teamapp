package ecell.app.ecellteam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.internal.view.SupportMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[@#$%^&+=])(?=.*[0-9])(?=\\S+$).{6,20}$");
    private TextView alreadyHaveAcc;
    private String department;
    private Spinner deptSpinner;
    /* access modifiers changed from: private */
    public ProgressDialog loadingBar;
    /* access modifiers changed from: private */
    public FirebaseAuth mAuth;
    private Button registerButton;
    /* access modifiers changed from: private */
    public DatabaseReference rootRef;
    private EditText userEmail;
    private EditText userName;
    private EditText userPassword;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_register);
        this.mAuth = FirebaseAuth.getInstance();
        this.rootRef = FirebaseDatabase.getInstance().getReference();
        InitializeFields();
        this.alreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RegisterActivity.this.SendUserToLoginActivity();
            }
        });
        this.registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RegisterActivity.this.CreateNewAccount();
            }
        });
    }

    /* access modifiers changed from: private */
    public void CreateNewAccount() {
        String userEmailText = this.userEmail.getText().toString();
        String userPasswordText = this.userPassword.getText().toString();
        String userNameText = this.userName.getText().toString();
        if (TextUtils.isEmpty(userEmailText)) {
            this.userEmail.setError("Please Enter Email...");
        }
        if (TextUtils.isEmpty(userPasswordText)) {
            this.userPassword.setError("Please Enter Email...");
        }
        if (TextUtils.isEmpty(userNameText)) {
            this.userName.setError("Please Enter Your Name");
        }
        if (this.department.equals("Choose Your Department")) {
            TextView errorText = (TextView) this.deptSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(SupportMenu.CATEGORY_MASK);
            errorText.setText("Please Choose Your Department");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmailText).matches()) {
            this.userEmail.setError("Please enter a valid email address");
        }
        if (!passwordPattern.matcher(userPasswordText).matches()) {
            this.userPassword.setError("Please enter a valid password");
        }
        if (userPasswordText.length() < 6) {
            this.userPassword.setError("Password must contain at least 6 characters");
            return;
        }
        this.loadingBar.setTitle("Creating New Account");
        this.loadingBar.setMessage("Please Wait,while we are creating new account for you...");
        this.loadingBar.setCanceledOnTouchOutside(true);
        this.loadingBar.show();
        this.mAuth.createUserWithEmailAndPassword(userEmailText, userPasswordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    RegisterActivity.this.rootRef.child("Users").child(RegisterActivity.this.mAuth.getCurrentUser().getUid()).setValue("");
                    RegisterActivity.this.loadingBar.dismiss();
                    RegisterActivity.this.StoreInfoInDatabase();
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", 0).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "ERROR : " + task.getException().toString(), 0).show();
                RegisterActivity.this.loadingBar.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void StoreInfoInDatabase() {
        String userNameText = this.userName.getText().toString();
        String userEmailText = this.userEmail.getText().toString();
        final String currentUserId = this.mAuth.getCurrentUser().getUid();
        HashMap<String, String> profileMap = new HashMap<>();
        profileMap.put("uid", currentUserId);
        profileMap.put("name", userNameText);
        profileMap.put("email", userEmailText);
        profileMap.put("department", this.department);
        this.rootRef.child("Users").child(currentUserId).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    RegisterActivity.this.rootRef.child("Users").child(currentUserId).child("LeaderBoardScore").setValue(0);
                    RegisterActivity.this.SendUserToDashboardActivity();
                    Toast.makeText(RegisterActivity.this, "Profile Updated Successfully", 0).show();
                    RegisterActivity.this.SendUserToDashboardActivity();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "ERROR : " + task.getException().toString(), 0).show();
            }
        });
    }

    private void InitializeFields() {
        this.registerButton = (Button) findViewById(C0250R.C0253id.registerButton);
        this.userEmail = (EditText) findViewById(C0250R.C0253id.emailTxt);
        this.userPassword = (EditText) findViewById(C0250R.C0253id.passwordTxt);
        this.userName = (EditText) findViewById(C0250R.C0253id.usernameTxt);
        this.loadingBar = new ProgressDialog(this);
        this.alreadyHaveAcc = (TextView) findViewById(C0250R.C0253id.already_have_an_account_link);
        this.deptSpinner = (Spinner) findViewById(C0250R.C0253id.departmentSpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, C0250R.array.department, 17367048);
        arrayAdapter.setDropDownViewResource(17367050);
        this.deptSpinner.setAdapter(arrayAdapter);
        this.deptSpinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.department = adapterView.getItemAtPosition(i).toString();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /* access modifiers changed from: private */
    public void SendUserToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    /* access modifiers changed from: private */
    public void SendUserToDashboardActivity() {
        Intent mainIntent = new Intent(this, DashboardActivity.class);
        mainIntent.addFlags(268468224);
        startActivity(mainIntent);
        finish();
    }
}
