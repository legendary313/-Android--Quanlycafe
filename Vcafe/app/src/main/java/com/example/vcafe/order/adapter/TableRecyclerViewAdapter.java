package com.example.vcafe.order.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcafe.R;
import com.example.vcafe.man_hinh_chinh;
import com.example.vcafe.order.model.Table;

import java.util.ArrayList;
import java.util.List;



public class TableRecyclerViewAdapter  extends RecyclerView.Adapter<TableRecyclerViewAdapter.TableViewHolder> {
    private List<Table> tables=new ArrayList<>();
    private Context context;
    private  TableRecyclerViewAdapter.OnItemTableClickListener onItemTableClickListener;

    public TableRecyclerViewAdapter(List<Table> tables, Context context,  TableRecyclerViewAdapter.OnItemTableClickListener onItemTableClickListener) {
        this.tables = tables;
        this.context = context;
        this.onItemTableClickListener = onItemTableClickListener;
    }
    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView;
        RecyclerView.ViewHolder viewHolder=null;
        itemView=inflater.inflate(R.layout.table_item,parent,false);

        viewHolder=new  TableViewHolder(itemView,onItemTableClickListener);

        return  (TableViewHolder)viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        Table table=tables.get(position);
        holder.txtName.setText(table.getName());
        holder.txtChange.setText("Nhân viên : "+table.getLastChange());
        int status=table.getStatus();
        if(status== Table.STATUS_AVAILABLE) {
            int color= ContextCompat.getColor(context, R.color.tableAvailabe);
            holder.cvBackground.setCardBackgroundColor(color);
          //  holder.cvBackground.setBackgroundColor(color);
        } else if(status==Table.STATUS_PROCESSING){
            int color=ContextCompat.getColor(context , R.color.tableProccessing);
            holder.cvBackground.setCardBackgroundColor(color);
        } else if(status==Table.STATUS_UNFINISHED){
            if(!table.getLastChange().equals(man_hinh_chinh.nhanviensudung)){
                int color=ContextCompat.getColor(context , R.color.tableAnotherChange);
                holder.cvBackground.setCardBackgroundColor(color);
                return;
            }
            int color=ContextCompat.getColor(context , R.color.tableUnfinished);
            holder.cvBackground.setCardBackgroundColor(color);
        }
    }



    @Override
    public int getItemCount() {
        return tables.size();
    }



    public class TableViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener  {
        public TextView txtName,txtChange;
        public CardView cvBackground;

        private OnItemTableClickListener onItemTableClickListener;

        public TableViewHolder(@NonNull View itemView,OnItemTableClickListener onItemTableClickListener) {
            super(itemView);
            txtName=(TextView)itemView.findViewById(R.id.txtv_table_name);
            txtChange=(TextView)itemView.findViewById(R.id.txtv_table_last_change);
            cvBackground=(CardView)itemView.findViewById(R.id.cv_table_content);
            this.onItemTableClickListener=onItemTableClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }




        @Override
        public void onClick(View view) {
            onItemTableClickListener.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {

            onItemTableClickListener.onLongClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnItemTableClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }


}
