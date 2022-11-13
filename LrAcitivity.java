package ecell.app.ecellteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LrAcitivity extends AppCompatActivity {
    private Button loginBtn;
    private Button registerBtn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_lr_acitivity);
        this.loginBtn = (Button) findViewById(C0250R.C0253id.loginButton);
        this.registerBtn = (Button) findViewById(C0250R.C0253id.registerButton);
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LrAcitivity.this.startActivity(new Intent(LrAcitivity.this, LoginActivity.class));
            }
        });
        this.registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LrAcitivity.this.startActivity(new Intent(LrAcitivity.this, RegisterActivity.class));
            }
        });
    }

    public void onBackPressed() {
        finishAffinity();
    }
}
