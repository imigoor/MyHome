package service.login;

import domain.entities.Usuario;

public interface IRealizarLoginUseCase {
    Usuario execute(String email, String senha);
}
