package com.pshekha.githubrepos;


import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.arch.lifecycle.Observer;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.pshekha.githubrepos.Adapter.AdapterRepoList;
import com.pshekha.githubrepos.Utils.Constants;
import com.pshekha.githubrepos.Utils.NetworkUtility;
import com.pshekha.githubrepos.ViewModel.ReposViewModel;
import com.pshekha.githubrepos.Model.Item;
import android.view.inputmethod.InputMethodManager;
import android.app.Activity;




public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    LinearLayoutManager linearLayoutManager;
    MainActivity mainActivity= this;
    ReposViewModel itemViewModel;
    AdapterRepoList adapter;
    EditText etvSearchQuery;
    Button bSearch;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find and set Views
        initializeViews();

        //restore search query incase of system death
        if(savedInstanceState != null)
        {
         String savedQuery = savedInstanceState.getString(Constants.SEARCH_QUERY);
         loadSearchResults(savedQuery);
        }



        bSearch= findViewById(R.id.btnSearch);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check for empty search strings
                if(!etvSearchQuery.getText().toString().equals(Constants.EMPTY_STRING))
                {
                    hideKeyboard(MainActivity.this);

                    //Check if there is an internet connection available before making the network call
                    if(NetworkUtility.isNetworkAvailable(getApplicationContext()))
                        loadSearchResults(etvSearchQuery.getText().toString());
                    else
                        showSnackBar(findViewById(R.id.layoutRoot), Constants.NO_CONNECTION_MSG);


                }
                else
                    showSnackBar(findViewById(R.id.layoutRoot), Constants.NO_QUERY_MSG);

            }


        });


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(!etvSearchQuery.getText().toString().equals(Constants.EMPTY_STRING))
            outState.putString(Constants.SEARCH_QUERY,etvSearchQuery.getText().toString() );
        super.onSaveInstanceState(outState);
    }

    private void initializeViews(){
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView= findViewById(R.id.rvRepoList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        etvSearchQuery= findViewById(R.id.etxtSearch);

        //getting our ItemViewModel
        itemViewModel = ViewModelProviders.of(this).get(ReposViewModel.class);

        //creating the Adapter
        adapter = new AdapterRepoList(mainActivity);
        //setting the adapter
        recyclerView.setAdapter(adapter);


    }


    private void loadSearchResults(String sQuery){

        // Github Api returns the
        String sFormattedQuery= formatString(sQuery);

        //Pass search query to the viewmodel
        itemViewModel.setQueryString(sFormattedQuery);

        // Check for 0 results
        itemViewModel.getItemPagedList().observe(mainActivity, new Observer<PagedList<Item>>() {
            @Override
            public void onChanged(@Nullable PagedList<Item> items) {
                //In case of any changes submitting the items to adapter
                adapter.submitList(items);
            }
        });
    }

    private String formatString(String sQuery){
        return sQuery.trim().replaceAll("[^A-Za-z0-9]","+");
    }

    private void showSnackBar(View rootView, String sMessage){
        Snackbar.make(rootView,sMessage, Snackbar.LENGTH_LONG).show();
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
