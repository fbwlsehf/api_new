package com.example.api.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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


public class Barcord_Info extends Activity {

    String barcode_num;
    TextView raw_info,more_info,pd_name_view;
    String key="1332c45fd60e4b9ca83c";
    String list_num="2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_info);

        raw_info = findViewById(R.id.raw_info);
        more_info= findViewById(R.id.more_info);
        pd_name_view = findViewById(R.id.productName);

        Intent intent = getIntent();
        barcode_num = (String)intent.getStringExtra("barcodeNum") ;

        new Thread(new Runnable() {
            String barcode_data;
            @Override
            public void run() {
                // TODO Auto-generated method stub
                getProductnum(barcode_num);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                       // textView.setText(barcode_data);
                    }
                });
            }
        }).start();

    }

    private void getProductnum(String barcode_num) {
        try {
            barcode_num = URLEncoder.encode(barcode_num,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//http://openapi.foodsafetykorea.go.kr/api/sample/C005/xml/1/5
        //<BAR_CD>8802039211424</BAR_CD>
    //<PRDLST_NM>면사랑 구수한 메밀국수</PRDLST_NM>
//<PRDLST_REPORT_NO>19930443028350</PRDLST_REPORT_NO>

        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/"//요청 URL
                +key+"/C005/xml/1/"+list_num+"/BAR_CD="+barcode_num;

        Log.d("pd_name_api",queryUrl);


        try {

            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결


            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();

            // inputstream 으로부터 xml 입력받기1
            xpp.setInput( new InputStreamReader(is, StandardCharsets.UTF_8) );

            String tag = "";

            xpp.next();

            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        break;

                    case XmlPullParser.TEXT:
                        Log.d("app","Text");
                        if(tag.equals("PRDLST_NM")){
                            pd_name_view.setText(xpp.getText());
                            Log.d("pd_name",xpp.getText());
                        }

                        else if(tag.equals("PRDLST_REPORT_NO")){ //품목제조번호 --> 원재료 / 부가 정보 api 연결
                            Log.d("pd_report_num",xpp.getText());
                            raw_info.setText(getRawData(xpp.getText()));
                            more_info.setText(getMoreData(xpp.getText()));
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        xpp.next();
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("url","파싱에러"+e);
        }
    }



    String getRawData(String pd_report_num){

        StringBuilder buffer=new StringBuilder();


        try {
            pd_report_num = URLEncoder.encode(pd_report_num,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    //   http://openapi.foodsafetykorea.go.kr/api/keyId/serviceId/dataType/startIdx/endIdx
    //PRDLST_NM	STRING(선택)	제품명	추가 파라미터값
    //http://openapi.foodsafetykorea.go.kr/api/1332c45fd60e4b9ca83c/C002/xml/1/5/PRDLST_NM=%22%EC%96%B4%ED%8F%AC%22

    //<RAWMTRL_NM>밀가루,곡류가공품,정제소금,변성전분,곡류가공품,정제수</RAWMTRL_NM>
    //<PRDLST_REPORT_NO>19930443028350</PRDLST_REPORT_NO>


        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/"//요청 URL
                +key+"/C002/xml/1/"+list_num+"/PRDLST_REPORT_NO="+pd_report_num;

        Log.d("raw_url",queryUrl);

        try {

            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결


            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();

            // inputstream 으로부터 xml 입력받기1
            xpp.setInput( new InputStreamReader(is, StandardCharsets.UTF_8) );

            String tag = "";

            xpp.next();

            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        break;

                    case XmlPullParser.TEXT:
                        Log.d("app","Text");

                       if(tag.equals("RAWMTRL_NM")){
                            buffer.append("원재료명: ");
                            buffer.append(xpp.getText());
                            Log.d("app",xpp.getText());
                            buffer.append("\n"); // 줄바꿈 문자 추가
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        if(tag.equals("row"))
                            buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
                        xpp.next();
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("url","파싱에러"+e);
        }

        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
    }


    String getMoreData(String pd_report_num){

        StringBuilder buffer=new StringBuilder();


        try {
            pd_report_num = URLEncoder.encode(pd_report_num,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/"//요청 URL
                +key+"/I1250/xml/1/"+list_num+"/PRDLST_REPORT_NO="+pd_report_num; // 품목제조번호

        //

        Log.d("api2",queryUrl);

        //<POG_DAYCNT>24개월 실온</POG_DAYCNT>
        //<PRDLST_REPORT_NO>19930443028350</PRDLST_REPORT_NO>
        //<HIENG_LNTRT_DVS_NM>해당없음</HIENG_LNTRT_DVS_NM>
        //<CHILD_CRTFC_YN/>


        try {

            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();

            // inputstream 으로부터 xml 입력받기1
            xpp.setInput( new InputStreamReader(is, StandardCharsets.UTF_8) );

            String tag = "";

            xpp.next();

            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작 단계 \n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        break;

                    case XmlPullParser.TEXT:
                        if(tag.equals("row")) ;

                        else if(tag.equals("POG_DAYCNT")){
                            buffer.append("유통기한: ");
                            buffer.append(xpp.getText());
                            buffer.append("\n"); // 줄바꿈 문자 추가
                        }

                        else if(tag.equals("HIENG_LNTRT_DVS_NM")){
                            buffer.append("고열량저영양식품여부 : ");
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }

                        else if(tag.equals("CHILD_CRTFC_YN")){
                            buffer.append("어린이기호식품품질인증여부 :");
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        if(tag.equals("row")) buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
                        xpp.next();
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("url2","파싱에러"+e);
        }
        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
    }
}
