package com.ao.apiretrofit2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ao.apiretrofit2.Api.ApiManager;
import com.ao.apiretrofit2.Api.WebServices;
import com.ao.apiretrofit2.adapter.NewAdapter;
import com.ao.apiretrofit2.model.ArticlesItem;
import com.ao.apiretrofit2.model.NewResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
//API key  35b2f2b16e844957940d0890b0141ab3

	public static final String API_KEY = "35b2f2b16e844957940d0890b0141ab3";
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private List<ArticlesItem> articles = new ArrayList<>();
	private NewAdapter adapter;
	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		recyclerView = findViewById(R.id.recycler);


		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);

		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setNestedScrollingEnabled(false);
		LoadJson(" ");


	}

	Call<NewResponse> call;

	public void LoadJson(String keyword) {
		WebServices webServices = ApiManager.webApis();

		String country = Utils.getCountry();
		String language = Utils.getLanguage();


		if (keyword.length() > 0) {
			call = webServices.getNewResponse(country, API_KEY);
		}

		call.enqueue(new Callback<NewResponse>() {
			@Override
			public void onResponse(Call<NewResponse> call, Response<NewResponse> response) {

				if (response.body() != null) {
					if (response.isSuccessful() && response.body().getArticles() != null) {

						if (articles.isEmpty()) {
							articles.clear();
						}
						articles = response.body().getArticles();
						adapter = new NewAdapter(articles, MainActivity.this);
						recyclerView.setAdapter(adapter);
						recyclerView.setVisibility(View.VISIBLE);

						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onFailure(Call<NewResponse> call, Throwable t) {


				Toast.makeText(MainActivity.this, "Oops ", Toast.LENGTH_SHORT).show();
				Toast.makeText(MainActivity.this, "Network failure, Please Try Again!!!", Toast.LENGTH_SHORT).show();

			}
		});


	}

}
