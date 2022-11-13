package ecell.app.ecellteam;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class TeamprofileActivity extends AppCompatActivity {
    Member_Adapter1 adapter;
    /* access modifiers changed from: private */
    public String department1;
    /* access modifiers changed from: private */
    public Spinner deptSpinner;
    /* access modifiers changed from: private */
    public ArrayList<Member> members;
    private RecyclerView recycler_view;

    private void InitializeFields() {
        this.deptSpinner = (Spinner) findViewById(C0250R.C0253id.departmentSpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, C0250R.array.department, 17367048);
        arrayAdapter.setDropDownViewResource(17367050);
        this.deptSpinner.setAdapter(arrayAdapter);
        this.deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String unused = TeamprofileActivity.this.department1 = parent.getItemAtPosition(position).toString();
                if (TeamprofileActivity.this.department1.equals("Please Choose Department")) {
                    TextView errorText = (TextView) TeamprofileActivity.this.deptSpinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(SupportMenu.CATEGORY_MASK);
                    errorText.setText("Please Choose Department");
                }
                TeamprofileActivity.this.loadMembers();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void setupRecyclerView() {
        Member_Adapter1 member_Adapter1 = new Member_Adapter1(this.members);
        this.adapter = member_Adapter1;
        this.recycler_view.setAdapter(member_Adapter1);
        this.recycler_view.setLayoutManager(new LinearLayoutManager(this));
        this.recycler_view.setHasFixedSize(true);
    }

    /* access modifiers changed from: private */
    public void loadMembers() {
        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                TeamprofileActivity.this.members.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = (String) ds.child("name").getValue(String.class);
                    String department = (String) ds.child("department").getValue(String.class);
                    if (department.equals(TeamprofileActivity.this.department1)) {
                        Log.i("reached", department);
                        Member member = new Member();
                        member.setName(name);
                        member.setDepartment(department);
                        TeamprofileActivity.this.members.add(member);
                    }
                }
                TeamprofileActivity.this.setupRecyclerView();
            }

            public void onCancelled(DatabaseError error) {
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_teamprofile);
        this.recycler_view = (RecyclerView) findViewById(C0250R.C0253id.recycler_view);
        this.members = new ArrayList<>();
        InitializeFields();
        ActionBar supportActionBar = getSupportActionBar();
        new ColorDrawable(Color.parseColor("#f58220"));
    }
}
