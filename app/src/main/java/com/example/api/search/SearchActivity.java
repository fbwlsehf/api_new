package com.example.api.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;


import com.example.api.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class SearchActivity extends Activity {

    EditText productName;Button search; ListView list;
    Button camera;Button barcode;

    String key="1332c45fd60e4b9ca83c"; String list_num="10";

    ArrayList<nutData> nutDataArrayList = new ArrayList<>();
    ArrayList<String> pd_list = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        productName = findViewById(R.id.productName);
        search = findViewById(R.id.searchBtn);
        camera = findViewById(R.id.cameraBtn);
        barcode = findViewById(R.id.barcodeBtn);
        list = findViewById(R.id.pd_name_list);


        Log.d("point","확인포인트");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( v.getId() == R.id.searchBtn){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pd_list.clear();
                            pd_list=getXmlData3();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                            Log.d("point",pd_list.toString());

                            final String[] list_data = new String[pd_list.size()];
                            for(int i=0;i<pd_list.size();i++){
                                list_data[i]=pd_list.get(i);
                            }
                            if(pd_list.size()!=0) adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list_data);

                            else {
                                String[] empty_list = new String[1];
                                empty_list[0]="데이터가 없습니다.";
                                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, empty_list);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    list.setAdapter(adapter);
                                }
                            });
                        }
                    }).start();
                }

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                nutData put_data = new nutData();
                put_data = nutDataArrayList.get(i);
                intent.putExtra("OBJECT", put_data);
                startActivity(intent);
            }
        });

    }


    ArrayList<String> getXmlData3(){
        String name = productName.getText().toString();//EditText에 작성된 Text얻어오기
        boolean[] empty = new boolean[13];
        ArrayList<String> pd_list_data = new ArrayList<>();
        for(int i=0;i<13;i++) empty[i] = true;
        try {
            name = URLEncoder.encode(name,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // http://openapi.foodsafetykorea.go.kr/api/1332c45fd60e4b9ca83c/I2790/xml/1/6/DESC_KOR=%22%EC%96%BC%ED%81%B0%ED%95%9C%20%EB%84%88%EA%B5%AC%EB%A6%AC%22&MAKER_NAME=%22%EB%86%8D%EC%8B%AC%22
        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/"//요청 URL
                +key+"/I2790/xml/1/"+list_num+"/DESC_KOR="+ name;

        Log.d("api3",queryUrl);

        try {
            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            // inputstream 으로부터 xml 입력받기1
            xpp.setInput( new InputStreamReader(is, StandardCharsets.UTF_8) );

            String tag = "";

            xpp.next();

            nutData data =null;
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        Log.d("api3_start",tag);

                        switch (tag) {

                            case "row":
                                data = new nutData();
                                break;
                            case "GROUP_NAME":
                                empty[0] = false;
                                break;
                            case "DESC_KOR":
                                empty[1] = false;
                                break;
                            case "MAKER_NAME":
                                empty[2] = false;
                                break;
                            case "SERVING_SIZE":
                                empty[3] = false;
                                break;
                            case "NUTR_CONT1":
                                empty[4] = false;
                                break;
                            case "NUTR_CONT2":
                                empty[5] = false;
                                break;
                            case "NUTR_CONT3":
                                empty[6] = false;
                                break;
                            case "NUTR_CONT4":
                                empty[7] = false;
                                break;
                            case "NUTR_CONT5":
                                empty[8] = false;
                                break;
                            case "NUTR_CONT6":
                                empty[9] = false;
                                break;
                            case "NUTR_CONT7":
                                empty[10] = false;
                                break;
                            case "NUTR_CONT8":
                                empty[11] = false;
                                break;
                            case "NUTR_CONT9":
                                empty[12] = false;
                                break;

                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        Log.d("api3_end",tag);
                        switch (tag) {
                            case "row":
                                Log.d("api3_final","final end tag");
                                nutDataArrayList.add(data);
                                String temp ="(분류) " + data.getGroupName()+  "\n(제품명) "+ data.getDescKor() + "\n(회사) " +  data.getMakerName() + "\n(총제공량) " + data.getServingSize() +"\n";
                                pd_list_data.add(temp);
                                Log.d("api3_pd_list_data",temp);
                                data =null;
                                break;
                            case "GROUP_NAME":
                                empty[0] = true;
                                break;
                            case "DESC_KOR":
                                empty[1] = true;
                                break;
                            case "MAKER_NAME":
                                empty[2] = true;
                                break;
                            case "SERVING_SIZE":
                                empty[3] = true;
                                break;
                            case "NUTR_CONT1":
                                empty[4] = true;
                                break;
                            case "NUTR_CONT2":
                                empty[5] = true;
                                break;
                            case "NUTR_CONT3":
                                empty[6] = true;
                                break;
                            case "NUTR_CONT4":
                                empty[7] = true;
                                break;
                            case "NUTR_CONT5":
                                empty[8] = true;
                                break;
                            case "NUTR_CONT6":
                                empty[9] = true;
                                break;
                            case "NUTR_CONT7":
                                empty[10] = true;
                                break;
                            case "NUTR_CONT8":
                                empty[11] = true;
                                break;
                            case "NUTR_CONT9":
                                empty[12] = true;
                                break;
                        }
                        xpp.next();
                        break;

                    case XmlPullParser.TEXT:

                        String temp = xpp.getText();
                        Log.d("api3","text tag");
                        Log.d("api3_set",temp);
                        if (!empty[0]) { data.setGroupName(temp); }
                        else if (!empty[1]) {data.setDescKor(temp);}
                        else if (!empty[2]) {data.setMakerName(temp);}
                        else if (!empty[3]) {data.setServingSize(temp); }
                        else if (!empty[4]) {data.setNutcont1(temp);;}
                        else if (!empty[5]) { data.setNutcont2(temp);}
                        else if (!empty[6]) {data.setNutcont3(temp);}
                        else if (!empty[7]) {data.setNutcont4(temp);}
                        else if (!empty[8]) { data.setNutcont5(temp);}
                        else if (!empty[9]) {data.setNutcont6(temp); }
                        else if (!empty[10]) {data.setNutcont7(temp);}
                        else if (!empty[11]) {data.setNutcont8(temp);}
                        else if (!empty[12]) {data.setNutcont9(temp);}
                        Log.d("api3",xpp.getText());
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("url3","파싱에러"+e);
        }

        return pd_list_data;

    }



    public void barcodeListener(View view) {
        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
        startActivity(intent);
    }
}




