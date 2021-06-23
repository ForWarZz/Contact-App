package fr.forwarzz.tpone.contacts;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import fr.forwarzz.tpone.R;
import fr.forwarzz.tpone.listeners.ContactListener;

public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView avatar;
    private TextView fullName;
    private CheckBox favorite;

    private ContactListener contactListener;

    public ContactViewHolder(View itemView, ContactListener contactListener) {
        super(itemView);

        this.avatar = itemView.findViewById(R.id.avatar);
        this.fullName = itemView.findViewById(R.id.fullName);
        this.favorite = itemView.findViewById(R.id.favoriteStar);

        this.contactListener = contactListener;

        itemView.setOnClickListener(this);
    }

    void display(Contact contact) {
        this.avatar.setImageResource(R.drawable.avatar_rounded);
        this.fullName.setText(contact.getFullName());
        this.favorite.setChecked(contact.isFavorite());

        // When the favorite star was clicked, update the contacts list and save

        this.favorite.setOnClickListener(view -> {
            contact.setFavorite(favorite.isChecked());

            contactListener.onContactUpdated();
        });
    }

    @Override
    public void onClick(View view) {
        contactListener.onContactClick(getAdapterPosition());
    }
}
