package com.example.hizligeliyoproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hizligeliyoproject.adapter.CustomAdapter;
import com.example.hizligeliyoproject.api.ClientAPi;
import com.example.hizligeliyoproject.api.ProductsApi;
import com.example.hizligeliyoproject.model.ProductList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomAdapter customAdapter;
    private ArrayList<ProductList> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getProductListFromApi();
    }

    private void getProductListFromApi(){
        progressDialog = createProgressDialog(MainActivity.this);
        ProductsApi productsApi= ClientAPi.getRetrofit().create(ProductsApi.class);

        Call<List<ProductList>> call=productsApi.getProducts();
       call.enqueue(new Callback<List<ProductList>>() {
           @Override
           public void onResponse(Call<List<ProductList>> call, Response<List<ProductList>> response) {
               progressDialog.dismiss();
               productList = new ArrayList<>(response.body());
               customAdapter = new CustomAdapter(productList, getApplicationContext(), new CustomItemClickListener() {
                                  @Override
                                  public void onItemClick(ProductList product, int position) {

                                      Toast.makeText(getApplicationContext(),""+product.getTitle(),Toast.LENGTH_SHORT).show();

                                  }
                              });
               recyclerView.setAdapter(customAdapter);
           }

           @Override
           public void onFailure(Call<List<ProductList>> call, Throwable t) {
                int x=0;
           }
       });
    }
    public ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_layout);
        return dialog;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
