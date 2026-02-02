package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import domain.imovel.Endereco;

public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    // .............MÉTODOS DE SAÍDA (O que aparece na tela).............
    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void mostrarErro(String erro) {
        System.err.println("ERRO: " + erro);
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    public void limparTela() {
        mostrarMensagem("\n".repeat(50));
    }

    // .............MÉTODOS DE ENTRADA (Leitura do teclado).............
    public String lerTexto(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public String lerTextoObrigatorio(String prompt) {
        while (true) {
            System.out.print(prompt + " (Obrigatório): ");
            String input = scanner.nextLine();

            if (!input.trim().isEmpty()) {
                return input;
            }

            mostrarErro("Este campo não pode ficar vazio!");
        }
    }

    public Double lerDecimal(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                String input = scanner.nextLine();
                // troca vírgula por ponto para evitar erro se o pc tiver em pt-br
                return Double.parseDouble(input.replace(",", "."));
            } catch (NumberFormatException e) {
                mostrarMensagem("Valor inválido! Digite um número (ex: 1500.00).");
            }
        }
    }

    public Integer lerInteiro(String prompt) {
        while (true) {
            try {
                mostrarMensagem(prompt + ": ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                mostrarMensagem("Valor inválido! Digite apenas números inteiros.");
            }
        }
    }

    public Boolean lerBooleano(String prompt) {
        System.out.print(prompt + " (S/N): ");
        String entrada = scanner.nextLine();
        return entrada.trim().equalsIgnoreCase("S");
    }

//    public <T extends Enum<T>> T lerEnum(String mensagem, Class<T> classeDoEnum) {
//        while (true) {
//            System.out.print(mensagem + ": ");
//            String input = scanner.nextLine().toUpperCase().trim();
//
//            if (input.isEmpty()) {
//                mostrarErro("Campo obrigatório.");
//                continue;
//            }
//
//            try {
//                return Enum.valueOf(classeDoEnum, input);
//            } catch (IllegalArgumentException e) {
//                System.err.println("Opção inválida! Escolha entre:");
//                for (T opcao : classeDoEnum.getEnumConstants()) {
//                    System.err.print("[" + opcao + "] ");
//                }
//                System.err.println();
//            }
//        }
//    }
//
//    public Anuncio lerAnuncio(){
//        System.out.println("--- Anuncio do Imóvel ---");
//        String titulo = lerTextoObrigatorio("Título do Anúncio");
//        Double valorD = lerDecimal("Valor (R$)");
//        String tipoStr = lerTextoObrigatorio("Tipo (VENDA/ALUGUEL)").toUpperCase();
//
//        return new Anuncio();
//    }

    public Endereco lerEndereco() {
        mostrarMensagem("--- Endereço do Imóvel ---");
        String rua = lerTextoObrigatorio("Rua");
        String bairro = lerTextoObrigatorio("Bairro");
        String cidade = lerTextoObrigatorio("Cidade");
        String estado = lerTextoObrigatorio("Estado");

        return new Endereco(rua, bairro, cidade, estado);
    }

    // .............Guias pro RF01.............
    public Map<String, Object> coletarDadosImovel(String tipoImovel) {
        Map<String, Object> dados = new HashMap<>();

        mostrarMensagem("--- Dados Específicos para " + tipoImovel + " ---");
        // pra guiar as perguntas
        switch (tipoImovel.toUpperCase()) {
            case "CASA":
                dados.put("quartos", lerInteiro("Quartos"));
                dados.put("banheiros", lerInteiro("Banheiros"));
                dados.put("quintal", lerBooleano("Tem Quintal?"));
                dados.put("piscina", lerBooleano("Tem Piscina?"));
                dados.put("vagas", lerInteiro("Vagas Garagem"));
                break;

            case "APARTAMENTO":
                dados.put("quartos", lerInteiro("Quartos"));
                dados.put("banheiros", lerInteiro("Banheiros"));
                dados.put("andar", lerInteiro("Andar"));
                dados.put("elevador", lerBooleano("Tem Elevador?"));
                dados.put("portaria", lerBooleano("Portaria 24h?"));
                dados.put("condominio", lerDecimal("Valor Condomínio"));
                break;

            case "TERRENO":
                dados.put("murado", lerBooleano("É Murado?"));
                dados.put("topografia", lerTexto("Topografia"));
                break;

            default:
                throw new IllegalArgumentException("Tipo de imóvel '" + tipoImovel.toUpperCase() + "' não é suportado pelo sistema.");
        }

        mostrarMensagem("--- Dados Gerais do Imóvel ---");
        dados.put("descricao", lerTexto("Descrição"));
        dados.put("area", lerDecimal("Área (m²)"));

        dados.put("endereco", lerEndereco());

        return dados;
    }
}
