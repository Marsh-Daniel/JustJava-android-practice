package com.example.danielmarsh.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    int quantity;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String total = " Total Price \n Thank You";

        displayPrice(calculatePrice(), total);
    }

    public Boolean hasWhippedCream() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.whippedCream);
        boolean hasTopping = checkBox.isChecked();
        return hasTopping;
    }

    public void incrementQuantity(View view) {
        if (quantity < 100) {
            quantity++;
        } else {
            Toast.makeText(this, "you cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
        }

        display(quantity);
    }

    public int calculatePrice() {
        int price = 5;
        return quantity * price;
    }


    public void decrementQuantity(View view) {
        quantity--;
        if (quantity > 0) {
            display(quantity);
        } else {
            quantity = 0;
            Toast.makeText(this, "you cannot have less than 0 coffees", Toast.LENGTH_SHORT).show();
            display(quantity);
        }
    }

    public Boolean hasChocolate() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.Chocolate);
        boolean hasTopping = checkBox.isChecked();
        return hasTopping;
    }

    private void displayWhippedTopping() {

        TextView view = (TextView) findViewById(R.id.whippedCreamSummary);
        view.setText("This Order comes with whipped topping: " + hasWhippedCream());
    }

    private void displayChocolateTopping() {

        TextView view = (TextView) findViewById(R.id.ChocolateSummary);
        view.setText("This Order comes with Chocolate topping: " + hasChocolate());
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    private void displayPrice(int price, String Total) {

        if (hasChocolate() == true) {
            price += 2;
        }
        if (hasWhippedCream()) {
            price++;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.setType(price + Total);

        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for" + name);
        intent.putExtra(intent.EXTRA_TEXT, price + Total);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
