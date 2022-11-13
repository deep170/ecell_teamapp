package ecell.app.ecellteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import java.util.Calendar;
import java.util.TimeZone;

public class CurrentworkActivity extends AppCompatActivity {
    Button button;
    String dateShow;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0250R.layout.activity_currentwork);
        this.button = (Button) findViewById(C0250R.C0253id.button);
        Calendar.getInstance(TimeZone.getTimeZone("UTC")).clear();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText((CharSequence) "Select Date");
        builder.setSelection(Long.valueOf(today));
        final MaterialDatePicker materialDatePicker = builder.build();
        this.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                materialDatePicker.show(CurrentworkActivity.this.getSupportFragmentManager(), "DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            public void onPositiveButtonClick(Object selection) {
                CurrentworkActivity.this.dateShow = materialDatePicker.getHeaderText();
                Intent intent = new Intent(CurrentworkActivity.this, CurrentworkActivityShow.class);
                intent.putExtra("dateShow", CurrentworkActivity.this.dateShow);
                CurrentworkActivity.this.startActivity(intent);
            }
        });
    }
}
