package com.pshekha.githubrepos.Data;

/**
 * Created by krrathore on 11/15/18.
 */
import com.pshekha.githubrepos.Model.*;
import com.pshekha.githubrepos.ViewModel.ReposViewModel;

import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.DataSource;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

public class ReposDataSourceFactory extends DataSource.Factory {

    private static final String TAG = ReposDataSourceFactory.class.getSimpleName();

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Item>> itemLiveDataSource = new MutableLiveData<>();
    private String sQuery=null;
    private ReposDataSourceClass itemDataSource;

        @Override
        public DataSource<Integer, Item> create() {
            //getting our data source object
            itemDataSource = new ReposDataSourceClass(sQuery);

            //posting the datasource to get the values
            itemLiveDataSource.postValue(itemDataSource);

            return itemDataSource;
        }


        //getter for itemlivedatasource
        public MutableLiveData<PageKeyedDataSource<Integer, Item>> getItemLiveDataSource() {
            return itemLiveDataSource;
        }

       // Recieve the query from viewmodel and pass it to the DataSourceClass to get the values
        public void setQuery(String Query){
            sQuery=Query;
             //  itemDataSource.invalidate();

        }
    }


