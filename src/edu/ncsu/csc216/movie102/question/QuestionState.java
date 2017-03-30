package edu.ncsu.csc216.movie102.question;
import java.util.List;
import edu.ncsu.csc216.movie102.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.Question;
import java.util.ArrayList;
/**
 * An abstract class that represents state in the FSM
 * @author Ethan
 * @author Terrance
 * @author Mboya
 */
public abstract class QuestionState {

	private static final int FRONT = 0;
	//private Question askedQuestion;
	private Question currentQuestion;
	//private Question waitingQuestion;
	private ArrayList<Question> askedList = new ArrayList<Question>();
	private ArrayList<Question> waitingList = new ArrayList<Question>();

	
	/**
	 * Constructs the object for the QuestionState.
	 * Takes in a list of Questions that is added to the waitingList.
	 * @param questionList the list of questions .
	 */
	public QuestionState(List<Question> questionList){
		waitingList.addAll(questionList);
		// waitingQuestion = waitingList.get(0);

	}
	/**
	 *  Abstract method to process the user's answer; 
	 *  throws an EmptyQuestionListExceptions if currentQuestion is null. 
	 *  This method corresponds to transitions in the FSM. 
	 *  Each concrete state (nested classes inside MovieQuestions)
	 *   defines this method according to the transitions that go from that state.
	 * @param answer the answer submitted by the user
	 * @return CORRECT if the answer is right
	 * or INCORRECT if the answer is wrong.
	 * @throws EmptyQuestionListException 
	 */
	public abstract  String processAnswer( String answer) throws EmptyQuestionListException;

	/**
	 * True if currentQuestion is not null or waitingQuestions is not empty.
	 * @return true if the CurrentQuestion is not empty.
	 */
	public boolean hasMoreQuestions(){
		return (!waitingList.isEmpty() || currentQuestion != null);	
	}
	/**
	 * Return Strings of question.
	 * throw  new EmptyQuestionListExceptions 
	 * if the currentQuestion is null
	 * @return  currentQuestion
	 */
	public String getCurrentQuestionText() throws EmptyQuestionListException{
		if(currentQuestion == null) {
			throw new EmptyQuestionListException();
		} else
		return currentQuestion.getQuestion();
	}

	/**
	 * Return Strings of question choices.
	 * throw  new EmptyQuestionListExceptions 
	 * if the currentQuestion is null
	 * @return questionChoices
	 * @throws EmptyQuestionListException 
	 */
	public String[] getCurrentQuestionChoices() throws EmptyQuestionListException{
		if(currentQuestion == null){
			throw new EmptyQuestionListException("EmptyQuestionList");
			}
		String[] questionChoices = {currentQuestion.getChoiceA(), currentQuestion.getChoiceB(),
				currentQuestion.getChoiceC(), currentQuestion.getChoiceD()};
		return questionChoices;
	}

	/**
	 * Returns the answer of the question
	 * throw  new EmptyQuestionListExceptions 
	 * if the currentQuestion is null
	 * @return  answer to the current question
	 */
	public String getCurrentQuestionAnswer() throws EmptyQuestionListException{
		if(currentQuestion == null) {
			throw new EmptyQuestionListException();
		} else {
			return currentQuestion.getAnswer();
		}
	}

	/**
	 * Return Strings of current question.
	 * throw  new EmptyQuestionListExceptions 
	 * if the currentQuestion is null
	 * @return  the currentQuestion
	 */
	public Question getCurrentQuestion() throws EmptyQuestionListException{
		if(currentQuestion == null) {
			throw new EmptyQuestionListException();
		} else {
			return currentQuestion;
		}
	}

	/**
	 * Sets currentQuestion to the next item in the waitingQuestions list, 
	 * or null if there are no more questions in the list. 
	 * The currentQuestion is added to the end of the askedQuestions list
	 */
	public void nextQuestion(){
		if(!waitingList.isEmpty()) {
			//currentQuestion = waitingQuestion;
			currentQuestion = waitingList.remove(FRONT);
			askedList.add(currentQuestion);
		} else {
			currentQuestion = null;
		}
	}
	/**
	 * Adds the given question to the end of the waitingQuestions list. 
	 * If currentQuestion is null, implying there were no more questions of that type to ask,
	 *  nextQuestion() is executed because there is now a question to ask.
	 *  @param q the question to add
	 */
	public  void addQuestion(Question q){
		//Changed this method a little bit.
		//adding the right question?
		waitingList.add(q);
		if(currentQuestion == null) {
			nextQuestion();	
		}
	//	waitingList.add(q);
	}

	/**
	 * Returns a list of questions. 
	 * The list of questions combines the askedQuestions with the waitingQuestions. 
	 * The askedQuestions are added to the joint collection first. 
	 * @return temp the list of questions
	 */
	public ArrayList<Question> getQuestions(){
		ArrayList<Question> temp = new ArrayList<Question>();
		for(int i = 0; i < askedList.size(); i++) {
			temp.add(askedList.get(i));
		}
		for(int i = 0; i < waitingList.size(); i++) {
			temp.add(waitingList.get(i));
		}
		return temp;
	}

}
