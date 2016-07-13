package jono.bedheadalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Jono on 27/06/2016.
 * method is stolen from http://www.learn-android.com/2011/11/22/lots-of-lists-custom-adapter/
 * Adapts Alarm objects onto views for lists
 */

public final class CustomAdapter extends ArrayAdapter<Alarm> {

    private final int ItemLayout;

    public CustomAdapter(final Context context, final int ItemLayout) {
        super(context, 0);
        this.ItemLayout = ItemLayout;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        // We need to get the best view (re-used if possible) and then
        // retrieve its corresponding ViewHolder, which optimizes lookup efficiency
        final View view = getWorkingView(convertView);
        final ViewHolder viewHolder = getViewHolder(view);
        final Alarm entry = getItem(position);

        // Setting the title view is straightforward
        viewHolder.Name.setText(entry.getName()+' '+entry.getDays());

        // Setting the subTitle view requires a tiny bit of formatting
        final String formattedTime = String.format("%s : %s",
                entry.getHours(),
                entry.getMins()
        );

        viewHolder.Time.setText(formattedTime);

        return view;
    }

    private View getWorkingView(final View convertView) {
        // The workingView is basically just the convertView re-used if possible
        // or inflated new if not possible
        View workingView = null;

        if(null == convertView) {
            final Context context = getContext();
            final LayoutInflater inflater = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);

            workingView = inflater.inflate(ItemLayout, null);
        } else {
            workingView = convertView;
        }

        return workingView;
    }

    private ViewHolder getViewHolder(final View workingView) {
        // The viewHolder allows us to avoid re-looking up view references
        // Since views are recycled, these references will never change
        final Object tag = workingView.getTag();
        ViewHolder viewHolder = null;


        if(null == tag || !(tag instanceof ViewHolder)) {
            viewHolder = new ViewHolder();

            viewHolder.Name = (TextView) workingView.findViewById(R.id.ListName);
            viewHolder.Time = (TextView) workingView.findViewById(R.id.ListTime);

            workingView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) tag;
        }

        return viewHolder;
    }

    /**
     * ViewHolder allows us to avoid re-looking up view references
     * Since views are recycled, these references will never change
     */
    private static class ViewHolder {
        public TextView Name;
        public TextView Time;
    }

}