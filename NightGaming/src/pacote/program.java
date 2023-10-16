package pacote;
import crud.CRUD_Produto;
import crud.CRUD_Cliente;
import crud.CRUD_Venda;
import entities.Cliente;
import entities.Produto;
import entities.ProdutoVenda;
import entities.Venda;
import java.util.Scanner;

public class program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        CRUD_Produto crudProduto = new CRUD_Produto();
        CRUD_Cliente crudCliente = new CRUD_Cliente();
        CRUD_Venda crudVenda = new CRUD_Venda();

        int i = 0;

        while(i < 1){
            System.out.println("Qual das tabelas você gostaria de administrar? Produto / Cliente / Venda");
            String resp = sc.next();
            switch(resp){
                case "Produto":
                    System.out.println("Você gostaria de realizar qual ação? Excluir / Cadastrar / Atualizar / Listar / UnicoCodigo / UnicoNome / UnicoCategoria");
                    resp = sc.next();
                    switch(resp){
                        case "Excluir":
                            crudProduto.excluirProduto();
                            break;

                        case "Cadastrar":
                            crudProduto.cadastrarProduto();
                            break;

                        case "Atualizar":
                            crudProduto.atualizarProduto();
                            break;

                        case "Listar":
                            crudProduto.listarProduto();
                            break;

                        case "UnicoCodigo":
                            crudProduto.listarUnicoProdutoCodigo();
                            break;

                        case "UnicoNome":
                            crudProduto.listarUnicoProdutoNome();
                            break;

                        case "UnicoCategoria":
                            crudProduto.listarUnicoProdutoCategoria();
                            break;

                        default:
                            System.out.println("Digite uma alternativa correta!");
                    }
                    break;

                case "Cliente":
                    System.out.println("Você gostaria de realizar qual ação? Excluir / Cadastrar / Atualizar / Listar / UnicoNome / UnicoCPF");
                    resp = sc.next();
                    switch(resp){
                        case "Excluir":
                            crudCliente.excluirCliente();
                            break;

                        case "Cadastrar":
                            crudCliente.cadastrarCliente();
                            break;

                        case "Atualizar":
                            crudCliente.atualizarCliente();
                            break;

                        case "Listar":
                            crudCliente.listarCliente();
                            break;

                        case "UnicoNome":
                            crudCliente.listarUnicoCliente();
                            break;

                        case "UnicoCPF":
                            crudCliente.listarUnicoClienteNome();
                            break;

                        default:
                            System.out.println("Digite uma alternativa correta!");
                    }
                    break;

                case "Venda":
                    System.out.println("Você gostaria de realizar qual ação? Excluir / Cadastrar / Listar / Unico / Maiores");
                    resp = sc.next();
                    switch(resp){
                        case "Excluir":
                            crudVenda.excluirVenda();
                            break;

                        case "Cadastrar":
                            crudVenda.inserirVenda();
                            break;

                        case "Unico":
                            crudVenda.listarUnicoVenda();
                            break;

                        case "Listar":
                            crudVenda.listarVenda();
                            break;

                        case "Maiores":
                            crudVenda.numeroDeVendas();
                            break;

                        default:
                            System.out.println("Digite uma alternativa correta!");
                    }
                    break;

                default:
                    System.out.println("Digite uma alterntiva correta!");
            }
            System.out.println("Gostaria de executar novamente?");
            resp = sc.next();
            if (resp.contains("n")){
                i++;
            }
        }
    }
}

