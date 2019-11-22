package cs.hku.hk.memome.uiAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import cs.hku.hk.memome.R;

/**
 * The adapter for the To Do item lists. Being initialized and utilized in ToDoFragment.
 */
public class MyListViewAdapter extends BaseAdapter
{
    private List<Item> items;
    private Context context;
    private ItemClickListener itemClickListener;

    /**
     * Abstraction for each row (single to do item)
     */
    public class Item
    {
        boolean checked;
        String text;

        /**
         * Construct an item object
         * @param text The output text
         * @param checked The init status for the checkbox
         */
        Item(String text, boolean checked)
        {
            this.text= text;
            this.checked = checked;
        }

        /**
         * Retrieve the status of the checkbox
         * @return whether the checkbox of this item is checked or not
         */
        public boolean isChecked()
        {
            return checked;
        }

        /**
         * Set the status for each item
         * @param status The target status
         */
        public void setChecked(boolean status) {checked = status;}
    }

    /**
     * The view holder for each item view. A view holder helps to increase the efficiency by enabling
     * recycling usage.
     */
    static class ViewHolder
    {
        CheckBox checkBox;
        TextView textView;
    }

    public MyListViewAdapter(Context c, String [] allDetails, boolean [] loadedResult)
    {
        context = c;
        items = new ArrayList<>();

        for(int i=0; i<allDetails.length && i<loadedResult.length; i++)
        {
            items.add(new Item(allDetails[i], loadedResult[i]));
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
            conterView = inflater.inflate(R.layout.list_details, null);
            viewHolder.checkBox = conterView.findViewById(R.id.todo_checkbox);
            viewHolder.textView = conterView.findViewById(R.id.checkbox_text);
            conterView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)conterView.getTag();
        }

        viewHolder.checkBox.setTag(position);
        viewHolder.checkBox.setChecked(items.get(position).checked);

        viewHolder.textView.setText(items.get(position).text);
        viewHolder.textView.setTag(position);

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener()
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
        viewHolder.textView.setOnClickListener(new View.OnClickListener()
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
        viewHolder.checkBox.setChecked(isChecked(position));
        return conterView;
    }

    /**
     * Determined whether the given item is checked or not
     * @param position the index of the item
     * @return The status of the item
     */
    public boolean isChecked(int position)
    {
        return items.get(position).checked;
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
