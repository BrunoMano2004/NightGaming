package conexao;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    public Connection conectaBD(){
         Connection conn = null;
         
         try{
             String url = "jdbc:mysql://localhost:3306/NightGaming?user=root&password=bruno2004";
             conn = DriverManager.getConnection(url);
         } catch (SQLException erro){
             JOptionPane.showMessageDialog(null,"ERRO" + erro.getMessage());
         }
         return conn;
    }
    
}
