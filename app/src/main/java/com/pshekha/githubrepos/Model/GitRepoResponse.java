package com.pshekha.githubrepos.Model;

/**
 * Created by krrathore on 11/13/18.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GitRepoResponse {

    @SerializedName("total_count")
    @Expose
    private long totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}