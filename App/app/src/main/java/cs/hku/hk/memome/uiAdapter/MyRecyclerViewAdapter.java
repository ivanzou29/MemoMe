package cs.hku.hk.memome.uiAdapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cs.hku.hk.memome.R;

/**
 * The adapter for the posts lists. Being initialized and utilized in History and Community fragments.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal;
        if(mData.get(position).length()>=23)
        {
            animal = mData.get(position).substring(0,mData.get(position).length()-23);
        }
        else
        {
            animal = mData.get(position);
        }

        holder.myTextView.setText(animal);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * Stores and recycles views as they are scrolled off screen
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /**
     * Convenience method for getting data at click position
     * @param id the index of the item
     * @return the data on the position indexed by the id
     */
    public String getItem(int id) {
        return mData.get(id);
    }
    /**
     * Allow the caller class to set on click listener for all the items altogether via implementing
     * an ItemClickListener interface.
     * @param itemClickListener the object where the ItemClickListener is implemented and the onItem-
     *                          Click function is set.
     */
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    /**
     * The interface via which an on click listener can be implemented for the item in the list.
     */
    public interface ItemClickListener {
        /**
         * A holder for the function to override the super.onItemClick() for each item.
         * @param view The clicked view object
         * @param position The position of the clicked object within the list
         */
        void onItemClick(View view, int position);
    }
}