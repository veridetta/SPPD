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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.inc.vr.corp.apps.sppd.R;
import com.inc.vr.corp.apps.sppd.api.ApiClient;
import com.inc.vr.corp.apps.sppd.model.RiwayatModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<RiwayatModel> contactList;
    private List<RiwayatModel> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_sppd_title;
        EditText item_pd_nama,item_pd_no_sppd,item_pd_tugas,item_pd_tujuan;
        public Button btnHapus, btnUbah, btnSimpan;
        LinearLayout item_spt_kontent;
        CardView item_spt_id;

        public MyViewHolder(View view) {
            super(view);
            item_sppd_title = view.findViewById(R.id.item_spt_title_id);
            item_pd_nama = view.findViewById(R.id.item_pd_nama);
            item_pd_no_sppd = view.findViewById(R.id.item_pd_no_sppd);
            item_pd_tugas = view.findViewById(R.id.item_pd_tugas);
            item_pd_tujuan = view.findViewById(R.id.item_pd_tujuan);

            btnHapus = view.findViewById(R.id.btn_hapus);
            btnUbah = view.findViewById(R.id.btn_edit);
            btnSimpan = view.findViewById(R.id.btn_simpan);

            item_spt_kontent = view.findViewById(R.id.item_spt_kontent);

            item_spt_id = view.findViewById(R.id.item_spt_id);

            item_pd_nama.setEnabled(false);
            item_pd_no_sppd.setEnabled(false);
            item_pd_tugas.setEnabled(false);
            item_pd_tujuan.setEnabled(false);

            final Boolean[] bisa_simpan = {false};
            btnSimpan.setAlpha(0.5f);

            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bisa_simpan[0]){
                        bisa_simpan[0] = false;
                        btnSimpan.setAlpha(0.5f);
                        btnUbah.setText("Ubah");
                        item_pd_tugas.setEnabled(false);
                    }else{
                        item_pd_tugas.setEnabled(true);
                        bisa_simpan[0] = true;
                        btnSimpan.setAlpha(1f);
                        btnUbah.setText("Cancel");
                    }
                }
            });

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

            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    //dialog from default
                    dialog.setTitle("Loading");
                    dialog.setCancelable(false);
                    dialog.show();
                    //show confirm dialog before delete
                    ApiClient client = new ApiClient();
                    client.getServies().hapus_riwayat("android",
                            contactList.get(getAdapterPosition()).getId_nppt()).enqueue(new retrofit2.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.isSuccessful()) {
                                contactList.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(context, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            });

            btnSimpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show dialog loading
                    final Dialog dialog = new Dialog(context);
                    //dialog from default
                    dialog.setTitle("Loading");
                    dialog.setCancelable(false);
                    dialog.show();
                    ApiClient client = new ApiClient();
                    client.getServies().ubah_riwayat("android",
                            contactList.get(getAdapterPosition()).getId_nppt(),
                            item_pd_tujuan.getText().toString())
                            .enqueue(new retrofit2.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                if(bisa_simpan[0]){
                                    bisa_simpan[0] = false;
                                    btnSimpan.setAlpha(0.5f);
                                    btnUbah.setText("Ubah");
                                    item_pd_tugas.setEnabled(false);
                                }else{
                                    item_pd_tugas.setEnabled(true);
                                    bisa_simpan[0] = true;
                                    btnSimpan.setAlpha(1f);
                                    btnUbah.setText("Cancel");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(context, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
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


    public RiwayatAdapter(Context context, List<RiwayatModel> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data_riwayat_perjalanan, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final RiwayatModel contact = contactListFiltered.get(position);
        holder.item_sppd_title.setText(position + " ." + contact.getNo_sppd());
        holder.item_pd_nama.setText(contact.getNama_pegawai());
        holder.item_pd_tugas.setText(contact.getTugas());
        holder.item_pd_tujuan.setText(contact.getTujuan());

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
                    List<RiwayatModel> filteredList = new ArrayList<>();
                    for (RiwayatModel row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNo_sppd().toLowerCase().contains(charString.toLowerCase()) ||
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
                contactListFiltered = (ArrayList<RiwayatModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(RiwayatModel contact);
    }
}
