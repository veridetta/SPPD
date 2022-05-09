package com.inc.vr.corp.apps.sppd.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.inc.vr.corp.apps.sppd.R;
import com.inc.vr.corp.apps.sppd.api.ApiClient;
import com.inc.vr.corp.apps.sppd.model.RiwayatModel;
import com.inc.vr.corp.apps.sppd.model.SptModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SptAdapter extends RecyclerView.Adapter<SptAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<SptModel> contactList;
    private List<SptModel> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_spt_title_id,item_spt_no,item_spt_tugas,
                item_spt_tanggal_pergi,item_spt_tanggal_kembali,item_spt_lama,item_spt_laporan;
        LinearLayout item_spt_kontent;
        CardView item_spt_id;

        public MyViewHolder(View view) {
            super(view);
            item_spt_title_id = view.findViewById(R.id.item_spt_title_id);
            item_spt_no = view.findViewById(R.id.item_spt_no);
            item_spt_tugas = view.findViewById(R.id.item_spt_tugas);
            item_spt_tanggal_pergi = view.findViewById(R.id.item_spt_tanggal_pergi);
            item_spt_tanggal_kembali = view.findViewById(R.id.item_spt_tanggal_kembali);
            item_spt_lama = view.findViewById(R.id.item_spt_lama);
            item_spt_kontent = view.findViewById(R.id.item_spt_kontent);
            item_spt_id = view.findViewById(R.id.item_spt_id);

            item_spt_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //toggle item_spt_content visible
                    if (item_spt_kontent.getVisibility() == View.VISIBLE) {
                        item_spt_kontent.setVisibility(View.GONE);
                    } else {
                        item_spt_kontent.setVisibility(View.VISIBLE);
                    }
                }
            });


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public SptAdapter(Context context, List<SptModel> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spt, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SptModel contact = contactListFiltered.get(position);
        holder.item_spt_title_id.setText(position+1 + " ." + contact.getNo_spt()+"("+contact.getLama()+")");
        holder.item_spt_no.setText(contact.getNo_spt());
        holder.item_spt_tugas.setText(contact.getTugas());
        holder.item_spt_tanggal_pergi.setText(contact.getTgl_pergi());
        holder.item_spt_tanggal_kembali.setText(contact.getTgl_kembali());
        holder.item_spt_lama.setText(contact.getLama());

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<SptModel> filteredList = new ArrayList<>();
                    for (SptModel row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNo_spt().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getNama_pegawai().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<SptModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(SptModel contact);
    }
}
