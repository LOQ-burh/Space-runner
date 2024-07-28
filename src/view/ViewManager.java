	package view;

	import javafx.event.ActionEvent;
	import javafx.event.Event;
	import javafx.event.EventHandler;
	import javafx.scene.Scene;
	import javafx.scene.control.Button;
	import javafx.scene.effect.DropShadow;
	import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;
	import javafx.scene.input.MouseEvent;
	import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.InfoLabel;
import model.SHIP;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
	import java.util.List;

	public class ViewManager {
	    private static final int HEIGHT = 768;
	    private static final int WIDTH = 1024;
	    private AnchorPane mainPane;
	    private Scene mainScene;
	    private Stage mainStage;

	    private final static int MENU_BUTTONS_START_X = 100;
	    private final static int MENU_BUTTONS_START_Y = 150;
	    List<SpaceRunnerButton> menuButtons;


	    private SpaceRunnerSubScene creditsSubScene;
	    private SpaceRunnerSubScene helpSubScene;
	    private SpaceRunnerSubScene scoreSubScene;
	    private SpaceRunnerSubScene shipChooserSubScene;
	    private SpaceRunnerSubScene sceneToHide;
	    
	    List<ShipPicker> shipsList;
	    private SHIP choosenShip;

	    public ViewManager() {
	        menuButtons = new ArrayList<>();
	        mainPane = new AnchorPane();
	        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
	        mainStage = new Stage();
	        mainStage.setScene(mainScene);
	        createSubScene();
	        createButtons();
	        createBackground();
//	        createBackgroundMusic();
	        createLogo();
	    }

	    private void showSubScene(SpaceRunnerSubScene subScene){
	        if(sceneToHide != null){
	            sceneToHide.moveSubScene();
	        }
	        subScene.moveSubScene();
	        sceneToHide = subScene;
	    }

	    private void createSubScene() {
	        creditsSubScene = new SpaceRunnerSubScene();
	        mainPane.getChildren().add(creditsSubScene);

	        helpSubScene = new SpaceRunnerSubScene();
	        mainPane.getChildren().add(helpSubScene);

	        scoreSubScene = new SpaceRunnerSubScene();
	        mainPane.getChildren().add(scoreSubScene);
	        
	        createShipChooserSubScene();
	    }


	    private void createShipChooserSubScene() {
	        shipChooserSubScene = new SpaceRunnerSubScene();
	        mainPane.getChildren().add(shipChooserSubScene);

	        InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
	        chooseShipLabel.setLayoutX(110);
	        chooseShipLabel.setLayoutY(25);
	        shipChooserSubScene.getPane().getChildren().add(chooseShipLabel);
	        shipChooserSubScene.getPane().getChildren().add(createShipsToChoose());
	        shipChooserSubScene.getPane().getChildren().add(createButtonToStart());
	        
	    }

		public Stage getMainStage(){
	        return mainStage;
	    }
		
		private HBox createShipsToChoose(){
	        HBox box = new HBox();
	        box.setSpacing(20);
	        shipsList = new ArrayList<>();
	        for(SHIP ship : SHIP.values()){
	            ShipPicker shipToPick = new ShipPicker(ship);
	            shipsList.add(shipToPick);
	            box.getChildren().add(shipToPick);
	            shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                @Override
	                public void handle(MouseEvent event) {
	                    for (ShipPicker ship : shipsList){
	                        ship.setIsCircleChoosen(false);
	                    }
	                    shipToPick.setIsCircleChoosen(true);
	                    choosenShip = shipToPick.getShip();
	                }
	            });
	        }
	        box.setLayoutX(300 - (118*2));
	        box.setLayoutY(100);
	        return box;
	    }
		
		private SpaceRunnerButton createButtonToStart(){
	        SpaceRunnerButton startButton = new SpaceRunnerButton("START");
	        startButton.setLayoutX(350);
	        startButton.setLayoutY(300);
	        
	        startButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent actionEvent) {
	                if(choosenShip != null){
	                    GameViewManager gameManager =new GameViewManager();
	                    gameManager.createNewGame(mainStage, choosenShip);
	                }
	            }
	        });
	        
	        return startButton;
	    }

	    private void createButtons() {
	        createStartButton();
	        createScoresButton();
	        createHelpButton();
	        createCreditsButton();
	        createExitButton();
	    }

	    private void createStartButton(){
	        SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
	        addMenuButton(startButton);

//	        String soundPath = getClass().getResource("view/resources/sounds/click-a.ogg").toString();
//	        Media clickSound = new Media(soundPath);
//	        MediaPlayer clickMediaPlayer = new MediaPlayer(clickSound);
	        
	        startButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
//	            	clickMediaPlayer.play();
	                showSubScene(shipChooserSubScene);
	            }
	        });
	    }
	    private void createScoresButton(){
	        SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
	        addMenuButton(scoresButton);

	        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                showSubScene(scoreSubScene);
	            }
	        });
	    }
	    private void createHelpButton(){
	        SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
	        addMenuButton(helpButton);

	        helpButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                showSubScene(helpSubScene);
	            }
	        });
	    }
	    private void createCreditsButton(){
	        SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
	        addMenuButton(creditsButton);
	        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                showSubScene(creditsSubScene);
	            }
	        });
	    }
	    private void createExitButton(){
	        SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
	        addMenuButton(exitButton);
	        
	        exitButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                mainStage.close();
	            }
	        });
	    }
	    
	    Media media;
	    MediaPlayer mediaPlayer;
	    MediaView mediaView;
	    private void createBackgroundMusic(){
//	    	songs = new ArrayList<File>();
//	    	directory = new File("music");
//	    	files = directory.listFiles();
//	    	if(files != null) {
//				
//				for(File file : files) {
//					
//					songs.add(file);
//				}
//			}
	    	String resource;
			if(mediaView.getMediaPlayer() == null) {
				try {
					resource = getClass().getResource("view/music/spaceMusic.mp3").toURI().toString();
					media = new Media(resource);
			        mediaPlayer = new MediaPlayer(media);
			        mediaView.setMediaPlayer(mediaPlayer);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//	        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//	        mediaPlayer.setOnEndOfMedia( () -> mediaPlayer.seek(Duration.ZERO));
	        mediaView.getMediaPlayer().play();
	    }
	    
	  


	    private void createBackground() {
	        Image backgroundImage = new Image("view/resources/deep_blue.png",
	                256,
	                256,
	                false,
	                true);
	        BackgroundImage background = new BackgroundImage(
	                backgroundImage,
	                BackgroundRepeat.REPEAT,
	                BackgroundRepeat.REPEAT,
	                BackgroundPosition.DEFAULT,
	                null);
	        mainPane.setBackground(new Background(background));
	    }

	    private void addMenuButton(SpaceRunnerButton button){
	        button.setLayoutX(MENU_BUTTONS_START_X);
	        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
	        menuButtons.add(button);
	        mainPane.getChildren().add(button);
	    }

	    private void createLogo(){
	        ImageView logo = new ImageView("view/resources/space_runner.png");
	        logo.setLayoutX(400);
	        logo.setLayoutY(50);

	        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                logo.setEffect(new DropShadow());
	            }
	        });
	        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                logo.setEffect(null);
	            }
	        });
	        mainPane.getChildren().add(logo);
	    }
	}