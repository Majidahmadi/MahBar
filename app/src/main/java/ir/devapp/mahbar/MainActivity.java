package ir.devapp.mahbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MahBar mahBar = findViewById(R.id.mahbar);
        mahBar.setValue(22);
    }
}
