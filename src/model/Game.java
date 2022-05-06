package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import thread.Load;

public class Game {	

	//Constants
	public final static String SPLIT=";";
	
	//Attributes 
	private int seeds;
	private List<Player> winners;

	//Relations
	private Load load;
	private Grid grid;
	private Player firstPlayer;
	private Player auxPlayer;
	
	private Player winner;	
	


	//Constructor 
	/**
	 * <b>Name: </b>Game<br>
	 * This method is the constructor #2 of the class. It is use when the user write the amount of players.<br>
	 * <b>Pos: </b> The game was created successfully.
	 * @param rows int. Amount of rows. rows!=0.
	 * @param columns int. Amount of columns. columns!=0.
	 * @param SeedsNumber int. Amount of Seeds. SeedsNumber!=0.
	 * @param PortalsNumber int. Amount of Portal. PortalsNumber!=0.
	 * @param numberOfPlayers int. Amount of players. numberOfPlayers higher or equal to 1 and numberOfPlayers lower or equal to 8.
	 */

	public Game(int rows, int columns,int seed, int links,String rick, String morty) {			
		grid=new Grid(rows, columns, seed, links);	
		seeds = seed;
		winners = new ArrayList<Player>();
		createPlayers(2, rick, morty,null);
		load = new Load();
		load.start();
		//createPlayers(firstPlayer,numberOfPlayers);
	}
	
	public void setSeeds(int seeds) {
		this.seeds = seeds;
	}

	public int getSeeds() {
		return seeds;
	}
	
	public void setWinners(List<Player> winners) {
		this.winners = winners;
	}

	public List<Player> getWinners() {
		return winners;
	}
	
	public void setFirstPlayer(Player firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}


	public Player getAuxPlayer() {
		return auxPlayer;
	}

	public Player getWinner() {
		return winner;
	}

	public Grid getGrid() {
		return grid;
	}


	//Creo una lista circular de los jugadores

	/**
	 * <b>Name: </b>createPlayers.<br>
	 * This method create the amount of players according to the amount of symbols written by the user,  and each player has one of the symbols written by the user.<br>
	 *	<b>Pre: </b>The grid must already be created and must have the numbers.<br>
	 *<b>Pre: </b>The user  wrote a symbols string to create the players.
	 * <b>Pos: </b>The players were created successfully.
	 * @param pPlayer Player. Previous player.<br>
	 * @param parts String. Word with the symbols.<br>
	 * @param i int. Index of each symbol. Every time that a player is created, its value increases but it has to be less than the amount of symbols.<br>
	 * @param p int. Amount of players. Every time that a player is created, its value decreases.<br>
	 */
	public void createPlayers(int players, String rick, String morty, Player prevPlayer) {
		Player newPlayer = null;
		if (players == 0){//Caso base

		}else {
			int column = (int) (Math.random() * (grid.getColumns() + 1 - 1)) + 1;
			int fila = (int) (Math.random() * (((grid.getRows()-1) + 1) - 1)) + 1;

			Box positionPlayer = grid.findBoxCoordenates(grid.getInitial(), fila, column, false);


			if (positionPlayer.getSeed()==null) {//Verificación para que los jugadores no queden en una casilla con semilla
				newPlayer = new Player("",positionPlayer);
				positionPlayer.setPlayer(true);

				if (firstPlayer==null) {
					firstPlayer = newPlayer;
					newPlayer.setNickName(rick);
					newPlayer.setSymbol("R");	
					auxPlayer = firstPlayer;
				}else {
					newPlayer.setNickName(morty);
					newPlayer.setSymbol("M");
					prevPlayer.setNextPlayer(newPlayer);
					newPlayer.setNextPlayer(prevPlayer);				
				}


				positionPlayer.setContent(newPlayer.getSymbol());


				createPlayers(--players,rick,morty,newPlayer);

			}else {
				createPlayers(players, rick, morty, prevPlayer);
			}


		}

	}		



	/**
	 * <b>Name: </b>movePlayer.<br>
	 * This method move player by player according to the value of the dice. To move the next player, is necessary a line break<br>
	 * <b>Pre: </b> The players must already be created.<br>
	 * <b>Pos: </b> The player was moved successfully.
	 * @param player Player. Next Player.
	 * @param end boolean. Indicate if there is a player in the last box.
	 * @return stop boolean. It will be true when a player gets to the last box and the game will end.
	 */


	public void movePlayer(int dice, Player player, int direction) {
		int numberNewPosition = 0;

		if (player!=null) {
			//(int) (Math.random() * (<número_máximo + 1> - <número_mínimo>)) + <numero_mínimo>;

			Box initialBox = player.getBoxUbication();		

			if (direction==1) {

				numberNewPosition = initialBox.getBoxNumber() + dice;	

				if (numberNewPosition>0 && numberNewPosition<=(grid.getRows()*grid.getColumns())) {

					Box newPosition = grid.findBoxWithNumber(grid.getInitial(), numberNewPosition, false);

					initialBox.setPlayer(false);
					initialBox.setContent(String.valueOf(initialBox.getBoxNumber()));
					newPosition.setPlayer(true);					
					auxPlayer.setBoxUbication(newPosition);
					newPosition.setContent(auxPlayer.getSymbol());


					if (newPosition.getSeed()!=null) {//Si en la nueva posición hay un semilla
						auxPlayer.setPuntaje(auxPlayer.getPuntaje()+1);
						newPosition.setSeed(null);
						seeds-=1;
						//newPosition.setContent(auxPlayer.getSymbol());
					}

					if (newPosition.getPortal()!=null) {//Si en la nueva posición hay un portal
						newPosition.setPlayer(false);
						newPosition.setContent(String.valueOf(newPosition.getBoxNumber()));

						if (newPosition == newPosition.getPortal().getEnd()) {//Si la nueva posición a la que se mueve es el final de un portal, se mueve al inicio del portal
							newPosition = newPosition.getPortal().getStart();

						}else if (newPosition == newPosition.getPortal().getStart()){//Si la nueva posición a la que se mueve es el inicio de un portal, se mueve al final del portal
							newPosition = newPosition.getPortal().getEnd();

						}
						
						auxPlayer.setBoxUbication(newPosition);

						if (newPosition.getSeed()!=null) {//Verificación para que si al inicio del portal no hay semilla, tal vez en el final si puede haber semilla
							auxPlayer.setPuntaje(auxPlayer.getPuntaje()+1);
							newPosition.setSeed(null);
							seeds-=1;
						}						 

						newPosition.setPlayer(true);
						newPosition.setContent(auxPlayer.getSymbol());						
					}							 
					
					auxPlayer = auxPlayer.getNextPlayer();

				}else {
					System.out.println("No es posible avanzar porque la nueva posición no existe en el tablero");
				}


			}else if (direction==2) {
				
				numberNewPosition = initialBox.getBoxNumber() - dice;	

				if (numberNewPosition>0 && numberNewPosition<=(grid.getRows()*grid.getColumns())) {

					Box newPosition = grid.findBoxWithNumber(grid.getInitial(), numberNewPosition, false);

					initialBox.setPlayer(false);
					initialBox.setContent(String.valueOf(initialBox.getBoxNumber()));
					newPosition.setPlayer(true);					
					auxPlayer.setBoxUbication(newPosition);
					newPosition.setContent(auxPlayer.getSymbol());


					if (newPosition.getSeed()!=null) {//Si en la nueva posición hay un semilla
						auxPlayer.setPuntaje(auxPlayer.getPuntaje()+1);
						newPosition.setSeed(null);
						seeds-=1;
						//newPosition.setContent(auxPlayer.getSymbol());
					}

					if (newPosition.getPortal()!=null) {//Si en la nueva posición hay un portal
						newPosition.setPlayer(false);
						newPosition.setContent(String.valueOf(newPosition.getBoxNumber()));

						if (newPosition == newPosition.getPortal().getEnd()) {//Si la nueva posición a la que se mueve es el final de un portal, se mueve al inicio del portal
							newPosition = newPosition.getPortal().getStart();

						}else if (newPosition == newPosition.getPortal().getStart()){//Si la nueva posición a la que se mueve es el inicio de un portal, se mueve al final del portal
							newPosition = newPosition.getPortal().getEnd();

						}
						
						auxPlayer.setBoxUbication(newPosition);

						if (newPosition.getSeed()!=null) {//Verificación para que si al inicio del portal no hay semilla, tal vez en el final si puede haber semilla
							auxPlayer.setPuntaje(auxPlayer.getPuntaje()+1);
							newPosition.setSeed(null);
							seeds-=1;
						}						 

						newPosition.setPlayer(true);
						newPosition.setContent(auxPlayer.getSymbol());						
					}	
					
					auxPlayer = auxPlayer.getNextPlayer();


				}else {
					System.out.println("No es posible retroceder porque la nueva posición no existe en el tablero");
				}
			}			
		}
	}


	public void chooseWinner() {
		load.setRunning(false);
		
		
		if (firstPlayer.getPuntaje()>firstPlayer.getNextPlayer().getPuntaje()) {
			winner = firstPlayer;
			
		}else if (firstPlayer.getNextPlayer().getPuntaje()>firstPlayer.getPuntaje()) {
			winner = firstPlayer.getNextPlayer();
			
		}
		
		int score = winner.getPuntaje()*120-load.getSeconds();
		winner.setPuntaje(score);
		boolean exit = false;
		for (int i=0;i<winners.size() && !exit;i++) {
			if (winner.getNickName().equals(winners.get(i).getNickName())) {
				winners.get(i).setPuntaje(winners.get(i).getPuntaje()+score);
				exit = true;
			}
		}
		
		if (exit==false) {
			winners.add(winner);
		}
		try {
			exportData();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	
	//Import data
	public void importData() throws IOException {
		File file  = new File("data/winnersData.txt");
		
		if (file!=null) {
    		System.out.println("Se importará la información del archivo txt");
    		try {
    			BufferedReader br = new BufferedReader(new FileReader(file));
    			String line = br.readLine();
    			while(line!=null){	
    				String[] parts = line.split(SPLIT);
    				
    				Player player = new Player(parts[0],Integer.parseInt(parts[1]));
    				winners.add(player);    			
    				line = br.readLine();    				
    			}
    			br.close();
    			System.out.println("La información se ha importado correctamente");
    			
    		}catch (IOException e) {
    			System.out.println("Hubo un problema. No se pudo importar la información");    			 			
    		}    		
    	}
		
	}
	

	
	
    //Export data
    public void exportData() throws IOException{
    	File file = new File ("data/winnersData.txt"); 
    	PrintWriter pw =  new PrintWriter(file);
    	
    	if (file!=null) {    	
    		System.out.println("Se exportará la información del archivo txt");

    		for (int i=0;i<winners.size();i++) {
    			pw.println(winners.get(i).getNickName()+";"+winners.get(i).getPuntaje());
    		}		
    		pw.close();
    		System.out.println("Se ha exportado exitosamente la información del archivo txt"); 		   	
    	}     			    
    }
	




	




}
