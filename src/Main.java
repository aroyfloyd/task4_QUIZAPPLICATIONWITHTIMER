import java.util.*;

class Question {
    String questionText;
    String[] options;
    int correctAnswerIndex;

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

class Quiz {
    private List<Question> questions;
    private int score;
    private List<String> results;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.score = 0;
        this.results = new ArrayList<>();
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < questions.size(); i++) {
            final int questionIndex = i;  // Create a final variable to hold the index
            Question question = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + question.questionText);

            for (int j = 0; j < question.options.length; j++) {
                System.out.println((j + 1) + ". " + question.options[j]);
            }

            System.out.println("You have 10 seconds to answer.");
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    System.out.println("\nTime's up! Moving to the next question.");
                    results.add("Q" + (questionIndex + 1) + ": Incorrect (Time's up)");
                    timer.cancel();
                }
            };
            timer.schedule(task, 10000);

            System.out.print("Enter your answer (1-" + question.options.length + "): ");
            long startTime = System.currentTimeMillis();
            int answer = scanner.nextInt();
            long elapsedTime = System.currentTimeMillis() - startTime;

            timer.cancel(); // Stop timer if the user answers in time

            // Check if answer is within time limit
            if (elapsedTime < 10000) {
                if (answer - 1 == question.correctAnswerIndex) {
                    score++;
                    results.add("Q" + (questionIndex + 1) + ": Correct");
                    System.out.println("Correct!");
                } else {
                    results.add("Q" + (questionIndex + 1) + ": Incorrect");
                    System.out.println("Incorrect.");
                }
            } else {
                results.add("Q" + (questionIndex + 1) + ": Incorrect (Time's up)");
                System.out.println("You ran out of time.");
            }
        }

        scanner.close();
        displayResults();
    }

    private void displayResults() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your final score: " + score + " out of " + questions.size());
        System.out.println("\nResults Summary:");
        for (String result : results) {
            System.out.println(result);
        }
    }
}

class QuizSystem {
    public static void main(String[] args) {
        // Create questions
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(
                "What is the capital of France?",
                new String[]{"Berlin", "Paris", "Rome", "Madrid"},
                1
        ));
        questions.add(new Question(
                "Who is known as the father of computer science?",
                new String[]{"Charles Babbage", "Alan Turing", "Ada Lovelace", "Tim Berners-Lee"},
                1
        ));
        questions.add(new Question(
                "What is the largest planet in our Solar System?",
                new String[]{"Earth", "Jupiter", "Mars", "Saturn"},
                1
        ));

        // Start quiz
        Quiz quiz = new Quiz(questions);
        quiz.startQuiz();
    }
}
