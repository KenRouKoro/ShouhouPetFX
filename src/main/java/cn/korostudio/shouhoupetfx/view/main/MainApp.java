package cn.korostudio.shouhoupetfx.view.main;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.URLUtil;
import cn.korostudio.shouhoupetfx.data.FXAppData;
import cn.korostudio.shouhoupetfx.data.NowSkinData;
import cn.korostudio.shouhoupetfx.system.PetSystem;
import cn.korostudio.shouhoupetfx.util.FXUtil;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jfxtras.styles.jmetro.Style;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    protected static Logger logger = LoggerFactory.getLogger(MainApp.class);

    @Getter
    protected Stage stage;
    @Getter
    protected Scene scene;
    @Getter
    protected Timeline mainPetViewTimeline;

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("FX is Start,Init start.");

        FXAppData.mainApp= this;
        this.stage = stage;

        initScreen();

        initStage();

        ThreadUtil.execute(()->{
            petInit();

            Platform.runLater(stage::show);

            petStart();
        });
    }

    protected void petStart(){
        logger.info("Start Pet System.");

        PetSystem.start();

        mainPetViewTimeline.play();
    }

    protected void petInit(){
        logger.info("Init Pets System.");

        PetSystem.init();

        FXAppData.mainAppController.petImageView.setImage(new Image(URLUtil.getURL(NowSkinData.NowPetImageUrl).toExternalForm()));

        KeyValue moveKeyValue = new KeyValue(FXAppData.mainAppController.petShowPanel.layoutYProperty(),100, Interpolator.EASE_BOTH);
        KeyFrame moveKeyFrame = new KeyFrame(Duration.millis(4000),moveKeyValue);

        mainPetViewTimeline  = new Timeline();
        mainPetViewTimeline.setCycleCount(Timeline.INDEFINITE);
        mainPetViewTimeline.setAutoReverse(true);
        mainPetViewTimeline.getKeyFrames().add(moveKeyFrame);

        FXAppData.mainAppController.textPanel.setOpacity(0);
        FXAppData.mainAppController.textPanel.setVisible(false);
    }

    protected void initScreen() throws IOException {
        logger.info("Init Screen.");
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Main.fxml"));

        scene = new Scene(fxmlLoader.load(),700,600);

        FXAppData.mainAppController = fxmlLoader.getController();

        FXUtil.SetMetro(scene,Style.LIGHT);
        URL mainCssUrl = this.getClass().getResource("Main.css");
        scene.getStylesheets().add(mainCssUrl.toExternalForm());

    }

    protected void initStage(){
        logger.info("Init Stage.");

        scene.setFill(null);

        stage.setTitle("ShouHouPet-JFX17");
        stage.setScene(scene);
        stage.getIcons().add(new Image("icon.png"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
    }
}
