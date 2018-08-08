package com.partyhard.partyhard.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.partyhard.partyhard.R;
import com.partyhard.partyhard.domain.Party;

import java.util.ArrayList;
import java.util.List;

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
        TextView description = (TextView)v.findViewById(R.id.party_description);
        TextView username = (TextView)v.findViewById(R.id.party_user);
        //CircleImageView avatar = (CircleImageView) v.findViewById(R.id.sAvatar);
        title.setText(parties.get(position).getTitle());
        description.setText(parties.get(position).getDescription());
        username.setText(parties.get(position).getUsername());
        /*String image = Stickers.get(position).getStickerImage();
        URL imageUrl = null;
        try {
            imageUrl = new URL(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapDrawable background = new BitmapDrawable(mContext.getResources(), bitmap);
        relativeLayout.setBackgroundDrawable(background);*/
        v.setTag(parties.get(position).getId());
        return v;



    }
}
