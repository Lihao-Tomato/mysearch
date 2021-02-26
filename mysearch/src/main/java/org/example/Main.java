package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.service.DBService;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 14:34
 */
public class Main extends Application {
    public void start(Stage primaryStage) throws Exception {
        URL url = Main.class.getClassLoader().getResource("app.fxml");
        if(url == null){
            throw new RuntimeException("app.fxml 没有找到");
        }
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        primaryStage.setTitle("本地文件搜索工具");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(900);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        DBService dbService = new DBService();
        dbService.init();

        launch(args);
    }
}
