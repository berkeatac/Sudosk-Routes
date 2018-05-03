package com.berkeatac.sudoskroutes.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.berkeatac.sudoskroutes.R;
import com.berkeatac.sudoskroutes.RouteListClickListener;
import com.berkeatac.sudoskroutes.Model.RouteObject;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.ViewHolder> {
    private List<RouteObject> mDataset;
    private static RouteListClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public CardView mCardView;
        public TextView routeNameTextView;
        public TextView routeCreatorTextView;
        public TextView routeDateTextView;
        public ImageView routeImageView;
        public ViewHolder(CardView v, TextView tv, ImageView iv, TextView creatorTv, TextView dateTv) {
            super(v);
            mCardView = v;
            routeNameTextView = tv;
            routeImageView = iv;
            routeCreatorTextView = creatorTv;
            routeDateTextView = dateTv;

            mCardView.setOnClickListener(view -> listener.recyclerViewListClicked(view,getLayoutPosition()));
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RouteListAdapter(List<RouteObject> myDataset, RouteListClickListener itemListener) {
        mDataset = myDataset;
        listener = itemListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RouteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_list_item, parent, false);
        TextView routeNameTextView = v.findViewById(R.id.name);
        TextView routeCreatorTextView = v.findViewById(R.id.creator);
        TextView routeCreationDate = v.findViewById(R.id.date);
        ImageView routeImageView = v.findViewById(R.id.imageSmall);
        ViewHolder vh = new ViewHolder(v, routeNameTextView, routeImageView, routeCreatorTextView,
                routeCreationDate);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.routeNameTextView.setText(mDataset.get(position).getName());
        holder.routeCreatorTextView.setText(mDataset.get(position).getCreator());
        holder.routeDateTextView.setText(mDataset.get(position).getDate());
        Picasso.get().load(mDataset.get(position).getImageUrl()).placeholder(R.drawable.placeholder_mtn).into(holder.routeImageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
