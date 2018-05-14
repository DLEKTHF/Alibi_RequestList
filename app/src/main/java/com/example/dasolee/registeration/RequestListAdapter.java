package com.example.dasolee.registeration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RequestListAdapter extends BaseAdapter {

    private Context context;
    private List<Request> requestList;

    public RequestListAdapter(Context context, List<Request> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @Override
    public int getCount() {
        return requestList.size();
    }

    @Override
    public Object getItem(int position) {
        return requestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.request, null);
        TextView requestTitle = (TextView) v.findViewById(R.id.requestTitle);
        TextView requestContent = (TextView) v.findViewById(R.id.requestContent);


        requestTitle.setText(requestList.get(position).getTitle());
        requestContent.setText(requestList.get(position).getContent());

        v.setTag(requestList.get(position).getTitle());
        return v;
    }
}
