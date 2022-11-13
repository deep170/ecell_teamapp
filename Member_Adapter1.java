package ecell.app.ecellteam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Member_Adapter1 extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<Member> membersList = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        public TextView tv_department;
        public TextView tv_name;

        public ViewHolder(View v) {
            super(v);
            this.layout = v;
            this.tv_name = (TextView) v.findViewById(C0250R.C0253id.tv_name);
            this.tv_department = (TextView) v.findViewById(C0250R.C0253id.tv_department);
        }
    }

    public Member_Adapter1(ArrayList<Member> members) {
        this.membersList = members;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0250R.layout.name_members, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Member member = this.membersList.get(position);
        holder.tv_name.setText(member.getName());
        holder.tv_department.setText(member.getDepartment());
    }

    public int getItemCount() {
        return this.membersList.size();
    }
}
