package ecell.app.ecellteam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    public boolean isOnline() {
        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            Log.d("Network", "Connected");
            return true;
        }
        Log.d("Network", "Not Connected");
        return false;
    }

    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Kindly, Turn On Internet Connection To Continue");
        builder.setNegativeButton("EXIT APP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_splash);
        getWindow().setFlags(1024, 1024);
        if (isOnline()) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, DashboardActivity.class).addFlags(32768));
                        SplashActivity.this.finish();
                        return;
                    }
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, LrAcitivity.class));
                    SplashActivity.this.finish();
                }
            }, 3000);
        } else {
            checkNetworkConnection();
        }
    }
}
