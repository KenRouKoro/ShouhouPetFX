package cn.korostudio.shouhoupetfx.system;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.cron.CronUtil;
import cn.korostudio.shouhoupetfx.data.FXAppData;
import javafx.application.Platform;

import java.util.List;

import static cn.korostudio.shouhoupetfx.data.NowSkinData.*;

public class PetSystem {

    public static void init(){
        testInit();
        CronUtil.setMatchSecond(true);
        CronUtil.schedule("*/1 * * * *",new MainCueTimer());
    }

    public static void start(){
        testStart();
        CronUtil.start();
    }

    public static void readPet(String url){

    }

    protected static void testInit(){
        NowPetImageUrl="shouhou/image/祥凤.png";
        TouchCueUrl.add("shouhou/cue/普通触摸 未改造.wav");
        TouchCueText.add("虽然吾不在乎，但是最好别对其他人也这样！");
        TouchCueTime.add(4000L);

        MainCueText.addAll(List.of(new String[]{"呀，指挥官，今天也是干劲十足的一天呢，吾已经准备好把那些蠢货痛扁一顿了～","我、吾平时都很受姐姐们的照顾……说实话真想快点成为能独当一面的人啊。","指挥官，今天天气这么好不要闷不做声啦！来，一~起~玩~嘛~"}));
        MainCueUrl.addAll(List.of(new String[]{"shouhou/cue/主界面1 未改造.wav","shouhou/cue/主界面2 未改造.wav","shouhou/cue/主界面3 未改造.wav"}));
        MainCueTime.addAll(List.of(new Long[]{7000L,8000L,10000L}));

        SPXPoints[0] = 0.506;
        SPXPoints[1] = 0.482;
        SPXPoints[2] = 0.58;
        SPXPoints[3] = 0.606;
        SPYPoints[0] = 0.386;
        SPYPoints[1] = 0.442;
        SPYPoints[2] = 0.494;
        SPYPoints[3] = 0.428;


        SPTouchCueText.add("嗯……这种事，还是等晚上再继续会好点……");
        SPTouchCueUrl.add("shouhou/cue/特殊触摸 未改造.wav");
        SPTouchCueTime.add(7000L);
    }

    protected static void testStart(){

    }

    protected static class MainCueTimer implements Runnable{
        @Override
        public void run(){
                int index = RandomUtil.randomInt(0, MainCueUrl.size());

                String cueUrl = MainCueUrl.get(index), cueText = MainCueText.get(index);

                Long cueTime = MainCueTime.get(index);

                Platform.runLater(()-> FXAppData.mainAppController.play(cueUrl, cueText, cueTime));
        }
    }

}
