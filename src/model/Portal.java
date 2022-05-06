package model;

public class Portal {
	//Attributes
	private String content;	

	//Relations
	private Box start;
	private Box end;
	private Portal next;
	private Portal previous;
	
	//Constructor
	/**
	 * <b>Name: </b>Portal<br>
	 * This method is the constructor of a Portal.<br>
	 * <b>Pos: </b> The Portal was created successfully.<br>
	 */
	public Portal() {
		this.start=null;
		this.end=null;
		this.content="";		
	}
	public Box getStart() {
		return start;
	}
	public void setStart(Box start) {
		this.start = start;
	}
	public Box getEnd() {
		return end;
	}
	public void setEnd(Box end) {
		this.end = end;
	}
	public Portal getNext() {
		return next;
	}
	public void setNext(Portal next) {
		this.next = next;
	}
	public Portal getPrevious() {
		return previous;
	}
	public void setPrevious(Portal previous) {
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
	* This method prints the content of the Portal<br>
	*<b>Pos:</b> The content of the Portal was shown<br>
	*@return the content of the Portal.
	*/	
	public String toString() {
		return this.content;
	}
}
