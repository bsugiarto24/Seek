package com.aacfslo.tzli.seek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bsugiarto on 7/31/16.
 */
public class PrayerFragment extends Fragment {
    private LinearLayout rlLayout;

    protected FacebookProfile personal;
    protected Firebase myFirebaseRef;
    protected ArrayList<Prayer> displayArray;
    protected ArrayList<Card> cards;
    CardArrayRecyclerViewAdapter mCardArrayAdapter;
    CardRecyclerView cardRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rlLayout = (LinearLayout) inflater.inflate(R.layout.fragment_prayer, container, false);

        personal = ((TabActivity) getActivity()).getPersonal();


        cards = new ArrayList<>();
        displayArray = new ArrayList<>();

        mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getContext(), cards);

        cardRecyclerView = (CardRecyclerView) rlLayout.findViewById(R.id.myList);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (cardRecyclerView != null) {
            cardRecyclerView.setAdapter(mCardArrayAdapter);
        }

        myFirebaseRef = new Firebase(TabActivity.FIREBASE_URL2 + "/prayers");


        getPairings();

        return rlLayout;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    /**
     * Function to get values from Firebase
     */
    public void getPairings() {
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                int arraySize = displayArray.size();

                ArrayList<Prayer> displayArray2 = new ArrayList<Prayer>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Prayer p = postSnapshot.getValue(Prayer.class);
                    displayArray2.add(p);
                    System.out.println(p);
                }

                for(int i = displayArray2.size() - 1; i >= 0; i--) {
                    displayArray.add(displayArray2.get(i));
                }


                if (arraySize != displayArray.size()) {
                    initCards();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });
    }

    /**
     * Function to Initialize cards from Firebase
     */
    public void initCards() {
        for (Prayer p : displayArray) {
            //Create a Card
            Card card = new Card(getContext());

            Date d = new Date(p.date);


            card.setTitle(p.prayer +  "\nBy " + p.author + " on " + d.toString() );

            //Create thumbnail
            CardThumbnail thumb = new CardThumbnail(getContext());

            //Set URL resource
            //thumb.setUrlResource(m.retrievePicture());

            //Add thumbnail to a card
            //card.addCardThumbnail(thumb);

            cards.add(card);
        }
        mCardArrayAdapter.notifyDataSetChanged();
    }

    public void onCheckboxClicked(){
        System.out.println("asdfsadf");
    }

}
