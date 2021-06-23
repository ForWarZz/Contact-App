package bricout.maxence.tpone.utils;

import java.util.List;

import bricout.maxence.tpone.contacts.Contact;

public interface IContactListener {
    void onContactAdded(Contact contact);
    void onContactRemoved(int position);
    void onContactUpdated();
}
