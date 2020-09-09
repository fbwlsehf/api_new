//package com.example.api;
//
//import android.util.Log;
//import android.view.View;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//public class temp {
//
//    String getXmlData(){
//
//        StringBuilder buffer=new StringBuilder();
//
//        String str= productName.getText().toString();//EditText에 작성된 Text얻어오기
//        String name = null;
//        try {
//            name = URLEncoder.encode(str,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//
//        //   http://openapi.foodsafetykorea.go.kr/api/keyId/serviceId/dataType/startIdx/endIdx
//        //PRDLST_NM	STRING(선택)	제품명	추가 파라미터값
//        //http://openapi.foodsafetykorea.go.kr/api/1332c45fd60e4b9ca83c/C002/xml/1/5/PRDLST_NM=%22%EC%96%B4%ED%8F%AC%22
//
//        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/"//요청 URL
//                +key+"/C002/xml/1/"+list_num+"/PRDLST_NM="+name;
//
//        Log.d("api1",queryUrl);
//
//
//        try {
//
//            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
//            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결
//
//
//            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
//            XmlPullParser xpp= factory.newPullParser();
//
//            // inputstream 으로부터 xml 입력받기1
//            xpp.setInput( new InputStreamReader(is, StandardCharsets.UTF_8) );
//
//            String tag = "";
//            String pd_name = "";
//
//            xpp.next();
//
//            int eventType= xpp.getEventType();
//
//            while( eventType != XmlPullParser.END_DOCUMENT ){
//                switch( eventType ){
//                    case XmlPullParser.START_DOCUMENT:
//                        break;
//
//                    case XmlPullParser.START_TAG:
//                        tag= xpp.getName(); // 태그 이름 얻어오기
//                        break;
//
//                    case XmlPullParser.TEXT:
//                        Log.d("app","Text");
//
//                        if(tag.equals("row")) ;
//
//                        else if(tag.equals("PRDLST_DCNM")){
//                            buffer.append("분류: ");
//                            buffer.append(xpp.getText());
//                            Log.d("app",xpp.getText());
//                            buffer.append("\n"); // 줄바꿈 문자 추가
//                        }
//
//                        else if(tag.equals("PRDLST_NM")){
//                            buffer.append("제품명 : ");
//                            buffer.append(xpp.getText());
//                            Log.d("app",xpp.getText());
//                            pd_name = xpp.getText();
//                            buffer.append("\n");
//
//                        }
//
//                        else if(tag.equals("RAWMTRL_NM")){
//                            buffer.append("원재료명 :");
//                            buffer.append(xpp.getText());
//                            Log.d("app",xpp.getText());
//                            buffer.append("\n");
//                        }
//
//                        else if(tag.equals("PRDLST_REPORT_NO")){
//                            buffer.append(getXmlData2(xpp.getText()));
//                        }
//
//                        else if(tag.equals("BSSH_NM"))
//                        {
//                            buffer.append(getXmlData3(pd_name,xpp.getText()));
//                        }
//
//                        break;
//
//                    case XmlPullParser.END_TAG:
//                        tag= xpp.getName(); // 태그 이름 얻어오기
//                        if(tag.equals("row"))
//                            buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
//                        xpp.next();
//                        break;
//                }
//                eventType= xpp.next();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("url","파싱에러"+e);
//        }
//
//
//        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
//    }
//
//
//
//
//    String getXmlData2(String pd_num){
//
//        StringBuilder buffer=new StringBuilder();
//
//
//        try {
//            pd_num = URLEncoder.encode(pd_num,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/"//요청 URL
//                +key+"/I1250/xml/1/"+list_num+"/PRDLST_REPORT_NO="+pd_num; // 품목제조번호
//
//
//        Log.d("api2",queryUrl);
//
//
//        try {
//
//            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
//            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결
//
//            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
//            XmlPullParser xpp= factory.newPullParser();
//
//            // inputstream 으로부터 xml 입력받기1
//            xpp.setInput( new InputStreamReader(is, StandardCharsets.UTF_8) );
//
//            String tag = "";
//
//            xpp.next();
//
//            int eventType= xpp.getEventType();
//
//            while( eventType != XmlPullParser.END_DOCUMENT ){
//                switch( eventType ){
//                    case XmlPullParser.START_DOCUMENT:
//                        buffer.append("파싱 시작 단계 \n\n");
//                        break;
//
//                    case XmlPullParser.START_TAG:
//                        tag= xpp.getName(); // 태그 이름 얻어오기
//                        break;
//
//                    case XmlPullParser.TEXT:
//                        if(tag.equals("row")) ;
//
//                        else if(tag.equals("POG_DAYCNT")){
//                            buffer.append("유통기한: ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n"); // 줄바꿈 문자 추가
//                        }
//
//                        else if(tag.equals("HIENG_LNTRT_DVS_NM")){
//                            buffer.append("고열량저영양식품여부 : ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        }
//
//                        else if(tag.equals("CHILD_CRTFC_YN")){
//                            buffer.append("어린이기호식품품질인증여부 :");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        }
//                        break;
//
//                    case XmlPullParser.END_TAG:
//                        tag= xpp.getName(); // 태그 이름 얻어오기
//                        if(tag.equals("row")) buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
//                        xpp.next();
//                        break;
//                }
//                eventType= xpp.next();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("url2","파싱에러"+e);
//        }
//
//        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
//
//    }
//
//
//
//
//    String getXmlData3(String pd_name,String bss_name){
//
//        StringBuilder buffer=new StringBuilder();
//
//
//        try {
//            pd_name = URLEncoder.encode(pd_name,"UTF-8");
//            bss_name = URLEncoder.encode(bss_name,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        // http://openapi.foodsafetykorea.go.kr/api/1332c45fd60e4b9ca83c/I2790/xml/1/6/DESC_KOR=%22%EC%96%BC%ED%81%B0%ED%95%9C%20%EB%84%88%EA%B5%AC%EB%A6%AC%22&MAKER_NAME=%22%EB%86%8D%EC%8B%AC%22
//        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/"//요청 URL
//                +key+"/I2790/xml/1/"+list_num+"/DESC_KOR="+ pd_name; // + "&MAKER_NAME="+bss_name; // 품목제조번호
//
//
//        Log.d("api3",queryUrl);
//
//
//        try {
//
//            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
//            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결
//
//            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
//            XmlPullParser xpp= factory.newPullParser();
//
//            // inputstream 으로부터 xml 입력받기1
//            xpp.setInput( new InputStreamReader(is, StandardCharsets.UTF_8) );
//
//            String tag = "";
//
//            xpp.next();
//
//            int eventType= xpp.getEventType();
//
//            while( eventType != XmlPullParser.END_DOCUMENT ){
//                switch( eventType ){
//                    case XmlPullParser.START_DOCUMENT:
//                        buffer.append("파싱 시작 단계 \n\n");
//                        break;
//
//                    case XmlPullParser.START_TAG:
//                        tag= xpp.getName(); // 태그 이름 얻어오기
//                        break;
//
//                    case XmlPullParser.TEXT:
//                        if(tag.equals("row")) ;
//
//                        else if(tag.equals("SERVING_SIZE")){
//                            buffer.append("총내용량: ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n"); // 줄바꿈 문자 추가
//                        }
//
//                        else if(tag.equals("NUTR_CONT1")){
//                            buffer.append("열량(kcal)(1회제공량당): ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        }
//
//                        else if(tag.equals("NUTR_CONT2")){
//                            buffer.append("탄수화물(g)(1회제공량당): ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        }
//
//                        else if(tag.equals("NUTR_CONT3")){
//                            buffer.append("단백질(g)(1회제공량당):");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        }
//                        else if(tag.equals("NUTR_CONT4")){
//                            buffer.append("지방(g)(1회제공량당): ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n"); // 줄바꿈 문자 추가
//                        }
//
//                        else if(tag.equals("NUTR_CONT5")){
//                            buffer.append("당류(g)(1회제공량당): ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        }
//
//                        else if(tag.equals("NUTR_CONT6")){
//                            buffer.append("나트륨(mg)(1회제공량당): ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        }
//                        else if(tag.equals("NUTR_CONT7")){
//                            buffer.append("콜레스테롤(mg)(1회제공량당): ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n"); // 줄바꿈 문자 추가
//                        }
//
//                        else if(tag.equals("NUTR_CONT8")){
//                            buffer.append("포화지방산(g)(1회제공량당): ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        }
//
//                        else if(tag.equals("NUTR_CONT9")){
//                            buffer.append("트랜스지방(g)(1회제공량당): ");
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        }
//                        break;
//
//                    case XmlPullParser.END_TAG:
//                        tag= xpp.getName(); // 태그 이름 얻어오기
//                        if(tag.equals("row")) buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
//                        xpp.next();
//                        break;
//                }
//                eventType= xpp.next();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("url3","파싱에러"+e);
//        }
//
//        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
//
//    }
//
//}
//
//
////http://openapi.foodsafetykorea.go.kr/api/sample/I1250/xml/1/5/PRDLST_REPORT_NO=20180384540102
//
//
////
////<I1250>
////<total_count>1</total_count>
////<RESULT>
////<CODE>INFO-000</CODE>
////<MSG>정상처리되었습니다.</MSG>
////</RESULT>
//
////<row id="0">
////<PRDLST_NM>바나나우유</PRDLST_NM>품목명
////<PRMS_DT>20200223</PRMS_DT>허가일자
////<POG_DAYCNT>냉장1개월, 냉동6개월</POG_DAYCNT>유통기한
////<PRDLST_REPORT_NO>20180384540102</PRDLST_REPORT_NO>품목제조번호
////<QLITY_MNTNC_TMLMT_DAYCNT/>품질유지기한일수
////<PRODUCTION>아니오</PRODUCTION>생산종료여부
////<HIENG_LNTRT_DVS_NM>해당없음</HIENG_LNTRT_DVS_NM>고열량저영양식품여부
////<PRDLST_DCNM>과자</PRDLST_DCNM>유형
////<BSSH_NM>온울</BSSH_NM>업소명
////<LCNS_NO>20180384540</LCNS_NO>인허가번호
////<LAST_UPDT_DTM>20200225</LAST_UPDT_DTM>최종수정일자
////<INDUTY_CD_NM>식품제조가공업</INDUTY_CD_NM>업종
////<CHILD_CRTFC_YN/>어린이기호식품품질인증여부
////</row>
////</I1250>
//
//
