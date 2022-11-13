package ecell.app.ecellteam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[@#$%^&+=])(?=.*[0-9])(?=\\S+$).{6,20}$");
    private EditText emailText;
    /* access modifiers changed from: private */
    public ProgressDialog loadingBar;
    private Button loginButton;
    private FirebaseAuth mAuth;
    /* access modifiers changed from: private */
    public TextView messageText;
    private Button newAccBtn;
    private EditText passwordText;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_login);
        this.emailText = (EditText) findViewById(C0250R.C0253id.login_email);
        this.passwordText = (EditText) findViewById(C0250R.C0253id.login_password);
        this.loginButton = (Button) findViewById(C0250R.C0253id.loginButton);
        this.messageText = (TextView) findViewById(C0250R.C0253id.messageText);
        this.newAccBtn = (Button) findViewById(C0250R.C0253id.createAccButton);
        this.mAuth = FirebaseAuth.getInstance();
        this.loadingBar = new ProgressDialog(this);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.messageText.setVisibility(4);
                LoginActivity.this.AllowUserToLogin();
            }
        });
        this.newAccBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    /* access modifiers changed from: private */
    public void AllowUserToLogin() {
        String userEmailText = this.emailText.getText().toString();
        String userPasswordText = this.passwordText.getText().toString();
        if (userEmailText.isEmpty()) {
            this.emailText.setError("This field is required");
        }
        if (userPasswordText.isEmpty()) {
            this.passwordText.setError("This field is required");
        }
        if (userEmailText.isEmpty() || userPasswordText.isEmpty()) {
            if (userEmailText.isEmpty()) {
                this.emailText.setError("This field is required");
            }
            if (userPasswordText.isEmpty()) {
                this.passwordText.setError("This field is required");
            }
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmailText).matches()) {
            this.emailText.setError("Please enter a valid email address");
        }
        if (!passwordPattern.matcher(userPasswordText).matches()) {
            this.passwordText.setError("Please enter a valid password");
        }
        if (userPasswordText.length() < 6) {
            this.passwordText.setError("Password must contain at least 6 characters");
            return;
        }
        this.loadingBar.setTitle("Logging In");
        this.loadingBar.setMessage("Please Wait...");
        this.loadingBar.setCanceledOnTouchOutside(true);
        this.loadingBar.show();
        this.mAuth.signInWithEmailAndPassword(userEmailText, userPasswordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    LoginActivity.this.SendUserToDashboard();
                    LoginActivity.this.loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Logged In Successfully", 0).show();
                    return;
                }
                LoginActivity.this.messageText.setVisibility(0);
                LoginActivity.this.messageText.setText("your email or password is not correct");
                LoginActivity.this.loadingBar.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void SendUserToDashboard() {
        Intent mainIntent = new Intent(this, DashboardActivity.class);
        mainIntent.addFlags(268468224);
        startActivity(mainIntent);
        finish();
    }
}
