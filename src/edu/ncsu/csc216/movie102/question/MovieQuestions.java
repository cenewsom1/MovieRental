package edu.ncsu.csc216.movie102.question;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc216.movie102.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.AdvancedQuestion;
import edu.ncsu.csc216.question_library.ElementaryQuestion;
import edu.ncsu.csc216.question_library.Question;
import edu.ncsu.csc216.question_library.StandardQuestion;

/**
 * Implements a Finite State Machine for question sequencing, additions, and
 * transitions.
 * 
 * @author Ethan
 * @author Mboya
 * @author Terrance
 */

public class MovieQuestions {

	private int numCorrectAnswers;
	private int numAttemptAnswers;

	private QuestionState advState;
	private QuestionState stdState;
	private QuestionState elemState;
	private QuestionState state;
    /**Message printed when the answer is  right*/
	public static final String CORRECT = "Correct!";
	/**Message printed when the answer is  wrong*/
	public static final String INCORRECT = "Incorrect!";
	/**Prints the spaces*/
	public static final String SEPARATOR = "Separator";

	/**
	 * A constructor that takes three Lists to initialize its nested concrete
	 * state classes.
	 * @param stdList the list of StandardQuestion
	 * @param advList the list of AdvancedQuestion
	 * @param elemList the list of ElementaryQuestion
	 */
	public MovieQuestions(List<StandardQuestion> stdList,
			List<ElementaryQuestion> elemList,
			List<AdvancedQuestion> advList) {
		advState = new AdvancedQuestionState(advList);
		stdState = new StandardQuestionState(stdList);
		elemState = new ElementaryQuestionState(elemList);
		state = stdState; // UC 4
		state.nextQuestion();
		numAttemptAnswers = 0;
		numCorrectAnswers = 0;
	}

	/**
	 * True if there are any more questions remaining in the quiz. 
	 * @return false
	 */
	public boolean hasMoreQuestions() {
		return state.hasMoreQuestions();
	}

	/**
	 * Returns the current question displayed to the user
	 * 
	 * @return question
	 */
	public String getCurrentQuestionText() throws EmptyQuestionListException {
		return state.getCurrentQuestionText();
	}

	/**
	 * Returns the four choices of the current question
	 * 
	 * @return choices
	 */
	public String[] getCurrentQuestionChoices() throws EmptyQuestionListException {
		return state.getCurrentQuestionChoices();
	}

	/**
	 * Processes the answer submitted by the user. MovieQuestions relies on its
	 * current state to handle the processing. May throw an
	 * EmptyQuestionListException if the answer cannot be processed.	 * 
	 * @param answer the user's password
	 * @return theAnswer
	 * @throws EmptyQuestionListException 
	 */
	public String processAnswer(String answer) throws EmptyQuestionListException {
		//if(state.getQuestions().isEmpty()) {
			//throw new EmptyQuestionListException();
		//} else
		return state.processAnswer(answer);
	}

	/**
	 * Returns the number of questions whose answers were correct.
	 * 
	 * @return numCorrectAnswer
	 */
	public int getNumCorrectQuestions() {
		return numCorrectAnswers;
	}

	/**
	 * Returns the number of questions attempted by the user.
	 * 
	 * @return numAttemptAnswers
	 */
	public int getNumAttemptedQuestions() {
		return numAttemptAnswers;

	}

	/**
	 * Adds a Standard question to the appropriate state object through
	 * delegation	
	 * @param standardQ the standard questions to be added
	 */
	public void addStandardQuestion(StandardQuestion standardQ) {
		stdState.addQuestion(standardQ);
	}

	/**
	 * Adds a Elementary question to the appropriate state object through
	 * delegation
	 * 
	 * @param elemQ the elementary question to be added
	 */
	public void addElementaryQuestion(ElementaryQuestion elemQ) {
		elemState.addQuestion(elemQ);
	}

	/**
	 * Adds a Advanced question to the appropriate state object through
	 * delegation
	 * 
	 * @param advancedQ the advancedQuestion to be added
	 */
	public void addAdvancedQuestion(AdvancedQuestion advancedQ) {
		advState.addQuestion(advancedQ);
	}

	/**
	 * Returns a list of Standard Questions	 * 
	 * @return stdQsn
	 */
	public ArrayList<Question> getStandardQuestions() {
		return stdState.getQuestions();
	}

	/**
	 * Returns a list of Elementary Questions
	 * 
	 * @return elemQsn
	 */
	public ArrayList<Question> getElementaryQuestions() {
		return elemState.getQuestions();
	}

	/**
	 * Returns a list of Standard Questions
	 * 
	 * @return stdQsn
	 */
	public ArrayList<Question> getAdvancedQuestions() {
		return advState.getQuestions();
	}
	/**
	 * AdvancedQuestionState class extends QuestionState.
	 * Handles all the questions and transitions regarding the 
	 * Advanced questions.
	 * @author Samuel
	 *
	 */
	private class AdvancedQuestionState extends QuestionState {

		public AdvancedQuestionState(List<AdvancedQuestion> advList) {
			super(new ArrayList<Question>(advList));
		}

		/**
		 * Processes the answer submitted by the user. MovieQuestions relies on
		 * its current state to handle the processing. May throw an
		 * EmptyQuestionListException if the answer cannot be processed. 
		 * @param answer
		 * @return theAnswer
		 * @throws EmptyQuestionListException 
		 */
		@Override
		public String processAnswer(String answer) throws EmptyQuestionListException {
			if (answer.equalsIgnoreCase(state.getCurrentQuestionAnswer())) {				
				numAttemptAnswers++;
				numCorrectAnswers++;
				String comment = ((AdvancedQuestion)state.getCurrentQuestion()).getComment();
				state.nextQuestion();
				return CORRECT +  comment;
			} else {
				state = stdState;
				numAttemptAnswers++;
				state.nextQuestion();
				return INCORRECT;
			}
		}
	}

	/**
	 * ElementaryQuestionState class extends QuestionState.
	 * Handles all the questions and transitions regarding the 
	 * elementary questions.
	 * @author Samuel
	 *
	 */
	private class ElementaryQuestionState extends QuestionState {

		private int attempts;
		private int numCorrectInRow;

		public ElementaryQuestionState(List<ElementaryQuestion> elemList) {
			super(new ArrayList<Question>(elemList));
			attempts = 0;
			numCorrectInRow = 0;
		}

		/**
		 * Processes the answer submitted by the user. MovieQuestions relies on
		 * its current state to handle the processing. May throw an
		 * EmptyQuestionListException if the answer cannot be processed.
		 * 
		 * @param answer
		 * @return theHint
		 * @throws EmptyQuestionListException 
		 */
		@Override
		public String processAnswer(String answer) throws EmptyQuestionListException {
			if (answer.equalsIgnoreCase(state.getCurrentQuestionAnswer())) {				
				if (attempts == 0){
					numCorrectInRow++;
					numAttemptAnswers++; }
					numCorrectAnswers++;
				if (numCorrectInRow == 2) {
					attempts = 0;
					state = stdState;
					numCorrectInRow = 0;
					state.nextQuestion();
					return CORRECT;
				} else {
					attempts = 0;
					state.nextQuestion();
					return CORRECT;
				}
			} else {
				numCorrectInRow = 0;
				attempts++;
				if (attempts == 1)
					numAttemptAnswers++;
				if (attempts == 2) {
					state.nextQuestion();

					attempts = 0;
					return INCORRECT;
				} else {
					String theHint = ((ElementaryQuestion) state
							.getCurrentQuestion()).getHint();
					return INCORRECT + " Hint: " + theHint;
				}
			}
		}
	}

	/**
	 * StandardQuestionState class extends QuestionState.
	 * Handles all the questions and transitions regarding the 
	 * standard questions.
	 * @author Samuel
	 *
	 */
	private class StandardQuestionState extends QuestionState {

		private int numCorrectInRow;

		public StandardQuestionState(List<StandardQuestion> standList) {
			super(new ArrayList<Question>(standList));
			numCorrectInRow = 0;
		}

		/**
		 * Processes the answer submitted by the user. MovieQuestions relies on
		 * its current state to handle the processing. May throw an
		 * EmptyQuestionListException if the answer cannot be processed.
		 * 
		 * @param answer
		 * @return theAnswer
		 * @throws EmptyQuestionListException 
		 */
		@Override
		public String processAnswer(String answer) throws EmptyQuestionListException {
			if (answer.equalsIgnoreCase(state.getCurrentQuestionAnswer())) {
				numCorrectInRow++;
				numAttemptAnswers++;
				numCorrectAnswers++;
				if (numCorrectInRow == 2) {
					state = advState;
					state.nextQuestion();
					numCorrectInRow = 0;
					return CORRECT;
				} else {
					state.nextQuestion();
					return CORRECT;
				}
			} else {
				state = elemState;
				numAttemptAnswers++;
				state.nextQuestion();
				numCorrectInRow = 0;
				return INCORRECT;
			}

		}
	}
}
