package crud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import entities.Produto;
import conexao.Conexao;
import entities.ProdutoVenda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class CRUD_Produto {
    Connection conn; //Objeto para realizar a conexão com o BD
    PreparedStatement pstm; //Objeto utilizado para executar o comando SQL dentro do banco de dados através da conexão estabelecida
    Scanner sc = new Scanner(System.in);
    
    public void cadastrarProduto(){
        String sql = "insert into Produto (Produto_Nome, Produto_Preco, Produto_Categoria, Produto_Descricao, Produto_Marca, Produto_Modelo, Produto_Qntd, Produto_Codigo) values (?,?,?,?,?,?,?,?)";
        //Comando SQL no qual irá inserir os dados dentro da tabela Produto - os pontos de interrogação serão utilizados com espaços em que o objeto "PrepareStatement" irá inserir

        conn = new Conexao().conectaBD(); //Objeto estabelecendo a conexão através da classe de conexão
        
        try{
            System.out.println("Digite o nome do produto: ");
            sc.next();
            Produto.Produto_Nome = sc.nextLine();
            System.out.println("Digite o preço do produto: ");
            Produto.Produto_Preco = sc.nextFloat();
            System.out.println("Digite a categoria do produto: ");
            sc.next();
            Produto.Produto_Categoria = sc.nextLine();
            System.out.println("Digite uma descrição para o produto: ");
            sc.next();
            Produto.Produto_Descricao = sc.nextLine();
            System.out.println("Digite a marca do produto: ");
            sc.next();
            Produto.Produto_Marca = sc.nextLine();
            System.out.println("Digite o modelo/Plataforma do produto: ");
            sc.next();
            Produto.Produto_Modelo = sc.nextLine();
            System.out.println("Digite a quantidade: ");
            Produto.Produto_Qntd = sc.nextInt();
            System.out.println("Digite o código de barras: ");
            Produto.Produto_Codigo = sc.nextLong();

            pstm = conn.prepareStatement(sql); //Atribuindo a conexão junto ao comando SQL de inserção
            pstm.setString(1, Produto.Produto_Nome); //inserindo o valor armazenado dentro do model de produto para o comando SQL
            pstm.setFloat(2, Produto.Produto_Preco); //inserindo o valor armazenado dentro do model de produto para o comando SQL
            pstm.setString(3, Produto.Produto_Categoria); //inserindo o valor armazenado dentro do model de produto para o comando SQL
            pstm.setString(4, Produto.Produto_Descricao); //inserindo o valor armazenado dentro do model de produto para o comando SQL
            pstm.setString(5, Produto.Produto_Marca); //inserindo o valor armazenado dentro do model de produto para o comando SQL
            pstm.setString(6, Produto.Produto_Modelo); //inserindo o valor armazenado dentro do model de produto para o comando SQL
            pstm.setInt(7, Produto.Produto_Qntd); //inserindo o valor armazenado dentro do model de produto para o comando SQL
            pstm.setLong(8, Produto.Produto_Codigo); //inserindo o valor armazenado dentro do model de produto para o comando SQL
             
            pstm.execute(); //Executa o comando SQL dentro do BD
            pstm.close(); //fecha a execução
            System.out.println("Produto cadastrado com sucesso!");
         } catch (SQLException erro){
             JOptionPane.showMessageDialog(null,"ERROR" + erro.getMessage()); //Exibe uma mensagem de erro direto do BD caso algo dê errado
         }
    }

    public void excluirProduto(){
        System.out.println("Digite o código do produto que deseja excluir: ");
        Produto.Produto_Codigo = sc.nextLong();
        String sql = "delete from Produto where Produto_Codigo = " + Produto.Produto_Codigo;

        conn = new Conexao().conectaBD();
        try{
            pstm = conn.prepareStatement(sql);
            pstm.execute();
            pstm.close();
            System.out.println("Produto excluido com sucesso!");
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }

    public void atualizarProduto(){
        String sql = "update Produto set Produto_Nome = ?, Produto_Preco = ?, Produto_Categoria = ?, Produto_Descricao = ?, Produto_Marca = ?, Produto_Modelo = ?, Produto_Qntd = ?, Produto_Codigo = ? where Produto_Codigo = ? ";

        conn = new Conexao().conectaBD();
        try{
            pstm = conn.prepareStatement(sql);
            System.out.println("Digite o codigo do produto que deseja atualizar: ");
            Produto.Produto_Codigo = sc.nextLong();
            pstm.setLong(9, Produto.Produto_Codigo);

            System.out.println("Digite o nome do produto: ");
            sc.next();
            Produto.Produto_Nome = sc.nextLine();
            System.out.println("Digite o preço do produto: ");
            Produto.Produto_Preco = sc.nextFloat();
            System.out.println("Digite a categoria do produto: ");
            sc.next();
            Produto.Produto_Categoria = sc.nextLine();
            System.out.println("Digite uma descrição para o produto: ");
            sc.next();
            Produto.Produto_Descricao = sc.nextLine();
            System.out.println("Digite a marca do produto: ");
            sc.next();
            Produto.Produto_Marca = sc.nextLine();
            System.out.println("Digite o modelo/Plataforma do produto: ");
            sc.next();
            Produto.Produto_Modelo = sc.nextLine();
            System.out.println("Digite a quantidade: ");
            Produto.Produto_Qntd = sc.nextInt();
            System.out.println("Digite o código de barras: ");
            Produto.Produto_Codigo = sc.nextLong();


            pstm.setString(1, Produto.Produto_Nome);
            pstm.setFloat(2, Produto.Produto_Preco);
            pstm.setString(3, Produto.Produto_Categoria);
            pstm.setString(4, Produto.Produto_Descricao);
            pstm.setString(5, Produto.Produto_Marca);
            pstm.setString(6, Produto.Produto_Modelo);
            pstm.setInt(7, Produto.Produto_Qntd);
            pstm.setLong(8, Produto.Produto_Codigo);

            pstm.execute();
            pstm.close();
            System.out.println("Produto atualizado com sucesso!");
        } catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }

    public void listarProduto() {

        String sql = "select * from Produto";
        conn = new Conexao().conectaBD();
        ResultSet rset = null;

        try {
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                Produto.Produto_Id = rset.getInt("Produto_Id");
                Produto.Produto_Nome = rset.getString("Produto_Nome");
                Produto.Produto_Preco = rset.getFloat("Produto_Preco");
                Produto.Produto_Categoria = rset.getString("Produto_Categoria");
                Produto.Produto_Descricao = rset.getString("Produto_Descricao");
                Produto.Produto_Marca = rset.getString("Produto_Marca");
                Produto.Produto_Modelo = rset.getString("Produto_Modelo");
                Produto.Produto_Qntd = rset.getInt("Produto_Qntd");
                Produto.Produto_Codigo = rset.getLong("Produto_Codigo");

                System.out.println("\n");
                System.out.println("Id Produto: " + Produto.Produto_Id);
                System.out.println("Nome Produto: " + Produto.Produto_Nome);
                System.out.println("Preço Produto: " + Produto.Produto_Preco);
                System.out.println("Categoria Produto: " + Produto.Produto_Categoria);
                System.out.println("Descrição Produto: " + Produto.Produto_Descricao);
                System.out.println("Marca Produto: " + Produto.Produto_Marca);
                System.out.println("Modelo/Plataforma Produto: " + Produto.Produto_Modelo);
                System.out.println("Quantidade Produtos: " + Produto.Produto_Qntd);
                System.out.println("Codigo de barras: " + Produto.Produto_Codigo);
                System.out.println("\n");
                System.out.println("---------------------------------------------------");
            }
            if(Produto.Produto_Nome == null){
                System.out.println("Nenhum registro cadastrado!");
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }

    public void listarUnicoProdutoCodigo(){
        System.out.println("Digite o codigo do produto: ");
        long codigo = sc.nextLong();
        String sql = "select * from Produto where Produto_Codigo = " + codigo;
        conn = new Conexao().conectaBD();
        ResultSet rset = null;
        int i = 0;

        try{
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                Produto.Produto_Id = rset.getInt("Produto_Id");
                Produto.Produto_Nome = rset.getString("Produto_Nome");
                Produto.Produto_Preco = rset.getFloat("Produto_Preco");
                Produto.Produto_Categoria = rset.getString("Produto_Categoria");
                Produto.Produto_Descricao = rset.getString("Produto_Descricao");
                Produto.Produto_Marca = rset.getString("Produto_Marca");
                Produto.Produto_Modelo = rset.getString("Produto_Modelo");
                Produto.Produto_Qntd = rset.getInt("Produto_Qntd");
                Produto.Produto_Codigo = rset.getLong("Produto_Codigo");

                if(Produto.Produto_Codigo == codigo) {
                    System.out.println("Nome Produto: " + Produto.Produto_Nome);
                    System.out.println("Preço Produto: " + Produto.Produto_Preco);
                    System.out.println("Categoria Produto: " + Produto.Produto_Categoria);
                    System.out.println("Descrição Produto: " + Produto.Produto_Descricao);
                    System.out.println("Marca Produto: " + Produto.Produto_Marca);
                    System.out.println("Modelo/Plataforma Produto: " + Produto.Produto_Modelo);
                    System.out.println("Quantidade Produtos: " + Produto.Produto_Qntd);
                    System.out.println("Codigo de barras: " + Produto.Produto_Codigo);
                }
            }
            if(i == 0){
                System.out.println("Nenhum registro foi encontrado!");
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }

    public void listarUnicoProdutoNome(){
        String sql = "select * from Produto";
        conn = new Conexao().conectaBD();
        ResultSet rset = null;
        String nome;
        int i = 0;

        try{
            System.out.println("Digite o nome do produto: ");
            nome = sc.next();
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                Produto.Produto_Id = rset.getInt("Produto_Id");
                Produto.Produto_Nome = rset.getString("Produto_Nome");
                Produto.Produto_Preco = rset.getFloat("Produto_Preco");
                Produto.Produto_Categoria = rset.getString("Produto_Categoria");
                Produto.Produto_Descricao = rset.getString("Produto_Descricao");
                Produto.Produto_Marca = rset.getString("Produto_Marca");
                Produto.Produto_Modelo = rset.getString("Produto_Modelo");
                Produto.Produto_Qntd = rset.getInt("Produto_Qntd");
                Produto.Produto_Codigo = rset.getLong("Produto_Codigo");

                if(Produto.Produto_Nome.contains(nome)){
                    System.out.println("Nome Produto: " + Produto.Produto_Nome);
                    System.out.println("Preço Produto: " + Produto.Produto_Preco);
                    System.out.println("Categoria Produto: " + Produto.Produto_Categoria);
                    System.out.println("Descrição Produto: " + Produto.Produto_Descricao);
                    System.out.println("Marca Produto: " + Produto.Produto_Marca);
                    System.out.println("Modelo/Plataforma Produto: " + Produto.Produto_Modelo);
                    System.out.println("Quantidade Produtos: " + Produto.Produto_Qntd);
                    System.out.println("Codigo de barras: " + Produto.Produto_Codigo);
                    System.out.println("\n");
                    System.out.println("--------------------------------------------");
                    System.out.println("\n");
                    i++;
                }
            }
            if(i == 0){
                System.out.println("Nenhum registro foi encontrado!");
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }

    public void listarUnicoProdutoCategoria(){
        String sql = "select * from Produto";
        conn = new Conexao().conectaBD();
        ResultSet rset = null;
        String categoria;
        int i = 0;

        try{
            System.out.println("Digite a categoria do produto: ");
            categoria = sc.next();
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while(rset.next()){
                Produto.Produto_Id = rset.getInt("Produto_Id");
                Produto.Produto_Nome = rset.getString("Produto_Nome");
                Produto.Produto_Preco = rset.getFloat("Produto_Preco");
                Produto.Produto_Categoria = rset.getString("Produto_Categoria");
                Produto.Produto_Descricao = rset.getString("Produto_Descricao");
                Produto.Produto_Marca = rset.getString("Produto_Marca");
                Produto.Produto_Modelo = rset.getString("Produto_Modelo");
                Produto.Produto_Qntd = rset.getInt("Produto_Qntd");
                Produto.Produto_Codigo = rset.getLong("Produto_Codigo");

                if(Produto.Produto_Categoria.contains(categoria)){
                    System.out.println("Nome Produto: " + Produto.Produto_Nome);
                    System.out.println("Preço Produto: " + Produto.Produto_Preco);
                    System.out.println("Categoria Produto: " + Produto.Produto_Categoria);
                    System.out.println("Descrição Produto: " + Produto.Produto_Descricao);
                    System.out.println("Marca Produto: " + Produto.Produto_Marca);
                    System.out.println("Modelo/Plataforma Produto: " + Produto.Produto_Modelo);
                    System.out.println("Quantidade Produtos: " + Produto.Produto_Qntd);
                    System.out.println("Codigo de barras: " + Produto.Produto_Codigo);
                    System.out.println("\n");
                    System.out.println("--------------------------------------------");
                    System.out.println("\n");
                    i++;
                }
            }
            if(i == 0){
                System.out.println("Nenhum registro foi encontrado!");
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO" + erro.getMessage());
        }
    }
}
