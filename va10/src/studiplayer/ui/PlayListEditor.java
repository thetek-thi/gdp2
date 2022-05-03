package studiplayer.ui;

import java.io.File;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import studiplayer.audio.AudioFile;
import studiplayer.audio.AudioFileFactory;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;
import studiplayer.audio.TaggedFile;

public class PlayListEditor {
	private Stage stage;
	private Player player;
	private PlayList playList;
	private boolean rand = false;
	private ImageView randOn, randOff;
	private ObservableList<Song> tableData;
	
	public PlayListEditor(Player player, PlayList playList) {
		this.player = player;
		this.playList = playList;
		randOn = createIcon("random.png");
		randOff = createIcon("random_no.png");
		stage = new Stage();
		stage.setTitle("Playlist - " + playList.size() + " Songs");

		BorderPane mainPane = new BorderPane();
		TableView<Song> tableView = createTable();
		FlowPane bottom = new FlowPane();

		// bottom Pane
		Button addButton = createButton(createIcon("add.png"));
		addButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Song hinzufügen");
			File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				String path = selectedFile.getAbsolutePath();
				AudioFile af;
				try {
					af = AudioFileFactory.getInstance(path);
				} catch (NotPlayableException e1) {
					e1.printStackTrace();
					return;
				}
				String album = "";
				
				if (af instanceof TaggedFile) {
					album = ((TaggedFile) af).getAlbum();
				}
				Song song = new Song(playList.size(), af, af.getAuthor(), 
						af.getTitle(), album, af.getFormattedDuration());
	            playList.add(af);
	            tableData.add(song);
	            playlistChanged();				
			}
		});
		Button delButton = createButton(createIcon("sub.png"));
		delButton.setOnAction(e -> {
			Song selectedSong = tableView.getSelectionModel().getSelectedItem();
  			
  			if (selectedSong == null) {
  				return;
  			}
            playList.remove(selectedSong.getAudioFile());
            tableData.remove(selectedSong);
            playlistChanged();
		});
		Button randomButton = createButton(randOn);
		randomButton.setOnAction(e -> {
            rand = !rand;
            playList.setRandomOrder(rand);
            if (rand) {
                randomButton.setGraphic(randOff);
            } else {
                randomButton.setGraphic(randOn);
            }
            tableData.clear();
            refreshTableData();
		});
		bottom.getChildren().addAll(addButton, delButton, randomButton);
		bottom.setAlignment(Pos.CENTER);
		FlowPane.setMargin(addButton, new Insets(8, 5, 8, 5));
		FlowPane.setMargin(delButton, new Insets(8, 5, 8, 5));
		FlowPane.setMargin(randomButton, new Insets(8, 5, 8, 5));
		bottom.setVgap(10);
		
		mainPane.setCenter(tableView);
		mainPane.setBottom(bottom);
		
        Scene scene = new Scene(mainPane);
        scene.getRoot().setStyle(".root { -fx-accent: #1e74c6; -fx-focus-color: -fx-accent; -fx-base: #373e43; -fx-control-inner-background: derive(-fx-base, 35%); -fx-control-inner-background-alt: -fx-control-inner-background ; } .label{ -fx-text-fill: lightgray; } .text-field { -fx-prompt-text-fill: gray; } .titulo{ -fx-font-weight: bold; -fx-font-size: 18px; } .button{ -fx-focus-traversable: false; } .button:hover{ -fx-text-fill: white; } .separator *.line { -fx-background-color: #3C3C3C; -fx-border-style: solid; -fx-border-width: 1px; } .scroll-bar{ -fx-background-color: derive(-fx-base,45%) } .button:default { -fx-base: -fx-accent ; }  .table-view{ /*-fx-background-color: derive(-fx-base, 10%);*/ -fx-selection-bar-non-focused: derive(-fx-base, 50%); } .table-view .column-header .label{ -fx-alignment: CENTER_LEFT; -fx-font-weight: none; } .list-cell:even, .list-cell:odd, .table-row-cell:even, .table-row-cell:odd{    -fx-control-inner-background: derive(-fx-base, 15%); } .list-cell:empty, .table-row-cell:empty { -fx-background-color: transparent; } .list-cell, .table-row-cell{ -fx-border-color: transparent; -fx-table-cell-border-color:transparent; }");
		stage.setScene(scene);
		stage.setWidth(512.);
		stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
			this.player.setEditorVisible(false);
		});		
	}
	
    private void playlistChanged() {
        stage.setTitle("Playlist - " + playList.size() + " Songs");
        playList.saveAsM3U(player.getPlayListPathname());
    }

	private ImageView createIcon(String iconfile) {
		ImageView imageView = null;
		
		try {
			URL url = getClass().getResource("/icons/" + iconfile);
			Image icon = new Image(url.toString());
			imageView = new ImageView(icon);
			imageView.setFitHeight(48);	
			imageView.setFitWidth(48);	
		} catch (Exception e) {
			System.out.println("Image " + "icons/" + iconfile + " nicht gefunden!");
			System.exit(-1);
		}
		return imageView;
	}
	
	private Button createButton(ImageView icon) {
		Button button = null;
		button = new Button("", icon);
		button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		return button;
	}
	
	public void show() {
		refreshTableData();
		
		stage.show();
	}

	public void hide() {
		stage.hide();
	}
	
	public void close() {
		stage.close();
	}
	
	private TableView<Song> createTable() {
		/*
		 * Daten für Tabelle erzeugen
		 */
		tableData = FXCollections.observableArrayList();
		refreshTableData();

		/*
		 * TableView erzeugen und Daten setzen
		 */
		TableView<Song> tableView = new TableView<>(tableData);

		/*
		 * Spalten definieren und der tableView bekannt machen
		 */
        TableColumn<Song, String> interpretColumn = new TableColumn<>("Interpret");
        interpretColumn.setCellValueFactory(
				new PropertyValueFactory<Song, String>("interpret"));
		TableColumn<Song, String> titelColumn = new TableColumn<>("Titel");
		titelColumn.setCellValueFactory(
				new PropertyValueFactory<Song, String>("titel"));
		TableColumn<Song, String> albumColumn = new TableColumn<>("Album");
		albumColumn.setCellValueFactory(
				new PropertyValueFactory<Song, String>("album"));
		TableColumn<Song, String> laengeColumn = new TableColumn<>("Länge");
		laengeColumn.setCellValueFactory(
				new PropertyValueFactory<Song, String>("laenge"));
		tableView.getColumns().add(interpretColumn);
		tableView.getColumns().add(titelColumn);
		tableView.getColumns().add(albumColumn);
		tableView.getColumns().add(laengeColumn);
        tableView.setEditable(false);

        /*
         * Doppelklick-Handler setzen
         */
        tableView.setOnMouseClicked(e -> {
  			Song selectedSong = tableView.getSelectionModel().getSelectedItem();
  			
  			if (selectedSong == null) {
  				return;
  			}
  			if (e.getClickCount() == 2) {
  				playList.setCurrent(selectedSong.getIndex());
  				player.playCurrentSong();
  			}
        });
   
        return tableView;
	}
	
	private void refreshTableData() {
		int i = 0;
		
		tableData.clear();
		for (AudioFile af : playList) {
			String album = "";
			
			if (af instanceof TaggedFile) {
				album = ((TaggedFile) af).getAlbum();
			}
			Song song = new Song(i++, af, af.getAuthor(), af.getTitle(), album, af.getFormattedDuration());
			tableData.add(song);
		}
	}
}

