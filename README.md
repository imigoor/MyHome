
<h1 align="center">Projeto MyHome</h1>

## Descrição

Sejam bem-vindos ao repositório do projeto **MyHome**, desenvolvido para a disciplina de `Padrões de Projeto de Software`, do curso de Sistemas para Internet no Instituto Federal de Educação, Ciência e Tecnologia da Paraíba (IFPB), ministrada pelo professor **Alex Cunha**.

A aplicação consiste em um sistema de gerenciamento de anúncios imobiliários via console (CLI), desenvolvido em Java. O sistema permite que proprietários e corretores criem, gerenciem e publiquem anúncios de imóveis (Casas, Apartamentos e Terrenos), enquanto interessados podem buscar propriedades utilizando filtros avançados.

O objetivo principal deste projeto não é apenas a funcionalidade de negócio, mas a aplicação prática de Padrões de Projeto (Design Patterns) do GoF para resolver problemas recorrentes de arquitetura, garantindo um código flexível, extensível e de fácil manutenção.

## Funcionalidades

- __Gestão de Usuários:__ Login diferenciado para Corretores, Proprietários e Interessados.
- __Criação de Anúncios:__ Cadastro detalhado de imóveis para Venda ou Aluguel.
- __Criação Rápida (Protótipos):__ Uso de modelos pré-definidos para agilizar o cadastro.
- __Busca Avançada:__ Filtragem dinâmica de anúncios por cidade, tipo de imóvel e preço máximo.
- __Moderação de Anúncios:__ Sistema automático de validação de regras antes da publicação.
- __Gestão de Estados:__ Ciclo de vida completo do anúncio (Rascunho -> Moderação -> Ativo -> Vendido/Suspenso).
- __Notificações:__ Alertas automáticos para o dono do anúncio quando o status é alterado.

## Padrões de Projeto Utilizados

A arquitetura do sistema foi desenhada para demonstrar o uso robusto de diversos padrões de projeto. Abaixo, detalhamos onde e por que cada um foi aplicado:

### Padrões de Criação
- __Factory Method:__ Utilizado na criação dos diferentes tipos de imóveis (`Casa`, `Apartamento`, `Terreno`). Cada tipo possui sua própria fábrica (`CasaFactory`, etc.), permitindo a inclusão de novos tipos de imóveis sem alterar o código cliente de criação.
- __Prototype:__ Implementado para permitir a criação rápida de anúncios baseados em "templates" (`AnuncioPrototypeRegistry`). O usuário pode clonar um imóvel padrão (ex: "Apartamento Padrão") e apenas ajustar o endereço, economizando tempo de preenchimento.
- __Singleton:__ Aplicado na classe `Configuracao` para gerenciar as configurações globais do sistema. Ele garante que o arquivo externo `config.properties` seja lido apenas uma vez, fornecendo acesso centralizado a parâmetros vitais (como precos, limites de caracteres e lista de termos proibidos), eliminando "números mágicos" e garantindo consistência.

### Padrões Estruturais
- __Decorator:__ Utilizado no sistema de busca. Os filtros (`FiltroCidade`, `FiltroPrecoMaximo`, `FiltroTipoImovel`) envolvem a busca base dinamicamente. Isso permite combinar filtros de qualquer maneira (ex: apenas cidade, ou cidade + preço) sem criar métodos rígidos de busca com múltiplos parâmetros.

### Padrões Comportamentais
- __Observer:__ Implementado para monitorar mudanças no estado dos anúncios. Quando um anúncio muda de status (ex: de "Moderação" para "Ativo"), o `NotificacaoAnuncioObserver` é disparado automaticamente para notificar o proprietário.
- __State:__ Gerencia o ciclo de vida do anúncio. Cada estado (`Rascunho`, `Moderacao`, `Ativo`, `Vendido`, `Suspenso`) é uma classe que define quais transições são permitidas, eliminando condicionais complexos (`if/else`) e garantindo a integridade do fluxo.
- __Chain of Responsibility:__ Aplicado no processo de submissão de anúncios. Uma cadeia de validadores (`ValidadorPreco` -> `ValidadorTexto` -> `ValidadorTamanho`) processa o anúncio sequencialmente. Se uma validação falhar, o processo para e o erro é retornado, desacoplando as regras de validação.
- __Strategy:__ Define como as notificações são enviadas. O sistema define uma interface comum e implementações concretas (como `EmailNotificacaoStrategy`), permitindo trocar o canal de notificação (Email, SMS, WhatsApp) dinamicamente conforme a preferência do usuário.
- __Template Method:__ Aplicado no processo de venda de imóveis (`ProcessoVenda`). Uma classe abstrata define o esqueleto do algoritmo de venda (validação -> cálculo -> pagamento -> finalização), delegando para as subclasses (`VendaAvista`, `VendaFinanciada`) a implementação dos passos específicos de cálculo (descontos ou juros), garantindo que o fluxo transacional seja respeitado.

## Estrutura de Arquivos

A organização do projeto segue uma arquitetura em camadas bem definida:

| Pacote | Descrição |
| --- | --- |
| `domain.anuncio` | Entidades principais relacionadas ao Anúncio e seu ciclo de vida. |
| `domain.imovel` | Modelos de domínio dos imóveis (Casa, Apartamento, Terreno, Endereço). |
| `domain.interfaces` | Contratos e Interfaces que definem os Padrões de Projeto (Observer, State, Strategy, etc.). |
| `patterns.*` | Implementações concretas dos Padrões de Projeto (ex: `patterns.factory`, `patterns.decorator`). |
| `service.*` | Casos de Uso (Regras de Negócio) da aplicação, como `CriarAnuncioUseCase` e `BuscarAnunciosUseCase`. |
| `repository` | Camada de persistência simulada em memória e leitura/escrita em arquivos `.csv`. |
| `view` | Interface de usuário baseada em console (`ConsoleUI`). |

## Instruções de Execução

O projeto foi desenvolvido em Java 17. Para executá-lo localmente:

1. Clone o repositório:
    ```bash
    git clone https://github.com/imigoor/MyHome.git
    ```

2. Abra o projeto em sua IDE de preferência (IntelliJ IDEA, Eclipse ou VS Code).

3. **Requisito Obrigatório:** Certifique-se de que os seguintes arquivos estejam na **raiz** do diretório do projeto (fora da pasta `src`):
   - `usuarios.csv` (Base de dados de usuários)
   - `anuncios.csv` (Base de dados de anúncios)
   - `config.properties` (Arquivo de configuração do Singleton)

4. Execute a classe principal:
    `src/Main.java`

## Autores

Este projeto foi desenvolvido por:

- __Gustavo Gabriel__ 
	- [GitHub](https://github.com/uGustavoB)
	- [Linkedin](https://www.linkedin.com/in/gustavobatistaa/)
- __Igor Miranda__
	- [GitHub](https://github.com/imigoor)
	- [Linkedin](https://www.linkedin.com/in/imigor/)
- __Flávio Henrique__
	- [GitHub](https://github.com/flaviorique10)
	- [Linkedin](https://www.linkedin.com/in/flaviorique10/)

---
<p align="center">IFPB - Sistemas para Internet - 2025</p>
