package cn.korostudio.shouhoupetfx.main;

import cn.korostudio.shouhoupetfx.KoroStudioINFO;
import cn.korostudio.shouhoupetfx.view.main.MainApp;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        //打印版权
        KoroStudioINFO.LogInfo();
        //启动FX应用
        Application.launch(MainApp.class,args);
    }
}
