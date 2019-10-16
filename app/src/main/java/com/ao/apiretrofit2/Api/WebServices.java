package com.ao.apiretrofit2.Api;

import com.ao.apiretrofit2.model.NewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServices {


	@GET("top-headlines")
	Call<NewResponse> getNewResponse(
			@Query("country") String country ,
			@Query("apiKey") String apiKey

	);


}
