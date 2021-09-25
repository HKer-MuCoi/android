package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GoiYTang extends AppCompatActivity {

    private Button btnBack;
    private TextView tvGoiYTang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goi_y_tang);

        tvGoiYTang = findViewById(R.id.tvGoiYTang);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String resultGiam, goiy1, goiy2, goiy3, goiy4, goiy5;
        goiy1 = String.format("1.Bạn nên bổ sung nhiều rau củ quả để cung cấp cho cơ thể lượng vitamin và khoáng chất cần thiết, đặc biệt là các chất chống oxy hóa giúp thanh lọc cơ thể, cải thiện hệ tiêu hóa, từ đó giúp tăng cân nhanh chóng.\n");
        goiy2 = String.format("\n2.Hạn chế bổ sung đạm từ động vật, bởi trong thịt động vật có chứa nhiều chất béo bão hòa và lượng lớn cholesterol xấu có hại cho sức khỏe, dễ gây ra tình trạng táo bón.\n");
        goiy3 = String.format("\n3.Bạn hãy tích cực sử dụng đậu phộng, bơ đậu phộng. Nguồn thực phẩm này vừa an toàn lại bổ sung nhiều calo cho cơ thể.\n");
        goiy4 = String.format("\n4.Các loại thịt màu đỏ: thịt bò, thịt lợn, thịt bê, thịt cừu…rất giàu protein và một loại axit kích thích trong quá trình tổng hợp protein.\n");
        goiy5 = String.format("\n5.Khoai tây và thực phẩm giàu tinh bột sẽ là nguồn năng lượng chính cho các hoạt động. Bạn có thể lựa chọn những loại thực phẩm như đậu, khoai lang, bí, ngũ cốc…\n");

        resultGiam = goiy1 + goiy2 + goiy3 + goiy4 + goiy5;

        tvGoiYTang.setText(resultGiam);


    }
}