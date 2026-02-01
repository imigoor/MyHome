package patterns.singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Classe responsável por gerenciar as configurações globais do sistema.
// Implementa o padrão de projeto **SINGLETON** (RF07) para garantir que O arquivo de configuração seja lido apenas uma vez.
public class Configuracao {
    // Guarda a única instância da classe que existirá durante toda a execuçã
    private static Configuracao instance;
    private Properties properties;

    // Impede que outras classes façam "new Configuracao()".
    private Configuracao() {
        properties = new Properties();
        try {            
            FileInputStream file = new FileInputStream("config.properties");
            properties.load(file);
            file.close();
        } catch (IOException e) {
            System.err.println("ERRO ao carregar config: " + e.getMessage());
        }
    }

    // Método público para obter a instância única da classe
    public static Configuracao getInstance() {
        if (instance == null) {
            instance = new Configuracao();
        }
        return instance;
    }

    // Método para obter o valor de uma propriedade pelo nome (chave)
    public String getPropriedade(String chave) {
        return properties.getProperty(chave);
    }
}
