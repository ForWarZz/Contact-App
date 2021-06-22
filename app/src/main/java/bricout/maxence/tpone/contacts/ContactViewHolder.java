package bricout.maxence.tpone.contacts;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import bricout.maxence.tpone.MainActivity;
import bricout.maxence.tpone.R;
import bricout.maxence.tpone.utils.IContactClickListener;
import bricout.maxence.tpone.utils.IContactListener;

public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView avatar;
    private TextView fullName;
    private CheckBox favorite;

    IContactClickListener contactClickListener;
    IContactListener contactListener;

    public ContactViewHolder(View itemView, IContactClickListener contactClickListener, IContactListener contactListener) {
        super(itemView);

        this.avatar = itemView.findViewById(R.id.avatar);
        this.fullName = itemView.findViewById(R.id.fullName);
        this.favorite = itemView.findViewById(R.id.favoriteStar);

        this.contactClickListener = contactClickListener;
        this.contactListener = contactListener;

        itemView.setOnClickListener(this);
    }

    void display(Contact contact) {
        this.avatar.setImageResource(R.drawable.avatar_rounded);
        this.fullName.setText(contact.getFullName());
        this.favorite.setChecked(contact.isFavorite());

        this.favorite.setOnClickListener(view -> {
            contact.setFavorite(favorite.isChecked());

            contactListener.onContactUpdated();
        });
    }

    @Override
    public void onClick(View view) {
        contactClickListener.onContactClick(getAdapterPosition());
    }
}
