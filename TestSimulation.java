// HOBEIKA Youssef

// Contient le main qui lance plusieurs simulations avec des configurations différentes pour produire des logs
public class TestSimulation {
    public static void main(String[] args) {
        System.out.println("############ SIMULATION 1 : petite mer, peu d'agents ############");
        // Petite mer : 8x10, 3 coffres, 2 îles, 2 courants, 2 pirates, 1 garde-côte
        Simulation sim1 = Simulation.getInstance(8, 10, 3, 2, 2, 2, 1);
        sim1.lancer(15);

        // On reset le Singleton pour relancer une autre simulation
        Simulation.reset();

        System.out.println("\n\n############ SIMULATION 2 : mer moyenne, plus de pirates ############");
        // Mer moyenne : 10x12, 6 coffres, 3 îles, 3 courants, 4 pirates, 2 garde-côtes
        Simulation sim2 = Simulation.getInstance(10, 12, 6, 3, 3, 4, 2);
        sim2.lancer(20);

        Simulation.reset();

        System.out.println("\n\n############ SIMULATION 3 : grande mer, beaucoup de tout ############");
        // Grande mer : 12x15, 10 coffres, 5 îles, 4 courants, 6 pirates, 3 garde-côtes
        Simulation sim3 = Simulation.getInstance(12, 15, 10, 5, 4, 6, 3);
        sim3.lancer(25);
    }
}
