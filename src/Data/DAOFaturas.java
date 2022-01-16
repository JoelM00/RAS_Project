package Data;

import Objects.Fatura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOFaturas {
    private static DAOFaturas singleton = null;

    private DAOFaturas() {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`fatura` ("+
                "`idfatura` VARCHAR(45) NOT NULL,"+
                "`valor` FLOAT NOT NULL,"+
                "`tipoDepOuRec` CHAR(1) NOT NULL,"+
                "`data` DATE NOT NULL,"+
                "`estado` VARCHAR(45) NOT NULL,"+
                "`dataValidade` DATE NOT NULL,"+
                "`utilizador_username` VARCHAR(45) NOT NULL,"+
                "`moeda_nome` VARCHAR(45) NOT NULL,"+
                "PRIMARY KEY (`idfatura`),"+
                "INDEX `fk_fatura_utilizador1_idx` (`utilizador_username` ASC) VISIBLE,"+
                "INDEX `fk_fatura_moeda1_idx` (`moeda_nome` ASC) VISIBLE,"+
                "CONSTRAINT `fk_fatura_utilizador1`"+
                "FOREIGN KEY (`utilizador_username`)"+
                "REFERENCES `BetDataBase`.`utilizador` (`username`)"+
                "ON DELETE NO ACTION "+
                "ON UPDATE NO ACTION,"+
                "CONSTRAINT `fk_fatura_moeda1`"+
                "FOREIGN KEY (`moeda_nome`)"+
                "REFERENCES `BetDataBase`.`moeda` (`nome`)"+
                "ON DELETE NO ACTION "+
                "ON UPDATE NO ACTION)"+
                "ENGINE = InnoDB;";

            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`moeda` ("+
                "`nome` VARCHAR(45) NOT NULL,"+
                "`rateToBet` FLOAT NOT NULL,"+
                "`rateFromBet` FLOAT NOT NULL,"+
                "PRIMARY KEY (`nome`))"+
                "ENGINE = InnoDB;";

            stm.executeUpdate(sql);

        }  catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
    }

    public static DAOFaturas getInstance() {
        if (DAOFaturas.singleton == null) {
            DAOFaturas.singleton = new DAOFaturas();
        }
        return DAOFaturas.singleton;
    }


    public void	registaFatura(String idFatura,float valor,String tipo,String data,String estado,String dataV,String utilizador,String moeda) {
        String query = "{CALL registaFatura(?,?,?,?,?,?,?,?);}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, idFatura);
            stm.setFloat(2, valor);
            stm.setString(3, tipo);
            stm.setString(4, data);
            stm.setString(5, estado);
            stm.setString(6, dataV);
            stm.setString(7, utilizador);
            stm.setString(8, moeda);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public void confirmaFatura(String idFatura) {
        String query = "{CALL confirmaFatura(?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, idFatura);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public List<Fatura> verFaturas(String idUti) {
        String query = "{CALL verFaturas(?)}";
        List<Fatura> fat = new ArrayList<>();

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, idUti);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                fat.add(new Fatura(rs.getString("idFatura"),
                        rs.getFloat("valor"),
                        rs.getString("tipoDepOuRec"),
                        rs.getString("data"),
                        rs.getString("estado"),
                        rs.getString("dataValidade"),
                        rs.getString("utilizador_username"),
                        rs.getString("moeda_nome")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return fat;
    }


}