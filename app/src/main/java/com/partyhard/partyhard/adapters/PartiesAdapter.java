package com.partyhard.partyhard.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.partyhard.partyhard.MainActivity;
import com.partyhard.partyhard.R;
import com.partyhard.partyhard.domain.Party;
import com.partyhard.partyhard.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PartiesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Party> parties;

    public PartiesAdapter(Context c, ArrayList<Party> _parties) {
        mContext = c;
        parties = _parties;
    }

    public void addListItemToAdapter(List<Party> list) {
        parties.addAll(list);
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return parties.size();
    }

    @Override
    public Object getItem(int position) {
        return parties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.party, null);
        TextView title = (TextView)v.findViewById(R.id.party_title);
        title.setText(parties.get(position).getTitle());
        CircleImageView imageView = (CircleImageView)v.findViewById(R.id.party_img);
        Glide.with(mContext)
                .load(parties.get(position).getImage())
                .crossFade()
                .into(imageView);
        TextView username = (TextView)v.findViewById(R.id.party_user_name);
        username.setText(parties.get(position).getUser().getUsername());
        CircleImageView avatar = (CircleImageView) v.findViewById(R.id.party_user_av);
        Glide.with(mContext)
                .load(parties.get(position).getUser().getAvatar())
                .into(avatar);
        TextView description = (TextView)v.findViewById(R.id.party_description);
        description.setText(parties.get(position).getDescription());
        v.setTag(parties.get(position).getId());
        return v;



    }
}
