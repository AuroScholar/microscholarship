package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auro.scholr.R;
import com.auro.scholr.home.data.model.NavItemModel;
import com.auro.scholr.util.TextUtil;

import java.util.ArrayList;

public class DrawerListAdapter  extends BaseAdapter {

    Context mContext;
    ArrayList<NavItemModel> mNavItems;

    public DrawerListAdapter(Context context, ArrayList<NavItemModel> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    public void udpateList(ArrayList<NavItemModel> navItems) {
        mNavItems = navItems;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item_layout, null);
        } else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText(mNavItems.get(position).getmTitle());
        if (!TextUtil.isEmpty(mNavItems.get(position).getmSubtitle())) {
            subtitleView.setText(mNavItems.get(position).getmSubtitle());
        } else {
            subtitleView.setVisibility(View.GONE);
        }
        iconView.setImageResource(mNavItems.get(position).getmIcon());

        return view;
    }
}
