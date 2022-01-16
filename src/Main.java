import Data.DAOEquipa;
import Data.DAOJogo;

public class Main {
    public static void main(String[] args) {
        try {
            DAOJogo n = DAOJogo.getInstance();

            System.out.println(n.getEquipa("SL Benfica"));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
