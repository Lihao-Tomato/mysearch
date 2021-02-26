package org.example.ui;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import org.example.model.FileMeta;
import org.example.service.FileService;
import org.example.task.FileScanner;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 14:33
 */
public class AppController implements Initializable {
    @FXML
    public GridPane rootPane;
    @FXML
    public Label srcDirectory;
    @FXML
    public TextField searchField;
    @FXML
    public TableView<FileMeta> fileTable;
    @FXML
    public TableColumn<FileMeta, String> nameColumn;
    @FXML
    public TableColumn<FileMeta, String> sizeColumn;
    @FXML
    public TableColumn<FileMeta, String> lastModifiedColumn;

    private final FileService fileService = new FileService();
    private final FileScanner fileScanner = new FileScanner();

    public void choose(MouseEvent mouseEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        File root = chooser.showDialog(rootPane.getScene().getWindow());
        if(root == null){
            return;
        }

        Thread thread = new Thread(() -> {
            try {
                fileScanner.scan(root);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void initialize(URL location, ResourceBundle resources) {
        // 会在 FXMLLoader.load 执行时，实例化好 AppController 后调用
        StringProperty stringProperty = searchField.textProperty();
        stringProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println("old:|" + oldValue + "|");
            System.out.println("new: |" + newValue + "|");

            List<FileMeta> fileList = fileService.query(newValue.trim());
            Platform.runLater(() -> {
                fileTable.getItems().clear();
                fileTable.getItems().addAll(fileList);
            });
        });
    }
}
