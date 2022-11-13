package ecell.app.ecellteam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadMoreActivity extends AppCompatActivity {
    String desc;
    TextView descView;
    String imageUrl;
    ImageView imageView;
    File localFile = null;
    FirebaseAuth mAuth;
    String title;
    TextView titleView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_read_more);
        getPermissions();
        this.mAuth = FirebaseAuth.getInstance();
        Poster poster = (Poster) getIntent().getSerializableExtra("data");
        this.title = poster.getTitle();
        this.imageUrl = poster.getImageUrl();
        this.desc = poster.getMessage();
        this.imageView = (ImageView) findViewById(C0250R.C0253id.readMoreImage);
        this.titleView = (TextView) findViewById(C0250R.C0253id.readMoreTitle);
        this.descView = (TextView) findViewById(C0250R.C0253id.readMoreDesc);
        this.titleView.setText(this.title);
        this.descView.setText(this.desc + "\n\n\n\n\n\n\n\n\n\n\n\n");
        Glide.with(getApplicationContext()).load(this.imageUrl).into(this.imageView);
        ((MaterialButton) findViewById(C0250R.C0253id.shareButton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final ProgressDialog dialog = ProgressDialog.show(ReadMoreActivity.this, "", "Loading. Please wait...", true);
                StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(ReadMoreActivity.this.imageUrl);
                String suffix = ReadMoreActivity.this.imageUrl;
                Matcher matcher = Pattern.compile("%2F(.*?)\\?", 32).matcher(suffix);
                while (matcher.find()) {
                    suffix = matcher.group(1);
                }
                String suffix2 = suffix.split("\\.")[1];
                ReadMoreActivity.this.localFile = new File(ReadMoreActivity.this.getExternalFilesDir((String) null), "yoo." + suffix2);
                String str = suffix2;
                httpsReference.getFile(ReadMoreActivity.this.localFile).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Uri imageUri = FileProvider.getUriForFile(ReadMoreActivity.this, ReadMoreActivity.this.getApplicationContext().getPackageName() + ".provider", ReadMoreActivity.this.localFile);
                        dialog.dismiss();
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("image/*");
                        intent.setFlags(1);
                        intent.putExtra("android.intent.extra.STREAM", imageUri);
                        intent.putExtra("android.intent.extra.TEXT", ReadMoreActivity.this.desc);
                        ReadMoreActivity.this.startActivity(Intent.createChooser(intent, "Share Poster"));
                    }
                }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
                    public void onFailure(Exception exception) {
                    }
                });
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (this.mAuth.getCurrentUser() == null) {
            this.mAuth.signInWithEmailAndPassword("demo@gmail.com", "pra@123");
        }
    }

    public void shareImage(View view) throws IOException {
    }

    private void getPermissions() {
        String externalReadPermission = "android.permission.READ_EXTERNAL_STORAGE".toString();
        String externalWritePermission = "android.permission.WRITE_EXTERNAL_STORAGE".toString();
        if (ContextCompat.checkSelfPermission(this, externalReadPermission) != 0 && ContextCompat.checkSelfPermission(this, externalWritePermission) != 0 && Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{externalReadPermission, externalWritePermission}, 101);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0) {
            if (grantResults[0] == 0) {
                Toast.makeText(this, "Permission Granted", 0).show();
            } else {
                Toast.makeText(this, "Please allow permissions", 0).show();
            }
        }
    }
}
