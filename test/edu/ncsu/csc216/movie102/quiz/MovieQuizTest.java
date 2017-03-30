package edu.ncsu.csc216.movie102.quiz;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.movie102.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.QuestionException;
/**
 *Test class for MovieQuiz.Test that the object is constructed correctly
 * and that the state of the object is manipulated properly.
 * @author Ethan
 * @author Terrance
 * @author Mboya
 */
public class MovieQuizTest {
	private MovieQuiz movieQuiz;
	/**
	 *  Sets up the MovieQuizTest by using a file, all.xml, to test in creating
     * a MovieQuizTest object.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		movieQuiz = new MovieQuiz("all.xml");
		//Make around three questions.
		//Initialize constructor.
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#hasMoreQuestions()}.
	 */
	@Test
	public void testHasMoreQuestions() {
		//after making the questions, add them to the waiting list
		//run the method, make sure the questions were properly placed.
		assertTrue(movieQuiz.hasMoreQuestions());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#getCurrentQuestionText()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGetCurrentQuestionText() throws EmptyQuestionListException {
		//pretty simple, just set a current question.
		assertEquals("How many licks to reach the center of a Tootsie Pop?", movieQuiz.getCurrentQuestionText());
		movieQuiz.processAnswer("c");
		assertEquals("How much wood can a woodchuck chuck?", movieQuiz.getCurrentQuestionText());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#getCurrentQuestionChoices()}.
	 */
	@Test
	public void testGetCurrentQuestionChoices(){
		try {
			String[] test = {"1", "2", "3", "the world may never know"};
			movieQuiz.processAnswer("d");
			String[] t =  movieQuiz.getCurrentQuestionChoices();
		} catch(EmptyQuestionListException e) {
			assertEquals("EmptyQuestionList", e.getMessage());
		}
		String [] questionChoices = {"a", "b", "c", "d"};
		movieQuiz.addStandardQuestion("Answer A", questionChoices, "a");
		try {
			assertArrayEquals(questionChoices, movieQuiz.getCurrentQuestionChoices());
		} catch (EmptyQuestionListException e) {
			fail();
		}
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#processAnswer(java.lang.String)}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testProcessAnswer() throws EmptyQuestionListException {
		//Use this to see if the program goes to the correct state.
		assertEquals("How many licks to reach the center of a Tootsie Pop?", movieQuiz.getCurrentQuestionText());
		movieQuiz.processAnswer("c");
		assertEquals("How much wood can a woodchuck chuck?", movieQuiz.getCurrentQuestionText());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#getNumCorrectQuestions()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test(expected = EmptyQuestionListException.class)
	public void testGetNumCorrectQuestions() throws EmptyQuestionListException {
		//String[] test = {"1", "2", "3", "4"};
		//movieQuiz.addStandardQuestion("1?", test, "c");
		movieQuiz.processAnswer("d");
		assertEquals(1, movieQuiz.getNumCorrectQuestions());
		movieQuiz.processAnswer("c");
		assertEquals(2, movieQuiz.getNumCorrectQuestions());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#getNumAttemptedQuestions()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGetNumAttemptedQuestions() throws EmptyQuestionListException {
		//run some questions through and then check the current
		//question's state or something to make sure it transitioned
		//properly.
		assertEquals("How many licks to reach the center of a Tootsie Pop?", movieQuiz.getCurrentQuestionText());
		movieQuiz.processAnswer("a");
		assertEquals(1, movieQuiz.getNumAttemptedQuestions());
		movieQuiz.processAnswer("a");
		assertEquals(2, movieQuiz.getNumAttemptedQuestions());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#addStandardQuestion(java.lang.String, java.lang.String[], java.lang.String)}.
	 * @throws EmptyQuestionListException If the list is empty.
	 */
	@Test
	public void testAddStandardQuestion() throws EmptyQuestionListException {
		//pretty easy, just add one then make sure its there
		String [] questionChoices = {"a", "b", "c", "d"};
		movieQuiz.addStandardQuestion("Answer is a", questionChoices, "a");
		movieQuiz.addStandardQuestion("Answer is not b", questionChoices, "a");
		movieQuiz.processAnswer("d");
		assertEquals("Answer is a", movieQuiz.getCurrentQuestionText());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#addElementaryQuestion(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testAddElementaryQuestion() throws EmptyQuestionListException {
		String [] questionChoices = {"a", "b", "c", "d"};
		movieQuiz.addStandardQuestion("Answer is a", questionChoices, "a");
		movieQuiz.addElementaryQuestion("Answer is b", questionChoices, "b", "It's b");
		movieQuiz.processAnswer("c");
		movieQuiz.processAnswer("a");
		assertEquals("Answer is b", movieQuiz.getCurrentQuestionText());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#addAdvancedQuestion(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testAddAdvancedQuestion() throws EmptyQuestionListException {
		//up two
		String [] questionChoices = {"a", "b", "c", "d"};
		movieQuiz.addStandardQuestion("Answer is a", questionChoices, "a");
		movieQuiz.addAdvancedQuestion("Answer is b", questionChoices, "b", "It's b");
		movieQuiz.processAnswer("d");
		movieQuiz.processAnswer("a");
		assertEquals("Answer is b", movieQuiz.getCurrentQuestionText());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.quiz.MovieQuiz#writeQuestions(java.lang.String)}.
	 * @throws QuestionException 
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testWriteQuestions() throws QuestionException, EmptyQuestionListException {
		//write a question, check to make sure it is in a list.
		MovieQuiz test1 = new MovieQuiz("all.xml");
		test1.writeQuestions("testFile.xml");
		MovieQuiz test2 = new MovieQuiz("testFile.xml");
		assertEquals(test1.getCurrentQuestionText(), test2.getCurrentQuestionText());
	}

}
