package ecell.app.ecellteam;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardActivity extends AppCompatActivity {
    MemberAdapter adapter;
    /* access modifiers changed from: private */
    public ArrayList<Member> members;
    private RecyclerView recycler_view;
    private Toolbar toolbar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_leaderboard);
        this.toolbar = (Toolbar) findViewById(C0250R.C0253id.toolbar);
        this.recycler_view = (RecyclerView) findViewById(C0250R.C0253id.recycler_view);
        this.members = new ArrayList<>();
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f58220")));
        loadMembers();
    }

    /* access modifiers changed from: private */
    public void setupRecyclerView() {
        MemberAdapter memberAdapter = new MemberAdapter(this.members);
        this.adapter = memberAdapter;
        this.recycler_view.setAdapter(memberAdapter);
        this.recycler_view.setLayoutManager(new LinearLayoutManager(this));
        this.recycler_view.setHasFixedSize(true);
    }

    private void loadMembers() {
        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                LeaderboardActivity.this.members.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = (String) ds.child("name").getValue(String.class);
                    Log.i("name", name);
                    String department = (String) ds.child("department").getValue(String.class);
                    Log.i("department", department);
                    Integer score = (Integer) ds.child("LeaderBoardScore").getValue(Integer.class);
                    if (score == null) {
                        ds.child("LeaderBoardScore").getRef().setValue(0);
                        score = 0;
                    }
                    Log.i("score", score.toString());
                    Member member = new Member();
                    member.setName(name);
                    member.setDepartment(department);
                    member.setScore(score.intValue());
                    LeaderboardActivity.this.members.add(member);
                }
                Collections.sort(LeaderboardActivity.this.members, Collections.reverseOrder());
                LeaderboardActivity.this.setupRecyclerView();
                LeaderboardActivity.this.showChampions();
            }

            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void showChampions() {
    }
}
