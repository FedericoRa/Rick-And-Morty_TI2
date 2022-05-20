package model;

public class Seed {
	//Attributes	
	private String content;

	//Relations
	private Box position;	
	private Seed next;
	private Seed previous;
	
	//Constructor
	/**
	 * <b>Name: </b>Seed<br>
	 * This method is the constructor of a Seed.<br>
	 * <b>Pos: </b> The Seed was created successfully.<br>
	 */
	public Seed(String content) {
		this.position=null;		
		this.content=content;
	}
	
	public Box getPosition() {
		return position;
	}
	public void setPosition(Box start) {
		this.position = start;
	}
	public Seed getNext() {
		return next;
	}
	public void setNext(Seed next) {
		this.next = next;
	}
	public Seed getPrevious() {
		return previous;
	}
	public void setPrevious(Seed previous) {
		this.previous = previous;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/**
	* <b>Name: </b>toString<br>
	* This method prints the content of the Seed<br>
	*<b>Pos:</b> The content of the Seed was shown<br>
	*@return the content of the Seed.
	*/	
	public String toString() {
		return this.content;
	}
	
	
}
