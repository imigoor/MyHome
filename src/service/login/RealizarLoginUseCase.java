package service.login;

import domain.entities.Usuario;
import repository.usuario.UsuarioRepository;

public class RealizarLoginUseCase implements IRealizarLoginUseCase{
    private UsuarioRepository usuarioRepository;

    public RealizarLoginUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario execute(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario == null) {
            throw new SecurityException("Usuário não encontrado.");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new SecurityException("Senha incorreta.");
        }

        return usuario;
    }
}
