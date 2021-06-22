package bricout.maxence.tpone.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bricout.maxence.tpone.MainActivity;
import bricout.maxence.tpone.utils.IContactClickListener;
import bricout.maxence.tpone.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<Contact> contacts;
    private IContactClickListener contactClickListener;
    private MainActivity mainActivity;

    public ContactsAdapter(List<Contact> contacts, IContactClickListener contactClickListener, MainActivity mainActivity) {
        this.contacts = contacts;
        this.contactClickListener = contactClickListener;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_contact, parent, false);

        return new ContactViewHolder(view, contactClickListener);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.display(contacts.get(position), mainActivity);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
