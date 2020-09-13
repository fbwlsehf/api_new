package com.example.api.search;

import java.io.Serializable;

public class nutData implements Serializable {
    private String groupName,descKor,makerName;
    private String servingSize,nutcont1,nutcont2,nutcont3,nutcont4,nutcont5,nutcont6,nutcont7,nutcont8,nutcont9;


//7	GROUP_NAME	식품군
//8	DESC_KOR	식품이름
//10	MAKER_NAME	제조사명

//12	SERVING_SIZE	총내용량
//13	NUTR_CONT1	열량(kcal)(1회제공량당)
//14	NUTR_CONT2	탄수화물(g)(1회제공량당)
//15	NUTR_CONT3	단백질(g)(1회제공량당)
//            16	NUTR_CONT4	지방(g)(1회제공량당)
//            17	NUTR_CONT5	당류(g)(1회제공량당)
//            18	NUTR_CONT6	나트륨(mg)(1회제공량당)
//            19	NUTR_CONT7	콜레스테롤(mg)(1회제공량당)
//            20	NUTR_CONT8	포화지방산(g)(1회제공량당)
//            21	NUTR_CONT9	트랜스지방(g)(1회제공량당)


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescKor() {
        return descKor;
    }

    public void setDescKor(String descKor) {
        this.descKor = descKor;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }


    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public String getNutcont1() {
        return nutcont1;
    }

    public void setNutcont1(String nutcont1) {
        this.nutcont1 = nutcont1;
    }

    public String getNutcont2() {
        return nutcont2;
    }

    public void setNutcont2(String nutcont2) {
        this.nutcont2 = nutcont2;
    }

    public String getNutcont3() {
        return nutcont3;
    }

    public void setNutcont3(String nutcont3) {
        this.nutcont3 = nutcont3;
    }

    public String getNutcont4() {
        return nutcont4;
    }

    public void setNutcont4(String nutcont4) {
        this.nutcont4 = nutcont4;
    }

    public String getNutcont5() {
        return nutcont5;
    }

    public void setNutcont5(String nutcont5) {
        this.nutcont5 = nutcont5;
    }

    public String getNutcont6() {
        return nutcont6;
    }

    public void setNutcont6(String nutcont6) {
        this.nutcont6 = nutcont6;
    }

    public String getNutcont7() {
        return nutcont7;
    }

    public void setNutcont7(String nutcont7) {
        this.nutcont7 = nutcont7;
    }

    public String getNutcont8() {
        return nutcont8;
    }

    public void setNutcont8(String nutcont8) {
        this.nutcont8 = nutcont8;
    }

    public String getNutcont9() {
        return nutcont9;
    }

    public void setNutcont9(String nutcont9) {
        this.nutcont9 = nutcont9;
    }
}
