package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fipl.R;

import java.util.ArrayList;

import account.BankAccount;

/**
 * Diese Klasse ist ein Adapter für die Liste aller Bankkonten um diese
 * in der Listview von AccountsHomeFragment anzeigen zu können
 */
public class AccountListAdapter extends ArrayAdapter<BankAccount> {

    private Context context;
    private int resource;

    public AccountListAdapter(@NonNull Context context, int resource, ArrayList<BankAccount> objects) {
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

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewAccountListRowTitle);
        TextView textViewSubTitle = (TextView) convertView.findViewById(R.id.textViewAccountListRowSubtitle);

        textViewTitle.setText(name);
        textViewSubTitle.setText(balance);

        return convertView;









    }
}
