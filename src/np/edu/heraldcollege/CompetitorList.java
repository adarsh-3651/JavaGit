package np.edu.heraldcollege;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages a list of competitors in a quiz game.
 * <p>
 * This class provides functionality to store and manage competitors, 
 * as well as interact with the associated CompetitorManager.
 */

public class CompetitorList {
    private final List<Competitor> competitors;
    private final CompetitorManager manager;

    /**
     * Constructor to initialize CompetitorList.
     */
    public CompetitorList() {
        this.competitors = new ArrayList<>();
        this.manager = new CompetitorManager();
    }

    /**
     * Adds a competitor to the list and saves it to the database.
     *
     * @param competitor Competitor object to add.
     */
    public void addCompetitor(Competitor competitor) {
        competitors.add(competitor);
        manager.saveCompetitor(competitor); // Persist to database
    }

    /**
     * Retrieves all competitors from the database and updates the list.
     *
     * @return List of competitors.
     */
    public List<Competitor> fetchAllCompetitors() {
        competitors.clear();
        competitors.addAll(manager.getAllCompetitors());
        return competitors;
    }

    /**
     * Generates a detailed report for all competitors.
     *
     * @return Formatted report string.
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Competitor Table:\n");
        report.append(String.format("%-15s %-15s %-15s %-15s %-15s\n", "Competitor ID", "Name", "Level", "Scores", "Overall"));

        for (Competitor competitor : competitors) {
            String scoresStr = Arrays.stream(competitor.getScores())
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" "));
            double overallScore = calculateOverallScore(competitor.getScores());
            report.append(String.format("%-15d %-15s %-15s %-15s %-15.1f\n",
                    competitor.getCompetitorID(),
                    competitor.getFirstName() + " " + competitor.getLastName(),
                    competitor.getLevel(),
                    scoresStr,
                    overallScore));
        }

        // Full details for Competitor ID 200
        Competitor alice = competitors.stream()
                .filter(c -> c.getCompetitorID() == 200)
                .findFirst()
                .orElse(null);
        if (alice != null) {
            String scoresStr = Arrays.toString(alice.getScores()).replaceAll("[\\[\\]]", "");
            double overallScore = calculateOverallScore(alice.getScores());
            report.append("\nFull Details for CompetitorID 200:\n");
            report.append(String.format("CompetitorID %d, name %s %s.\n", alice.getCompetitorID(), alice.getFirstName(), alice.getLastName()));
            report.append(String.format("%s is a %s and received these scores: %s.\n", alice.getFirstName(), alice.getLevel(), scoresStr));
            report.append(String.format("This gives her an overall score of %.1f.\n", overallScore));
        }

        // Short details for Competitor ID 202
        Competitor carol = competitors.stream()
                .filter(c -> c.getCompetitorID() == 202)
                .findFirst()
                .orElse(null);
        if (carol != null) {
            double overallScore = calculateOverallScore(carol.getScores());
            report.append("\nShort Details for CompetitorID 202:\n");
            report.append(String.format("CN %d (%s%s) has an overall score of %.1f.\n",
                    carol.getCompetitorID(),
                    carol.getFirstName().charAt(0),
                    carol.getLastName().charAt(0),
                    overallScore));
        }

        // Statistical summary
        report.append("\nStatistical Summary:\n");
        report.append(String.format("• Total number of competitors: %d\n", competitors.size()));

        Competitor highestScorer = competitors.stream()
                .max(Comparator.comparingDouble(c -> calculateOverallScore(c.getScores())))
                .orElse(null);
        if (highestScorer != null) {
            double highestScore = calculateOverallScore(highestScorer.getScores());
            report.append(String.format("• Competitor with the highest score: %s %s with an overall score of %.1f\n",
                    highestScorer.getFirstName(), highestScorer.getLastName(), highestScore));
        }

        // Frequency of individual scores
        Map<Integer, Long> frequencyMap = Arrays.stream(competitors.stream()
                        .flatMapToInt(c -> Arrays.stream(c.getScores()))
                        .toArray())
                .boxed()
                .collect(Collectors.groupingBy(score -> score, Collectors.counting()));

        report.append("• Frequency of individual scores:\n");
        report.append("Score:\t\t" + frequencyMap.keySet().stream().sorted().map(String::valueOf).collect(Collectors.joining("\t")) + "\n");
        report.append("Frequency:\t" + frequencyMap.keySet().stream().sorted().map(score -> frequencyMap.get(score).toString()).collect(Collectors.joining("\t")) + "\n");

        return report.toString();
    }

    /**
     * Calculates the overall score of a competitor.
     *
     * @param scores Array of scores.
     * @return Overall score as a double.
     */
    private double calculateOverallScore(int[] scores) {
        return Arrays.stream(scores).average().orElse(0.0);
    }
}
