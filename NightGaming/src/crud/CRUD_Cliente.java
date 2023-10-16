package crud;
import java.sql.Connection;
import java.sql.PreparedStatement;

import entities.Cliente;
import conexao.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class CRUD_Cliente {
    Connection conn;
    PreparedStatement pstm;
    Scanner sc = new Scanner(System.in);

    public void cadastrarCliente(){
        String sql = "insert into Cliente (Cliente_Nome, Cliente_CPF, Cliente_Rua, Cliente_Numero, Cliente_CEP, Cliente_Telefone) values (?,?,?,?,?,?)";

        conn = new Conexao().conectaBD();

        try{
            System.out.println("Digite o nome do cliente: ");
            Cliente.Cliente_Nome = sc.nextLine();
            System.out.println("Digite o CPF do cliente: ");
            Cliente.Cliente_CPF = sc.nextLong();
            sc.nextLine();
            System.out.println("Digite a rua do cliente: ");
            Cliente.Cliente_Rua = sc.next();
            sc.nextLine();
            System.out.println("Digite o numero do cliente: ");
            Cliente.Cliente_Numero = sc.nextInt();
            sc.nextLine();
            System.out.println("Digite o CEP do cliente: ");
            Cliente.Cliente_CEP = sc.next();
            sc.nextLine();
            System.out.println("Digite o telefone do cliente: ");
            Cliente.Cliente_Telefone = sc.next();

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, Cliente.Cliente_Nome);
            pstm.setLong(2, Cliente.Cliente_CPF);
            pstm.setString(3, Cliente.Cliente_Rua);
            pstm.setInt(4, Cliente.Cliente_Numero);
            pstm.setString(5, Cliente.Cliente_CEP);
            pstm.setString(6, Cliente.Cliente_Telefone);

            pstm.execute();
            pstm.close();
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"ERROR" + erro.getMessage());
        }
    }

    public void excluirCliente(){
        String sql = "delete from Cliente where Cliente_CPF = ?";

        conn = new Conexao().conectaBD();
        try{
            System.out.println("Digite o CPF do cliente: ");
            Cliente.Cliente_CPF = sc.nextLong();
            pstm = conn.prepareStatement(sql);
            pstm.setLong(1, Cliente.Cliente_CPF);

            pstm.execute();
            pstm.close();
            System.out.println("Cliente excluído com sucesso!");
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }

    public void atualizarCliente(){
        String sql = "update Cliente set Cliente_Nome = ?, Cliente_CPF = ?, Cliente_Rua = ?, Cliente_Numero = ?, Cliente_CEP = ?, Cliente_Telefone = ? where Cliente_CPF = ? ";

        conn = new Conexao().conectaBD();
        long cpf;
        ResultSet rset = null;
        try{
            pstm = conn.prepareStatement(sql);
            System.out.println("Digite o CPF do cliente que deseja atualizar: ");
            Cliente.Cliente_CPF = sc.nextLong();
            cpf = Cliente.Cliente_CPF;

            pstm = conn.prepareStatement(sql);
            pstm.setLong(7, Cliente.Cliente_CPF);

            System.out.println("Digite o nome do cliente: ");
            Cliente.Cliente_Nome = sc.next();
            sc.nextLine();
            System.out.println("Digite o CPF do cliente: ");
            Cliente.Cliente_CPF = sc.nextLong();
            sc.nextLine();
            System.out.println("Digite a rua do cliente: ");
            Cliente.Cliente_Rua = sc.next();
            sc.nextLine();
            System.out.println("Digite o numero do cliente: ");
            Cliente.Cliente_Numero = sc.nextInt();
            sc.nextLine();
            System.out.println("Digite o CEP do cliente: ");
            Cliente.Cliente_CEP = sc.next();
            sc.nextLine();
            System.out.println("Digite o telefone do cliente: ");
            Cliente.Cliente_Telefone = sc.nextLine();


            pstm.setString(1, Cliente.Cliente_Nome);
            pstm.setLong(2, Cliente.Cliente_CPF);
            pstm.setString(3, Cliente.Cliente_Rua);
            pstm.setInt(4, Cliente.Cliente_Numero);
            pstm.setString(5, Cliente.Cliente_CEP);
            pstm.setString(6, Cliente.Cliente_Telefone);

            pstm.execute();
            pstm.close();

            System.out.println("Cliente atualizado com sucesso!");
        } catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }

    public void listarCliente() {

        String sql = "select * from Cliente";
        conn = new Conexao().conectaBD();
        ResultSet rset = null;

        try {
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                Cliente.Cliente_Id = rset.getInt("Cliente_Id");
                Cliente.Cliente_Nome = rset.getString("Cliente_Nome");
                Cliente.Cliente_CPF = rset.getLong("Cliente_CPF");
                Cliente.Cliente_Rua = rset.getString("Cliente_Rua");
                Cliente.Cliente_Numero = rset.getInt("Cliente_Numero");
                Cliente.Cliente_CEP = rset.getString("Cliente_CEP");
                Cliente.Cliente_Telefone = rset.getString("Cliente_Telefone");

                System.out.println("\n");
                System.out.println("Id Cliente: " + Cliente.Cliente_Id);
                System.out.println("Nome Cliente: " + Cliente.Cliente_Nome);
                System.out.println("CPF Cliente: " + Cliente.Cliente_CPF);
                System.out.println("Rua Cliente: " + Cliente.Cliente_Rua);
                System.out.println("Numero Cliente: " + Cliente.Cliente_Numero);
                System.out.println("CEP Cliente: " + Cliente.Cliente_CEP);
                System.out.println("Telefone Cliente: " + Cliente.Cliente_Telefone);
                System.out.println("\n");
                System.out.println("---------------------------------------------------");
            }
            if(Cliente.Cliente_Id == 0){
                System.out.println("Nenhum registro encontrado!");
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }

    public void listarUnicoCliente(){
        String sql = "select * from Cliente";
        conn = new Conexao().conectaBD();
        ResultSet rset = null;
        String nome;

        try{
            System.out.println("Digite o nome do cliente: ");
            nome = sc.next();
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();
            int i = 0;

            //pstm.setInt(1, Cliente.Cliente_Id);
            while(rset.next()){
                Cliente.Cliente_Id = rset.getInt("Cliente_Id");
                Cliente.Cliente_Nome = rset.getString("Cliente_Nome");
                Cliente.Cliente_CPF = rset.getLong("Cliente_CPF");
                Cliente.Cliente_Rua = rset.getString("Cliente_Rua");
                Cliente.Cliente_Numero = rset.getInt("Cliente_Numero");
                Cliente.Cliente_CEP = rset.getString("Cliente_CEP");
                Cliente.Cliente_Telefone = rset.getString("Cliente_Telefone");

                if(Cliente.Cliente_Nome.contains(nome)){
                    System.out.println("\n");
                    System.out.println("Nome Cliente: " + Cliente.Cliente_Nome);
                    System.out.println("CPF Cliente: " + Cliente.Cliente_CPF);
                    System.out.println("Rua Cliente: " + Cliente.Cliente_Rua);
                    System.out.println("Numero Cliente: " + Cliente.Cliente_Numero);
                    System.out.println("CEP Cliente: " + Cliente.Cliente_CEP);
                    System.out.println("Telefone Cliente" + Cliente.Cliente_Telefone);
                    System.out.println("\n");
                    i++;
                }
            }
            if(i == 0){
                System.out.println("Nenhum registro encontrado!");
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }

    public void listarUnicoClienteNome(){
        System.out.println("Digite o CPF do cliente: ");
        long CPF = sc.nextLong();
        String sql = "select * from Cliente where Cliente_CPF = " + CPF;
        conn = new Conexao().conectaBD();
        ResultSet rset = null;
        String nome;

        try{
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();
            int i = 0;

            //pstm.setInt(1, Cliente.Cliente_Id);
            while(rset.next()){
                Cliente.Cliente_Id = rset.getInt("Cliente_Id");
                Cliente.Cliente_Nome = rset.getString("Cliente_Nome");
                Cliente.Cliente_CPF = rset.getLong("Cliente_CPF");
                Cliente.Cliente_Rua = rset.getString("Cliente_Rua");
                Cliente.Cliente_Numero = rset.getInt("Cliente_Numero");
                Cliente.Cliente_CEP = rset.getString("Cliente_CEP");
                Cliente.Cliente_Telefone = rset.getString("Cliente_Telefone");

                System.out.println("\n");
                System.out.println("Nome Cliente: " + Cliente.Cliente_Nome);
                System.out.println("CPF Cliente: " + Cliente.Cliente_CPF);
                System.out.println("Rua Cliente: " + Cliente.Cliente_Rua);
                System.out.println("Numero Cliente: " + Cliente.Cliente_Numero);
                System.out.println("CEP Cliente: " + Cliente.Cliente_CEP);
                System.out.println("Telefone Cliente" + Cliente.Cliente_Telefone);
                System.out.println("\n");
                i++;

            }
            if(i == 0){
                System.out.println("Nenhum registro encontrado!");
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }
}
