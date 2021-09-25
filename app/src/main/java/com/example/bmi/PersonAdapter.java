package com.example.bmi;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PersonAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Person> personList;
    BmiActivity bmi = new BmiActivity();

    public PersonAdapter(MainActivity context, int layout, List<Person> personList) {
        this.context = context;
        this.layout = layout;
        this.personList = personList;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tvTen,tvGT,tvChieucao,tvCannang,tvBMI, tvTuoi,tvNgay;
        Button btnXoa, btnSua;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvTen = (TextView) view.findViewById(R.id.tvTen);
            holder.tvGT = (TextView) view.findViewById(R.id.tvGioitinh);
            holder.tvTuoi = (TextView) view.findViewById(R.id.tvTuoi);
            holder.tvChieucao = (TextView) view.findViewById(R.id.tvChieucao);
            holder.tvCannang = (TextView) view.findViewById(R.id.tvCannang);
            //holder.tvBMI = (TextView) view.findViewById(R.id.tvBmi);
            holder.tvNgay = (TextView) view.findViewById(R.id.tvNgay);
            holder.btnXoa = (Button) view.findViewById(R.id.btnXoa);
            holder.btnSua = (Button) view.findViewById(R.id.btnSua);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        final Person person = personList.get(i);
        holder.tvTen.setText(person.getHt());
        holder.tvGT.setText(person.getGt());
        holder.tvTuoi.setText(String.valueOf(person.getT()));
        holder.tvChieucao.setText(String.valueOf(person.getCc()));
        holder.tvCannang.setText(String.valueOf(person.getCn()));
        holder.tvNgay.setText(person.getDate());
        //holder.tvBMI.;



        //Bắt sự kiện
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              context.DialogXoaPerson(person.getHt(),person.getId());
            }
        });
        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               context.DialogSuaPerson(person.getId(),person.getHt(),person.getGt(),person.getT(),person.getCc(),person.getCn(),person.getDate());
            }
        });
        holder.tvTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.Chitiet(person.getId(),person.getHt(),person.getGt(),person.getT(),person.getCc(),person.getCn(),person.getDate(),person.getCn()/(person.getCc()/100*person.getCc()/100),((person.getCc()%100)*9/10));
            }
        });
        return view;
    }
}
