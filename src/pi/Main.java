package pi;
public class Main {
    public static void main(String[] args) {
 
        MonteCarlo mc = new MonteCarlo();
 
        mc.init(20000);
        mc.setTimeout(0);
       mc.doVisual(true);
        double pi = mc.calculate();
         
        // N�herung f�r 20 Tropfen
        System.out.println(20 + "\t" + pi);
 
    }
}