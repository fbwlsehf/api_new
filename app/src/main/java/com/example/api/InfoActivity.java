package com.example.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class InfoActivity extends Activity implements Serializable {


    TextView pd_name ;
    TextView more_info;
    TextView nut_info;
    ListView listView;
    String key="1332c45fd60e4b9ca83c";
    String list_num="2";

    nutData nutData = new nutData();
    ArrayList<Check_nut> nut_list;
    float[] rda = {2600,324,55,54,100,2000,300,15,2.2f}; // 영양성분 권장섭취량
    // 열량(kcal) 탄수화물(g) 단백질(g) 지방(g) 당류(g) 나트륨(mg) 콜레스테롤(mg) 포화지방산(g) 트랜스지방(g) // 트랜스지방은 부정확




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        pd_name = findViewById(R.id.productName);
        more_info = findViewById(R.id.more_info);
        nut_info = findViewById(R.id.nut_info);
        listView = findViewById(R.id.raw_list_view);

        Intent intent = getIntent();
        nutData = (nutData)intent.getSerializableExtra("OBJECT");

//7	GROUP_NAME	식품군//8	DESC_KOR	식품이름//10	MAKER_NAME	제조사명


        pd_name.setText(nutData.getDescKor());
        more_info.setText("...");

        String temp;
        temp ="총내용량: " + nutData.getServingSize() +
                "\n열량(kcal)(1회제공량당): " + nutData.getNutcont1() +
                "\n탄수화물(g)(1회제공량당): " + nutData.getNutcont2() +
                "\n단백질(g)(1회제공량당): " + nutData.getNutcont3() +
                "\n지방(g)(1회제공량당): " + nutData.getNutcont4() +
                "\n당류(g)(1회제공량당): " + nutData.getNutcont5() +
                "\n나트륨(mg)(1회제공량당): " + nutData.getNutcont6() +
                "\n콜레스테롤(mg)(1회제공량당): " + nutData.getNutcont7() +
                "\n포화지방산(g)(1회제공량당): " + nutData.getNutcont8() +
                "\n트랜스지방(g)(1회제공량당): " + nutData.getNutcont9() +"\n";
        nut_info.setText(temp);

        new Thread(new Runnable() {
            String more_data;
            @Override
            public void run() {
                // TODO Auto-generated method stub
                more_data = getXmlData2();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        more_info.setText(more_data);
                    }
                });
            }
        }).start();

        nut_list= new ArrayList<>();  // 영양성분 정보가 있는가 표시,영양성분 값 int 저장
        set_data();
        chart_make();

        }

    String getXmlData2(){

        StringBuilder buffer=new StringBuilder();
        String pd_name = "";
        String bs_name = "";

        try {
            pd_name = URLEncoder.encode(nutData.getDescKor(),"UTF-8");
            bs_name = URLEncoder.encode(nutData.getMakerName(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //BSSH_NM="매일유업"&PRDLST_NM="CAFELATTE-CAPPUCHINO"
        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/"//요청 URL
                +key+"/I1250/xml/1/"+list_num+"/BSSH_NM="+pd_name+"&PRDLST_NM="+bs_name; // 품목제조번호

        Log.d("api2",queryUrl);

        try {

            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, StandardCharsets.UTF_8) );

            String tag ="";
            Boolean[] empty= new Boolean[3];
            Boolean empty_all = false;
            for(int i=0;i<3;i++){
                empty[i]=true;
            }
            xpp.next();

            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작 단계 \n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        Log.d("api2_start",tag);

                    switch (tag) {
                        case "POG_DAYCNT":
                            empty[0] = false;
                            break;
                        case "HIENG_LNTRT_DVS_NM":
                            empty[1] = false;
                            break;
                        case "CHILD_CRTFC_YN":
                            empty[2] = false;
                            break;
                    }
                    break;

                    case XmlPullParser.TEXT:
                        if(!empty[0]){
                            buffer.append("유통기한: ");
                            buffer.append(xpp.getText());
                            buffer.append("\n"); // 줄바꿈 문자 추가
                        }

                        else if(!empty[1]){
                            buffer.append("고열량저영양식품여부 : ");
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }

                        else if(!empty[2]){
                            buffer.append("어린이기호식품품질인증여부 :");
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else
                            empty_all=true;
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        Log.d("api2_end",tag);
                        switch (tag) {
                            case "POG_DAYCNT":
                                empty[0] = true;
                                break;
                            case "HIENG_LNTRT_DVS_NM":
                                empty[1] = true;
                                break;
                            case "CHILD_CRTFC_YN":
                                empty[2] = true;
                                break;
                        }
                        xpp.next();
                        break;

                }
                eventType= xpp.next();
            }
            if(empty_all) buffer.append("영양성분 외의 정보가 없습니다.");


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("url2","파싱에러"+e);
        }

        return buffer.toString();

    }


    void set_data(){
//double d = Double.parseDouble(String.format("%.3f", 1.4455555));
        if(nutData.getNutcont1().equals("null")){ nut_list.add(new Check_nut(false,0f));} else{nut_list.add(new Check_nut(Float.parseFloat(nutData.getNutcont1())));}
        if(nutData.getNutcont2().equals("null")){ nut_list.add(new Check_nut(false,0f));}else{nut_list.add(new Check_nut(Float.parseFloat(nutData.getNutcont2())));}
        if(nutData.getNutcont3().equals("null")){ nut_list.add(new Check_nut(false,0f));}else{nut_list.add(new Check_nut(Float.parseFloat(nutData.getNutcont3())));}
        if(nutData.getNutcont4().equals("null")){nut_list.add(new Check_nut(false,0f));}else{nut_list.add(new Check_nut(Float.parseFloat(nutData.getNutcont4())));}
        if(nutData.getNutcont5().equals("null")){nut_list.add(new Check_nut(false,0f));}else{nut_list.add(new Check_nut(Float.parseFloat(nutData.getNutcont5())));}
        if(nutData.getNutcont6().equals("null")){ nut_list.add(new Check_nut(false,0f));}else{nut_list.add(new Check_nut(Float.parseFloat(nutData.getNutcont6())));}
        if(nutData.getNutcont7().equals("null")){nut_list.add(new Check_nut(false,0f));}else{nut_list.add(new Check_nut(Float.parseFloat(nutData.getNutcont7())));}
        if(nutData.getNutcont8().equals("null")){ nut_list.add(new Check_nut(false,0f));}else{nut_list.add(new Check_nut(Float.parseFloat(nutData.getNutcont8())));}
        if(nutData.getNutcont9().equals("null")){nut_list.add(new Check_nut(false,0f));}else{nut_list.add(new Check_nut(Float.parseFloat(nutData.getNutcont9())));}
    } // 영양성분을 ArrayList로 저장--> 반복문 쓰기 위함  // 정리가 필요할듯

    void chart_make(){
        StackedBarChart mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);
        ArrayList<StackedBarModel> bar_list = new ArrayList<>();

//        열량(kcal) 탄수화물(g) 단백질(g) 지방(g) 당류(g) 나트륨(mg) 콜레스테롤(mg) 포화지방산(g) 트랜스지방(g)

        StackedBarModel s1 = new StackedBarModel("열량");
        StackedBarModel s2 = new StackedBarModel("탄수화물");
        StackedBarModel s3 = new StackedBarModel("단백질");
        StackedBarModel s4 = new StackedBarModel("지방");
        StackedBarModel s5 = new StackedBarModel("당류");
        StackedBarModel s6 = new StackedBarModel("나트륨");
        StackedBarModel s7 = new StackedBarModel("콜레스테롤");
        StackedBarModel s8 = new StackedBarModel("포화지방산");
        StackedBarModel s9 = new StackedBarModel("트랜스지방");

        bar_list.add(s1);bar_list.add(s2);bar_list.add(s3);bar_list.add(s4);bar_list.add(s5);bar_list.add(s6);bar_list.add(s7);bar_list.add(s8);bar_list.add(s9);

        for(int i=0;i<bar_list.size();i++){
            if(nut_list.get(i).getIsdata()){  //영양성분이 null 아니면
                bar_list.get(i).addBar(new BarModel((nut_list.get(i).getData()),0xFF63CBB0));}
            else{
                bar_list.get(i).addBar(new BarModel(0, 0xFF63CBB0)); bar_list.get(i).setLegendLabel("정보없음");}

            mStackedBarChart.addBar(bar_list.get(i));
        }

        mStackedBarChart.startAnimation();

    }






}
