package application;

import java.util.List;

//import java.time.LocalDate;

public class Statistics {

	private List<String> players;
	private String statisticsId;
	private String winner;
	private String player1;
	private String player2;
	private String player3;
	private String player4;
	
	/*public Statistics(List<String> players, int statisticsId, String winner) {
		this.players = players;
		this.statisticsId = statisticsId;
		this.winner = winner;
	}*/
	
	public Statistics(List<String> players, String winner) {
		this.players = players;
		this.winner = winner;
	}
	
	public Statistics(String player1, String player2, String player3, String player4, String winner) {
		this.player1 = player1;
		this.player2 = player2;
		this.player3 = player3;
		this.player4 = player4;
		this.winner = winner;
	}
	
	public Statistics(int statisticsId, String player1, String player2, String player3, String player4, String winner) {
		this.statisticsId = String.valueOf(statisticsId);
		this.player1 = player1;
		this.player2 = player2;
		this.player3 = player3;
		this.player4 = player4;
		this.winner = winner;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public List<String> getPlayers() {
		return players;
	}
	
	public String getStatisticsId() {
		return statisticsId;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public String getPlayer3() {
		return player3;
	}

	public void setPlayer3(String player3) {
		this.player3 = player3;
	}

	public String getPlayer4() {
		return player4;
	}

	public void setPlayer4(String player4) {
		this.player4 = player4;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public void setStatisticsId(String statisticsId) {
		this.statisticsId = statisticsId;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	
}

