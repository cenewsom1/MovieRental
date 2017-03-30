package edu.ncsu.csc216.movie102.util;

/**
 * This is an exception class extended by QuizMaster class.
 * Throws exception if the class list of question is empty
 * @author Ethan
 * @author Terrance
 *  @author Mboya
 *
 */
public class EmptyQuestionListException extends Exception {
	
	/**
	 * A number used during the deserialization  of any two objects
	 * to verify that the sender and receiver of a serialized object 
	 * have loaded classes for that object that are compatible 
	 * with respect to serialization
	 */
	private static final long serialVersionUID = -5976310991635731775L;
	//private static final String MESSAGE = "" ;
	
	/**
	 * Calls the Exception superclass.
	 */
	public EmptyQuestionListException(){
		super();
	}
	
	/**
	 * Gives the message that the exception will display when thrown.
	 * @param message The message to display.
	 */
	public EmptyQuestionListException(String message){
		super(message);
	}	
}
