package model;

public class Player {
	//Attributes
	private String nickname;
	private String symbol;	
	private int puntaje;
	
	
	//Relation
	private Game game;
	
	private Box boxUbication;
	private Player nextPlayer;

	

	/**
	 * <b>Name: </b>Player<br>
	 * This method is the constructor of a Player.<br>
	 * <b>Pos: </b> The Player was created successfully.<br>
	 * @param s String. Symbol of the player.
	 * @param box Box. Box where the player is.
	 */
	public Player(String username, Box box) {
		nickname=username;
		symbol = "";		
		boxUbication = box;
		setPuntaje(0);		
	}
	
	public Player (String username, int score) {
		nickname = username;
		puntaje = score;
	}
	public void setNickName(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickName() {
		return nickname;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	

	public String getSymbol() {
		return symbol;
	}

	
	public void setBoxUbication(Box boxUbication) {
		this.boxUbication = boxUbication;
	}
	
	public Box getBoxUbication() {
		return boxUbication;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	
}