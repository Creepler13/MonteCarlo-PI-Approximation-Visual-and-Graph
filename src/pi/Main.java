package pi;
public class Main {
    public static void main(String[] args) {
 
        MonteCarlo mc = new MonteCarlo();
 
        mc.init(20000);
        mc.setTimeout(1);
        mc.doVisual(true);
        double pi = mc.calculate();
         
        // Näherung für 20 Tropfen
        System.out.println(20 + "\t" + pi);
 
    }
}