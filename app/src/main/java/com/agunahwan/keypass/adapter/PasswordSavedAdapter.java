package com.agunahwan.keypass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agunahwan.keypass.R;
import com.agunahwan.keypass.entity.PasswordSaved;

import java.util.List;
import java.util.Objects;

/**
 * Created by InspiroPC on 13/09/2015.
 */
public class PasswordSavedAdapter extends BaseAdapter {
    static class Holder {
        TextView nama;
        TextView username;
    }

    private LayoutInflater layoutInflater;
    private List<PasswordSaved> passwordSavedList;

    public PasswordSavedAdapter(Context context, List<PasswordSaved> passwordSavedList) {
        layoutInflater = LayoutInflater.from(context);
        this.passwordSavedList = passwordSavedList;
    }

    @Override
    public int getCount() {
        return passwordSavedList.size();
    }

    @Override
    public Object getItem(int position) {
        return passwordSavedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.single_password, null);

            holder = new Holder();
            holder.nama = (TextView) convertView.findViewById(R.id.nama_password);
            holder.username = (TextView) convertView.findViewById(R.id.username_password);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.nama.setText(passwordSavedList.get(position).getNama());
        holder.username.setText(passwordSavedList.get(position).getUsername());

        return convertView;
    }
}
