package com.aacfslo.tzli.seek;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bsugiarto on 7/31/16.
 */
public class PrayerFragment extends Fragment implements View.OnClickListener {
    private LinearLayout rlLayout;

    protected FacebookProfile personal;
    protected Firebase myFirebaseRef;
    protected ArrayList<Prayer> displayArray;
    protected ArrayList<Card> cards;
    protected Button sendPrayer;
    protected Button checkbox;
    protected TextView prayertext;

    protected boolean isAnonymous;

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

        //initialize personal
        personal = ((TabActivity) getActivity()).getPersonal();

        //set to anonymous as false
        isAnonymous = false;

        //init cards and array of prayers
        cards = new ArrayList<>();
        displayArray = new ArrayList<>();


        //init prayer text view
        prayertext = (TextView) rlLayout.findViewById(R.id.prayer_text);

        //init send prayer and check box buttons
        sendPrayer = (Button) rlLayout.findViewById(R.id.send_prayer);
        sendPrayer.setOnClickListener(this);
        checkbox = (Button) rlLayout.findViewById(R.id.checkbox_cheese);
        checkbox.setOnClickListener(this);


        //init card recycle view
        mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getContext(), cards);
        cardRecyclerView = (CardRecyclerView) rlLayout.findViewById(R.id.myList);
        cardRecyclerView.setOnClickListener(this);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (cardRecyclerView != null) {
            cardRecyclerView.setAdapter(mCardArrayAdapter);
        }

        //init firebase
        myFirebaseRef = new Firebase(TabActivity.FIREBASE_URL2 + "/prayers");

        //make database calls
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
        myFirebaseRef.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                int arraySize = displayArray.size();

                ArrayList<Prayer> displayArray2 = new ArrayList<Prayer>();

                //get prayers
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Prayer p = postSnapshot.getValue(Prayer.class);
                    displayArray2.add(p);
                    System.out.println(p);
                }

                //reverse the prayer order
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
     * Function to Initialize cards from Firebase values
     */
    public void initCards() {
        for (Prayer p : displayArray) {

            Date d = new Date(p.date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            sdf.set2DigitYearStart(d);

            Card card = new Card(getContext());
            card.setTitle(p.prayer +  "\nBy " + p.author + " on " + sdf.format(d));

            cards.add(card);
        }
        mCardArrayAdapter.notifyDataSetChanged();
    }


    /**
     * function for onClick listener
     */
    public void onClick(View v) {
        switch (v.getId()) {

            //call to send prayer!
            case R.id.send_prayer:
                System.out.println("sending prayer");

                //get prayer
                String text = prayertext.getText().toString();
                Prayer p = new Prayer();
                p.author = (isAnonymous)? "anonymous" : personal.getName();
                p.date =  System.currentTimeMillis();
                p.prayer = text;

                //push stuff to database
                Firebase getKeyBase = myFirebaseRef.push();
                getKeyBase.setValue(p);
                String postId = getKeyBase.getKey();


                //make text show up at bottom and reset prayer text
                Toast.makeText(getContext(), "Sent prayer to God", Toast.LENGTH_SHORT).show();
                prayertext.setText("");
                break;

            //call to click on checkbox
            case R.id.checkbox_cheese:
                System.out.println("check!");
                isAnonymous = !isAnonymous;
                break;


            case R.id.myList:
                System.out.println("list click!");
                break;
        }
    }

}
