package edu.ncsu.csc216.movie102.ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import edu.ncsu.csc216.movie102.quiz.MovieQuiz;
import edu.ncsu.csc216.movie102.quiz.QuizMaster;
import edu.ncsu.csc216.movie102.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.QuestionException;

/**
 * Teaching Staff GUI for the Movies102 Project
 * @author Jo Perry, Sarah Heckman, Jason King
 *
 */
public class Movie102GUI extends JFrame implements ActionListener {
	
	// Used for object serialization
	private static final long serialVersionUID = 1L;
	// Parameters for component sizes and spacings 
	private static final int FRAME_WIDTH = 550; 
	private static final int FRAME_HEIGHT = 300;
	// private static final int USAGE_EXIT_CODE = 1;
	private static final int FILE_EXIT_CODE = 2;
	private static final int TEXT_FIELD_LENGTH = 20;
	
	// Panel and window titles
	private static final String MENU_PANEL = "MenuPanel";
	private static final String Q_MAKER_PANEL = "QuestionMakerPanel";
	private static final String QUESTIONIER_PANEL = "QuestionierPanel";
	private static final String WINDOW_TITLE = "Movies 102";
	private static final String QUESTION_MAKER = "Add Questions";
	private static final String QUESTIONIER = "Take Movie Quiz";
	private static final String NEXT_QUESTION = "Next Question";
	private static final String SUBMIT = "Submit Answer";
	private static final String ADD_QUESTION = "Add";
	private static final String WRITE_QUESTIONS = "Write All";
	private static final String DONE = "Done";
	private static final String QUIT = "Quit";
	private static final String QUESTION_TYPE = "Question Type:";
	private static final String QUESTION_TEXT = "Question:";
	private static final String CHOICE_A = "Choice A:";
	private static final String CHOICE_B = "Choice B:";
	private static final String CHOICE_C = "Choice C:";
	private static final String CHOICE_D = "Choice D:";
	private static final String ANSWER = "Answer:";
	private static final String HINT = "Hint:";
	private static final String COMMENT = "Comment:";
	private static final int NUM_ANSWERS = 4;
	
	// Buttons, radio buttons
	private JButton btnQuestionMaker = new JButton(QUESTION_MAKER);
	private JButton btnQuestionier = new JButton(QUESTIONIER);
	private JButton btnNextQuestion = new JButton(NEXT_QUESTION);
	private JButton btnSubmitAnswer = new JButton(SUBMIT);
	private JButton btnDoneQuizzing = new JButton(DONE);
	private JButton btnQuitMenu = new JButton(QUIT);
	private JButton btnQuitQuizMaker = new JButton(QUIT);
	private JButton btnQuitQuestioner = new JButton(QUIT);
	private JButton btnAddQuestion = new JButton(ADD_QUESTION);
	private JButton btnWriteQuestions = new JButton(WRITE_QUESTIONS);
	private JButton btnDoneQuizMaker = new JButton(DONE);
	private JRadioButton[] rbAnswer = new JRadioButton[NUM_ANSWERS];
	private ButtonGroup group = new ButtonGroup();	
	
	// Labels
	private JLabel lblQuestion = new JLabel();
	private JLabel lblComment = new JLabel();
	private JLabel lblQuestionText = new JLabel(QUESTION_TEXT);
	private JLabel lblQChoiceA = new JLabel(CHOICE_A);
	private JLabel lblQChoiceB = new JLabel(CHOICE_B);
	private JLabel lblQChoiceC = new JLabel(CHOICE_C);
	private JLabel lblQChoiceD = new JLabel(CHOICE_D);
	private JLabel lblAnswer = new JLabel(ANSWER);
	private JLabel lblQuestionType = new JLabel(QUESTION_TYPE);
	private JLabel lblHintComment = new JLabel();	
	
	// Text Fields
	private JTextField txtQuestionText = new JTextField(TEXT_FIELD_LENGTH);
	private JTextField txtQChoiceA = new JTextField(TEXT_FIELD_LENGTH);
	private JTextField txtQChoiceB = new JTextField(TEXT_FIELD_LENGTH);
	private JTextField txtQChoiceC = new JTextField(TEXT_FIELD_LENGTH);
	private JTextField txtQChoiceD = new JTextField(TEXT_FIELD_LENGTH);
	private JTextField txtHintComment = new JTextField(TEXT_FIELD_LENGTH);
	
	// Panels
	private JPanel pnlMaster = new JPanel();
	private JPanel pnlMenu = new JPanel(new FlowLayout());
	private JPanel pnlQuestionier = new JPanel(new BorderLayout());
	private JPanel pnlQuestion = new JPanel(new BorderLayout());
	private JPanel pnlAnswers = new JPanel(new GridLayout(0, 1));
	private JPanel pnlButtons = new JPanel(new FlowLayout());
	private JPanel pnlQuestionMaker = new JPanel(new GridLayout(9, 2));
	private JPanel pnlAddQButtons1 = new JPanel(new FlowLayout());
	private JPanel pnlAddQButtons2 = new JPanel(new FlowLayout());	
	
	// Combo boxes
	private String [] questionTypes = {"Standard Question", "Elementary Question", "Advanced Question"};
	private String [] answers = {"A", "B", "C", "D"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JComboBox boxQuestionType = new JComboBox(questionTypes);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JComboBox boxAnswer = new JComboBox(answers);
	
	// Main window
	private Container mainWindow = getContentPane();
	private CardLayout cardLayout;

	private QuizMaster quizMaster;
	
	private String intToAlpha(int number){
		int newNumber = (int)'a' + number;
		return Character.toString((char) newNumber);
	}
	/**
	 * Performs actions in the GUI.
	 * @param event Any event being passed.
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnQuitMenu)
			quit();
		if (event.getSource() == btnQuitQuizMaker)
			quit();
		if (event.getSource() == btnQuitQuestioner) 
			quit();
		
		//Menu buttons
		if (event.getSource() == btnQuestionMaker) {
			cardLayout.show(pnlMaster, Q_MAKER_PANEL);
		}
		if (event.getSource() == btnQuestionier) {
			cardLayout.show(pnlMaster, QUESTIONIER_PANEL);
			populateQuestion();
		}
		
		//Buttons for adding questions
		if (event.getSource() == boxQuestionType) {
			btnAddQuestion.setEnabled(true);
			btnWriteQuestions.setEnabled(true);
			btnDoneQuizMaker.setEnabled(true);
			btnQuitQuizMaker.setEnabled(true);
			txtQuestionText.setEnabled(true);
			txtQChoiceA.setEnabled(true);
			txtQChoiceB.setEnabled(true);
			txtQChoiceC.setEnabled(true);
			txtQChoiceD.setEnabled(true);
			boxAnswer.setEnabled(true);
			
			int idx = boxQuestionType.getSelectedIndex();
			if (idx == 0) {
				lblHintComment.setVisible(false);  //Hide until question type is selected
				txtHintComment.setVisible(false);
			} else {
				txtHintComment.setVisible(true);
				if (idx == 1)
					lblHintComment.setText(HINT);
				if (idx == 2)
					lblHintComment.setText(COMMENT);
				lblHintComment.setVisible(true); 
			}
		}
		if (event.getSource() == btnAddQuestion) {
			int idx = boxQuestionType.getSelectedIndex();
			String questionText = txtQuestionText.getText();
			String [] questionChoices = new String[4];
			questionChoices[0] = txtQChoiceA.getText();
			questionChoices[1] = txtQChoiceB.getText();
			questionChoices[2] = txtQChoiceC.getText();
			questionChoices[3] = txtQChoiceD.getText();
			String answer = (String)boxAnswer.getSelectedItem();
			String hintComment = txtHintComment.getText();
			try {
				if (idx == 0) {
					quizMaster.addStandardQuestion(questionText, questionChoices, answer);
				} else if (idx == 1) {
					quizMaster.addElementaryQuestion(questionText, questionChoices, answer, hintComment);
				} else if (idx == 2) {
					quizMaster.addAdvancedQuestion(questionText, questionChoices, answer, hintComment);
				} 
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Cannot create question.");
			}
			//Clear out everything
			txtQuestionText.setText("");
			txtQChoiceA.setText("");
			txtQChoiceB.setText("");
			txtQChoiceC.setText("");
			txtQChoiceD.setText("");
			boxAnswer.setSelectedIndex(-1);
			txtHintComment.setText("");
			btnAddQuestion.setEnabled(false);
		}
		if (event.getSource() == btnDoneQuizMaker) {
			cardLayout.show(pnlMaster, MENU_PANEL);
		}
		if (event.getSource() == btnWriteQuestions) {
			//Get file name
			String userPickFilename = null;
			JFileChooser fc = new JFileChooser(".");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				userPickFilename = fc.getSelectedFile().getName();
			}
			try {
				quizMaster.writeQuestions(userPickFilename);
			} catch (QuestionException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Unable to write to file.");
			}
		}
		
		//Buttons when taking the Movie quiz
		if (event.getSource() == btnDoneQuizzing) 
		{
			doneQuizzing();
		}
		if (event.getSource() == btnSubmitAnswer) {
			int selected = 0;
			for (int k = 0; k < NUM_ANSWERS; k++) {
				if (rbAnswer[k].isSelected()) {
					selected = k;
				break;
				}
			}
			try {
			   lblComment.setText(quizMaster.processAnswer(intToAlpha(selected)));
			   btnSubmitAnswer.setEnabled(false);
			   btnNextQuestion.setEnabled(true);
			}
			catch (EmptyQuestionListException e) {
				// Do something!
				e.getMessage();
			}
		}
		if (event.getSource() == btnNextQuestion) {
			populateQuestion();
			lblComment.setText("");
			btnSubmitAnswer.setEnabled(true);
			btnNextQuestion.setEnabled(false);
		}
	}

	private Movie102GUI(String questionFile) {
		if (questionFile == null) {
			String userPickFilename = null;
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				userPickFilename = fc.getSelectedFile().getName();
			}
			questionFile = userPickFilename;
		}
		try {
			quizMaster = new MovieQuiz(questionFile);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			addListeners();
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			setTitle(WINDOW_TITLE);
			
			setUpMenu();
			setUpQuestionier();
			setUpQuestionMaker();
			
			cardLayout = new CardLayout();
			pnlMaster.setLayout(cardLayout);
			pnlMaster.add(pnlMenu, MENU_PANEL);
			pnlMaster.add(pnlQuestionMaker, Q_MAKER_PANEL);
			pnlMaster.add(pnlQuestionier, QUESTIONIER_PANEL);
			cardLayout.show(pnlMaster, MENU_PANEL);
			
			mainWindow.add(pnlMaster);
			this.setVisible(true);
		} catch (QuestionException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid File: " + e.getMessage());
			System.exit(FILE_EXIT_CODE);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
			System.exit(FILE_EXIT_CODE);
		}
	}
		
	private void addListeners() {
		//Menu functionality
		btnQuestionMaker.addActionListener(this);
		btnQuestionier.addActionListener(this);
		
		// Play game functionality
		btnSubmitAnswer.addActionListener(this);
		btnNextQuestion.addActionListener(this);
		btnQuitMenu.addActionListener(this);
		btnQuitQuizMaker.addActionListener(this);
		btnQuitQuestioner.addActionListener(this);
		for (int k = 0; k < NUM_ANSWERS; k++) {
			rbAnswer[k] = new JRadioButton();
			rbAnswer[k].addActionListener(this);
			rbAnswer[k].setText("");
			group.add(rbAnswer[k]);
		}
		
		// Add questions functionality
		boxQuestionType.addActionListener(this);
		btnAddQuestion.addActionListener(this);
		btnWriteQuestions.addActionListener(this);
		btnDoneQuizMaker.addActionListener(this);
		btnDoneQuizzing.addActionListener(this);
	}
	
	private void setUpMenu() {
		pnlMenu.add(btnQuestionMaker);
		pnlMenu.add(btnQuestionier);
		pnlMenu.add(btnQuitMenu);
	}
	
	private void setUpQuestionier() {
		pnlQuestion.add(lblQuestion, BorderLayout.NORTH);
		for (int k = 0; k < NUM_ANSWERS; k++)
			pnlAnswers.add(rbAnswer[k]);
		pnlQuestion.add(pnlAnswers, BorderLayout.CENTER);
		pnlQuestion.add(lblComment, BorderLayout.SOUTH);
		pnlButtons.add(btnSubmitAnswer);
		pnlButtons.add(btnNextQuestion);
		pnlButtons.add(btnDoneQuizzing);
		pnlButtons.add(btnQuitQuestioner);
		pnlQuestionier.add(pnlQuestion, BorderLayout.CENTER);
		pnlQuestionier.add(pnlButtons, BorderLayout.SOUTH);
		btnNextQuestion.setEnabled(false);
		btnSubmitAnswer.setEnabled(true);
	}
	
	private void setUpQuestionMaker() {
		pnlAddQButtons1.add(btnAddQuestion);
		pnlAddQButtons1.add(btnWriteQuestions);
		pnlAddQButtons2.add(btnDoneQuizMaker);
		pnlAddQButtons2.add(btnQuitQuizMaker);
		
		pnlQuestionMaker.add(lblQuestionType);
		pnlQuestionMaker.add(boxQuestionType); 
		boxQuestionType.setSelectedIndex(-1);
		pnlQuestionMaker.add(lblQuestionText);
		pnlQuestionMaker.add(txtQuestionText);
		pnlQuestionMaker.add(lblQChoiceA);
		pnlQuestionMaker.add(txtQChoiceA);
		pnlQuestionMaker.add(lblQChoiceB);
		pnlQuestionMaker.add(txtQChoiceB);
		pnlQuestionMaker.add(lblQChoiceC);
		pnlQuestionMaker.add(txtQChoiceC);
		pnlQuestionMaker.add(lblQChoiceD);
		pnlQuestionMaker.add(txtQChoiceD);
		pnlQuestionMaker.add(lblAnswer);
		pnlQuestionMaker.add(boxAnswer);
		pnlQuestionMaker.add(lblHintComment);
		pnlQuestionMaker.add(txtHintComment);
		pnlQuestionMaker.add(pnlAddQButtons1);
		pnlQuestionMaker.add(pnlAddQButtons2);
		
		btnAddQuestion.setEnabled(false);
		btnWriteQuestions.setEnabled(true);
		btnDoneQuizMaker.setEnabled(true);
		btnQuitQuizMaker.setEnabled(true);
		txtQuestionText.setEnabled(false);
		txtQChoiceA.setEnabled(false);
		txtQChoiceB.setEnabled(false);
		txtQChoiceC.setEnabled(false);
		txtQChoiceD.setEnabled(false);
		boxAnswer.setEnabled(false);
		lblHintComment.setVisible(false);  //Hide until question type is selected
		txtHintComment.setVisible(false);
		
	}
	
	private void populateQuestion() {
		if (quizMaster.hasMoreQuestions()) {
			try {
				lblQuestion.setText(quizMaster.getCurrentQuestionText());
				String[] s = quizMaster.getCurrentQuestionChoices();
				for (int k = 0; k < NUM_ANSWERS; k++)
					rbAnswer[k].setText(s[k]);
				group.clearSelection();
			} catch (EmptyQuestionListException e) {
				doneQuizzing();
			}
		}
		else
			doneQuizzing();
	}
	
	private void doneQuizzing() {
		int x = quizMaster.getNumCorrectQuestions();
		int y = quizMaster.getNumAttemptedQuestions();
		String goodbye = "You answered " + x + " questions correctly out of " + y + " attempts.";
		JOptionPane.showMessageDialog(new JFrame(), goodbye);
		
		cardLayout.show(pnlMaster, MENU_PANEL);
	}
	
	private void quit() {
		System.exit(0);
	}

	/**
	 * Starts the Movie101 Program
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length == 0)
			new Movie102GUI(null);
		else
			new Movie102GUI(args[0]);
	}
}

