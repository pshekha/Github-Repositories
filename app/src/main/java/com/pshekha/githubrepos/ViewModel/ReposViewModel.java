package com.pshekha.githubrepos.ViewModel;

/**
 * Created by krrathore on 11/15/18.
 */
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LiveData;
import com.pshekha.githubrepos.Data.ReposDataSourceFactory;
import com.pshekha.githubrepos.Model.Item;
import com.pshekha.githubrepos.Utils.Constants;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.util.Log;

public class ReposViewModel extends ViewModel {
    private static final String TAG = ReposViewModel.class.getSimpleName();


    //creating livedata for PagedList  and PagedKeyedDataSource
    LiveData<PagedList<Item>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, Item>> liveDataSource;
    ReposDataSourceFactory reposDataSourceFactory;

    //constructor
    public ReposViewModel() {
            //getting our data source factory
            reposDataSourceFactory = new ReposDataSourceFactory();

            //getting the live data source from data source factory
            liveDataSource = reposDataSourceFactory.getItemLiveDataSource();

            //Getting PagedList config
            PagedList.Config pagedListConfig =
                    (new PagedList.Config.Builder())
                            .setEnablePlaceholders(false)
                            .setPageSize(Constants.PAGE_SIZE).build();

            //Building the paged list
            itemPagedList = (new LivePagedListBuilder(reposDataSourceFactory, pagedListConfig))
                    .build();
        }

    public LiveData<PagedList<Item>> getItemPagedList(){
        return itemPagedList;
        }

        //Recieve query from the view and forward it to DataSource
    public void setQueryString(String sQuery) {
         reposDataSourceFactory.setQuery(sQuery);
        }


}
