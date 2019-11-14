package cs.hku.hk.memome.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;

import java.util.ArrayList;
import java.util.List;
import cs.hku.hk.memome.R;

public class MyListViewAdapter extends BaseAdapter
{
    private List<Item> items;
    private Context context;
    private ItemClickListener itemClickListener;

    public class Item
    {
        boolean checked;
        String text;
        Item(String text, boolean checked)
        {
            this.text= text;
            this.checked = checked;
        }
        public boolean isChecked()
        {
            return checked;
        }
        public void setChecked(boolean status) {checked = status;}
    }

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

    public boolean isChecked(int position)
    {
        return items.get(position).checked;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }
}
