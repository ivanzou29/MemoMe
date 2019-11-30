package cs.hku.hk.memome.uiAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cs.hku.hk.memome.R;

/**
 * The adapter for the To Do item lists. Being initialized and utilized in ToDoFragment.
 */
public class ProfileListViewAdapter extends BaseAdapter
{
    private List<Item> items;
    private Context context;
    private ItemClickListener itemClickListener;

    /**
     * Abstraction for each row (single to do item)
     */
    public class Item
    {
        Drawable icon;
        String text;

        /**
         * Construct an item object
         * @param text The output text
         * @param checked The init status for the checkbox
         */
        Item(String text, Drawable icon)
        {
            this.text= text;
            this.icon = icon;
        }
    }

    /**
     * The view holder for each item view. A view holder helps to increase the efficiency by enabling
     * recycling usage.
     */
    static class ViewHolder
    {
        ImageButton iconButton;
    }

    public ProfileListViewAdapter(Context c, String [] allDetails, Drawable [] iconList)
    {
        context = c;
        items = new ArrayList<>();

        for(int i=0; i<allDetails.length && i<iconList.length; i++)
        {
            items.add(new Item(allDetails[i], iconList[i]));
        }
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Item getItem(int position)
    {
        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(final int position, View conterView, ViewGroup parent)
    {
        ViewHolder viewHolder = new ViewHolder();
        if(conterView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            conterView = inflater.inflate(R.layout.profile_item, null);
            viewHolder.iconButton = conterView.findViewById(R.id.profile_icon);
            conterView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)conterView.getTag();
        }

        viewHolder.iconButton.setTag(position);
        viewHolder.iconButton.setImageDrawable(items.get(position).icon);

        viewHolder.iconButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(itemClickListener!=null)
                    itemClickListener.onItemClick(v, position);
                else
                    Toast.makeText(context, "An item has been clicked",Toast.LENGTH_SHORT).show();
            }
        });
        return conterView;
    }

    // allows clicks events to be caught

    /**
     * Allow the caller class to set on click listener for all the items altogether via implementing
     * an ItemClickListener interface.
     * @param itemClickListener the object where the ItemClickListener is implemented and the onItem-
     *                          Click function is set.
     */
    public void setClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events

    /**
     * The interface via which an on click listener can be implemented for the item in the list.
     */
    public interface ItemClickListener
    {
        /**
         * A holder for the function to override the super.onItemClick() for each item.
         * @param view The clicked view object
         * @param position The position of the clicked object within the list
         */
        void onItemClick(View view, int position);
    }
}
