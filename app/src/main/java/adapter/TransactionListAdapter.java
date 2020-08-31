package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.fipl.R;
import java.util.ArrayList;
import transaction.Transaction;

/**
 * Diese Klasse ist ein Adapter für die Liste Transaktionen eines Bankkontos um diese
 * in der Listview von TransactionBankACcountFragment und TransactionHomeFragment anzeigen zu können
 */
public class TransactionListAdapter extends ArrayAdapter<Transaction> {

    private Context context;
    private int resource;
    private String bankAccountName;

    public TransactionListAdapter(@NonNull Context context, int resource, ArrayList<Transaction> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.bankAccountName=bankAccountName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getName();
        String balance = getItem(position).getBalance() + " EUR";

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTransactionListRowTitle);
        TextView textViewSubTitle = (TextView) convertView.findViewById(R.id.textViewTransactionListRowSubtitle);



        textViewTitle.setText(name);
        textViewSubTitle.setText(balance);

        if (getItem(position).getBalance()>0){
            textViewSubTitle.setTextColor(Color.GREEN);
        }
        else if (getItem(position).getBalance()<0){
            textViewSubTitle.setTextColor(Color.RED);
        }
        else{
            textViewSubTitle.setTextColor(Color.BLACK);
        }

        return convertView;
    }
}

