package com.corelibrary.common.engine;

import com.corelibrary.models.SubjectResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kamalverma on 30/12/16.
 */

public interface ApiUtills {
    @GET("/rest/v1/category/list")
    Call<SubjectResponse> getSubjectList(@Query("appId") String aapId);
}