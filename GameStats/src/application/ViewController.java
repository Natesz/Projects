package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ViewController implements Initializable{

	@FXML
	Pane gamesPane;
	@FXML
	Pane statPane;
	@FXML
	Pane showPlayers;
	@FXML
	Pane createPlayerPane;
	@FXML
	Button createPlayerButton;
	@FXML
	TextField exportTextField;
	@FXML
	Button exportButton;
	@FXML
	Button addNewContactButton;
	@FXML
	TextField inputName;
	@FXML
	TextField inputNick;
	@FXML
	TableView table;
	@FXML
	TableView statsTableView;
	@FXML
	StackPane menuPane;
	@FXML
	Pane playersPane;
	@FXML
	Pane exportPane;
	@FXML
	AnchorPane anchor;
	@FXML
	SplitPane mainSplit;
	@FXML
	TextField name;
	@FXML
	TextField nickName;
	@FXML
	ChoiceBox<String> nickCBox4;
	@FXML
	ChoiceBox<String> nickCBox3;
	@FXML
	ChoiceBox<String> nickCBox2;
	@FXML
	ChoiceBox<String> nickCBox1;
	@FXML
	ChoiceBox<String> winnerCBox;
	@FXML
	ChoiceBox<String> winnerStatCBox;
	@FXML
	Button createGame;
	@FXML
	Button showStatistics;
	@FXML
	TableView gamesTableView;
	
	DB db = new DB();
	
	private final ObservableList<Player> data =	FXCollections.observableArrayList();
	
	private final ObservableList<String> nicks = FXCollections.observableArrayList();
	
	private final ObservableList<Statistics> games = FXCollections.observableArrayList();
	
	private final ObservableList<Statistics> stats = FXCollections.observableArrayList();
	
	public void alert(String text) {
		mainSplit.setDisable(true);
		mainSplit.setOpacity(0.4);
		
		Label label = new Label(text);
		Button alertButton = new Button("OK");
		VBox vbox = new VBox(label,alertButton);
		vbox.setAlignment(Pos.CENTER);

		anchor.getChildren().add(vbox);
		anchor.setTopAnchor(vbox, 300.0);
		anchor.setLeftAnchor(vbox, 400.0);

		alertButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent e) {
				mainSplit.setDisable(false);
				mainSplit.setOpacity(1);
				vbox.setVisible(false);
			}
		});
	}
	
	public void createGameButton() {
		
		String winner = winnerCBox.getValue();
		String player1 = nickCBox1.getValue();
		String player2 = nickCBox2.getValue();
		String player3 = nickCBox3.getValue();
		String player4 = nickCBox4.getValue();
		int playerCounter = 0;
		if(winner!=null && (winner.equals(player1) || winner.equals(player2) || winner.equals(player3) || winner.equals(player4))) {
			if(player1!=null && !player1.equals(player2) && !player1.equals(player3) && !player1.equals(player4)) {
				playerCounter++;
			};
			if(player2!=null && !player2.equals(player1) && !player2.equals(player3) && !player2.equals(player4)) {
				playerCounter++;
			};
			if(player3!=null && !player3.equals(player2) && !player3.equals(player1) && !player3.equals(player4)) {
				playerCounter++;
			};
			if(player4!=null && !player4.equals(player2) && !player4.equals(player3) && !player4.equals(player1)) {
				playerCounter++;
			};
					
			
			if(playerCounter>2) {
				Statistics stat = new Statistics(player1,player2,player3,player4,winner);
				games.add(stat);
				db.addStatistic(stat);
			}else {
				alert("Need 3 or 4 different player!");
			}
		}
		
		if(winner==null) {
			alert("Need winner!");
		}else if(!winner.equals(player1) && !winner.equals(player2) && !winner.equals(player3) && !winner.equals(player4)){
			alert("Pick winner from players!");
		}
		
		resetPlayers();
		
	}
	
	public void showStatistics(){
		stats.clear();
		stats.addAll(db.getStatistic(winnerStatCBox.getValue()));
		statsTableView.setItems(stats);
	}
	
	public void resetPlayers() {
		winnerCBox.setValue(null);
		nickCBox1.setValue(null);
		nickCBox2.setValue(null);
		nickCBox3.setValue(null);
		nickCBox4.setValue(null);
	}
	
	public void resetStats() {
		stats.clear();
		stats.addAll(db.getAllGames());
		statsTableView.setItems(stats);
	}
		
	public void addNewContactButton() {
		if(inputName.getText() == null || inputName.getText().equals("") || inputNick.getText().equals("") || inputNick.getText() == null) {
			alert("Player Name and Nickname can't be empty!");
		}else {
			boolean isDuplicateNick = false;
			
			for(String nick : nicks) {
				if (nick.equals(inputNick.getText())){
					isDuplicateNick = true;
				}
			}
			
			if(!isDuplicateNick) {
				Player newPerson = new Player(inputName.getText(), inputNick.getText()); 
				data.add(newPerson);
				db.addUser(newPerson);
				nicks.add(newPerson.getNick());
				inputName.setText(null);
				inputNick.setText(null);
			}else {
				alert("Nick already exist!");
			}
		}
	}
	
	public void exportButton() {
		String pdfFileName = exportTextField.getText();
		PdfGeneration pdfGeneration = new PdfGeneration();
		if(exportTextField.getText().equals("")) {
			alert("Enter a filename!");
		}else {
		pdfGeneration.pdfGeneration(pdfFileName,stats);
		}
	}
	
	public void setGamesData() {
		TableColumn<Statistics, String> player1Col = new TableColumn<Statistics, String>("Player 1");
		player1Col.setMinWidth(100);
		player1Col.setCellValueFactory(new PropertyValueFactory<Statistics, String>("player1"));
		
		TableColumn<Statistics, String> player2Col = new TableColumn<Statistics, String>("Player 2");
		player2Col.setMinWidth(100);
		player2Col.setCellValueFactory(new PropertyValueFactory<Statistics, String>("player2"));
		
		TableColumn<Statistics, String> player3Col = new TableColumn<Statistics, String>("Player 3");
		player3Col.setMinWidth(100);
		player3Col.setCellValueFactory(new PropertyValueFactory<Statistics, String>("player3"));
		
		TableColumn<Statistics, String> player4Col = new TableColumn<Statistics, String>("Player 4");
		player4Col.setMinWidth(100);
		player4Col.setCellValueFactory(new PropertyValueFactory<Statistics, String>("player4"));
		
		TableColumn<Statistics, String> winner = new TableColumn<Statistics, String>("Winner");
		winner.setMinWidth(100);
		winner.setCellValueFactory(new PropertyValueFactory<Statistics, String>("winner"));
		
		TableColumn removeCol = new TableColumn ("Delete");
		
		Callback<TableColumn<Statistics, String>, TableCell<Statistics, String>> cellFactory = 
				new Callback<TableColumn<Statistics, String>, TableCell<Statistics, String>>()
				{
					@Override
					public TableCell call(final TableColumn<Statistics, String> param)
					{
						final TableCell<Statistics, String> cell = new TableCell<Statistics, String>()
								{
									final Button btn = new Button("Delete");
									
									@Override
									public void updateItem( String item, boolean empty)
									{
										super.updateItem( item, empty );
										if( empty )
										{
											setGraphic(null);
											setText(null);
										}
										else
										{
											btn.setOnAction( ( ActionEvent ) ->
													{
														Statistics statistic = getTableView().getItems().get( getIndex() );
														games.remove(statistic);
														db.removeGame(statistic);
												} );
											setGraphic( btn );
											setText( null );
										}
									}
								};
								return cell;
					}
				};
				
		removeCol.setCellFactory( cellFactory );
		
		gamesTableView.getColumns().addAll(player1Col, player2Col, player3Col, player4Col, winner, removeCol);
		games.addAll(db.getAllGames());
		nicks.addAll(db.getAllNicks());
		gamesTableView.setItems(games);
		
	}
	
	public void setTableData() {
		TableColumn<Player, String> nameCol = new TableColumn<Player, String>("Player Name");
		nameCol.setMinWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
		
		TableColumn<Player, String> nickCol = new TableColumn<Player, String>("Nickname");
		nickCol.setCellValueFactory(new PropertyValueFactory<Player, String>("nick"));
		nickCol.setMinWidth(100);
		
		TableColumn removeCol = new TableColumn ("Delete");
	
		Callback<TableColumn<Player, String>, TableCell<Player, String>> cellFactory = 
				new Callback<TableColumn<Player, String>, TableCell<Player, String>>()
				{
					@Override
					public TableCell call(final TableColumn<Player, String> param)
					{
						final TableCell<Player, String> cell = new TableCell<Player, String>()
								{
									final Button btn = new Button("Delete");
									
									@Override
									public void updateItem( String item, boolean empty)
									{
										super.updateItem( item, empty );
										if( empty )
										{
											setGraphic(null);
											setText(null);
										}
										else
										{
											btn.setOnAction( ( ActionEvent ) ->
													{
														Player player = getTableView().getItems().get( getIndex() );
														data.remove(player);
														db.removePlayer(player);
														System.out.println(player.getNick());
														nicks.remove(player.getNick());
														System.out.println(nicks);
												} );
											setGraphic( btn );
											setText( null );
										}
									}
								};
								return cell;
					}
				};
				
		removeCol.setCellFactory( cellFactory );
		
		table.getColumns().addAll(nameCol, nickCol, removeCol);
		
		data.addAll(db.getAllPlayers());
		table.setItems(data);
	}
	
	public void setStatsData() {
		TableColumn<Statistics, String> player1Col = new TableColumn<Statistics, String>("Player 1");
		player1Col.setMinWidth(100);
		player1Col.setCellValueFactory(new PropertyValueFactory<Statistics, String>("player1"));
		
		TableColumn<Statistics, String> player2Col = new TableColumn<Statistics, String>("Player 2");
		player2Col.setMinWidth(100);
		player2Col.setCellValueFactory(new PropertyValueFactory<Statistics, String>("player2"));
		
		TableColumn<Statistics, String> player3Col = new TableColumn<Statistics, String>("Player 3");
		player3Col.setMinWidth(100);
		player3Col.setCellValueFactory(new PropertyValueFactory<Statistics, String>("player3"));
		
		TableColumn<Statistics, String> player4Col = new TableColumn<Statistics, String>("Player 4");
		player4Col.setMinWidth(100);
		player4Col.setCellValueFactory(new PropertyValueFactory<Statistics, String>("player4"));
		
		TableColumn<Statistics, String> winner = new TableColumn<Statistics, String>("Winner");
		winner.setMinWidth(100);
		winner.setCellValueFactory(new PropertyValueFactory<Statistics, String>("winner"));
		
		statsTableView.getColumns().addAll(player1Col, player2Col, player3Col, player4Col, winner);
		
		stats.addAll(db.getAllGames());
		statsTableView.setItems(stats);
	}

	public void setMenuData() {

		TreeItem<String> treeItemRoot1 = new TreeItem<>("Menu");
		TreeView<String> treeView = new TreeView<>(treeItemRoot1);
		treeView.setShowRoot(false);
		
		TreeItem<String> nodeItemA = new TreeItem<>("Players");
		TreeItem<String> nodeItemB = new TreeItem<>("Games");
		TreeItem<String> nodeItemC = new TreeItem<>("Statistics");
		TreeItem<String> nodeItemD = new TreeItem<>("Exit");
		
		treeItemRoot1.getChildren().addAll(nodeItemA,nodeItemB,nodeItemC,nodeItemD);
		nodeItemA.setExpanded(true);
		
		TreeItem<String> nodeItemC1 = new TreeItem<>("Export");
		nodeItemC.getChildren().add(nodeItemC1);
				
		menuPane.getChildren().add(treeView);
		
		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
				TreeItem<String> selectedItem = (TreeItem<String>) newValue;
				String selectedMenu;
				selectedMenu = selectedItem.getValue();
				
				if (null!= selectedMenu) {
					switch (selectedMenu) {
						case "Exit":
							System.exit(0);
							break;
						case "Export":
							playersPane.setVisible(false);
							exportPane.setVisible(true);
							gamesPane.setVisible(false);
							statPane.setVisible(false);
							break;
						case "Statistics":	
							resetStats();
							winnerStatCBox.setItems(nicks);
							playersPane.setVisible(false);
							exportPane.setVisible(false);
							gamesPane.setVisible(false);
							statPane.setVisible(true);
							break;
						case "Games":
							statPane.setVisible(false);
							playersPane.setVisible(false);
							exportPane.setVisible(false);
							gamesPane.setVisible(true);
							winnerCBox.setItems(nicks);
							nickCBox1.setItems(nicks);
							nickCBox2.setItems(nicks);
							nickCBox3.setItems(nicks);
							nickCBox4.setItems(nicks);
							break;
						case "Players":
							playersPane.setVisible(true);
							exportPane.setVisible(false);
							gamesPane.setVisible(false);
							statPane.setVisible(false);
							break;
					}
				}
			}
		});
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		setTableData();
		setGamesData();
		setStatsData();
		setMenuData();
	}

}

