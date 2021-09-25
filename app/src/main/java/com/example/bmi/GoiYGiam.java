package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GoiYGiam extends AppCompatActivity {

    private Button btnBack;
    private TextView tvGoiYGiam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goi_y_giam);

        tvGoiYGiam = findViewById(R.id.tvGoiYGiam);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String result, goiy1, goiy2, goiy3, goiy4, goiy5, goiy6, goiy7;
        goiy1 = String.format("1.Hạn chế sử dụng những thức ăn béo,ngọt, không nên ăn đã, mỡ động vật, bánh, kẹo…nên ăn cá nhiều hơn ăn thịt.\n");
        goiy2 = String.format("\n2.Tăng cường khẩu phần rau, trái cây ít ngọt và thực phẩm giàu chất xơ trong mỗi bữa ăn.\n");
        goiy3 = String.format("\n3.Uống đủ từ 2 đến 2,5 lít nước mỗi ngày, có thể bổ sung thêm vitamin và các loại khoáng chất.\n");
        goiy4 = String.format("\n4.Nên dùng nhiều các loại hoa quả như bưởi, cam, kết hợp với việc dùng trà xanh, tỏi, ớt… Giúp tăng cường đốt cháy chất béo trong cơ thể.\n");
        goiy5 = String.format("\n5.Ăn chậm, nhai kĩ, ăn uống điều độ, tuyệt đối không bỏ bữa sáng và không ăn bất kỳ một loại thực phẩm nào sau 8 giờ tối.\n");
        goiy6 = String.format("\n6.Điều chỉnh chế độ nghỉ ngơi sinh hoạt hợp lý, không thức khuya dậy muộn.\n");
        goiy7 = String.format("\n7.Tăng cường hoạt động thể lực mỗi ngày. Bạn nên tập thể dùng ít nhất 60 phút mỗi ngày.");

        result = goiy1 + goiy2 + goiy3 + goiy4 + goiy5 + goiy6 + goiy7;

        tvGoiYGiam.setText(result);
    }
}