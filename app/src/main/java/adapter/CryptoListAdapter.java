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

import account.CryptoAccount;

/**
 * Diese Klasse ist ein Adapter für die Liste aller Kryptokonten um diese
 * in der Listview von CryptosHomeFragment anzeigen zu können
 */
public class CryptoListAdapter extends ArrayAdapter<CryptoAccount> {

    private Context context;
    private int resource;

    public CryptoListAdapter(@NonNull Context context, int resource, ArrayList<CryptoAccount> objects) {
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

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewCryptoListName);
        TextView textViewSubTitle = (TextView) convertView.findViewById(R.id.textViewCryptoListBalance);

        textViewTitle.setText(name);
        textViewSubTitle.setText(balance);

        return convertView;
    }
}
