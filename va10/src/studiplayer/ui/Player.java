package studiplayer.ui;

import java.net.URL;
import java.util.list.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import studiplayer.audio.PlayList;

public class Player extends Application {
    private PlayList playList;
    private String   playListPathname;
    public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";

    public Player() { }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> parameters = getParameters().getRaw();
        if (parameters.size() > 1)
        {
            this.playList = new PlayList(parameters.get(1));
            this.playListPathname = parameters.get(1);
        }
        else
        {
            this.playList = new PlayList(DEFAULT_PLAYLIST);
            this.playListPathname = DEFAULT_PLAYLIST;
        }

        BorderPane mainPane        = new BorderPane();
        Scene      scene           = new Scene(mainPane, 700, 90);
        HBox       hbox            = new HBox();
        Label      songDescription = new Label("no current song");
        Label      playTime        = new Label("--:--");
        Button     playButton      = createButton("play.png");
        Button     pauseButton     = createButton("pause.png");
        Button     stopButton      = createButton("stop.png");
        Button     nextButton      = createButton("next.png");
        Button     editorButton    = createButton("pl_editor.png");

        hbox.getChildren().addAll(playTime, playButton, pauseButton, stopButton, nextButton, editorButton);
        mainPane.setTop(songDescription);
        mainPane.setBottom(hbox);

        scene.getRoot().setStyle(".root { -fx-accent: #1e74c6; -fx-focus-color: -fx-accent; -fx-base: #373e43; -fx-control-inner-background: derive(-fx-base, 35%); -fx-control-inner-background-alt: -fx-control-inner-background ; } .label{ -fx-text-fill: lightgray; } .text-field { -fx-prompt-text-fill: gray; } .titulo{ -fx-font-weight: bold; -fx-font-size: 18px; } .button{ -fx-focus-traversable: false; } .button:hover{ -fx-text-fill: white; } .separator *.line { -fx-background-color: #3C3C3C; -fx-border-style: solid; -fx-border-width: 1px; } .scroll-bar{ -fx-background-color: derive(-fx-base,45%) } .button:default { -fx-base: -fx-accent ; }  .table-view{ /*-fx-background-color: derive(-fx-base, 10%);*/ -fx-selection-bar-non-focused: derive(-fx-base, 50%); } .table-view .column-header .label{ -fx-alignment: CENTER_LEFT; -fx-font-weight: none; } .list-cell:even, .list-cell:odd, .table-row-cell:even, .table-row-cell:odd{    -fx-control-inner-background: derive(-fx-base, 15%); } .list-cell:empty, .table-row-cell:empty { -fx-background-color: transparent; } .list-cell, .table-row-cell{ -fx-border-color: transparent; -fx-table-cell-border-color:transparent; }");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String iconfile) {
        Button button = null;
        try {
            URL url = getClass().getResource("/icons/" + iconfile);
            Image icon = new Image(url.toString());
            ImageView imageView = new ImageView(icon);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
            button = new Button("", imageView);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } catch (Exception e) {
            System.out.println("Image " + "icons/" + iconfile + " not found");
            System.exit(-1);
        }
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

