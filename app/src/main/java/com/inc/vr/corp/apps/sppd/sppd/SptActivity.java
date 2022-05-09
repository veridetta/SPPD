package com.inc.vr.corp.apps.sppd.sppd;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.inc.vr.corp.apps.sppd.Adapter.RiwayatAdapter;
import com.inc.vr.corp.apps.sppd.Adapter.SptAdapter;
import com.inc.vr.corp.apps.sppd.MainActivity;
import com.inc.vr.corp.apps.sppd.R;
import com.inc.vr.corp.apps.sppd.api.ApiClient;
import com.inc.vr.corp.apps.sppd.model.ResponseRiwayatModel;
import com.inc.vr.corp.apps.sppd.model.ResponseSptModel;
import com.inc.vr.corp.apps.sppd.model.RiwayatModel;
import com.inc.vr.corp.apps.sppd.model.SptModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SptActivity extends AppCompatActivity
        implements SptAdapter.ContactsAdapterListener {

    private static final String TAG = com.inc.vr.corp.apps.sppd.sppd.SptActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<SptModel> sptList;
    private SptAdapter mAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spt);

        ActionBar toolbar = getSupportActionBar();

        // toolbar fancy stuff
        getSupportActionBar().setTitle("Data SPT");
        //requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        recyclerView = findViewById(R.id.rc_spt);
        sptList = new ArrayList<>();
        mAdapter = new SptAdapter(this, sptList, this);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        getSpt();
    }

    private void getSpt() {
        ApiClient client = new ApiClient();
        client.getServies().get_spt("android").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    //jika login berhasil
                    ResponseSptModel responseModel = (ResponseSptModel) response.body();
                    if (responseModel.getStatus().equals("sukses")) {
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                        List<SptModel> riwayatListx =  responseModel.getData();
                        sptList.clear();
                        sptList.addAll(riwayatListx);
                        mAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d("Login", "onResponse: " + response.message());
                    //jika login gagal
                    Toast.makeText(getApplicationContext(), "Login Gagal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Login", "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Login Gagal 2", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(SptModel contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getNo_spt() + ", " + contact.getNama_pegawai(), Toast.LENGTH_LONG).show();
    }
}