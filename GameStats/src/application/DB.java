package application;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import application.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DB {
	final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	final String URL = "jdbc:derby:sampleDB;create=true";
	final String USERNAME = "";
	final String PASSWORD = "";
	
	//letrehozzuk a kapcsolatot hidat
	Connection conn = null;
	Statement createStatement = null;
	DatabaseMetaData dbmd = null;

	public DB() {
		
		try {
			conn = DriverManager.getConnection(URL);
			System.out.println("a  hid letrejott");
		} catch (SQLException e) {
			System.out.println("Valami baj van a connection(hid) letrehozasakor");
			System.out.println(""+e);
		}
		
		//ha letrejott csinalunk egy megpakolhato teherautot
		if(conn!=null) {
			try {
				createStatement = conn.createStatement();
			} catch (SQLException e) {
				System.out.println("Valami baj van a createStatement letrehozasakor");
				System.out.println(""+e);
			}
		}
		
		//Megnezzuk, hogy ures -e az adatbazis? megnezzuk letezik e az adott adattabla
		try {
			dbmd = conn.getMetaData();
		}catch (SQLException e){
			System.out.println("Valami baj van a DatabaseMetaData (adatbazis leirasa) letrehozasakor");
			System.out.println(""+e);
		}
		
		
		try {
			ResultSet rs1 = dbmd.getTables(null, "APP", "PLAYERS", null);
			if(!rs1.next()) {
				createStatement.execute("create table players(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(20), nick varchar(20))");
				System.out.println("a tabla letrejott");
			}else {
				System.out.println("a tabla mar letezik");
			}
		}catch (SQLException e){
			System.out.println("Valami baj van a jatekosok adattabla letrehozasakor");
			System.out.println(""+e);
		}
		
		try {
			ResultSet rs2 = dbmd.getTables(null, "APP", "GAMES", null);
			
			if(!rs2.next()) {
				
				createStatement.execute("create table games(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
															+ "player1 varchar(20), "
															+ "player2 varchar(20), "
															+ "player3 varchar(20), "
															+ "player4 varchar(20), "
															+ "winner varchar(20))");
				System.out.println("a tabla letrejott");
			}else {
				System.out.println("a tabla mar letezik");
			}
		}catch (SQLException e){
			System.out.println("Valami baj van az games adattabla letrehozasakor");
			System.out.println(""+e);
		}
		
		
	}
	
	public ObservableList<String> getAllNicks(){
		ObservableList<String> nicks = null;
		String sql = "select nick from players";
		try {
			nicks = FXCollections.observableArrayList();
			ResultSet rs = createStatement.executeQuery(sql);
			while(rs.next()) {
				nicks.add(rs.getString("nick"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nicks;
	}
	
	public ArrayList<Statistics> getStatistic(String winner){
		ArrayList<Statistics> statistic = null;
		String sql = "select * from games where winner = '"+winner+"'";
		System.out.println(sql);
		try {
			statistic = new ArrayList<Statistics>();
			ResultSet rs = createStatement.executeQuery(sql);
			while(rs.next()) {
				Statistics stat = new Statistics(rs.getInt("id"), rs.getString("player1"), rs.getString("player2"), rs.getString("player3"), rs.getString("player4"), rs.getString("winner"));
				statistic.add(stat);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return statistic;
	}
	
	public ArrayList<Player> getAllPlayers(){
		ArrayList<Player> persons = null;
		String sql = "select * from players";
		try {
			persons = new ArrayList<Player>();
			ResultSet rs = createStatement.executeQuery(sql);
			while(rs.next()) {
				Player actualPerson = new Player(rs.getInt("id"), rs.getString("name"), rs.getString("nick"));
				persons.add(actualPerson);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return persons;
	}
	
	public ArrayList<Statistics> getAllGames(){
		ArrayList<Statistics> games = null;
		String sql = "select * from games";
		try {
			games = new ArrayList<Statistics>();
			ResultSet rs = createStatement.executeQuery(sql);
			while(rs.next()) {
				Statistics actualGame = new Statistics(rs.getInt("id"), rs.getString("player1"), rs.getString("player2"), rs.getString("player3"), rs.getString("player4"), rs.getString("winner"));
				games.add(actualGame);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return games;
	}
	
	public void addStatistic(Statistics statistic) {
		try {
			String sql = "insert into games (player1, player2, player3, player4, winner) values (?,?,?,?,?)";
			String sql2 = "select id from games";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1,statistic.getPlayer1());
			preparedStatement.setString(2,statistic.getPlayer2());
			preparedStatement.setString(3,statistic.getPlayer3());
			preparedStatement.setString(4,statistic.getPlayer4());
			preparedStatement.setString(5,statistic.getWinner());
			preparedStatement.execute();
			
			ResultSet rs = createStatement.executeQuery(sql2);
			
			while(rs.next()) {
				statistic.setStatisticsId(rs.getString("id"));
				System.out.println(statistic.getStatisticsId());
			}
		} catch (SQLException e) {
			System.out.println("Valami baj van a stat hozzaadasnal");
			System.out.println(""+e);
		}
	}
	
	public void addUser(Player player) {
		try {
			
			String sql = "insert into players (name, nick) values (?,?)";
			String sql2 = "select id from players";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1,player.getName());
			preparedStatement.setString(2,player.getNick());
			preparedStatement.execute();
			ResultSet rs = createStatement.executeQuery(sql2);
			
			while(rs.next()) {
				player.setId(rs.getString("id"));
				System.out.println(player.getId());
			}
			
		} catch (SQLException e) {
			System.out.println("Valami baj van a player hozzaadasnal");
			System.out.println(""+e);
		}
	}
	
	public void removeGame(Statistics statistic) {
		try {
			System.out.println("stat Id:" + statistic.getStatisticsId());
			String sql = "delete from games where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1,Integer.parseInt(statistic.getStatisticsId()));
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Valami baj van a game torlesekor");
			System.out.println(""+e);
		}
	}
	
	public void removePlayer(Player player) {
		try {
			System.out.println("plazer Id:" + player.getId());
			String sql = "delete from players where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1,Integer.parseInt(player.getId()));
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Valami baj van a player torlesekor");
			System.out.println(""+e);
		}
	}
}

