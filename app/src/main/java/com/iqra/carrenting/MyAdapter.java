package com.iqra.carrenting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyAdapter extends FirebaseRecyclerAdapter<model, MyAdapter.myviewmodel> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapter.myviewmodel holder, int position, @NonNull model model) {

        holder.caption.setText(model.getCaptions());

    }

    @NonNull
    @Override
    public myviewmodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_item_list, parent,false);
        return new myviewmodel(view);
    }

    public class myviewmodel extends RecyclerView.ViewHolder {

        TextView caption;

        public myviewmodel(@NonNull View itemView) {
            super(itemView);
            caption = itemView.findViewById(R.id.textView_caption);
        }
    }
}
