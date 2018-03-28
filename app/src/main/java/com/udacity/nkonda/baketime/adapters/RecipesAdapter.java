package com.udacity.nkonda.baketime.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.data.Recipe;

import java.util.List;

/**
 * Created by nkonda on 3/27/18.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecepieViewHolder>{
    private Context mContext;
    private List<Recipe> mRecipes;
    private OnItemClickedListener mListener;

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        mListener = listener;
    }

    @Override
    public RecepieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecepieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecepieViewHolder holder, int position) {
        holder.bind(mContext, position);
    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    public class RecepieViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        ImageView mRecipeImageView;
        TextView mRecipeNameView;

        public RecepieViewHolder(View itemView) {
            super(itemView);
            mRecipeImageView = itemView.findViewById(R.id.iv_recipe_image);
            mRecipeNameView = itemView.findViewById(R.id.tv_recipe_name);
        }

        void bind(Context context, int pos) {
            Recipe recipe = mRecipes.get(pos);
            mRecipeNameView.setText(recipe.getName());
            mRecipeImageView.setImageResource(recipe.getImageResId());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if (mListener != null) {
                mListener.onClick(pos);
            }
        }
    }

    public interface OnItemClickedListener {
        public void onClick(int position);
    }
}
