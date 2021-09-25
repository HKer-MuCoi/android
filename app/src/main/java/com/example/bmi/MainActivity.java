package com.example.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView listPerson;
    ArrayList<Person> arrayListPerson;
    PersonAdapter adapter;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    EditText txtTen, txtTuoi, txtGT, txtCc, txtCn, txtN, txtTim;
    Button btnTimkiem;
    //TextView Bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listPerson = (ListView) findViewById(R.id.lvPerson);
        arrayListPerson = new ArrayList<>();

        adapter = new PersonAdapter(this, R.layout.line_person, arrayListPerson);
        listPerson.setAdapter(adapter);

        database = new Database(this, "people.sqlite",null,1);
        //Tạo bảng Person
        database.QueryData("CREATE TABLE IF NOT EXISTS Person(Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   Ht NVARCHAR(50),\n" +
                "   Gt NVARCHAR(5),\n" +
                "   T INT,\n" +
                "   Cc FLOAT,\n" +
                "   Cn FLOAT,\n" +
                "   N NVARCHAR(20))");
        //Insert Data
        //database.QueryData("INSERT INTO Person VALUES(null, 'Trương Văn Nam', 'Nam', 25, 180, 90)" );
        //Select Data
        getDataPerson();
        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BmiActivity.class);
                startActivity(intent);
            }
        });
        Anhxa();
        Timkiem();

    }
    public void Timkiem()
    {
        btnTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!txtTim.getText().toString().trim().equals("")) {
                    arrayListPerson = new ArrayList<>();
                    String query_name = txtTim.getText().toString().trim();
                    Cursor cursor = database.getData("Select * from Person where Ht like '%" + query_name + "%'");
                    while (cursor.moveToNext())
                    {
                        String ten = cursor.getString(1);
                        int id = cursor.getInt(0);
                        String gt = cursor.getString(2);
                        int t = cursor.getInt(3);
                        float cc = cursor.getFloat(4);
                        float cn = cursor.getFloat(5);
                        String n = cursor.getString(6);

                        arrayListPerson.add(new Person(id,ten,gt,t,cc,cn,n));
                    }
                    PersonAdapter adapter = new PersonAdapter(MainActivity.this, R.layout.line_person, arrayListPerson);
                    listPerson.setAdapter(adapter);
                } else {
                    getDataPerson();
                    Toast.makeText(MainActivity.this, "Không có tên bạn cần tìm", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void DialogXoaPerson(final String ten, final int id)
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Bạn có muốn xóa "+ten+" không ?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM Person WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this,"Đã xóa "+ ten, Toast.LENGTH_SHORT).show();
                getDataPerson();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.show();
    }
    public void DialogSuaPerson(final int id, String ht, String gt, int t, Float cc, Float cn, final String date){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_person);
        final EditText Ten = (EditText) dialog.findViewById(R.id.edTen);
        final EditText GT = (EditText) dialog.findViewById(R.id.edGT);
        final EditText T = (EditText) dialog.findViewById(R.id.edT);
        final EditText Cc = (EditText) dialog.findViewById(R.id.edCc);
        final EditText Cn = (EditText) dialog.findViewById(R.id.edCn);
        final EditText N = (EditText) dialog.findViewById(R.id.edDate);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        N.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                calendar.set(i, i1, i2);
                                txtN.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        },nam,thang,ngay);
                datePickerDialog.show();
            }
        });
        Ten.setText(ht);
        GT.setText(gt);
        T.setText(String.valueOf(t));
        Cc.setText(String.valueOf(cc));
        Cn.setText(String.valueOf(cn));
        N.setText(date);


        Button btnCapnhat = (Button) dialog.findViewById(R.id.btnCapnhat);
        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = Ten.getText().toString();
                String gt = GT.getText().toString();
                String t = T.getText().toString();
                String cc = Cc.getText().toString();
                String cn = Cn.getText().toString();
                String n = N.getText().toString();

                if(ten.equals("") ||  gt.equals("") || t.equals("")|| cc.equals("") || cn.equals("")|| n.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đúng và đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.QueryData("UPDATE Person SET Ht = '"+ten+"', Gt = '"+gt+"', T = '"+t+"', Cc = '"+cc+"', Cn = '"+cn+"', N = '"+n+"' WHERE Id = '"+id+"'" );
                    Toast.makeText(MainActivity.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataPerson();
                }
            }

        });
        Button cancel = (Button) dialog.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void getDataPerson()
    {

        Cursor dataPerson = database.getData("SELECT * FROM Person ORDER BY N DESC");
        arrayListPerson.clear();
        while (dataPerson.moveToNext())
        {
            String ten = dataPerson.getString(1);
            int id = dataPerson.getInt(0);
            String gt = dataPerson.getString(2);
            int t = dataPerson.getInt(3);
            float cc = dataPerson.getFloat(4);
            float cn = dataPerson.getFloat(5);
            String n = dataPerson.getString(6);

            arrayListPerson.add(new Person(id,ten,gt,t,cc,cn,n));
        }
//        PersonAdapter adapter = new PersonAdapter(MainActivity.this, R.layout.line_person, arrayListPerson);
//        listPerson.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_person, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuAdd){

            DialogThem();
        }


        return super.onOptionsItemSelected(item);
    }


    private void DialogThem() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_demo);
        final EditText txtTen = (EditText) dialog.findViewById(R.id.edtTen);
        final EditText txtGT = (EditText) dialog.findViewById(R.id.edtGT);
        final EditText txtT = (EditText) dialog.findViewById(R.id.edtT);
        final EditText txtCc = (EditText) dialog.findViewById(R.id.edtCc);
        final EditText txtCn = (EditText) dialog.findViewById(R.id.edtCn);
        final EditText txtN = (EditText) dialog.findViewById(R.id.edtDate);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                calendar.set(i, i1, i2);
                                txtN.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        },nam,thang,ngay);
                datePickerDialog.show();
            }
        });

        Button them = (Button) dialog.findViewById(R.id.btnSua);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ten = txtTen.getText().toString();
                String gt = txtGT.getText().toString();
                String t = txtT.getText().toString();
                String cc = txtCc.getText().toString();
                String cn = txtCn.getText().toString();
                String n = txtN.getText().toString();

                if(ten.equals("") ||  gt.equals("") || t.equals("")|| cc.equals("") || cn.equals("")|| n.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đúng và đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.QueryData("INSERT INTO Person VALUES(null, '"+ten+"', '"+gt+"', '"+t+"', '"+cc+"', '"+cn+"', '"+n+"')" );
                    Toast.makeText(MainActivity.this,"Đã thêm",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataPerson();
                }
            }
        });
        Button Huy = (Button) dialog.findViewById(R.id.btnHuy);
        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
    public void Chitiet(final int id, String ht, String gt, int t, Float cc, Float cn, final String date, Float bmi, Float bestweight)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.chitiet_person);
        final TextView Ten = (TextView) dialog.findViewById(R.id.txtHoten);
        final TextView GT = (TextView) dialog.findViewById(R.id.txtGT);
        final TextView T = (TextView) dialog.findViewById(R.id.txtTuoi);
        final TextView Cc = (TextView) dialog.findViewById(R.id.txtCc);
        final TextView Cn = (TextView) dialog.findViewById(R.id.txtCn);
        final TextView N = (TextView) dialog.findViewById(R.id.txtNgay);
        final TextView Bmi = (TextView) dialog.findViewById(R.id.txtBmi);
        Ten.setText("Họ tên: "+ht);
        GT.setText("Giới tính: "+gt);
        T.setText("Tuổi: " +String.valueOf(t));
        Cc.setText("Chiều cao (cm): " +String.valueOf(cc));
        Cn.setText("Cân nặng (kg): "+String.valueOf(cn));
        N.setText("Ngày khai: "+date);
        Bmi.setText("Chỉ Số BMI: "+String.valueOf(bmi)+"\nCân nặng lý tưởng của bạn: "+bestweight+"kg");
        //int bestweight = (( % 100) * 9) / 10;                                         // Cân nặng lý tưởng: (Số lẻ của chiều cao * 9) / 10


        Button Quaylai = (Button) dialog.findViewById(R.id.btnQL);
        Quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void Anhxa()
    {
         txtTen = (EditText) findViewById(R.id.edtTen);
         txtGT = (EditText) findViewById(R.id.edtGT);
         txtTuoi = (EditText) findViewById(R.id.edtT);
         txtCc = (EditText) findViewById(R.id.edtCc);
         txtCn = (EditText) findViewById(R.id.edtCn);
         txtN = (EditText) findViewById(R.id.edtDate);
         txtTim = (EditText) findViewById(R.id.edtTimkiem);
         btnTimkiem = (Button) findViewById(R.id.btnTimkiem);
         //Bmi = (TextView) findViewById(R.id.tvBmi);
    }
}