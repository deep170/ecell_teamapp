package ecell.app.ecellteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CardView cardView;
    String deptName;
    CardView deptchat;
    ImageView drawer;
    DrawerLayout drawerLayout;
    String email;
    FirebaseUser firebaseUser;
    GridLayout gridLayout;
    String groupName = "Full Team Chat";
    View hview;
    FirebaseAuth mAuth;
    TextView name;
    NavigationView navigationView;
    DatabaseReference rootRef;
    CardView teamchat;
    String userId;
    String userName;

    public void activity(View view) {
        switch (view.getId()) {
            case C0250R.C0253id.announcement1 /*2131361872*/:
                startActivity(new Intent(this, AnnouncementActivity.class));
                return;
            case C0250R.C0253id.deparment /*2131361950*/:
                Intent deptChatIntent = new Intent(this, DeptchatActivity.class);
                deptChatIntent.putExtra("deptName", this.deptName);
                deptChatIntent.putExtra("userName", this.userName);
                startActivity(deptChatIntent);
                Toast.makeText(this, "Welocome to Deparment chat", 0).show();
                return;
            case C0250R.C0253id.inventory /*2131362035*/:
                startActivity(new Intent(this, InventoryActivity.class));
                Toast.makeText(this, "Welocome to Inventory", 0).show();
                return;
            case C0250R.C0253id.leaderboard /*2131362052*/:
                startActivity(new Intent(this, LeaderboardActivity.class));
                Toast.makeText(this, "Welcome to LEADERBOARD", 0).show();
                return;
            case C0250R.C0253id.logout /*2131362067*/:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LrAcitivity.class));
                Toast.makeText(this, "You Are Loged Out", 0).show();
                return;
            case C0250R.C0253id.teamchat1 /*2131362238*/:
                Toast.makeText(this, "Welocome to Teamchat", 0).show();
                Intent fullteamchatIntent = new Intent(this, FullteamchatActivity.class);
                fullteamchatIntent.putExtra("groupName", this.groupName);
                fullteamchatIntent.putExtra("userName", this.userName);
                startActivity(fullteamchatIntent);
                return;
            case C0250R.C0253id.teamprofile /*2131362239*/:
                startActivity(new Intent(this, TeamprofileActivity.class));
                Toast.makeText(this, "Welocome to Team Profile", 0).show();
                return;
            case C0250R.C0253id.work /*2131362301*/:
                startActivity(new Intent(this, CurrentworkActivity.class));
                Toast.makeText(this, "Welocome to Current work", 0).show();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_dashboard);
        this.drawerLayout = (DrawerLayout) findViewById(C0250R.C0253id.drawer_layout);
        this.navigationView = (NavigationView) findViewById(C0250R.C0253id.nav_view);
        this.drawer = (ImageView) findViewById(C0250R.C0253id.drawer);
        this.gridLayout = (GridLayout) findViewById(C0250R.C0253id.gridlayout);
        FirebaseAuth instance = FirebaseAuth.getInstance();
        this.mAuth = instance;
        this.firebaseUser = instance.getCurrentUser();
        this.rootRef = FirebaseDatabase.getInstance().getReference().child("Users");
        this.deptchat = (CardView) findViewById(C0250R.C0253id.deparment);
        this.teamchat = (CardView) findViewById(C0250R.C0253id.teamchat1);
        View headerView = this.navigationView.getHeaderView(0);
        this.hview = headerView;
        this.name = (TextView) headerView.findViewById(C0250R.C0253id.name);
        this.deptchat.setEnabled(false);
        this.teamchat.setEnabled(false);
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            this.userId = firebaseUser2.getUid();
            this.email = this.firebaseUser.getEmail();
        } else {
            startActivity(new Intent(this, LrAcitivity.class));
        }
        getUserDept();
        nevigationdrawer();
    }

    private void getUserDept() {
        this.rootRef.child(this.userId).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DashboardActivity.this.deptName = snapshot.child("department").getValue().toString();
                    DashboardActivity.this.userName = snapshot.child("name").getValue().toString();
                    DashboardActivity.this.name.setText(DashboardActivity.this.userName + "\n" + DashboardActivity.this.deptName + "\n" + DashboardActivity.this.email);
                    DashboardActivity.this.deptchat.setEnabled(true);
                    DashboardActivity.this.teamchat.setEnabled(true);
                }
            }

            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void nevigationdrawer() {
        this.navigationView.bringToFront();
        this.navigationView.setNavigationItemSelectedListener(this);
        this.navigationView.setCheckedItem((int) C0250R.C0253id.nav_home);
        this.drawer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (DashboardActivity.this.drawerLayout.isDrawerVisible((int) GravityCompat.START)) {
                    DashboardActivity.this.drawerLayout.closeDrawer((int) GravityCompat.START);
                } else {
                    DashboardActivity.this.drawerLayout.openDrawer((int) GravityCompat.START);
                }
            }
        });
    }

    public void onBackPressed() {
        if (this.drawerLayout.isDrawerVisible((int) GravityCompat.START)) {
            this.drawerLayout.closeDrawer((int) GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case C0250R.C0253id.Leadership /*2131361797*/:
                startActivity(new Intent(this, LeaderboardActivity.class));
                Toast.makeText(this, "Welcome to LEADERBOARD", 0).show();
                break;
            case C0250R.C0253id.Logout /*2131361798*/:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LrAcitivity.class));
                Toast.makeText(this, "You Are Loged Out", 0).show();
                break;
            case C0250R.C0253id.announcement1 /*2131361872*/:
                startActivity(new Intent(this, AnnouncementActivity.class));
                Toast.makeText(this, "Welcome to announcement", 0).show();
                break;
            case C0250R.C0253id.teamchat1 /*2131362238*/:
                startActivity(new Intent(this, TeamprofileActivity.class));
                Toast.makeText(this, "Welocome to Teamprofile", 0).show();
                break;
        }
        this.drawerLayout.closeDrawer((int) GravityCompat.START);
        return true;
    }
}
