package service.anuncio.notificar;

import domain.entities.Usuario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificacaoLogService {
    private final String filePath;

    public NotificacaoLogService(String filePath) {
        this.filePath = filePath;
        criarDiretorioSeNecessario();
    }

    private void criarDiretorioSeNecessario() {
        File file = new File(filePath);
        File diretorio = file.getParentFile();

        if (diretorio != null && !diretorio.exists()) {
            boolean criado = diretorio.mkdirs();
            if (!criado) {
                throw new RuntimeException(
                        "Não foi possível criar o diretório de logs: " + diretorio.getPath()
                );
            }
        }
    }

    public void registrarEnvio(
            String canal,
            Usuario destinatario,
            String titulo,
            String mensagem
    ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            String logEntry = String.format("""
                --------------------------------------------------
                [%s SERVER - %s]
                PARA: %s (%s)
                TÍTULO: %s
                CONTEÚDO: %s
                --------------------------------------------------
                """,
                    canal.toUpperCase(),
                    LocalDateTime.now().format(formatter),
                    destinatario.getNome(),
                    obterContato(destinatario, canal),
                    titulo,
                    mensagem
            );

            writer.write(logEntry);
            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao registrar log de notificação (" + canal + ")" + e.getMessage(), e
            );
        }
    }

    private String obterContato(Usuario usuario, String canal) {
        return switch (canal.toLowerCase()) {
            case "email" -> usuario.getEmail();
            case "whatsapp" -> usuario.getTelefone();
            case "sms" -> usuario.getTelefone();
//            case "telegram" -> usuario.getTelegramId();
            default -> "N/A";
        };
    }
}