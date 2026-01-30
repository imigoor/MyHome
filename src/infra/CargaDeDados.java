package infra;

import domain.anuncio.Anuncio;
import domain.entities.Corretor;
import domain.entities.Interessado;
import domain.entities.Proprietario;
import domain.entities.Usuario;
import domain.enums.StatusAnuncio;
import domain.enums.TipoAnuncio;
import domain.imovel.*;
import repository.anuncio.AnuncioRepository;
import repository.usuario.UsuarioRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class CargaDeDados {
    private final UsuarioRepository usuarioRepository;
    private final AnuncioRepository anuncioRepository;

    public CargaDeDados(UsuarioRepository usuarioRepository, AnuncioRepository anuncioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.anuncioRepository = anuncioRepository;
    }

    public void carregarTudo() {
        System.out.println("Iniciando carga de dados (E1)...");
        carregarUsuarios("usuarios.csv");
        carregarAnuncios("anuncios.csv");
    }

    private void carregarUsuarios(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine(); // pula cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                String tipo = dados[0].trim();
                String nome = dados[1].trim();
                String email = dados[2].trim();
                String senha = dados[3].trim();
                String cpf = dados[4].trim();
                String registro = dados.length > 5 ? dados[5].trim() : "";

                Usuario novoUsuario = null;

                // cria o objeto baseado no TIPO
                if (tipo.equalsIgnoreCase("CORRETOR")) {
                    novoUsuario = new Corretor(nome, email, senha, "0000-0000", null, registro, 0.05, cpf);
                } else if (tipo.equalsIgnoreCase("PROPRIETARIO")) {
                    novoUsuario = new Proprietario(nome, email, senha, "0000-0000", null, cpf);
                } else if (tipo.equalsIgnoreCase("INTERESSADO")) {
                    novoUsuario = new Interessado(nome, email, senha, "0000-0000", null, cpf);
                }

                if (novoUsuario != null) {
                    usuarioRepository.salvar(novoUsuario);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler usuarios.csv: " + e.getMessage());
        }
    }

    private void carregarAnuncios(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine(); // pula cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");

                // mapeamento das colunas
                String titulo = d[0];
                BigDecimal valor = new BigDecimal(d[1]);
                TipoAnuncio tipoAnuncio = TipoAnuncio.valueOf(d[2].toUpperCase());
                String tipoImovel = d[3].toUpperCase();
                String emailDono = d[4].trim();
                String descricao = d[5];

                // cria endereço
                Endereco endereco = new Endereco(d[6], d[7], d[8], d[9]);

                // busca o dono no repositório
                Usuario dono = usuarioRepository.buscarPorEmail(emailDono);
                if (dono == null) {
                    continue;
                }

                Imovel imovel = criarImovelDoCSV(tipoImovel, endereco, descricao);

                Anuncio anuncio = new Anuncio(titulo, valor, tipoAnuncio, imovel, dono);

                // Força o status para ATIVO para aparecer nas buscas de teste
                anuncio.setStatus(StatusAnuncio.RASCUNHO);

                anuncioRepository.salvar(anuncio);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler anuncios.csv: " + e.getMessage());
        }
    }

    private Imovel criarImovelDoCSV(String tipo, Endereco end, String desc) {
        switch (tipo) {
            case "CASA":
                return new Casa(end, 120.0, 3, 2, desc, true, true, 2);
            case "APARTAMENTO":
                return new Apartamento(end, 70.0, 2, 1, desc, 5, true, true, 450.0);
            case "TERRENO":
                return new Terreno(end, 300.0, 0, 0, desc, true, "Plano");
            default:
                throw new IllegalArgumentException("Tipo desconhecido no CSV: " + tipo);
        }
    }
}
