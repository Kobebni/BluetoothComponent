package org.printtech.printaddin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrintApp printApp = new PrintApp(this, 0);
        printApp.show();
    }

    public void loadLibOnClick(View view) {
        // Used to load the 'native-lib' library on application startup.
        System.loadLibrary("org_printtech_printaddin");
        PrintApp printApp = new PrintApp(this, 0);
        printApp.testScreenActions();
    }
}
