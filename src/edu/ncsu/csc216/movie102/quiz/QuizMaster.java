package edu.ncsu.csc216.movie102.quiz;

import edu.ncsu.csc216.movie102.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.QuestionException;

/**
 * Interface specifying the behaviors of the Movies102 project.
 * 
 * @author Dr. Sarah Heckman
 * @author Dr. Jo Perry
 * @author Dr. Krisy Boyer
 */
public interface QuizMaster {
	/**
	 * Are there any more questions remaining in this test?
	 * @return true if there are, false if there are not
	 */
	public boolean hasMoreQuestions();
	
	/**
	 * Get the text for the current question.
	 * @return the current question text
	 * @throws EmptyQuestionListException if there is no current question
	 */
	public String getCurrentQuestionText() throws EmptyQuestionListException;

	/**
	 * Get the possible answers for the current question
	 * @return the possible answers for the current question -- each answer
	 *         is a separate array element
	 * * @throws EmptyQuestionListException if there is no current question
	 */
	public String[] getCurrentQuestionChoices() throws EmptyQuestionListException;
	
	/**
	 * Process the user's answer to the current question.
	 * @param answer  the user's answer to the question
	 * @return the graded response to the question
	 * @throws EmptyQuestionListException if there is no current question
	 */
	public String processAnswer(String answer) throws EmptyQuestionListException;
	
	/**
	 * How many questions has the user answered correctly?
	 * @return the number of correct answers
	 */
	public int getNumCorrectQuestions();
	
	/**
	 * How many questions has the user attempted to answer.
	 * @return the number of attempts
	 */
	public int getNumAttemptedQuestions();
	
	/**
	 * Adds a StandardQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	public void addStandardQuestion(String questionText, String [] questionChoices, String correctAnswer);
	
	/**
	 * Adds an ElementaryQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @param hint a hint for the question
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	public void addElementaryQuestion(String questionText, String [] questionChoices, String correctAnswer, String hint);
	
	/**
	 * Adds an AdvancedQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @param comment a message for answering the question right
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	public void addAdvancedQuestion(String questionText, String [] questionChoices, String correctAnswer, String comment);
	
	/**
	 * Writes the Questions to the given file
	 * @param questionFile file name to write questions to
	 * @throws QuestionException if the questions cannot be written to the 
	 * given file
	 */
	public void writeQuestions(String questionFile) throws QuestionException;

}