package ecell.app.ecellteam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.p001ui.database.FirebaseRecyclerAdapter;
import com.firebase.p001ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class InventoryActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ImageView imageView;
    Query query;
    RecyclerView recyclerView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_inventory);
        DatabaseReference child = FirebaseDatabase.getInstance().getReference().child("Posters");
        this.databaseReference = child;
        child.keepSynced(true);
        this.query = this.databaseReference;
        RecyclerView recyclerView2 = (RecyclerView) findViewById(C0250R.C0253id.recyclerView);
        this.recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Poster, PosterHolder>(new FirebaseRecyclerOptions.Builder().setQuery(this.query, Poster.class).build()) {
            public PosterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new PosterHolder(LayoutInflater.from(parent.getContext()).inflate(C0250R.layout.poster_view, parent, false));
            }

            /* access modifiers changed from: protected */
            public void onBindViewHolder(PosterHolder holder, int position, final Poster model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getMessage());
                holder.setImage(InventoryActivity.this.getApplicationContext(), model.getImageUrl());
                holder.button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        InventoryActivity.this.startActivity(new Intent(InventoryActivity.this, ReadMoreActivity.class).putExtra("data", model));
                    }
                });
            }
        };
        adapter.startListening();
        this.recyclerView.setAdapter(adapter);
    }

    public static class PosterHolder extends RecyclerView.ViewHolder {
        MaterialButton button;
        View mView;

        public PosterHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            this.button = (MaterialButton) itemView.findViewById(C0250R.C0253id.readMoreButton);
        }

        public void setTitle(String title) {
            ((TextView) this.mView.findViewById(C0250R.C0253id.card_title)).setText(title);
        }

        public void setDesc(String desc) {
            TextView card_desc = (TextView) this.mView.findViewById(C0250R.C0253id.card_desc);
            if (desc.length() > 100) {
                desc = desc.substring(0, 100) + ".........";
            }
            card_desc.setText(desc);
        }

        public void setImage(Context ctx, String image) {
            Glide.with(ctx).load(image).into((ImageView) this.mView.findViewById(C0250R.C0253id.card_image));
        }
    }
}
