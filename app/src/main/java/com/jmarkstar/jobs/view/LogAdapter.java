package com.jmarkstar.jobs.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmarkstar.jobs.R;
import com.jmarkstar.jobs.model.LogModel;
import java.util.List;

/**
 * Created by jmarkstar on 14/01/2018.
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogVH> {

    private List<LogModel> logs;

    public void addList( List<LogModel> logs){
        this.logs = logs;
        notifyDataSetChanged();
    }

    @Override public LogVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new LogVH(v);
    }

    @Override public void onBindViewHolder(LogVH holder, int position) {
        holder.bind();
    }

    @Override public int getItemCount() {
        return logs == null?0:logs.size();
    }

    public class LogVH extends RecyclerView.ViewHolder{
        TextView tvMessage;
        public LogVH(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }

        public void bind(){
            tvMessage.setText(logs.get(getAdapterPosition()).message);
        }
    }
}
