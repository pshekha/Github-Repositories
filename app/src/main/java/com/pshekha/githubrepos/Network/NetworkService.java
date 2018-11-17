package com.pshekha.githubrepos.Network;

/**
 * Created by krrathore on 11/13/18.
 */

import com.pshekha.githubrepos.Model.GitRepoResponse;
import com.pshekha.githubrepos.Utils.Constants;


import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

public interface NetworkService {
    @GET(Constants.SEARCH_REPOS)
    Call<GitRepoResponse> getRepoList(@Query("q") String query,
                                          @Query("sort") String sort,
                                          @Query("order") String order,
                                      @Query("page") Integer page,
                                      @Query("per_page") Integer per_page);
}