package info.acidflow.waveplay.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.acidflow.waveplay.R;
import info.acidflow.waveplay.server.model.GsonFile;

/**
 * Created by paul on 25/10/14.
 */
public class FileExplorerAdapter extends ArrayAdapter<GsonFile> {

    public FileExplorerAdapter(Context context, int resource, List<GsonFile> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ){
            convertView = LayoutInflater.from( getContext() )
                    .inflate(R.layout.list_item_file_explorer, parent, false);
            convertView.setTag( new FileExplorerViewHolder( convertView ) );
        }
        FileExplorerViewHolder holder = (FileExplorerViewHolder) convertView.getTag();
        holder.fileName.setText(getItem(position).getFileName());
        return convertView;
    }


    static class FileExplorerViewHolder {

        @InjectView( R.id.file_icon )
        ImageView fileIcon;

        @InjectView( R.id.file_name )
        TextView fileName;

        FileExplorerViewHolder( View v ){
            ButterKnife.inject( this, v );
        }

    }
}
