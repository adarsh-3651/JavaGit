package np.edu.heraldcollege;

/**
 * The {@code Question} class represents a single question in the quiz game.
 * It contains the question text, four answer options, and the correct answer.
 * This class provides methods to access and modify the question and its options.
 */
public class Question {
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String rightAnswer;

    /**
     * Constructs a new {@code Question} with the specified question text, 
     * options, and correct answer.
     *
     * @param question   The question text.
     * @param optionA    The first answer option.
     * @param optionB    The second answer option.
     * @param optionC    The third answer option.
     * @param optionD    The fourth answer option.
     * @param rightAnswer The correct answer option.
     */
    public Question(String question, String optionA, String optionB, String optionC, String optionD, String rightAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.rightAnswer = rightAnswer;
    }

    /**
     * Returns the question text.
     *
     * @return The question text.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the first answer option.
     *
     * @return The first answer option.
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * Returns the second answer option.
     *
     * @return The second answer option.
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * Returns the third answer option.
     *
     * @return The third answer option.
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * Returns the fourth answer option.
     *
     * @return The fourth answer option.
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     * Returns the correct answer option.
     *
     * @return The correct answer option.
     */
    public String getRightAnswer() {
        return rightAnswer;
    }

    /**
     * Sets the question text.
     *
     * @param question The question text.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Sets the first answer option.
     *
     * @param optionA The first answer option.
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    /**
     * Sets the second answer option.
     *
     * @param optionB The second answer option.
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    /**
     * Sets the third answer option.
     *
     * @param optionC The third answer option.
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    /**
     * Sets the fourth answer option.
     *
     * @param optionD The fourth answer option.
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    /**
     * Sets the correct answer option.
     *
     * @param rightAnswer The correct answer option.
     */
    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
