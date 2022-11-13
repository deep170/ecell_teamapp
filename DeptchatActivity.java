package ecell.app.ecellteam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class DeptchatActivity extends AppCompatActivity {
    private String currentDate;
    private String currentTime;
    private String currentUserId;
    /* access modifiers changed from: private */
    public String currentUserName;
    private String groupChatName;
    private DatabaseReference groupMessageKeyRef;
    private DatabaseReference groupNameReference;
    private LinearLayout layout;
    private FirebaseAuth mAuth;
    private Toolbar mToolBar;
    /* access modifiers changed from: private */
    public EditText messageArea;
    /* access modifiers changed from: private */
    public ScrollView scrollView;
    private ImageView sendButton;
    private DatabaseReference userReference;
    private String username;

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
        setContentView((int) C0250R.layout.activity_deptchat);
        getWindow().setSoftInputMode(3);
        this.groupChatName = getIntent().getExtras().get("deptName").toString();
        this.username = getIntent().getExtras().get("userName").toString();
        FirebaseAuth instance = FirebaseAuth.getInstance();
        this.mAuth = instance;
        this.currentUserId = instance.getCurrentUser().getUid();
        this.userReference = FirebaseDatabase.getInstance().getReference().child("Users");
        this.groupNameReference = FirebaseDatabase.getInstance().getReference().child("Groups").child(this.groupChatName);
        InitializeFielda();
        getUserInfo();
        this.sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DeptchatActivity.this.saveMessageInToDatabase();
                DeptchatActivity.this.messageArea.setText("");
                DeptchatActivity.this.scrollView.fullScroll(130);
            }
        });
        this.groupNameReference.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                if (snapshot.exists()) {
                    DeptchatActivity.this.DisplayMessages(snapshot);
                }
            }

            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
            }

            public void onChildRemoved(DataSnapshot snapshot) {
            }

            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
            }

            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void addMessageBox(String messageFormat, int key) {
        TextView textView = new TextView(this);
        textView.setTextSize(18.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText(messageFormat);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
        lp.setMargins(15, 15, 15, 15);
        textView.setLayoutParams(lp);
        if (key == 1) {
            lp.gravity = 5;
            textView.setBackgroundResource(C0250R.C0252drawable.rounded_corner1);
        } else {
            textView.setBackgroundResource(C0250R.C0252drawable.rounded_corner2);
        }
        this.layout.addView(textView);
        this.scrollView.fullScroll(130);
    }

    /* access modifiers changed from: private */
    public void DisplayMessages(DataSnapshot snapshot) {
        Iterator iterator = snapshot.getChildren().iterator();
        while (iterator.hasNext()) {
            String str = (String) iterator.next().getValue();
            String chatMessage = (String) iterator.next().getValue();
            String chatName = (String) iterator.next().getValue();
            String chatTime = (String) iterator.next().getValue();
            String myMsgFormat = "You :-  " + chatTime + "\n\n" + chatMessage;
            String otherMsgFormat = chatName + "  " + chatTime + ":-\n\n" + chatMessage;
            if (chatName.equals(this.username)) {
                addMessageBox(myMsgFormat, 1);
            } else {
                addMessageBox(otherMsgFormat, 2);
            }
        }
        this.scrollView.post(new Runnable() {
            public void run() {
                DeptchatActivity.this.scrollView.fullScroll(130);
            }
        });
    }

    /* access modifiers changed from: private */
    public void saveMessageInToDatabase() {
        String message = this.messageArea.getText().toString();
        String messageKey = this.groupNameReference.push().getKey();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please Write Something", 0).show();
            return;
        }
        this.currentDate = new SimpleDateFormat("MMM dd, yyyy").format(Calendar.getInstance().getTime());
        this.currentTime = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
        this.groupNameReference.updateChildren(new HashMap<>());
        this.groupMessageKeyRef = this.groupNameReference.child(messageKey);
        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("name", this.currentUserName);
        messageInfoMap.put("message", message);
        messageInfoMap.put("date", this.currentDate);
        messageInfoMap.put("time", this.currentTime);
        this.groupMessageKeyRef.updateChildren(messageInfoMap);
    }

    private void getUserInfo() {
        this.userReference.child(this.currentUserId).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String unused = DeptchatActivity.this.currentUserName = snapshot.child("name").getValue().toString();
                }
            }

            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void InitializeFielda() {
        Toolbar toolbar = (Toolbar) findViewById(C0250R.C0253id.group_chat_bar_layout);
        this.mToolBar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle((CharSequence) this.groupChatName);
        this.layout = (LinearLayout) findViewById(C0250R.C0253id.layout1);
        this.sendButton = (ImageView) findViewById(C0250R.C0253id.sendButton);
        this.messageArea = (EditText) findViewById(C0250R.C0253id.messageArea);
        this.scrollView = (ScrollView) findViewById(C0250R.C0253id.scrollView);
    }
}
