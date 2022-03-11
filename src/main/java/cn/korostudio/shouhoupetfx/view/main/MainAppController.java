package cn.korostudio.shouhoupetfx.view.main;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.URLUtil;
import cn.korostudio.shouhoupetfx.data.FXAppData;
import cn.korostudio.shouhoupetfx.data.NowSkinData;
import cn.korostudio.shouhoupetfx.util.FXUtil;
import cn.korostudio.shouhoupetfx.view.dialog.ExitDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;


public class MainAppController implements Initializable {

    private boolean endTouch = true;

    private double rightXOffset = 0;
    private double rightYOffset = 0;

    private double toolXOffset = 0;
    private double toolYOffset = 0;

    protected Media petCueMedia;
    protected MediaPlayer petCueMediaPlayer =null;

    protected Timeline showTimeline;
    protected Timeline stopTimeline;

    protected Thread stopCallbackThread =null;

    protected Timeline rightToolShowTimeLine;
    protected Timeline rightToolStopTimeLine;
    protected boolean rightToolPanelShow = true;

    protected Timeline toolShowTimeLine;
    protected Timeline toolStopTimeLine;


    @FXML
    protected AnchorPane mainRightPanel;

    @FXML
    protected AnchorPane mainPanel;

    @FXML
    protected AnchorPane petShowPanel;

    @FXML
    protected ImageView petImageView;

    @FXML
    protected Label textLabel;

    @FXML
    protected AnchorPane textPanel;

    @FXML
    protected ImageView cofImageButton;

    @FXML
    protected HBox toolPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FlatSVGIcon flatSVGIcon = new FlatSVGIcon("zondicons/cog.svg",22,22);
        BufferedImage buttonBufferedImage  = new BufferedImage(30,30,BufferedImage.TYPE_INT_ARGB);
        buttonBufferedImage.getGraphics().setColor(Color.white);
        flatSVGIcon.paintIcon(null, buttonBufferedImage.getGraphics(), 4,4);
        cofImageButton.setImage(SwingFXUtils.toFXImage(buttonBufferedImage,null));



        KeyValue rightStartKeyValue = new KeyValue(mainRightPanel.opacityProperty(),1);
        KeyFrame rightStartKeyFrame = new KeyFrame(Duration.millis(400),rightStartKeyValue);

        rightToolShowTimeLine = new Timeline();
        rightToolShowTimeLine.getKeyFrames().add(rightStartKeyFrame);
        rightToolShowTimeLine.setCycleCount(1);
        rightToolShowTimeLine.setAutoReverse(false);

        KeyValue rightStopKeyValue = new KeyValue(mainRightPanel.opacityProperty(),0);
        KeyFrame rightStopKeyFrame = new KeyFrame(Duration.millis(400),rightStopKeyValue);

        rightToolStopTimeLine = new Timeline();
        rightToolStopTimeLine.getKeyFrames().add(rightStopKeyFrame);
        rightToolStopTimeLine.setCycleCount(1);
        rightToolStopTimeLine.setAutoReverse(false);

        KeyValue toolStartKeyValue = new KeyValue(toolPanel.opacityProperty(),1);
        KeyFrame toolStartKeyFrame = new KeyFrame(Duration.millis(400),toolStartKeyValue);

        toolShowTimeLine = new Timeline();
        toolShowTimeLine.getKeyFrames().add(toolStartKeyFrame);
        toolShowTimeLine.setCycleCount(1);
        toolShowTimeLine.setAutoReverse(false);

        KeyValue toolStopKeyValue = new KeyValue(toolPanel.opacityProperty(),0.05);
        KeyFrame toolStopKeyFrame = new KeyFrame(Duration.millis(400),toolStopKeyValue);

        toolStopTimeLine = new Timeline();
        toolStopTimeLine.getKeyFrames().add(toolStopKeyFrame);
        toolStopTimeLine.setCycleCount(1);
        toolStopTimeLine.setAutoReverse(false);
    }

    @FXML
    protected void toolEntered(MouseEvent event){
        toolStopTimeLine.stop();
        toolShowTimeLine.play();
    }

    @FXML
    protected void toolExited(MouseEvent event){
        toolShowTimeLine.stop();
        toolStopTimeLine.play();
    }

    @FXML
    protected void cofButtonClick(MouseEvent event){
        if(rightToolPanelShow){
            rightToolShowTimeLine.stop();
            rightToolStopTimeLine.play();
            rightToolPanelShow=false;
        }else{
            rightToolStopTimeLine.stop();
            rightToolShowTimeLine.play();
            rightToolPanelShow=true;
        }
    }

    @FXML
    protected void moveToolStart(MouseEvent event) {
        toolXOffset = event.getSceneX();
        toolYOffset = event.getSceneY();
    }

    @FXML
    protected void moveTool(MouseEvent event) {
        FXAppData.mainApp.getStage().setX(event.getScreenX() - toolXOffset);
        FXAppData.mainApp.getStage().setY(event.getScreenY() - toolYOffset);
    }

    @FXML
    protected void moveRightStart(MouseEvent event) {
        rightXOffset = event.getSceneX();
        rightYOffset = event.getSceneY();
    }

    @FXML
    protected void moveRight(MouseEvent event) {
        FXAppData.mainApp.getStage().setX(event.getScreenX() - rightXOffset);
        FXAppData.mainApp.getStage().setY(event.getScreenY() - rightYOffset);
    }

    @FXML
    protected void exitClick(MouseEvent event){
        ExitDialog.ShowDialog("确认","是否退出?",even->{
            System.exit(0);
        });
    }

    @FXML
    protected void touchPetEven(MouseEvent event) {
        if(!endTouch){
            return;
        }
        endTouch = false;

        ThreadLocalRandom random = ThreadLocalRandom.current();

        ThreadUtil.execute(()->{
            int x = (int) event.getX();
            int y = (int) event.getY();

            double []Xs={ (NowSkinData.SPXPoints[0]*500), (NowSkinData.SPXPoints[1]*500),(NowSkinData.SPXPoints[2]*500), (NowSkinData.SPXPoints[3]*500)},
                    Ys={ (NowSkinData.SPYPoints[0]*500),(NowSkinData.SPYPoints[1]*500), (NowSkinData.SPYPoints[2]*500),(NowSkinData.SPYPoints[3]*500)};

            boolean isSPTouch = FXUtil.isPointInRect(x,y,Xs,Ys);

            int index = isSPTouch? random.nextInt(NowSkinData.SPTouchCueUrl.size()):random.nextInt(NowSkinData.TouchCueUrl.size());


            FXAppData.mainApp.getMainPetViewTimeline().pause();

            double moveY = FXAppData.mainAppController.petShowPanel.getLayoutY() - 20;

            KeyValue touchKeyValue = new KeyValue(FXAppData.mainAppController.petShowPanel.layoutYProperty(),moveY , Interpolator.EASE_OUT);
            KeyFrame touchKeyFrame = new KeyFrame(Duration.millis(150), touchKeyValue);

            Timeline touchPetViewTimeline = new Timeline();

            touchPetViewTimeline.setCycleCount(2);
            touchPetViewTimeline.setAutoReverse(true);
            touchPetViewTimeline.getKeyFrames().add(touchKeyFrame);
            touchPetViewTimeline.setOnFinished(event1 -> {
                ThreadUtil.execute(()->{
                    ThreadUtil.sleep(300);
                    FXAppData.mainApp.getMainPetViewTimeline().play();
                });
            });

            Platform.runLater(()-> play( isSPTouch? NowSkinData.SPTouchCueUrl.get(index):NowSkinData.TouchCueUrl.get(index),
                    isSPTouch? NowSkinData.SPTouchCueText.get(index):NowSkinData.TouchCueText.get(index),
                    isSPTouch? NowSkinData.SPTouchCueTime.get(index):NowSkinData.TouchCueTime.get(index)));

            touchPetViewTimeline.play();
        });
    }

    protected void initMedia(String url){
        if(petCueMediaPlayer !=null){
            petCueMediaPlayer.stop();
            petCueMediaPlayer.dispose();
        }
        petCueMedia = new Media(URLUtil.getURL(url).toExternalForm());
        petCueMediaPlayer = new MediaPlayer(petCueMedia);
        petCueMediaPlayer.setAutoPlay(true);
    }

    public void play(String url,String text,long cueTime){

        if(url!=null){
            initMedia(url);
        }
        if(text !=null){
            textLabel.setText(text);
        }else{
            return;
        }

        if(textPanel.isVisible()){
            if(stopCallbackThread!=null&& (stopCallbackThread.getState() == Thread.State.TIMED_WAITING)){
                stopCallbackThread.interrupt();
                stopCallbackThread = new Thread(()->{
                    if(!ThreadUtil.sleep(cueTime+1000)){
                        return;
                    }
                    stopTimeline.play();
                });
                stopCallbackThread.start();
            }else if(stopTimeline!=null){
                stopTimeline.stop();
                showTimeline.stop();
                showTimeline.play();
                stopCallbackThread = new Thread(()->{
                    if(!ThreadUtil.sleep(cueTime+1000)){
                        return;
                    }
                    stopTimeline.play();
                    stopCallbackThread=null;
                });
                stopCallbackThread.start();
            }
            return;
        }

        KeyValue startKeyValue = new KeyValue(textPanel.opacityProperty(),1);
        KeyFrame startKeyFrame = new KeyFrame(Duration.millis(800),startKeyValue);

        showTimeline = new Timeline();
        showTimeline.setCycleCount(1);
        showTimeline.setAutoReverse(false);
        showTimeline.getKeyFrames().add(startKeyFrame);

        KeyValue stopKeyValue = new KeyValue(textPanel.opacityProperty(),0);
        KeyFrame stopKeyFrame = new KeyFrame(Duration.millis(800),stopKeyValue);

        stopTimeline = new Timeline();
        stopTimeline.setCycleCount(1);
        stopTimeline.setAutoReverse(false);
        stopTimeline.getKeyFrames().add(stopKeyFrame);
        stopTimeline.setOnFinished(event -> {
            stopTimeline = null;
            textPanel.setVisible(false);
            endTouch = true;
        });

        textPanel.setVisible(true);
        showTimeline.play();
        stopCallbackThread = new Thread(()->{
            if(!ThreadUtil.sleep(cueTime+1000)){
                return;
            }
            //showTimeline = null;
            stopTimeline.play();
            stopCallbackThread=null;
        });
         stopCallbackThread.start();
    }

}
