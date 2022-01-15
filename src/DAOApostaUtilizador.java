import Objetos.Aposta;
import Objetos.ApostaUtilizador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOApostaUtilizador {
    private static DAOApostaUtilizador singleton = null;

    /**
     * Construtor default para DAOApostaUtilizador.
     */
    private DAOApostaUtilizador() {
        Connection conn = ConfigDAO.connect();

        try {
            Statement stm = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`utilizador_Aposta` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `data` DATE NOT NULL," +
                    "  `valor` FLOAT NOT NULL," +
                    "  `rate` FLOAT NOT NULL," +
                    "  `utilizador_username` VARCHAR(45) NOT NULL," +
                    "  `aposta_id` INT NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  INDEX `fk_utilizador_Aposta_utilizador1_idx` (`utilizador_username` ASC) VISIBLE," +
                    "  INDEX `fk_utilizador_Aposta_aposta1_idx` (`aposta_id` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_utilizador_Aposta_utilizador1`" +
                    "    FOREIGN KEY (`utilizador_username`)" +
                    "    REFERENCES `BetDataBase`.`utilizador` (`username`)" +
                    "    ON DELETE NO ACTION " +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_utilizador_Aposta_aposta1`" +
                    "    FOREIGN KEY (`aposta_id`)" +
                    "    REFERENCES `BetDataBase`.`aposta` (`id`)" +
                    "    ON DELETE NO ACTION " +
                    "    ON UPDATE NO ACTION)" +
                    "ENGINE = InnoDB;";

            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    /**
     * Método que implementa o padrão Singleton.
     *
     * @return instância única desta classe.
     */
    public static DAOApostaUtilizador getInstance() {
        if (DAOApostaUtilizador.singleton == null) {
            DAOApostaUtilizador.singleton = new DAOApostaUtilizador();
        }
        return DAOApostaUtilizador.singleton;
    }

    public void fazAposta(String data, float valor, float rate, String utilizador,int apostaId){
        String query = "{CALL fazAposta(?, ?, ?, ?, ?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, data);
            stm.setFloat(2, valor);
            stm.setFloat(3, rate);
            stm.setString(4, utilizador);
            stm.setInt(5,apostaId);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public void pagaAposta(int idAposta){
        String query = "{CALL pagaAposta(?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setInt(1, idAposta);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public List<ApostaUtilizador> verApostasUtilizador(String username){
        String query = "{CALL verApostasUtilizador(?)}";
        List<ApostaUtilizador> apostas = new ArrayList<>();

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, username);

            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                apostas.add(new ApostaUtilizador(rs.getInt("idChave"),
                        rs.getString("data"),
                        rs.getFloat("valor"),
                        rs.getFloat("rate"),
                        rs.getString("tipo"),
                        rs.getString("equipa_idNome"),
                        rs.getString("descricao"),
                        rs.getBoolean("resultado")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }

        return apostas;
    }

    public void devolveAposta(int idAposta){
        String query = "{CALL devolveAposta(?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setInt(1, idAposta);

            stm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }
}
