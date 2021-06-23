package fr.forwarzz.tpone.listeners;

import android.content.Intent;

import fr.forwarzz.tpone.ContactInfoActivity;
import fr.forwarzz.tpone.MainActivity;
import fr.forwarzz.tpone.contacts.Contact;
import fr.forwarzz.tpone.utils.IContactClickListener;
import fr.forwarzz.tpone.utils.IContactListener;

public class ContactListener implements IContactListener, IContactClickListener {
    private MainActivity mainActivity;

    public ContactListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    // When contact is added

    @Override
    public void onContactAdded(Contact contact) {
        mainActivity.getContacts().add(contact);

        mainActivity.saveContacts();
        mainActivity.sortList();

        mainActivity.getContactsAdapter().notifyItemInserted(mainActivity.getContacts().indexOf(contact));
    }

    // When contact is removed

    @Override
    public void onContactRemoved(int position) {
        mainActivity.getContacts().remove(position);

        mainActivity.sortList();
        mainActivity.saveContacts();

        mainActivity.getContactsAdapter().notifyItemRemoved(position);
    }

    // When contact is updated

    @Override
    public void onContactUpdated() {
        mainActivity.sortList();
        mainActivity.saveContacts();

        mainActivity.getRecyclerView().post(() -> mainActivity.getContactsAdapter().notifyDataSetChanged());
    }

    // When recycler view item was clicked

    @Override
    public void onContactClick(int position) {
        Intent intent = new Intent(mainActivity, ContactInfoActivity.class);
        ContactInfoActivity.setContactListener(this);

        intent.putExtra(MainActivity.CONTACT, mainActivity.getContacts().get(position));
        intent.putExtra(MainActivity.CONTACT_POSITION, position);

        mainActivity.startActivity(intent);
    }
}
