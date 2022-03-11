package cn.korostudio.shouhoupetfx.data;

import java.util.ArrayList;

public class NowSkinData {
    public static int Mode = 0;

    public static ArrayList<String> TouchCueUrl = new ArrayList<>();
    public static ArrayList<String> TouchCueText = new ArrayList<>();
    public static ArrayList<Long> TouchCueTime = new ArrayList<>();

    public static ArrayList<String> SPTouchCueUrl = new ArrayList<>();
    public static ArrayList<String> SPTouchCueText = new ArrayList<>();
    public static ArrayList<Long> SPTouchCueTime = new ArrayList<>();

    //这里是百分比位置
    public static double[] SPXPoints = new double[4];
    public static double[] SPYPoints = new double[4];

    public static String NowPetImageUrl;

    public static ArrayList<String> MainCueUrl = new ArrayList<>();
    public static ArrayList<String> MainCueText = new ArrayList<>();
    public static ArrayList<Long> MainCueTime = new ArrayList<>();

    public static String ChangeCueUrl;
    public static String ChangeCueText;
    public static Long ChangeCueTime;
}
