package miso.demochatapplication;

/**
 * Created by Sonu on 29-Jul-17.
 */
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sonu on 19-Jul-17.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Activity activity;
    private List<Contact> items;
    private  int row;
    private Contact objDetail;

    public ContactAdapter(Activity act, int row, List<Contact> items)
    {
        super(act, row, items);
        this.activity = act;
        this.row = row;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        ViewHolder holder;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row,null);
            holder = new ViewHolder();
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }

        if((items == null) || ((position+1)>items.size()))
        {
            return view;
        }

        objDetail = items.get(position);

        holder.name = (TextView)view.findViewById(R.id.text_view_username);
        holder.id = (TextView)view.findViewById(R.id.text_view_uid);

        if(holder.name != null && objDetail.getName() != null && objDetail.getName().trim().length() > 0)
        {
            holder.name.setText(Html.fromHtml(objDetail.getName()));
        }

        if(holder.id != null && objDetail.getId() != null && objDetail.getId().trim().length()>0)
        {
            holder.id.setText(Html.fromHtml(objDetail.getId()));
        }
        return view;
    }

    class ViewHolder{
        public TextView id, name;
    }
}
