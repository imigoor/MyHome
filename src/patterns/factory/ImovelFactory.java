package patterns.factory;

import domain.imovel.Imovel;

import java.util.Map;

public abstract class ImovelFactory {
    public abstract Imovel criarImovel(Map<String, Object> dados);
}
