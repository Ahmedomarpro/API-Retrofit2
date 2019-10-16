package com.ao.apiretrofit2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ao.apiretrofit2.R;
import com.ao.apiretrofit2.Utils;
import com.ao.apiretrofit2.model.ArticlesItem;
import com.ao.apiretrofit2.model.NewResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {

	List<ArticlesItem> list ;
	private Context context;

	public NewAdapter(List<ArticlesItem> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemactivity,parent,false);


		return new ViewHolder(view);
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	@SuppressLint({"CheckResult", "SetTextI18n"})
	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


		final ArticlesItem listID = list.get(position);


		RequestOptions requestOptions = new RequestOptions();
		requestOptions.placeholder(Utils.getRandomDrawbleColor());
		requestOptions.error(Utils.getRandomDrawbleColor());
		requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
		requestOptions.centerCrop();

		Glide.with(context)
				.load(listID.getUrlToImage())
				.apply(requestOptions)
				.listener(new RequestListener<Drawable>() {
					@Override
					public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
						holder.progressBar.setVisibility(View.GONE);

						return false;
					}

					@Override
					public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
						holder.progressBar.setVisibility(View.GONE);

						return false;
					}
				})
                .transition(DrawableTransitionOptions.withCrossFade())
				.into(holder.imageView);

		holder.title.setText(listID.getTitle());
		holder.desc.setText(listID.getDescription());

		holder.source.setText(listID.getSource().getName());

		holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(listID.getPublishedAt()));
		holder.published_ad.setText(Utils.DateFormat(listID.getPublishedAt()));
		holder.author.setText(listID.getAuthor());

		holder.imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "  "+position, Toast.LENGTH_SHORT).show();
			}
		});





	}

	@Override
	public int getItemCount() {
 		return list == null? 0:list.size();


	}

	public class  ViewHolder extends RecyclerView.ViewHolder{
		TextView title, desc, author, published_ad, source, time;
		ImageView imageView;
		ProgressBar progressBar;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			title = itemView.findViewById(R.id.title);
			title.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			desc = itemView.findViewById(R.id.desc);
			author = itemView.findViewById(R.id.author);
			published_ad = itemView.findViewById(R.id.publishedAt);
			source = itemView.findViewById(R.id.source);
			time = itemView.findViewById(R.id.time);
			imageView = itemView.findViewById(R.id.img);
			progressBar = itemView.findViewById(R.id.prograss_load_photo);


		}
	}
}
