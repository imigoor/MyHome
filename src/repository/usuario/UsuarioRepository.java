package repository.usuario;

import domain.entities.Usuario;
import repository.BancoDeDados;

import java.util.List;

public class UsuarioRepository {
    private BancoDeDados db = BancoDeDados.getInstance();

    public void salvar(Usuario usuario){
        db.getTabelaUsuarios().add(usuario);
    }

    public void deletar(Usuario usuario){
        db.getTabelaUsuarios().remove(usuario);
    }

    public List<Usuario> listarTodos(){
        return db.getTabelaUsuarios();
    }

    public Usuario buscarPorEmail(String email) {
        List<Usuario> usuarios = listarTodos();

        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }

        return null;
    }
}
