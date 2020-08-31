package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fipl.R;

import java.util.ArrayList;

import account.StockAccount;


/**
 * Diese Klasse ist ein Adapter für die Liste aller Aktienkonten um diese
 * in der Listview von StockHomeFragment anzeigen zu können
 */
public class StockListAdapter extends ArrayAdapter<StockAccount> {

    private Context context;
    private int resource;

    public StockListAdapter(@NonNull Context context, int resource, ArrayList<StockAccount> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getAccountName();
        String balance = getItem(position).getBalance() + " " + getItem(position).getCurrency();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewStockListName);
        TextView textViewSubTitle = (TextView) convertView.findViewById(R.id.textViewStockListBalance);

        textViewTitle.setText(name);
        textViewSubTitle.setText(balance);

        return convertView;
    }
}
