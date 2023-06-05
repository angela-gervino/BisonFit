package funkyflamingos.bisonfit.ui;
import funkyflamingos.bisonfit.logic.WaterHandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import funkyflamingos.bisonfit.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class MainActivity extends AppCompatActivity {
    private CircularProgressIndicator waterTrackerView;
    private ImageButton waterTrackerButton;

    private WaterHandler waterHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waterTrackerView = findViewById(R.id.circularProgressView);
        waterTrackerButton = findViewById(R.id.circularProgressButton);
        waterHandler = new WaterHandler();

    }

    public void updateWaterTrackerUI(View v)
    {
        waterHandler.increment();
        waterTrackerView.setProgress(waterHandler.getProgress());
    }
}