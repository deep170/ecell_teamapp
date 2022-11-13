package ecell.app.ecellteam;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import java.util.Iterator;

public class CurrentworkActivityShow extends AppCompatActivity {
    DatabaseReference databaseReference;
    String dateShow;
    TextView dateTextView;
    String dategiven;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference first;
    TextView headingTextView;
    ImageView imageView;
    ScrollView scrollView;
    TextView taskTextView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_currentwork_show);
        this.scrollView = (ScrollView) findViewById(C0250R.C0253id.scrollView);
        this.headingTextView = (TextView) findViewById(C0250R.C0253id.headingTextView);
        this.imageView = (ImageView) findViewById(C0250R.C0253id.imageView);
        this.taskTextView = (TextView) findViewById(C0250R.C0253id.taskTextView);
        this.dateTextView = (TextView) findViewById(C0250R.C0253id.dateTextView);
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        this.firebaseDatabase = instance;
        this.databaseReference = instance.getReference();
        String stringExtra = getIntent().getStringExtra("dateShow");
        this.dateShow = stringExtra;
        if (stringExtra.contains(",")) {
            this.dategiven = "date";
        } else {
            this.dategiven = "date1";
        }
        FirebaseFirestore.getInstance().collection("Current").whereEqualTo(this.dategiven, (Object) this.dateShow).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Iterator<QueryDocumentSnapshot> it = task.getResult().iterator();
                    while (it.hasNext()) {
                        QueryDocumentSnapshot doc = it.next();
                        String mess = doc.getString("task");
                        CurrentworkActivityShow.this.headingTextView.setText(doc.getString("heading"));
                        CurrentworkActivityShow.this.dateTextView.setText(CurrentworkActivityShow.this.dateShow);
                        CurrentworkActivityShow currentworkActivityShow = CurrentworkActivityShow.this;
                        currentworkActivityShow.first = currentworkActivityShow.databaseReference.child("images").child(CurrentworkActivityShow.this.dateShow);
                        CurrentworkActivityShow.this.first.addValueEventListener(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Picasso.get().load((String) dataSnapshot.getValue(String.class)).into(CurrentworkActivityShow.this.imageView);
                            }

                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        CurrentworkActivityShow.this.taskTextView.setText(mess);
                    }
                }
                if (task.getResult().isEmpty()) {
                    CurrentworkActivityShow.this.taskTextView.setText("There is no task allotted for this date");
                    CurrentworkActivityShow.this.dateTextView.setText(CurrentworkActivityShow.this.dateShow);
                }
            }
        });
    }
}
