package com.equipments.Utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.equipments.GettersSetters.CardGetSet;
import com.equipments.R;

import java.util.List;

/**
 * Created by Administrator on 15/06/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{
List<CardGetSet> data;
    static OnItemClickListener listener;

    public RVAdapter(List<CardGetSet> data) {
        this.data = data;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview, parent, false);
        return    new PersonViewHolder(v);

    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.Institute.setText(data.get(position).IntituteType);
        holder.Id.setText(data.get(position).Id);
        holder.EQuipment.setText(data.get(position).Machineryname);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public  class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView EQuipment;
        TextView Institute;
        TextView Id;


        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            Institute = (TextView) itemView.findViewById(R.id.tvInstitute);
            EQuipment = (TextView) itemView.findViewById(R.id.tvEquipment);
            Id = (TextView) itemView.findViewById(R.id.tvId);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (listener!=null)
                listener.onItemClick(Id.getText().toString());
        }


    }



        public interface OnItemClickListener{
            public void onItemClick(String Id);
        }


    public void setOnItemClickListener(final OnItemClickListener listener){
        this.listener = listener;
    }

}