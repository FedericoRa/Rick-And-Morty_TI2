package ui;

import java.io.IOException;
import java.util.Scanner;
import model.Game;


public class Menu {
	public static Scanner entry = new Scanner(System.in);
	public final static String SPLIT=" ";	

	private static Game game;


	/**
	 * <b>Name: </b>Menu <br>
	 * This method is the constructor of the class.<br>
	 */

	public Menu() {	
	}



	public void infoGame(boolean gameOver) {
		int filas=0;
		int columnas=0;
		int semillas=0;
		int enlaces=0;
		String []parts = null;

		System.out.println("Ingrese la información del juego");
		String info = entry.nextLine() ;

		if (info!=null) {
			parts = info.split(SPLIT);
			columnas=Integer.parseInt(parts[0]);
			filas=Integer.parseInt(parts[1]);
			semillas=Integer.parseInt(parts[2]);			
		}

		System.out.println("Ingrese el número de enlaces");
		String connections = entry.nextLine();

		if (connections!=null) {
			enlaces = Integer.parseInt(connections);

			double verficationEnlaces = (0.5*(columnas*filas));

			if (enlaces < verficationEnlaces) {

				System.out.println("Ingrese el nombre de la persona que jugará como Rick");
				String rick = entry.nextLine();

				System.out.println("Ingrese el nombre de la persona que jugará como Morty");
				String morty = entry.nextLine();


				game=new Game(filas, columnas,semillas, enlaces,rick,morty);
				
				try {
					game.importData();
				} catch (IOException e) {					
					e.printStackTrace();
				}
							

				System.out.println(game.getGrid().toString());		

				showAndChoose();

			}else {

				System.out.println("No es posible solicitar los jugadores\n"+
						"No es posible añadir esa cantidad de enlaces a una cuadricula de juego de "+columnas+"x"+filas);
			}
		}

	}


	/**
	 * <b>Name: </b> showAndChoose<br>
	 * This method show the corresponding menu and will execute the option selected by the user.<br>
	 * <b>Pre: </b>This method must be called by the Main class.<br>
	 * <b>Post: </b>The option selected was executed successfully.
	 */
	public void showAndChoose() {
		showMenu();
		try {
			chooseMenuOption(Integer.parseInt(entry.nextLine()));
		}catch(NumberFormatException e) {
			System.out.println("Opcion invalida");
			showMenu();
		}
	}

	/**
	 * <b>Name: </b> showMenu<br>
	 * This method show the menu to the user.<br>
	 * <b>Pos:</b> The menu was shown successfully.
	 */
	public static void showMenu() {
		if (game.getAuxPlayer().getSymbol().equals("R")) {
			System.out.println("Es el turno de Rick!. ¿Qué deseas hacer? \n"+
					"1. Tirar dado\n"+
					"2. Ver tablero\n"+
					"3. Ver enlaces\n"+
					"4. Marcador\n"+
					"5. Salir del programa");
		}else if (game.getAuxPlayer().getSymbol().equals("M")) {
			System.out.println("Es el turno de Morty!. ¿Qué deseas hacer? \n"+
					"1. Tirar dado\n"+
					"2. Ver tablero\n"+
					"3. Ver enlaces\n"+
					"4. Marcador\n"+
					"5. Salir del programa");
		}


	}

	/**
	 * <b>Name: </b> showMenuGameOver<br>
	 * This method show the list of winners.<br>
	 * <b>Pos:</b> The menu was shown successfully.
	 */
	public static void showWinnersBoard() {		
		//Tablero de resultados		
		game.chooseWinner();
		
		boolean stop = false;
		int n = game.getWinners().size();
		for (int i=0;i<n && !stop;i++) {
			if (n>=5) {
				n=5;
			}			
			if (game.getWinners().get(i)!=null) {
				System.out.println("Username: "+game.getWinners().get(i).getNickName()+" Puntaje: "+game.getWinners().get(i).getPuntaje());
			}
			
		}		
		System.out.println("Gracias por jugar!");
		System.exit(0);
		

	}







	/**
	 * <b>Name: </b>chooseMenuOption <br>
	 * This method execute the option selected by the user.<br>
	 * <b>Pre: </b> This method must be called by the method showAndChoose.<br>
	 * <b>Pos: </b>The option selected by the user was executed successfully.
	 * @param optionNumber int. Option selected by the user. optionNumber higher or equal to 1 and optionNumber lower or equal to 3.
	 */
	public void chooseMenuOption(int optionNumber) {	
		switch(optionNumber) {
		case 1:
			int dice = (int) (Math.random()* ((6+1)-1))+1;			

			System.out.println("El jugador ha sacado "+dice+", ¿Qué desea hacer?\n"+"1. Avanza\n"+"2. Retrocede");
			String movement = entry.nextLine();
			game.movePlayer(dice,game.getAuxPlayer(),Integer.parseInt(movement));

			System.out.println(game.getGrid().toString());

			if (game.getSeeds()>0) {
				showAndChoose();
			}else {
				showWinnersBoard();
			}




			break;
		case 2:			
			System.out.println(game.getGrid().toString());	

			showAndChoose();
			break;
		case 3:			
			System.out.println(game.getGrid().toStringPortal());

			showAndChoose();
			break;

		case 4:
			System.out.println("Rick: "+game.getFirstPlayer().getPuntaje()+" semillas\n"
					+ "Morty: "+game.getFirstPlayer().getNextPlayer().getPuntaje()+" semillas");

			showAndChoose();


			break;

		case 5:
			System.out.println("Gracias por jugar!");
			System.exit(0);
			break;
		default:
			System.out.println("Opcion invalida, el numero de la opcion debe ser 1, 2 o 3");
		}
	}
}
