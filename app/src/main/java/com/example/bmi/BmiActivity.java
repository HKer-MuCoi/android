package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class BmiActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvResult;
    private EditText edtHeight, edtWeight, edtName, edtAge, edtGT;
    private Button btnView, btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        init();
        final Button reset = findViewById(R.id.btnReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setText("");
                edtAge.setText("");
                edtHeight.setText("");
                edtWeight.setText("");
                edtGT.setText("");
                tvResult.setText("Nhập chiều cao (cm) và cân nặng (kg) của bạn để xem kết quả");
            }
        });
        Button btnSotheodoi = (Button) findViewById(R.id.btnSotheodoi);
        btnSotheodoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BmiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button btnGoiy = (Button)findViewById(R.id.btnGoiy);
        btnGoiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DinhDuong(view);
            }
        });
    }
    public void init(){
        tvResult = (TextView) findViewById(R.id.tvResult);
        edtHeight = (EditText) findViewById(R.id.edtHeight);
        edtWeight = (EditText) findViewById(R.id.edtWeight);
        edtName = (EditText) findViewById(R.id.edtName);
        btnView = (Button) findViewById(R.id.btnView);
        edtGT = (EditText) findViewById(R.id.edtGioitinh);
        edtAge = (EditText) findViewById(R.id.edtTuoi);



        btnView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (TextUtils.isEmpty(edtHeight.getText()) || TextUtils.isEmpty(edtWeight.getText()) || TextUtils.isEmpty(edtName.getText())){
            showToast("Hãy nhập đầy đủ thông tin");
        } else {
            if (v.getId() == R.id.btnView){
                int height = Integer.parseInt(edtHeight.getText().toString());
                int weight = Integer.parseInt(edtWeight.getText() + "");                         // Lưu ý lỗi

                float bmi = BMIcaculator(weight, height);

                int bestweight = ((height % 100) * 9) / 10;                                         // Cân nặng lý tưởng: (Số lẻ của chiều cao * 9) / 10

                String advice = getAdvice(bestweight, weight);


                String result, record1, record2, record3, record4, record5,record6,record7,record8,record9;
                record1 = String.format("%nChỉ số BMI của bạn là: %.2f %n", bmi);
                record2 = BMIlevel(bmi);
                record3 = String.format("%nCân nặng lý tưởng của bạn là: %d kg (%s) %n", bestweight, advice);
                record4 = edtName.getText().toString();
                record5 = String.format("Họ và tên:");
                record6 = edtAge.getText().toString();
                record7 = String.format("\nTuổi: ");
                record8 = edtGT.getText().toString();
                record9 = String.format("  Giới tính: ");

                result = record5 + record4+ record7 + record6 + record9 + record8 + record1 + record2 + record3;

                tvResult.setText(result);

                hideKeyboard();

            }
        }


    }
    public void DinhDuong(View view){
        Intent intent = new Intent(this, DinhduongActivity.class);
        TextView editresult = findViewById(R.id.tvResult);
        String resultTra = editresult.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, resultTra);
        startActivity(intent);

    }

    public float BMIcaculator(float weight, float height){
        return (weight) / (height/100 * height/100);       // Vì nhập vào là cm nhưng công thức tính là m nên phải chia cho 100 để đổi lại đơn vị
        /*
        float bmi = (weight) / (height/100 * height/100);
        return bmi;
         */
    }

    public String BMIlevel(float bmilevel){
        String result = null;
        if(bmilevel < 16){
            result = " Bạn bi thiếu cân cấp độ III";
        } else if((16 <= bmilevel) && (bmilevel < 17)){
            result = " Bạn bị thiếu cân cấp độ II";
        } else if((17 <= bmilevel) && (bmilevel < 18.5)){
            result = "Bạn bi thiếu cân cấp độ I";
        } else if((18.5 <= bmilevel) && (bmilevel < 25)){
            result = "Bạn có thân hình bình thường";
        } else if((25 <= bmilevel) && (bmilevel < 30)){
            result = "Bạn bị thừa cân";
        } else if((30 <= bmilevel) && (bmilevel < 35)){
            result = "Bạn bị béo phì cấp độ I";
        } else if((35 <= bmilevel) && (bmilevel < 40)){
            result = "Bạn bị béo phì cấp độ II";
        } else{
            result = "Bạn bị béo phì cấp độ III";
        }

        return result;
    }


    public String getAdvice(int bestweight, int weight){
        String result = null;
        if(bestweight > weight){
            result = String.format("Bạn thiếu %d kg", bestweight - weight);
        } else if(bestweight < weight){
            result = String.format("Bạn thừa %d kg", weight - bestweight);
        } else {
            result = "Bạn có thân hình lý tưởng ^0^";
        }
        return result;
    }


    public void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}