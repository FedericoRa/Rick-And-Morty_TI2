package model;

// N x M donde 
// N numero de columnas
// M número de filas
public class Grid{
	//Attributes
	private String positionsGrid;
	private int rows;
	private int columns;
	private int seedsNumber;
	private int counterBoxes=1;
	private int rowActual;

	//Relations
	private Seed firstSeed;
	private Portal firstPortal;
	private Box initial;
	private Box boxUp;

		

	//Constructor
	/**
	 * <b>Name: </b>Grid<br>
	 * This method is the constructor of the Grid.<br>
	 * <b>Pos: </b>The grid was created successfully.
	 * @param rows int. Amount of rows. rows!=0.
	 * @param columns int. Amount of columns. columns!=0.
	 * @param SeedsNumber int. Amount of Seeds. SeedsNumber!=0.
	 * @param PortalsNumber int. Amount of Portal. PortalsNumber!=0.	
	 */
	public Grid(int rows, int columns, int seed, int links) {
		this.rows=rows;
		this.columns=columns;
		this.firstSeed=null;
		this.firstPortal=null;
		this.seedsNumber= seed;

		createGrid();		

		createSeeds(seedsNumber, firstSeed); //Hace una lista de semillas 
		assignSeeds(firstSeed);

		createPortals(links, firstPortal,65);
		assignPortals(firstPortal);

	}

	public Box getInitial() {
		return initial;
	}

	public void setInitial(Box initial) {
		this.initial = initial;
	}

	public int getCounterBoxes() {
		return counterBoxes;
	}

	public void setCounterBoxes(int counterBoxes) {
		this.counterBoxes = counterBoxes;
	}


	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getRowActual() {
		return rowActual;
	}

	public Seed getFirstSeed() {
		return firstSeed;
	}

	public void setFirstSeed(Seed firstSeed) {
		this.firstSeed = firstSeed;
	}

	public int getSeedsNumber() {
		return seedsNumber;
	}

	public void setSeedsNumber(int SeedsNumber) {
		this.seedsNumber = SeedsNumber;
	}
	public String getPositionsGrid() {
		return positionsGrid;
	}

	public void setPositionsGrid(String positionsGrid) {
		this.positionsGrid = positionsGrid;
	}


	/**
	 * <b>Name: </b>createGrid<br>
	 * This method creates a grid. <br>
	 *<b>Pre:</b> Method createRow(int, int, Box) must be already created<br>
	 *<b>Pos:</b> The grid has been created correctly<br>
	 */
	private void createGrid() {
		initial = new Box(0,0, String.valueOf(counterBoxes));
		initial.setBoxNumber(counterBoxes);	
		boxUp=initial;
		counterBoxes++;
		createRow(0,0,initial);

	}

	/**
	 * <b>Name: </b>createRow<br>
	 * This method creates a new Row. <br>
	 *<b>Pre:</b> Method createColumn must be already created<br>
	 *<b>Pos:</b> The row has been created correctly<br>
	 *@param i int. It's the integer number of the row.
	 *@param j int. It's the integer number of the column number.
	 *@param currentFirstRow Box. It's the Box of the firstBox of the row i.
	 */
	private void createRow(int i, int j, Box currentFirstRow) {		
		if (i<rows) {
			if ((i+1)%2!=0) {
				createCol(i,j+1,currentFirstRow);	
			}else if ((i+1)%2==0) {
				createCol(i,j-1,currentFirstRow);
			}
		}	
	}

	/**
	 * <b>Name: </b>createCol<br>
	 * This method creates a new Column. <br>
	 *<b>Pos:</b> The column has been created correctly<br>
	 *@param i int. It's the integer number of the row.
	 *@param j int. It's the integer number of the column.
	 *@param prev Box. It's the Box previous to the new Box.
	 *@param rowPrev Box. It's the Box previous in the upRow of the new Box.
	 */

	public void createCol(int i, int j, Box prev) {
		Box boxDown = null;
		if (j<columns && j>=0) {

			if ((i+1)%2!=0) {
				Box current = new Box(i, j, String.valueOf(counterBoxes));
				current.setBoxNumber(counterBoxes);
				counterBoxes++;
				prev.setNext(current);
				current.setPrevious(prev);								
				createCol(i,j+1,current);

			}else if ((i+1)%2==0) {
				Box current = new Box(i, j, String.valueOf(counterBoxes));
				current.setBoxNumber(counterBoxes);
				counterBoxes++;
				prev.setPrevious(current);	
				current.setNext(prev);						
				createCol(i,j-1,current);
			}

		}else {
			if (i<rows) {

				if ((i+2)%2!=0) {

					prev.setUp(boxUp);
					boxUp.setDown(prev);
					boxUp=prev;


					if (counterBoxes<=(rows*columns)) {
						j=0;
						boxDown = new Box(i+1,j,String.valueOf(counterBoxes));
						boxDown.setBoxNumber(counterBoxes);
						counterBoxes++;
						boxDown.setUp(prev);
						prev.setDown(boxDown);
					}					

				}else if ((i+2)%2==0) {

					if (counterBoxes<=(rows*columns)) {
						j= columns-1;
						boxDown = new Box(i+1,j,String.valueOf(counterBoxes));
						boxDown.setBoxNumber(counterBoxes);
						counterBoxes++;
						boxDown.setUp(prev);
						prev.setDown(boxDown);
					}


				}

				createRow(i+1,j,boxDown);				
			}			
		}	
	}




	/**
	 * <b>Name: </b>createPortals<br>
	 * This method creates the Portals. <br>
	 *<b>Pos:</b> All Portals has been created<br>
	 *@param number int. It's the integer number of Portals remaining to create.
	 *@param Portal Portal. It's the firstPortal, could be null if there has not been created a Portal yet.
	 */
	public void createPortals(int number, Portal Portal,int ascii) {
		if(number==0) {
			
		}
		else {
			if(Portal==null) {//Voy a crear la primera semilla
				
				firstPortal=new Portal();
				//firstSeed=Seed;
				firstPortal.setContent(String.valueOf((char)ascii));
				createPortals(--number, firstPortal,++ascii);
			}
			else {// si no es la primera iteracion
				Portal nextPortal= new Portal();
				Portal.setNext(nextPortal);
				nextPortal.setContent(String.valueOf((char)ascii));
				createPortals(--number, nextPortal,++ascii);
			}
		}
	}


	/**
	 * <b>Name: </b>assignPortals<br>
	 * This method assigns the start and final of all Portals. <br>
	 *<b>Pos:</b> All Portals has been assigned in the grid with a star and an ending<br>
	 *@param actualPortal Portal.It's the Portal to be worked on, actualPortal!=null.
	 */
	public void assignPortals(Portal actualPortal) {
		if(actualPortal==firstPortal) {
			chooseInitialBoxForPortal(firstPortal);// le asignó le box inicial
			chooseFinalBoxForPortal(firstPortal);
			if(firstPortal.getNext()!=null) {
				assignPortals(firstPortal.getNext());
			}
		}
		else {
			chooseInitialBoxForPortal(actualPortal);// le asignó le box inicial
			chooseFinalBoxForPortal(actualPortal);
			if(actualPortal.getNext()!=null) {
				assignPortals(actualPortal.getNext());
			}
		}

	}

	/**
	 * <b>Name: </b>chooseInitialBoxForPortal<br>
	 * This method choose where the Portal is going to start. <br>
	 *<b>Pos:</b> The Portal has a box where to start<br>
	 *@param actualPortal Portal. It's the Portal to assign a start (row, column), actualPortal!=null.
	 */
	public void chooseInitialBoxForPortal(Portal actualPortal) {// con este metodo asigno si o si la casilla inicial al portal
		int columnI=(int) (Math.random() * (columns + 1 - 1)) + 1;
		int filaI=(int) (Math.random() * (((rows) + 1) - 1)) + 1;

		Box boxInicial=findBoxCoordenates(initial,filaI, columnI, false);



		if(boxInicial.getPortal()==null) {//si la casilla en (filaI, columnI) no tiene portales
			boxInicial.setPortal(actualPortal);
			boxInicial.setPortalName(actualPortal.getContent());
			actualPortal.setStart(boxInicial);
		}
		else {
			chooseInitialBoxForPortal(actualPortal);
		}
	}	


	/**
	 * <b>Name: </b>chooseInitialBoxForPortal<br>
	 * This method choose where the Portal is going to end. <br>
	 *<b>Pos:</b> The Portal has a box where to end<br>
	 *@param actualPortal Portal. It's the Portal to assign a end (row, column), actualPortal!=null.
	 */
	public void chooseFinalBoxForPortal(Portal actualPortal) {
		int columnF=(int) (Math.random() * (columns + 1 - 1)) + 1;
		//System.out.println("ACTUAL Seed START ("+(actualSeed.getStart().getRow()+1)+","+(actualSeed.getStart().getColumn()+1)+")");

		int filaF=(int) (Math.random() * (((rows) + 1) - 1)) + 1;
		//System.out.println("Numeros aleatorios Finales("+filaF+","+columnF+")");


		Box boxFinal=findBoxCoordenates(initial,filaF, columnF, false);
		//System.out.println("PIRNT BOX FINAL"+ boxFinal);


		if(boxFinal.getSeed()==null && boxFinal.getPortal()==null) {//si la casilla en (filaI, columnI) no tiene semillas
			boxFinal.setPortal(actualPortal);
			boxFinal.setPortalName(actualPortal.getContent());
			actualPortal.setEnd(boxFinal);
		}
		else {
			chooseFinalBoxForPortal(actualPortal);
		}

	}

	/**
	 * <b>Name: </b>createSeeds<br>
	 * This method creates the Seeds. <br>
	 *<b>Pos:</b> All Seeds has been created<br>
	 *@param number int. It's the integer number of Seeds remaining to create.
	 *@param Seed is Seed.It's the firstSeed, could be null if there has not been created a Seed yet.
	 */
	public void createSeeds(int number, Seed Seed) {
		if(number==0) {//me creo todas o no hay semillas
		}
		else {
			if(Seed==null) {//Voy a crear la primera semilla
				//System.out.println("	Se creo la primera semilla con el contenido "+number);
				firstSeed=new Seed("*");

				createSeeds(--number, firstSeed);
			}
			else {// si no es la primera iteracion
				Seed nextSeed= new Seed("*");
				Seed.setNext(nextSeed);
				createSeeds(--number, nextSeed);
			}
		}
	}

	/**
	 * <b>Name: </b>assignSeeds<br>
	 * This method assigns the start and final of all Seeds. <br>
	 *<b>Pos:</b> All Seeds has been assigned in the grid with a start and an ending<br>
	 *@param actualSeed Seed. It's the Seed to be worked on, actualSeed!=null.
	 */
	public void assignSeeds(Seed actualSeed) {
		if(actualSeed==firstSeed) {
			chooseBoxForSeed(firstSeed);// le asignó una position

			if(firstSeed.getNext()!=null) {
				//System.out.println("____________________EN UNA NUEVA Semilla_______________");
				assignSeeds(firstSeed.getNext());
			}
		}
		else {
			chooseBoxForSeed(actualSeed);// le asignó una posicion			
			if(actualSeed.getNext()!=null) {
				//System.out.println("____________________EN UNA NUEVA Semilla_______________");
				assignSeeds(actualSeed.getNext());
			}
		}

	}





	/**
	 * <b>Name: </b>chooseInitialBoxForSeed<br>
	 * This method choose where the Seed is going to start. <br>
	 *<b>Pos:</b> The Seed has a box where to start<br>
	 *@param actualSeed Seed. It's the Seed to assign a start (row, column), actualSeed!=null.
	 */
	public void chooseBoxForSeed(Seed actualSeed) {// con este metodo asigno si o si la casilla de la semilla
		int columnI=(int) (Math.random() * (columns + 1 - 1)) + 1;
		int filaI=(int) (Math.random() * (((rows-1) + 1) - 1)) + 1;

		Box positionSeed=findBoxCoordenates(initial,filaI, columnI, false);

		if(positionSeed.getSeed()==null) {//si la casilla en (filaI, columnI) no tiene semillas 
			positionSeed.setSeed(actualSeed);
			positionSeed.setContent(actualSeed.getContent());
			actualSeed.setPosition(positionSeed);
		}
		else {
			chooseBoxForSeed(actualSeed);
		}
	}


	//}


	//}

	/**
	 * <b>Name: </b>findBoxCoordenates<br>
	 * This method finds a box depending on an (x,y) coordenate. <br>
	 *<b>Pos:</b> The box has been found<br>
	 *@param boxActual Box. It's the box with the recursion is on, it starts with the initial Box, boxActual!=null.
	 *@param row int. It's the integer of the number of the coordenate x.
	 *@param column int. It's the integer number of the coordenate y.
	 *@param salir boolean. It's the boolean that indicates if the box has been found.
	 *@return boxActual Box. It's the box in the coordenate(row, column).
	 */
	// initial, 2, 2, false
	public Box findBoxCoordenates(Box boxActual, int row, int column, boolean salir) {//INICIAL : (initial, rowx, columnx, false)
		if(salir==false && boxActual!=null) {		
			if((boxActual.getRow()+1)==row) {
				if((boxActual.getColumn()+1)==column) {
	
					salir=true;
					return findBoxCoordenates(boxActual, row, column, salir);
				}
				else {// if boxActual.getColumn()+1<column
					return findBoxCoordenates(boxActual.getNext(), row,column, salir);
				}
			}
			else {
				return findBoxCoordenates(boxActual.getDown(), row,column, salir);


			}
		}
		return boxActual;
	}

	/**
	 * <b>Name: </b>findBoxWithNumber<br>
	 * This method finds a box with the attribute boxNumber <br>
	 *<b>Pos:</b> The first box of the last row has been found<br>
	 * @param box Box. It's the initialBox.
	 * @param n int. It's the number of the box looked for.
	 * @param salir boolean. It's true when find the box, but while doesn't find the box, it will be false.
	 * @return box Box. It's the box with the number n.
	 */	
	public Box findBoxWithNumber(Box box, int n,boolean salir) {			
		if (salir==false && box!=null) {
			if (box.getBoxNumber()==n) {
				salir=true;				
			}else {
				if (box.getNext()!=null) {
					return findBoxWithNumber(box.getNext(),n,salir);					
				}else if (box.getNext()==null){					
					return findBoxWithNumberPrev(box.getDown(),n,salir);					
				}				
			}
		}
		return box;		
	}

	/**
	 * <b>Name: </b>findBoxWithNumberPrev<br>
	 * This method finds a box with the attribute boxNumber <br>
	 *<b>Pos:</b> The first box of the last row has been found<br>
	 *@param box Box. It's the initialBox.
	 *@param n int. It's the number of the box looked for.
	 *@return box Box. It's the box with the number n.
	 */	
	private Box findBoxWithNumberPrev(Box box, int n, boolean salir) {

		if (salir==false && box!=null) {
			if (box.getBoxNumber()==n) {
				salir=true;				
			}else {
				if (box.getPrevious()!=null) {
					return findBoxWithNumberPrev(box.getPrevious(),n,salir);
				}else if (box.getPrevious()==null) {
					return findBoxWithNumber(box.getDown(),n,salir);
				}
			}
		}
		return box;		
	}





	/**
	 * <b>Name: </b>toString<br>
	 * This method prints the grid<br>
	 *<b>Pos:</b> The grid has been converted to a string<br>
	 *@return message, the grid in a string
	 */		
	public String toString() {
		String message = "";
		int i = 0;
		message=toStringRow(i,initial);

		return message;

	}

	private String reverseString(int stop,Box box) {

		String reverse = "";
		if (box!=null && stop<columns) {
			reverse = box.toString();
			reverse+=reverseString(++stop,box.getNext());			
		}

		return reverse;
	}

	/**
	 * <b>Name: </b>toStringRow<br>
	 * This method prints the row<br>
	 *<b>Pos:</b>The row has been converted to a string<br>
	 *@return message, the row in a string
	 * @throws InterruptedException 
	 */	
	private String toStringRow(int i,Box row)  {
		String message="";	
		if(row!=null && i<rows) {
			message=toStringCol(i,row)+"\n";		
			if (boxUp!=null) {
				message+= toStringRow(++i,boxUp.getDown());
			}


		}		
		return message;
	}


	/**
	 * <b>Name: </b>toStringCol<br>
	 * This method prints the column<br>
	 *<b>Pos:</b>The column has been converted to a string<br>
	 *@return message, the column in a string
	 * @throws InterruptedException 
	 */	
	private String toStringCol(int i,Box current){
		String message="";
		if(current!=null) {
			message= current.toString();
			boxUp = current;


			if ((i+1)%2==0) {				
				message+=toStringCol(i,current.getPrevious());
			}else if ((i+1)%2!=0) {
				message+=toStringCol(i,current.getNext());
			}

		}		
		if ((i+1)%2==0) {			
			message = reverseString(0,boxUp);			
		}
		return message;
	}







	public String toStringPortal() {
		String message = "";
		int i = 0;
		message=toStringRowPortal(i,initial);

		return message;

	}

	private String reverseStringPortal(int stop,Box box) {

		String reverse = "";
		if (box!=null && stop<columns) {
			reverse = box.toStringPortal();
			reverse+=reverseStringPortal(++stop,box.getNext());			
		}

		return reverse;
	}

	/**
	 * <b>Name: </b>toStringRow<br>
	 * This method prints the row<br>
	 *<b>Pos:</b>The row has been converted to a string<br>
	 *@return message, the row in a string
	 * @throws InterruptedException 
	 */	
	private String toStringRowPortal(int i,Box row)  {
		String message="";	
		if(row!=null && i<rows) {
			message=toStringColPortal(i,row)+"\n";		
			if (boxUp!=null) {
				message+= toStringRowPortal(++i,boxUp.getDown());
			}


		}		
		return message;
	}


	/**
	 * <b>Name: </b>toStringCol<br>
	 * This method prints the column<br>
	 *<b>Pos:</b>The column has been converted to a string<br>
	 *@return message, the column in a string
	 * @throws InterruptedException 
	 */	
	private String toStringColPortal(int i,Box current){
		String message="";
		if(current!=null) {
			message= current.toStringPortal();
			boxUp = current;


			if ((i+1)%2==0) {				
				message+=toStringColPortal(i,current.getPrevious());
			}else if ((i+1)%2!=0) {
				message+=toStringColPortal(i,current.getNext());
			}

		}		
		if ((i+1)%2==0) {			
			message = reverseStringPortal(0,boxUp);			
		}
		return message;
	}




}
