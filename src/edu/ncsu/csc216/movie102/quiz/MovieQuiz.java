package edu.ncsu.csc216.movie102.quiz;
import java.util.List;

import edu.ncsu.csc216.movie102.question.MovieQuestions;
import edu.ncsu.csc216.movie102.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.AdvancedQuestion;
import edu.ncsu.csc216.question_library.ElementaryQuestion;
import edu.ncsu.csc216.question_library.Question;
import edu.ncsu.csc216.question_library.QuestionException;
import edu.ncsu.csc216.question_library.QuestionReader;
import edu.ncsu.csc216.question_library.QuestionWriter;
import edu.ncsu.csc216.question_library.StandardQuestion;

/**
 * This class implements the QuizMaster interface.
 * Handles questions: adding questions, writing questions, processingAnswers
 * and getting the question choices among others.
 *@author Ethan
 * @author Terrance
 *  @author Mboya
 */
public class MovieQuiz implements QuizMaster {

	private QuestionReader reader;
	private QuestionWriter writer;
	private MovieQuestions questions;
	
/**
 * Constructs the MovieQuiz objects. Initializes the reader for
 * questions for the three categories.
 * @param questionFile the file of questions
 * @throws QuestionException for catching errors.
 */
	public MovieQuiz(String questionFile) throws QuestionException {
		reader = new QuestionReader(questionFile);
		questions = new MovieQuestions(reader.getStandardQuestions(),
				reader.getElementaryQuestions(), 
				reader.getAdvancedQuestions());	
	}

	/**
	 * Are there any more questions remaining in this test?
	 * @return true if there are, false if there are not
	 */
	@Override
	public boolean hasMoreQuestions() {
		return questions.hasMoreQuestions();
	}

	/**
	 * Get the text for the current question.
	 * @return the current question text
	 * @throws EmptyQuestionListException if there is no current question
	 */
	@Override
	public String getCurrentQuestionText() throws EmptyQuestionListException {
		return questions.getCurrentQuestionText();		
	}

	/**
	 * Get the possible answers for the current question
	 * @return the possible answers for the current question -- each answer
	 *         is a separate array element
	 * * @throws EmptyQuestionListException if there is no current question
	 */
	public String[] getCurrentQuestionChoices()
			throws EmptyQuestionListException {
		return questions.getCurrentQuestionChoices();
	}

	/**
	 * Process the user's answer to the current question.
	 * @param answer  the user's answer to the question
	 * @return the graded response to the question
	 * @throws EmptyQuestionListException if there is no current question
	 */
	public String processAnswer(String answer)
			throws EmptyQuestionListException {
		 return questions.processAnswer(answer);
		/*if(questions.getCurrentQuestionText() == null) {
			throw new EmptyQuestionListException("No current Question");
		} else if(questions.processAnswer(answer).equals("Correct")) {
			return "Correct.";
		} else {
			return "Incorrect";
		}*/
	}

	/**
	 * How many questions has the user answered correctly?
	 * @return the number of correct answers
	 */
	public int getNumCorrectQuestions() {
		return questions.getNumCorrectQuestions();
	}

	/**
	 * How many questions has the user attempted to answer.
	 * @return the number of attempts
	 */
	public int getNumAttemptedQuestions() {
		return questions.getNumAttemptedQuestions();
	}

	/**
	 * Adds a StandardQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	public void addStandardQuestion(String questionText,
			String[] questionChoices, String correctAnswer) {
		 validateString(questionText);
		 validateString(correctAnswer);
		  validateStringArray(questionChoices);
			StandardQuestion stand = new StandardQuestion();
			stand.setQuestion(questionText);
			stand.setChoiceA(questionChoices[0]);
			stand.setChoiceB(questionChoices[1]);
			stand.setChoiceC(questionChoices[2]);
			stand.setChoiceD(questionChoices[3]);
			stand.setAnswer(correctAnswer);
			questions.addStandardQuestion(stand);
	
	}

	/**
	 * Adds an ElementaryQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @param hint a hint for the question
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	public void addElementaryQuestion(String questionText,
			String[] questionChoices, String correctAnswer, String hint) {		
		 validateString(questionText);
		 validateString(correctAnswer);
		 validateString(hint);
		 validateStringArray(questionChoices);
			ElementaryQuestion elem = new ElementaryQuestion();
			elem.setQuestion(questionText);
			elem.setChoiceA(questionChoices[0]);
			elem.setChoiceB(questionChoices[1]);
			elem.setChoiceC(questionChoices[2]);
			elem.setChoiceD(questionChoices[3]);
			elem.setAnswer(correctAnswer);
			elem.setHint(hint);
			questions.addElementaryQuestion(elem);
		}


	/**
	 * Adds an AdvancedQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @param comment a message for answering the question right
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	public void addAdvancedQuestion(String questionText,
			String[] questionChoices, String correctAnswer, String comment) {
		 validateString(questionText);
		 validateString(correctAnswer);
		 validateString(comment);
		 validateStringArray(questionChoices);
			AdvancedQuestion adv = new AdvancedQuestion();
			adv.setQuestion(questionText);
			adv.setChoiceA(questionChoices[0]);
			adv.setChoiceB(questionChoices[1]);
			adv.setChoiceC(questionChoices[2]);
			adv.setChoiceD(questionChoices[3]);
			adv.setAnswer(correctAnswer);
			adv.setComment(comment);
			questions.addAdvancedQuestion(adv);
		
	}

	/**
	 * Writes the Questions to the given file
	 * @param questionFile file name to write questions to
	 * @throws QuestionException if the questions cannot be written to the 
	 * given file
	 */
	public void writeQuestions(String questionFile) throws QuestionException {
		// initialize the writer by giving it the file passed
		writer = new QuestionWriter(questionFile);
		// Get the list of the questions
		List<Question> aq = questions.getAdvancedQuestions();
		List<Question> sq = questions.getStandardQuestions();
		List<Question> eq = questions.getElementaryQuestions();

		for (Question qsn : sq) {
			writer.addStandardQuestion((StandardQuestion) qsn);
		}

		for (Question qn : eq) {
			writer.addElementaryQuestion((ElementaryQuestion) qn);
		}

		for (Question q : aq) {
			writer.addAdvancedQuestion((AdvancedQuestion) q);
		}
		writer.marshal();
	}
	
	private void validateString( String givenString){
		if(givenString == null || givenString.length() == 0){
			throw new IllegalArgumentException();
		}
	}
	
	private void validateStringArray( String[] choices){
		if(choices == null || choices.length != 4){
			throw new IllegalArgumentException();
		}
		for (int index = 0; index < choices.length ; index++ ){
			validateString(choices[index]);
		}
	}

}
