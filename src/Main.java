
public class Main {
    public static void main(String[] args) {
        try {
            DAOApostaUtilizador n = DAOApostaUtilizador.getInstance();

            n.devolveAposta(1);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
