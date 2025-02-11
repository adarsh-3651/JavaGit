//package np.edu.heraldcollege;
//
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        CompetitorManager manager = new CompetitorManager();
//
//        // Sample competitors
//        int[] scores1 = {4, 3, 5, 2, 4};
//        int[] scores2 = {3, 4, 4, 5, 4};
//        int[] scores3 = {5, 5, 4, 4, 5};
//
//        Competitor c1 = new Competitor(200, "Alice", "Green", "Beginner", "USA", scores1);
//        Competitor c2 = new Competitor(201, "Bob", "Brown", "Intermediate", "Canada", scores2);
//        Competitor c3 = new Competitor(202, "Carol", "White", "Advanced", "UK", scores3);
//
//        // Save competitors to database
//        manager.saveCompetitor(c1);
//        manager.saveCompetitor(c2);
//        manager.saveCompetitor(c3);
//
//        // Retrieve competitors and generate a report
//        List<Competitor> competitors = manager.getAllCompetitors();
//        System.out.println("\nCompetitor Report:");
//        for (Competitor competitor : competitors) {
//            System.out.println(competitor.getFullDetails());
//        }
//
//        // Example: Find top performer
//        Competitor topPerformer = competitors.stream()
//                .max((c1_, c2_) -> Double.compare(c1_.getOverallScore(), c2_.getOverallScore()))
//                .orElse(null);
//
//        if (topPerformer != null) {
//            System.out.println("\nTop Performer:");
//            System.out.println(topPerformer.getFullDetails());
//        }
//    }
//}
//
package extraCode;


