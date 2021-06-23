package fr.forwarzz.tpone.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.forwarzz.tpone.R;
import fr.forwarzz.tpone.listeners.ContactListener;

public class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<Contact> contacts;
    private ContactListener contactListener;

    public ContactsAdapter(List<Contact> contacts, ContactListener contactListener) {
        this.contacts = contacts;
        this.contactListener = contactListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_contact, parent, false);

        return new ContactViewHolder(view, contactListener);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.display(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
