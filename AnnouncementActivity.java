package ecell.app.ecellteam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Iterator;

public class AnnouncementActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    Button button;

    /* renamed from: db */
    FirebaseFirestore f0db;
    private ListView listItems;

    /* renamed from: tv */
    TextView f1tv;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_announcement);
        this.listItems = (ListView) findViewById(C0250R.C0253id.listView1);
        this.button = (Button) findViewById(C0250R.C0253id.button);
        this.arrayList = new ArrayList<>();
        C00001 r0 = new ArrayAdapter<String>(this, 17367043, this.arrayList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                AnnouncementActivity.this.f1tv = (TextView) view.findViewById(16908308);
                AnnouncementActivity.this.f1tv.setTextColor(-1);
                AnnouncementActivity.this.f1tv.setTypeface(AnnouncementActivity.this.f1tv.getTypeface(), 3);
                AnnouncementActivity.this.f1tv.setTextSize(1, 20.0f);
                AnnouncementActivity.this.f1tv.setPadding(30, 0, 30, 40);
                return view;
            }
        };
        this.arrayAdapter = r0;
        this.listItems.setAdapter(r0);
        this.f0db = FirebaseFirestore.getInstance();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        FirebaseFirestore.getInstance().collection("Information").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    AnnouncementActivity.this.arrayList.clear();
                    Iterator<QueryDocumentSnapshot> it = task.getResult().iterator();
                    while (it.hasNext()) {
                        QueryDocumentSnapshot doc = it.next();
                        AnnouncementActivity.this.arrayList.add(doc.getString("announcement"));
                        Log.d("Document", doc.getId() + "=>" + doc.getData());
                    }
                    AnnouncementActivity.this.arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
