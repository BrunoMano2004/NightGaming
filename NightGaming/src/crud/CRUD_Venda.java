package crud;

import conexao.Conexao;
import entities.Cliente;
import entities.Produto;
import entities.ProdutoVenda;
import entities.Venda;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CRUD_Venda {

    Connection conn;
    PreparedStatement pstm;
    Scanner sc = new Scanner(System.in);

    public void excluirVenda() {
        System.out.println("Digite o id da venda: ");
        int id = sc.nextInt();
        String sql = "delete from ProdutoVenda where Venda_Id = " + id;

        conn = new Conexao().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.execute();
            pstm.close();

            sql = "delete from Venda where Venda_Id = " + id;

            pstm = conn.prepareStatement(sql);
            pstm.execute();
            pstm.close();
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"[ERROR] " + erro.getMessage());
        }
    }

    public void inserirProdutosVenda(){
        String sql = "select * from Venda";

        conn = new Conexao().conectaBD();
        ResultSet rset = null;

        try{
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                ProdutoVenda.Venda_Id = rset.getInt("Venda_Id");
            }
            rset.close();

            sql = "select * from Produto";
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                Produto.Produto_Id = rset.getInt("Produto_Id");
                Produto.Produto_Codigo = rset.getLong("Produto_Codigo");
                Produto.Produto_Preco = rset.getLong("Produto_Preco");
                Produto.Produto_Qntd = rset.getInt("Produto_Qntd");

                if(Produto.Produto_Codigo == ProdutoVenda.Produto_Codigo){
                    if(Produto.Produto_Qntd < ProdutoVenda.Quantidade){
                        System.out.println("Estoque insuficiente!");
                        Venda.Venda_Id = ProdutoVenda.Venda_Id;
                        excluirVenda();
                        System.exit(0);
                    } else {
                        rset.close();

                        ProdutoVenda.Produto_Id = Produto.Produto_Id;
                        ProdutoVenda.Produto_Codigo = Produto.Produto_Codigo;
                        Venda.Venda_PrecoTotal = Venda.Venda_PrecoTotal + Produto.Produto_Preco;

                        sql = "insert into ProdutoVenda (Venda_Id, Produto_Id, Produto_Codigo, Quantidade) values (?,?,?,?)";

                        pstm = conn.prepareStatement(sql);
                        pstm.setInt(1, ProdutoVenda.Venda_Id);
                        pstm.setInt(2, ProdutoVenda.Produto_Id);
                        pstm.setLong(3, ProdutoVenda.Produto_Codigo);
                        pstm.setInt(4, ProdutoVenda.Quantidade);
                        pstm.execute();
                        pstm.close();

                        sql = "update Produto set Produto_Qntd = " + (Produto.Produto_Qntd - ProdutoVenda.Quantidade) + " where Produto_Id = " + Produto.Produto_Id;
                        pstm = conn.prepareStatement(sql);
                        pstm.execute();
                        pstm.close();

                        break;
                    }
                }
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"[ERROR] " + erro.getMessage());
        }
    }

    public void inserirVenda(){
        String sql = "select * from Cliente";
        int num;

        conn = new Conexao().conectaBD();
        ResultSet rset = null;

        try{
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();
            System.out.println("Digite o CPF do cliente: ");
            Venda.Cliente_CPF = sc.nextLong();

            while(rset.next()) {
                Cliente.Cliente_Id = rset.getInt("Cliente_Id");
                Cliente.Cliente_CPF = rset.getLong("Cliente_CPF");

                if (Cliente.Cliente_CPF == Venda.Cliente_CPF) {
                    rset.close();

                    sql = "insert into Venda (Cliente_Id, Cliente_CPF) values (?,?)";

                    pstm = conn.prepareStatement(sql);
                    pstm.setInt(1, Cliente.Cliente_Id);
                    pstm.setLong(2, Venda.Cliente_CPF);
                    pstm.execute();
                    pstm.close();
                    break;
                }
            }
            System.out.println("Digite quantos produtos gostaria de adicionar: ");
            num = sc.nextInt();

            for(int i = 1; i <= num; i++){
                System.out.println("Escreve o código do produto: ");
                ProdutoVenda.Produto_Codigo = sc.nextLong();
                System.out.println("Digite a quantidade: ");
                ProdutoVenda.Quantidade = sc.nextInt();
                inserirProdutosVenda();
            }
            sql = "select * from Venda";

            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                Venda.Venda_Id = rset.getInt("Venda_Id");
            }
            rset.close();

            sql = "update Venda set Venda_PrecoTotal = ? where Venda_Id = " + Venda.Venda_Id;

            pstm = conn.prepareStatement(sql);
            pstm.setFloat(1, Venda.Venda_PrecoTotal);
            pstm.execute();
            pstm.close();

            System.out.println("Venda registrada com sucesso!");
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"[ERROR] " + erro.getMessage());
        }
    }

    public void nomeProduto(int num){
        String sql = "select * from Produto where Produto_Id = " + num;
        conn = new Conexao().conectaBD();
        ResultSet rset = null;

        try{
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                Produto.Produto_Nome = rset.getString("Produto_Nome");
                Produto.Produto_Preco = rset.getFloat("Produto_Preco");
            }

        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"[ERROR] " + erro.getMessage());
        }
        System.out.println("   Nome do Produto: " + Produto.Produto_Nome);
        System.out.println("   Preço do Produto: " + Produto.Produto_Preco);
    }

    public void listarProdutoVenda(int num){
        String sql = "select * from ProdutoVenda where Venda_Id = " + num;
        conn = new Conexao().conectaBD();
        ResultSet rset = null;

        try{
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                ProdutoVenda.id_produtoVenda = rset.getInt("id_produtoVenda");
                ProdutoVenda.Venda_Id = rset.getInt("Venda_Id");
                ProdutoVenda.Produto_Id = rset.getInt("Produto_Id");
                ProdutoVenda.Produto_Codigo = rset.getLong("Produto_Codigo");
                ProdutoVenda.Quantidade = rset.getInt("Quantidade");

                System.out.println("\n");
                System.out.println("   Id do Produto: " + ProdutoVenda.Produto_Id);
                nomeProduto(ProdutoVenda.Produto_Id);
                System.out.println("   Codigo do Produto: " + ProdutoVenda.Produto_Codigo);
                System.out.println("   Quantidade: " + ProdutoVenda.Quantidade);
                System.out.println("\n");
            }

        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"[ERROR] " + erro.getMessage());
        }
    }
    public void listarVenda(){
        String sql = "select * from Venda";
        conn = new Conexao().conectaBD();
        ResultSet rset = null;

        try{
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                Venda.Venda_Id = rset.getInt("Venda_Id");
                Venda.Cliente_Id = rset.getInt("Cliente_Id");
                Venda.Cliente_CPF = rset.getLong("Cliente_CPF");
                Venda.Venda_PrecoTotal = rset.getFloat("Venda_PrecoTotal");

                System.out.println("Id da Venda: " + Venda.Venda_Id);
                System.out.println("Id do Cliente: " + Venda.Cliente_Id);
                System.out.println("CPF do Cliente: " + Venda.Cliente_CPF);
                System.out.println("Preço total da venda: " + Venda.Venda_PrecoTotal);

                listarProdutoVenda(Venda.Venda_Id);

                System.out.println("\n");
                System.out.println("------------------------------------");
                System.out.println("\n");
            }
            if(Venda.Venda_Id == 0){
                System.out.println("Nenhum registro encontrado!");
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"[ERROR] " + erro.getMessage());
        }
    }

    public void listarUnicoVenda(){
        System.out.println("Digite o CPF do Cliente: ");
        Venda.Cliente_CPF = sc.nextLong();
        String sql = "select * from Venda where Cliente_CPF = " + Venda.Cliente_CPF;

        conn = new Conexao().conectaBD();
        ResultSet rset = null;
        int i = 0;
        float total = 0;

        try{
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                Venda.Venda_Id = rset.getInt("Venda_Id");
                Venda.Cliente_Id = rset.getInt("Cliente_Id");
                Venda.Cliente_CPF = rset.getLong("Cliente_CPF");
                Venda.Venda_PrecoTotal = rset.getFloat("Venda_PrecoTotal");

                total += Venda.Venda_PrecoTotal;
                i++;

                System.out.println("Id da Venda: " + Venda.Venda_Id);
                System.out.println("Id do Cliente: " + Venda.Cliente_Id);
                System.out.println("CPF do Cliente: " + Venda.Cliente_CPF);
                System.out.println("Preço total da venda: " + Venda.Venda_PrecoTotal);

                listarProdutoVenda(Venda.Venda_Id);

                System.out.println("\n");
                System.out.println("------------------------------------");
                System.out.println("\n");
            }
            if(Venda.Venda_Id == 0){
                System.out.println("Nenhum registro encontrado!");
            }
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("|  O cliente realizou " + i + " compras no total, gastando R$" + total + "  |");
            System.out.println("-----------------------------------------------------------------------------");
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"[ERROR] " + erro.getMessage());
        }
    }

    public void numeroDeVendas(){
        String sql = "select * from Produto";
        conn = new Conexao().conectaBD();
        ResultSet rset = null;
        int i = 0;
        int a = 0;

        try{
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                Produto.Produto_Codigo = rset.getLong("Produto_Codigo");
                i++;
            }
            rset.close();

            long produtos[] = new long[i];

            rset = pstm.executeQuery();
            while(rset.next()){
                produtos[a] = rset.getLong("Produto_Codigo");
                a++;
            }

            for (int b = 0; b < produtos.length; b++){
                sql = "select * from ProdutoVenda where Produto_Codigo = " + produtos[b];
                pstm = conn.prepareStatement(sql);
                rset = pstm.executeQuery();

                while(rset.next()){
                    ProdutoVenda.Quantidade += rset.getInt("Quantidade");
                }
                System.out.println("O produto de código " + produtos[b] + " possui um total de " + ProdutoVenda.Quantidade + " vendas");
                ProdutoVenda.Quantidade = 0;
            }
            
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"[ERROR] " + erro.getMessage());
        }
    }
}
