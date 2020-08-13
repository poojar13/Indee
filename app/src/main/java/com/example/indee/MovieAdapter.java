package com.example.indee;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indee.databinding.ListItemBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    ArrayList<MovieListItem.TestData> movieListItemArrayList = new ArrayList<>();
    ImageLoader imageLoader;

    public MovieAdapter(Context context) {
        this.imageLoader = new ImageLoader(context);
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, null, false);
        return new MovieAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        holder.bindData(movieListItemArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieListItemArrayList.size();
    }

    public void addListItem(ArrayList<MovieListItem.TestData> arrayList) {
        if (arrayList != null) {
            movieListItemArrayList.clear();
            movieListItemArrayList.addAll(arrayList);
        }
        notifyDataSetChanged();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        ListItemBinding binding;

        public MovieAdapterViewHolder(@NonNull ListItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bindData(MovieListItem.TestData testData) {

            if (!TextUtils.isEmpty(testData.name)) {
                binding.name.setVisibility(View.VISIBLE);
                binding.name.setText(testData.name);
            }
            if (!TextUtils.isEmpty(testData.payment_plan)) {
                binding.plan.setVisibility(View.VISIBLE);
                binding.plan.setText(testData.payment_plan);
            }
            if (!TextUtils.isEmpty(testData.type)) {
                binding.type.setVisibility(View.VISIBLE);
                binding.type.setText(testData.type);
            }
            if (!TextUtils.isEmpty(testData.created_on)) {
                binding.createdOn.setText(formattedDate(testData.created_on));
                binding.createdOnLayout.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(testData.updated_on)) {
                binding.updatedOnLayout.setVisibility(View.VISIBLE);
                binding.updatedOn.setText(formattedDate(testData.updated_on));
            }
            if (!TextUtils.isEmpty(testData.shortDescription)) {
                binding.shortDescription.setVisibility(View.VISIBLE);
                binding.shortDescription.setText(testData.shortDescription);
            }
            if (!TextUtils.isEmpty(testData.description)) {
                binding.description.setVisibility(View.VISIBLE);
                binding.description.setText(testData.description);
            }
            if(!TextUtils.isEmpty(testData.video_duration)){
                binding.duration.setVisibility(View.VISIBLE);
                binding.duration.setText(testData.video_duration);
            }
            if(!TextUtils.isEmpty(String.valueOf(testData.release_year))){
                binding.releaseYear.setVisibility(View.VISIBLE);
                binding.releaseYear.setText(String.valueOf(testData.release_year));
            }
            if(!TextUtils.isEmpty(testData.posterLink)){
//               new DownloadImageTask(binding.poster,binding.progress).execute(testData.posterLink);
                imageLoader.DisplayImage(testData.posterLink,binding.poster);
            }
        }
        private String formattedDate(String dateString){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String strDate = "";
            try {
                Date date = formatter.parse(dateString);
                strDate = new SimpleDateFormat("dd-MM-yyyy").format(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return strDate;
        }
    }
}
