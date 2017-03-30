package edu.ncsu.csc216.movie102.question;
import static org.junit.Assert.*;
import edu.ncsu.csc216.movie102.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
/**
 * Determines if the methods and paths in the MovieQuestion Class
 * work as expected
 * @author Samuel
 *
 */
public class MovieQuestionsTest {
	
	private MovieQuestions test;
	private QuestionReader reader;
	List<StandardQuestion> sTest = new ArrayList<StandardQuestion>();
	List<AdvancedQuestion> aTest = new ArrayList<AdvancedQuestion>();
	List<ElementaryQuestion> eTest = new ArrayList<ElementaryQuestion>();	

	/**
	 * Initializes the variables declared above.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {		
		//test = new MovieQuestions( null, null, null);
		reader = new QuestionReader("questions1.xml");
		aTest = reader.getAdvancedQuestions();
		sTest = reader.getStandardQuestions();
		eTest = reader.getElementaryQuestions();
		test = new MovieQuestions( sTest, eTest, aTest);
	}
	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.question.MovieQuestions#hasMoreQuestions()}.
	 */
	@Test
	public void testHasMoreQuestions() {
		assertEquals(true, test.hasMoreQuestions());
		StandardQuestion e = new StandardQuestion() ;
		sTest.add(e);
		assertEquals(true, test.hasMoreQuestions());

	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.question.MovieQuestions#geCurrentQuestionText()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGeCurrentQuestionText() throws EmptyQuestionListException {
		assertEquals("Standard Question 1", test.getCurrentQuestionText());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.question.MovieQuestions#getCurrentQuestionChoices()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGetCurrentQuestionChoices() throws EmptyQuestionListException {
		String[] test1 = {"Choice a", "Choice b", "Choice c", "Choice d"};
		assertArrayEquals(test1, test.getCurrentQuestionChoices());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.question.MovieQuestions#processAnswer(java.lang.String)}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testProcessAnswer() throws EmptyQuestionListException {
		test.processAnswer("d");
		test.processAnswer("c");
		assertEquals("Advanced Question 1", test.getCurrentQuestionText());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.question.MovieQuestions#getNumCorrectQuestions()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGetNumCorrectQuestions() throws EmptyQuestionListException {
		test.processAnswer("d");
		test.processAnswer("c");
		assertEquals(2, test.getNumCorrectQuestions());
		test.processAnswer("q");
		assertEquals(2, test.getNumCorrectQuestions());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.question.MovieQuestions#getNumAttemptedQuestions()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGetNumAttemptedQuestions() throws EmptyQuestionListException {
		test.processAnswer("d");
		test.processAnswer("c");
		assertEquals(2, test.getNumAttemptedQuestions());
		test.processAnswer("q");
		assertEquals(3, test.getNumAttemptedQuestions());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.question.MovieQuestions#getStandardQuestions()}.
	 */
	@Test
	public void testGetStandardQuestions() {
		assertEquals(3, test.getStandardQuestions().size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.question.MovieQuestions#getElementaryQuestions()}.
	 */
	@Test
	public void testGetElementaryQuestions() {
		assertEquals(4, test.getElementaryQuestions().size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.movie102.question.MovieQuestions#getAdvancedQuestions()}.
	 */
	@Test
	public void testGetAdvancedQuestions() {
		assertEquals(2, test.getAdvancedQuestions().size());
	}

}
