# TP_PUCFlix

O projeto PUCFlix é um cadastro de séries e episódios, com uma interface textual para mostrar os registros. O nosso projeto é uma implementação simples de um Banco de Dados sem utilizar um SGBD, contendo todas as operações de CRUD (Create, Read, Update e Delete) para as duas entidades, garantindo seus relacionamentos.

# Classes do sistema

Uma breve descrição de cada classe do Sistema, organizado semelhante à pastas de arquivos do projeto. Cada Classe com a descrição de todas as funções e atributos

### Principal

Classe principal do projeto, possuindo apenas duas funções: main e povoar. A função povoar realiza o povoar() de cada visão. Já a função main() cria a primeira interface com, atualmente, 4 opções, as duas primeiras opções leva para a classe de visão respectiva da classe escolhida, a terceira opção para chamar o povoar() e a última para encerrar o programa. Por fim, o único atributo dessa classe é a String versão, que tem o formato "x.x", onde x é um dígito

## Entidades

### Serie

Classe para criar a Entidade Serie. Implementa a interface EntidadeArquivo

#### Atributos

+ ID (int)
+ nome (String)
+ anoLancamento (int -> 4 dígitos)
+ sinopse (String)
+ streaming (String)
+ nota (int -> 0:10)
+ criadores (ArrayList de String)
+ genero (String)

#### Funções

+ Set e get de todos os atributos
+ 3 Construtores = Um com parâmetros vazio, Um parâmetros para cada atributos e outro com parâmetros para cada atributo sem o id (colocando o valor como -1), no final, todas vão chamar o construtor com todos os atributos. Validar todas as informações no construtor completo, porém permitindo que construa com os valores “vazios”.
+ toString( ): Formato tabelado, com uma linha para cada atributo, com o nome “completo” e gramaticalmente correto. Para a formação da tabela, preencher com pontos (‘.’) até o atributo com maior nome. Começar cada linha com ‘| ’ e terminar com ‘ |’, preenchendo com espaços em branco para manter as | alinhadas. Além disso, no começo e no final da função, colocar a sequência de (“+---+”) englobando todos as informações. Ex.:
```
+--------------------------+
| ID...............: 1     |
| Nome.............: XXXXX |
| Temporada........: 2     |
| Ano de lançamento: 2020  |
|            ...           |
+--------------------------+
```
+ byte [ ] toByteArray( ): Função para retornar um arranjo de bytes dado uma instância da classe, registrar os atributos na mesma ordem acima.
+ fromByteArray(byte[ ]): Função para preencher um objeto com dados vindo de um arranjo de bytes.

### Episodio

Classe para criar a Entidade Episódio. Implementa a interface EntidadeArquivo

#### Atributos

+ ID(int)
+ IDSerie (int)
+ nome (String)
+ temporada (int)
+ dataLancamento (Date)
+ duracao (double) (em minutos)
+ nota (int -> 0:10)
+ diretores (ArrayList de String)

#### Funções
+ Set e get dos atributos
+ 3 Construtores = Um com parâmetros vazio, Um parâmetros para cada atributos e outro com parâmetros para cada atributo sem o id (colocando o valor como -1). no final, todas vão chamar o construtor com todos os atributos. Validar todas as informações no construtor completo, porém permitindo que construa com os valores “vazios”. Para validar a IDSerie chamar a função ControleSerie.validarSerie( ) 
+ toString( ): Formato tabelado, com uma linha para cada atributo, com o nome “completo” e gramaticalmente correto. Para a formação da tabela, preencher com pontos (‘.’) até o atributo com maior nome. Começar cada linha com ‘| ’ e terminar com ‘ |’, preenchendo com espaços em branco para manter as | alinhadas. Além disso, no começo e no final da função, colocar a sequência de (“+---+”) englobando todos as informações. Ex.:
```
+---------------------------------+
| ID................: 1           |
| Nome..............: XXXXX       |
| Temporada.........: 2           |
| Data de lançamento: 11/11/2021  |
|            ...                  |
+---------------------------------+
```
+ byte [ ] toByteArray( ): Função para retornar um arranjo de bytes dado uma instância da classe, registrar os atributos na mesma ordem acima.
+ fromByteArray(byte[ ]): Função para preencher um objeto com dados vindo de um arranjo de bytes.

## Controle

Classes que realiza a mediação entre as operações dos arquivos e a entrada de dados da Visão

### ControleSerie

#### Atributos

+ ArquivoSerie arqSerie

#### Funções

+ incluirSerie(Serie serie): Função que ao ser chamada, vai chamar o VisaoSerie.lerSerie( ) e inserir no bd. Caso venha uma exceção de lerSerie( ), chamar novamente a função
+ lerSerie(  ), caso falhe de novo, encerre a função relatando mensagem de erro, caso contrário insira no bd. Além disso, tem o caso do lerSerie ser cancelado ao final, assim o incluirSerie também deve ser cancelado
+ excluirSerie(int id): Função para excluir serie por ID. Testar antes de deletar se tem algum episodio dessa serie usando ControleEpisodio.verificarEpisodiosSerie( )
+ excluirSerieNome(Strign nome): Função para excluir serie pelo nome. Usar buscarSerieNome( ) para coletar a serie desejada de exclusão e depois continuar o processo com a excluirSerie( ). 
+ alterarSerie (int id): Função para alterar algum valor da Serie. Usar ID para identificar a Serie a ser excluida, depois a função lerSerie( ) para criar a nova versão do objeto
+ buscarSerie (int id): Função que recebe um ID e retorna um Objeto Serie
+ buscarSerieNome(String entrada): Função que retorna um ou mais episódio que contém a sequência inserida.Ex. : Entrada: “Era” -> retorna [Serie Era uma vez, Serie Era do Gelo]
+ buscarSerieEpisodios (Serie serie): Função que mostra todos os Episódios da Série
+ buscarSerieEpisodiosPorTemporada(int temporada): Função que mostra os episódios de uma certa temporada de uma série
+ validarSerie(int id): Função estática que recebe um id de Série como parâmetro e retorna True ou False de acordo com sua existência válida no banco de dados. Para a leitura do objeto, instanciar um novo ArquivoSerie e usar o seu read com o id.
+ povoar( ): Primeiro carregamento de dados para o sistema.

### ControleEpisodio

#### Atributos

+ ArquivoEpisodio arqEpisodio
+ int idSerie

#### Funções

+ entrarSerie( ): Função que deve ser chamada antes de todos as funções não estáticas dessa classe para garantir que está mexendo dentro de uma série.atualizar valor de idSerie. Dentro de todas as funções, testar se já está dentro da série com o atributo idSerie
+ incluirEpisodio( ): Função que ao ser chamada, vai chamar o VisaoEpisodio.lerEpisodio( ) e inserir no bd. Caso venha uma exceção de lerEpisodio( ), chamar novamente a função lerEpisodio(  ), caso falhe de novo, encerre a função relatando mensagem de erro, caso contrário insira no bd. Além disso, tem o caso do lerEpisodio ser cancelado ao final, assim o incluirEpisodio também deve ser cancelado
+ excluirEpisodio( ): Função para excluir Episodio por ID. Testar antes de deletar se tem algum episodio dessa serie usando …
+ excluirEpisodioNome( ): Função para excluir Episodio pelo nome. Usar buscarEpisodio( ) para coletar a Episodio desejada de exclusão e depois continuar o processo com a excluirEpisodio( ). 
+ alterarEpisodio ( ): Função para alterar algum valor da Episodio. Usar ID para identificar a Episodio a ser excluida, depois a função visaoEpisodio.lerEpisodio( ) para criar a nova versão do objeto
+ buscarEpisodio( ): Função que busca um objeto Episódio pelo ID.
+ buscarEpisodioNome( ): Função que le um nome e retorna um episódio que contém a sequência inserida. Se receber mais de um objeto da funcao do arqEpisodio.
+ verificarEpisodiosSerie( ): Função estática que, com um ID de Série, retorna verdadeiro ou falso se tiver um ou mais episódios atrelados a essa série.
+ povoar( ): Primeiro carregamento de dados para o sistema.

## Visao

Classes para fazer o controle de Entradas e Saídas do Sistema para cada Entidade

### VisaoSerie

#### Atributos

+ private static Scanner console = new Scanner(System.in);

#### Funções

+ menu( ): Função que cria um menu, pede uma entrada de dados enquanto for diferente de 0. Para cada valor entre 1 e 5, realizar certas funções de CRUD, opções como excluir deve chamar função visaoSerie.excluir() que faz a escolha entre excluir por nome ou por ID. Estética:
```
PucFlix v(versao)
--------------------------
> Início > Séries
1 - Incluir
2 - Excluir 
3 - Alterar
4 - Buscar séries
5 - Buscar episódios
0 - Sair
```
+ lerSerie( ): Função para ler uma entrada de dados com suas devidas verificações e gerar um objeto da Classe Serie e retorna-lo, caso objeto não seja válido, levantar exceção. O questioário deve ter seguinte forma:
“Qual o/a (atributo) (tipo do atributo e/ou regras/formato)? ” para cada atributo. Ao final, pedir uma confirmação com (S/N)
+ mostrarSerie(Serie s): Chama o toString da classe Serie
+ incluirSerie( ): Chama a função ControleSerie.incluirSerie(). Mostrar mensagem de sucesso ou falha
+ excluirSerie( ): Primeiramente, realiza uma pergunta para o usuário decidir se a exclusão é por ID ou por Nome, depois, chamar a função respectiva a escolha da classe ControleSerie. "1. Excluir por ID\n 2. Excluir por Nome". Mostrar mensagem de sucesso ou falha
+ alterarSerie( ): Primeiramente, realiza uma pergunta para o usuário decidir se a alteração é por ID ou por Nome, depois, chamar a função respectiva a escolha da classe ControleSerie. "1. Alterar por ID\n 2. Alterar por Nome". Mostrar mensagem de sucesso ou falha
+ buscarSerie( ): Primeiramente, realiza uma pergunta para o usuário decidir se a busca é por ID ou por Nome, depois, chamar a função respectiva a escolha da classe ControleSerie. "1. Buscar por ID\n 2. Buscar por Nome". Mostrar mensagem de sucesso (a própria função mostrarSerie()) ou falha
+ buscarEpisodiosSerie( ): Primeiramente, chama por buscarSerie() e, caso retorne um objeto válido, realiza uma pergunta para o usuário decidir se a busca é para todos os episódios de uma Serie ou para uma temporada específica, depois, chamar a função respectiva a escolha da classe ControleSerie. Caso a escolha seja por 2, primeiramente fazer uma entrada perguntando a teproada desejada "1. Buscar todos os episódios\n 2. Buscar episódios de uma temporada". Mostrar os episódios chamando VisaoEpisodio.mostrarEpisodio()
+ povoar( ): Chama função do controle para povoar dados

### VisaoEpisodio

## Modelo

Classes para o tratamento dos arquivos, como expecializações da classe Arquivo e Classes de Pares para o uso dos índices

### ArquivoSerie

### ArquivoEpisodio

### ParIDID

### ParNomeID


# Autores
+ *Augusto Stambassi Duarte* - Project Manager 🧑‍💼 - [Git Pessoal](https://github.com/stambassi)
+ *Lucas Carneiro Nassau Malta* - Desenvolvedor 👨‍💻 - [Git Pessoal](https://github.com/lucascarneiro1202)
+ *João Pedro Torres* - Desenvolvedor 👨‍💻 - [Git Pessoal](https://github.com/Towers444)
