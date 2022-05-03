package studiplayer.ui;

import java.net.URL;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;

public class Player extends Application {
    private PlayList playList;
    private String   playListPathname;
    private Label    songDescription;
    private Label    playTime;
    private Stage    primaryStage;
    private Button   playButton;
    private Button   pauseButton;
    private Button   stopButton;
    private Button   nextButton;
    private Button   editorButton;

    private PlayListEditor playListEditor;
    private boolean        editorVisible;

    private volatile boolean stopped = true;

    public  static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
    private static final String INITIAL_POSITION = "00:00";
    private static final String NO_POSITION      = "--:--";
    private static final String CURRENT_SONG     = "Current song: ";
    private static final String NO_CURRENT_SONG  = "no current song";

    public Player() { }

    @Override
    public void start(Stage primaryStage) throws Exception {
System.out.println("\u001b[34m START \u001b[0m");
        this.primaryStage = primaryStage;

        List<String> parameters = getParameters().getRaw();
        if (parameters.size() > 1) {
            this.playList = new PlayList(parameters.get(1));
            this.playListPathname = parameters.get(1);
        } else {
            this.playList = new PlayList(DEFAULT_PLAYLIST);
            this.playListPathname = DEFAULT_PLAYLIST;
        }

        BorderPane mainPane = new BorderPane();
        Scene      scene    = new Scene(mainPane, 700, 90);
        HBox       hbox     = new HBox();

        this.playButton      = createButton("play.png");
        this.pauseButton     = createButton("pause.png");
        this.stopButton      = createButton("stop.png");
        this.nextButton      = createButton("next.png");
        this.editorButton    = createButton("pl_editor.png");
        this.playTime        = new Label(NO_POSITION);
        this.songDescription = new Label(NO_CURRENT_SONG);

        playButton.setOnAction (e -> { playCurrentSong (); });
        pauseButton.setOnAction(e -> { pauseCurrentSong(); });
        stopButton.setOnAction (e -> { stopCurrentSong (); });
        nextButton.setOnAction (e -> { nextSong        (); });

        editorButton.setOnAction(e -> {
            if (this.editorVisible) {
                this.editorVisible = false;
                playListEditor.hide();
            } else {
                this.editorVisible = true;
                playListEditor.show();
            }
        });

        hbox.getChildren().addAll(this.playTime, playButton, pauseButton, stopButton, nextButton, editorButton);
        mainPane.setTop(this.songDescription);
        mainPane.setBottom(hbox);

        scene.getRoot().setStyle(".root { -fx-accent: #1e74c6; -fx-focus-color: -fx-accent; -fx-base: #373e43; -fx-control-inner-background: derive(-fx-base, 35%); -fx-control-inner-background-alt: -fx-control-inner-background ; } .label{ -fx-text-fill: lightgray; } .text-field { -fx-prompt-text-fill: gray; } .titulo{ -fx-font-weight: bold; -fx-font-size: 18px; } .button{ -fx-focus-traversable: false; } .button:hover{ -fx-text-fill: white; } .separator *.line { -fx-background-color: #3C3C3C; -fx-border-style: solid; -fx-border-width: 1px; } .scroll-bar{ -fx-background-color: derive(-fx-base,45%) } .button:default { -fx-base: -fx-accent ; }  .table-view{ /*-fx-background-color: derive(-fx-base, 10%);*/ -fx-selection-bar-non-focused: derive(-fx-base, 50%); } .table-view .column-header .label{ -fx-alignment: CENTER_LEFT; -fx-font-weight: none; } .list-cell:even, .list-cell:odd, .table-row-cell:even, .table-row-cell:odd{    -fx-control-inner-background: derive(-fx-base, 15%); } .list-cell:empty, .table-row-cell:empty { -fx-background-color: transparent; } .list-cell, .table-row-cell{ -fx-border-color: transparent; -fx-table-cell-border-color:transparent; }");
        
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

        if (this.playList.size() > 0) {
            String songName = playList.getCurrentAudioFile().toString();
            this.songDescription.setText(songName);
            this.playTime.setText(INITIAL_POSITION);
            this.primaryStage.setTitle(CURRENT_SONG + songName);
        } else {
            this.songDescription.setText(NO_CURRENT_SONG);
            this.playTime.setText(NO_POSITION);
            this.primaryStage.setTitle(NO_CURRENT_SONG);
        }

        if (this.playList != null && playList.size() > 0) {
            setButtonStates(false, true, true, false, false);
        } else {
            setButtonStates(true, true, true, true, false);
        }

        this.playListEditor = new PlayListEditor(this, this.playList);
        this.editorVisible  = false;
    }

    private Button createButton(String iconfile) {
System.out.println("\u001b[34m START \u001b[0m");
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

    private void updateSongInfo(AudioFile af) {
System.out.println("\u001b[34m UPDATESONGINFO \u001b[0m");
        if (af == null) {
            this.songDescription.setText(NO_CURRENT_SONG);
            this.playTime.setText(NO_POSITION);
            this.primaryStage.setTitle(NO_CURRENT_SONG);
        } else {
            this.songDescription.setText(af.toString());
            this.playTime.setText(af.getFormattedPosition());
            this.primaryStage.setTitle(CURRENT_SONG + af.toString());
        }
    }

    public void playCurrentSong() {
System.out.println("\u001b[34m PLAYCURRENTSONG \u001b[0m");
        AudioFile af = this.playList.getCurrentAudioFile();
        updateSongInfo(af);
        this.stopped = false;
        if (af != null) {
            (new TimerThread()).start();
            (new PlayerThread()).start();
        }
        setButtonStates(true, false, false, false, false);
    }

    public void pauseCurrentSong() {
System.out.println("\u001b[34m PAUSECURRENTSONG \u001b[0m");
        AudioFile af = this.playList.getCurrentAudioFile();
        updateSongInfo(af);
        this.stopped = !this.stopped;
        setButtonStates(true, false, false, false, false);
        if (this.playList.size() > 0)
            af.togglePause();
        if (!this.stopped) {
            (new TimerThread()).start();
            (new PlayerThread()).start();
        }
    }

    public void stopCurrentSong() {
System.out.println("\u001b[34m STOPCURRENTSONG \u001b[0m");
        AudioFile af = this.playList.getCurrentAudioFile();
        if (this.playList.size() > 0)
            af.stop();
        updateSongInfo(af);
        this.stopped = true;
        setButtonStates(false, true, true, false, false);
    }

    public void nextSong() {
System.out.println("\u001b[34m NEXTSONG \u001b[0m");
        if (this.playList.size() > 0) {
            if (!this.stopped) {
                this.playList.getCurrentAudioFile().stop();
                this.stopped = true;
            }
            this.playList.changeCurrent();
            AudioFile af = this.playList.getCurrentAudioFile();
            updateSongInfo(af);
            this.stopped = false;
            setButtonStates(true, false, false, false, false);
        } else {
            if (this.playList.getCurrentAudioFile() != null)
            {
                this.playList.getCurrentAudioFile().stop();
                this.stopped = true;
            }
            updateSongInfo(null);
            setButtonStates(true, true, true, true, false);
        }
    }

    private void refreshUI() {
System.out.println("\u001b[34m REFRESHUI \u001b[0m");
        Platform.runLater(() -> {
            if (this.playList != null && playList.size() > 0) {
                updateSongInfo(this.playList.getCurrentAudioFile());
                //setButtonStates(false, true, false, true, false);
            } else {
                updateSongInfo(null);
                setButtonStates(true, true, true, true, false);
            }
        });
    }

    private void setButtonStates(boolean playButtonState, boolean pauseButtonState, boolean stopButtonState, boolean nextButtonState, boolean editorButtonState) {
System.out.println("\u001b[34m SETBUTTONSTATES \u001b[0m");
        this.playButton  .setDisable(playButtonState);
        this.pauseButton .setDisable(pauseButtonState);
        this.stopButton  .setDisable(stopButtonState);
        this.nextButton  .setDisable(nextButtonState);
        this.editorButton.setDisable(editorButtonState);
    }

    public void setEditorVisible(boolean editorVisible) {
System.out.println("\u001b[34m SETEDITORVISIBLE \u001b[0m");
        this.editorVisible = editorVisible;
    }

    public String getPlayListPathname() {
System.out.println("\u001b[34m GETPLAYLISTPATHNAME \u001b[0m");
        return this.playListPathname;
    }

    public void setPlayList(String playListPathname) {
System.out.println("\u001b[34m SETPLAYLIST \u001b[0m");
        if (playListPathname.equals("") || playListPathname == null) {
            this.playListPathname = DEFAULT_PLAYLIST;
            this.playList = new PlayList(DEFAULT_PLAYLIST);
        } else {
            this.playListPathname = playListPathname;
            this.playList = new PlayList(playListPathname);
        }
        refreshUI();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class TimerThread extends Thread {
        public void run() {
            while (!stopped && playList.size() > 0) {
System.out.println("\u001b[34m TIMERTHREAD-WHILE \u001b[0m");
                refreshUI();
                try {
                    sleep(100);
                } catch(InterruptedException e) { }
            }
        }
    }

    private class PlayerThread extends Thread {
        public void run() {
            while (!stopped && playList.size() > 0) {
System.out.println("\u001b[34m PLAYERTHREAD-WHILE \u001b[0m");
                try {
                    playList.getCurrentAudioFile().play();
                    if (!stopped) {
                        playList.changeCurrent();
                        Platform.runLater(() -> {
                            updateSongInfo(playList.getCurrentAudioFile());
                        });
                    }
                } catch (NotPlayableException e) { e.printStackTrace(); break; }
System.out.println("\u001b[34m PLAYERTHREAD-WHILEEND \u001b[0m");
            }
        }
    }
}

