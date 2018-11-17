package com.pshekha.githubrepos.Data;

import com.pshekha.githubrepos.Model.GitRepoResponse;
import com.pshekha.githubrepos.Model.Item;
import com.pshekha.githubrepos.Network.NetworkClient;
import com.pshekha.githubrepos.Network.NetworkService;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

import com.pshekha.githubrepos.Utils.Constants;

/**
 * Created by krrathore on 11/15/18.
 */

public class ReposDataSourceClass extends PageKeyedDataSource<Integer, Item> {

    private static final String TAG = ReposDataSourceClass.class.getSimpleName();


    private static long iTotalPages=0;
    private String sQuery;
    private NetworkService networkService;
    private long totalCount=0;

    ReposDataSourceClass(String Query){
        sQuery=Query;
        networkService=NetworkClient.getInstance().getNetworkService();
    }


    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Item> callback) {

        Call<GitRepoResponse> call = networkService.getRepoList(sQuery,Constants.SEARCH_SORT,Constants.SEARCH_ORDER, Constants.FIRST_PAGE,Constants.PAGE_SIZE);
        call.enqueue(new retrofit2.Callback<GitRepoResponse>() {
            @Override
            public void onResponse(@NonNull Call<GitRepoResponse>call,@NonNull Response<GitRepoResponse> response) {

                if(response.isSuccessful()){
                    if (response.body() != null) {

                        totalCount= response.body().getTotalCount();

                        //Calculate the total number of pages based on total_count and page_size
                        if(totalCount > 0)
                            setTotalCount();

                        callback.onResult(response.body().getItems(), null, Constants.FIRST_PAGE + 1);

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GitRepoResponse> call, @NonNull Throwable t) {

                Log.e(TAG, t.toString());
            }

        });

    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        // Do nothing as we retain the data from previous loads
    }


    //this will load the next page
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {

        Call<GitRepoResponse> call = networkService.getRepoList(sQuery,Constants.SEARCH_SORT,Constants.SEARCH_ORDER,params.key,Constants.PAGE_SIZE);
        call.enqueue(new retrofit2.Callback<GitRepoResponse>() {
            @Override
            public void onResponse(@NonNull Call<GitRepoResponse>call, @NonNull Response<GitRepoResponse> response) {

                if(response.isSuccessful()){
                    if (response.body() != null) {

                        Integer nextPage = getNextPage(params.key);
                        callback.onResult(response.body().getItems(), nextPage);

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<GitRepoResponse> call,@NonNull Throwable t) {

                Log.e(TAG, t.toString());
            }

        });


    }

    private void setTotalCount(){
        if(totalCount % Constants.PAGE_SIZE > 0)
            iTotalPages= totalCount/Constants.PAGE_SIZE + 1;
        else
            iTotalPages= totalCount/ Constants.PAGE_SIZE;
    }

    private Integer getNextPage(int iCurrentPage){
        return (iCurrentPage < iTotalPages) ? iCurrentPage + 1 : null;
    }
}


