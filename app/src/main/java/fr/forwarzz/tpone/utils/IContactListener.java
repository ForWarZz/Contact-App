package fr.forwarzz.tpone.utils;

import fr.forwarzz.tpone.contacts.Contact;

public interface IContactListener {
    void onContactAdded(Contact contact);
    void onContactRemoved(int position);
    void onContactUpdated();
}
