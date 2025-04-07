package visao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entidades.Episodio;
import entidades.Serie;
import controle.ControleEpisodio;
import controle.ControleSerie;

public class VisaoEpisodio {

    ControleEpisodio controle;
    Serie serie;
    int temp;
    private static Scanner console = new Scanner(System.in);

    /*
     * Definir construtor da classe VisaoEpisodio
     */
    public VisaoEpisodio() throws Exception {
        controle = new ControleEpisodio();
    }

    /*
     * Definir construtor da classe VisaoEpisodio
     * 
     * @param s - Objeto Série selecionada
     */
    public VisaoEpisodio(Serie s) throws Exception {
        controle = new ControleEpisodio(s);
        serie = s;
        temp = 0;
    }

    /*
     * menu - Função para controlar as opções da tela 'Início > Episódios > Nome da
     * Série'
     */
    public void menu() {
        // Definir variável auxiliar
        int opcao;

        // Testar se a Série foi selecionada
        if (serie == null) {
            System.err.println("[ERRO]: Série não encontrada!");
            return;
        }

        // Iniciar bloco de seleção
        do {
            // Mostrar Menu de seleção
            System.out.println("\nPUCFlix v");
            System.out.println("--------------------------");
            System.out.println("> Início > Episódios > " + serie.getNome());
            System.out.println("\n1 - Incluir Episódio");
            System.out.println("2 - Excluir Episódio");
            System.out.println("3 - Alterar Episódio");
            System.out.println("4 - Buscar Episódio");
            System.out.println("5 - Escolher Temporada");
            System.out.println("0 - Sair");
            System.out.print("\nOpção: ");

            // Ler a opção do console
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            // Testar a opção escolhida
            switch (opcao) {
                case 1:
                    incluirEpisodio();
                    break;
                case 2:
                    excluirEpisodio();
                    break;
                case 3:
                    alterarEpisodio();
                    break;
                case 4:
                    buscarUmEpisodio();
                    break;
                case 5:
                    entrarTemporada();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("[ERRO]: Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    /*
     * menuTemporada - Função para controlar as opções da tela 'Início > Episódios >
     * Nome da Série > Temporada x'
     */
    public void menuTemporada() {
        // Definir variável auxiliar
        int opcao;

        // Testar se a Série foi selecionada
        if (serie == null) {
            System.err.println("[ERRO]: Série não selecionada");
            return;
        }

        // Iniciar bloco de seleção
        do {
            // Mostar
            System.out.println("\nPUCFlix v");
            System.out.println("--------------------------");
            System.out.println("> Início > Episódios > " + serie.getNome() + " > Temporada " + this.temp);
            System.out.println("\n1 - Incluir Episódio");
            System.out.println("2 - Excluir Episódio");
            System.out.println("3 - Alterar Episódio");
            System.out.println("4 - Buscar Episódio");
            System.out.println("0 - Sair");
            System.out.print("\nOpção: ");

            // Ler a opção do console
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            // Testar a opção escolhida
            switch (opcao) {
                case 1:
                    incluirEpisodio();
                    break;
                case 2:
                    excluirEpisodio();
                    break;
                case 3:
                    alterarEpisodio();
                case 4:
                    buscarEpisodiosTemporada(this.temp);
                    break;
                case 0:
                    this.temp = 0;
                    break;
                default:
                    System.err.println("\n[ERRO]: Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    /*
     * lerEpisodio - Função para ler um Episódio no controle
     * 
     * @return
     */
    public Episodio lerEpisodio() {
        // Definir os atributos de um Episódio
        String nome;
        int IDSerie = serie.getID();
        int temporada = 0;
        LocalDate dataLancamento = null;
        float duracao = 0; // em minutos
        int nota = 0; // 0 a 10
        List<String> diretores = new ArrayList<String>();
        String diretor;

        // Definir variáveis auxiliares
        boolean dadosCorretos = false;
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);

        // Ler o nome do Episódio
        do {
            // Ler o nome do Episódio do console
            System.out.print("Qual o nome do Episódio? ");
            nome = console.nextLine();

            // Testar a leitura do nome
            if (nome.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: O nome deve ter no mínimo 4 caracteres!");
        } while (!dadosCorretos);

        // Testar se a temporara do Episódio já está selecionada
        if (this.temp > 0) {
            temporada = this.temp;
        } else {
            // Reiniciar variável de controle
            dadosCorretos = false;

            // Ler a temporada do Episódio
            do {
                // Ler a temporada do Episódio do console
                System.out.print("Qual a Temporada? ");
                if (console.hasNextInt()) {
                    temporada = console.nextInt();

                    // Testar se a temporada é válida
                    if (0 < temporada && temporada <= 127)
                        dadosCorretos = true;
                    else 
                        System.err.println("[ERRO]: A Temporada deve ser entre 1 e 127!");
                } else {
                    System.err.println("[ERRO]: A Temporada deve ser um número inteiro!");
                }

                // Limpar o buffer
                console.nextLine();
            } while (!dadosCorretos);
        }

        // Ler a data de lançamento do Episódio
        do {
            System.out.print("Qual a data de lançamento (dd/MM/yyyy)? ");
            String data = console.nextLine();
            Matcher matcher = pattern.matcher(data);

            if (matcher.matches()) {
                dadosCorretos = true;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataLancamento = LocalDate.parse(data, formatter);
            } else {
                dadosCorretos = false;
                System.err.println("[ERRO]: O formato deve ser (dd/MM/yyyy)!");
            }
        } while (!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual a duração (minutos)? ");
            if (console.hasNextFloat()) {
                duracao = console.nextFloat();
                if (0 < duracao){
                    dadosCorretos = true;
                } else {
                    System.err.println("[ERRO]: O episódio deve ter mais que 0 minutos!");
                }
            } else {
                System.err.println("[ERRO]: Deve entrar com um número! Usar ponto para casas decimais");
            }
            console.nextLine();
        } while (!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual a nota (0 a 10)? ");
            if (console.hasNextInt()) {
                nota = console.nextInt();
                if (0 <= nota && nota <= 10)
                    dadosCorretos = true;
            } else {
                System.err.println("[ERRO]: A nota deve ser entre 0 e 10!");
            }
            console.nextLine();
        } while (!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Quais são os diretores (digite FIM para parar)? ");
            diretor = console.nextLine();
            if (diretor.equals("FIM") && diretores.size() > 0) {
                dadosCorretos = true;
            } else {
                if (diretor.length() >= 4) {
                    diretores.add(diretor);
                } else if (diretor.length() == 0) {
                    System.err.println("[ERRO]: A Série deve conter pelo menos um diretor!");
                } else {
                    System.err.println("[ERRO]: O diretor deve ter no mínimo 4 caracteres!");
                }
            }
        } while (!dadosCorretos);

        Episodio ep = null;

        //
        try {
            ep = new Episodio(IDSerie, nome, temporada, dataLancamento, duracao, nota, diretores);
        } catch (Exception e) {
            System.err.println("[ERRO]: " + e.getMessage());
        }

        // Retornar
        return ep;
    }

    /*
     * lerEpisodio - Função para ler um Episódio no controle
     * 
     * @return
     */
    public Episodio lerEpisodio(Episodio antigo) {
        // Definir os atributos de um Episódio
        String nome;
        int IDSerie = antigo.getIDSerie();
        int temporada = 0;
        LocalDate dataLancamento = null;
        float duracao = 0; // em minutos
        int nota = 0; // 0 a 10
        List<String> diretores = new ArrayList<String>();
        String diretor;
        String aux;

        // Definir variáveis auxiliares
        boolean dadosCorretos = false;
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);

        // Ler o nome do Episódio
        do {
            // Ler o nome do Episódio do console
            System.out.print("Qual o nome do Episódio? ");
            nome = console.nextLine();
            if (nome.length() == 0) {
                nome = antigo.getNome();
                dadosCorretos = true;
            }
            // Testar a leitura do nome
            if (nome.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: O nome deve ter no mínimo 4 caracteres!");
        } while (!dadosCorretos);

        // Testar se a temporara do Episódio já está selecionada
        if (this.temp > 0) {
            temporada = this.temp;
        } else {
            // Reiniciar variável de controle
            dadosCorretos = false;

            // Ler a temporada do Episódio
            do {
                // Ler a temporada do Episódio do console
                System.out.print("Qual a Temporada? ");
                aux = console.nextLine();
                if (aux.length() == 0) {
                    temporada = antigo.getTemporada();
                    dadosCorretos = true;
                } else {
                    try {
                        temporada = Integer.parseInt(aux);
                        // Testar se o ano é válido
                        if (0 <= temporada && temporada <= 127) {
                            dadosCorretos = true;
                        } else {
                            System.err.println("[ERRO]: A Temporada deve ser entre 1 e 127!");
                        }
                    } catch (NumberFormatException e) {
                        dadosCorretos = false;
                        System.err.println("[ERRO]: Digite um número válido!");
                    }
                }
            } while (!dadosCorretos);
        }

        // Ler a data de lançamento do Episódio
        do {
            System.out.print("Qual a data de lançamento (dd/MM/yyyy)? ");
            String data = console.nextLine();
            Matcher matcher = pattern.matcher(data);
            if (data.length() == 0) {
                dataLancamento = antigo.getDataLancamento();
                dadosCorretos = true;
            } else {
                if (matcher.matches()) {
                    dadosCorretos = true;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    dataLancamento = LocalDate.parse(data, formatter);
                } else
                    System.err.println("[ERRO]: O formato deve ser (dd/MM/yyyy)!");
            }
        } while (!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual a duração (minutos)? ");
            aux = console.nextLine();
            if (aux.length() == 0) {
                duracao = antigo.getDuracao();
                dadosCorretos = true;
            } else {
                try {
                    duracao = Float.parseFloat(aux);
                    // Testar se o ano é válido
                    if (0 < duracao) {
                        dadosCorretos = true;
                    } else {
                        System.err.println("[ERRO]: O episódio deve ter mais que 0 minutos!");
                    }
                } catch (NumberFormatException e) {
                    dadosCorretos = false;
                    System.err.println("[ERRO]: Digite um número válido!");
                }
            }

        } while (!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual a nota (0 a 10)? ");
            aux = console.nextLine();
            if (aux.length() == 0) {
                nota = antigo.getNota();
                dadosCorretos = true;
            } else {
                try {
                    nota = Integer.parseInt(aux);
                    // Testar se o ano é válido
                    if (0 <= nota && nota <= 10) {
                        dadosCorretos = true;
                    } else {
                        System.err.println("[ERRO]: A nota deve ser entre 0 e 10!");
                    }
                } catch (NumberFormatException e) {
                    dadosCorretos = false;
                    System.err.println("[ERRO]: Digite um número válido!");
                }
            }

        } while (!dadosCorretos);

        dadosCorretos = false;
        do {
            // Ler os criadores da Série
            System.out.print("Quais são os diretores (digite FIM para parar)? ");
            diretor = console.nextLine();
            if (diretor.length() == 0) {
                diretores = antigo.getDiretores();
                dadosCorretos = true;
            } else if (diretor.equals("FIM") && diretores.size() > 0) {
                // Testar a leitura dos criadores
                dadosCorretos = true;
            } else {
                // Testar se os criadores da série são válidos
                if (diretor.length() >= 4) {
                    diretores.add(diretor);
                } else if (diretor.length() == 0) {
                    System.err.println("[ERRO]: O episódio deve conter pelo menos um diretor!");
                } else {
                    System.err.println("[ERRO]: O diretor deve ter no mínimo 4 caracteres.");
                }
            }
        } while (!dadosCorretos);

        Episodio ep = null;

        //
        try {
            ep = new Episodio(antigo.getID(),IDSerie, nome, temporada, dataLancamento, duracao, nota, diretores);
        } catch (Exception e) {
            System.err.println("[ERRO]: " + e.getMessage());
        }

        // Retornar
        return ep;
    }

    public Episodio buscarUmEpisodio() {
        // Definir variável de controle
        int opcao;

        // Definir a variável Episódio a ser retornada
        Episodio ep = null;

        // Definir lista auxiliar de Episódios
        List<Episodio> eps;

        // Definir variável de controle
        boolean dadosCorretos;

        // Exibir opções de seleção
        System.out.println("\n> Como deseja fazer a busca do Episódio?\n");
        System.out.println("1 - Buscar por Nome");
        System.out.println("2 - Buscar por ID");
        System.out.println("0 - Sair");
        System.out.print("\nOpção: ");

        // Tentar ler a opção do console
        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            opcao = -1;
        }

        // Testar a opção
        switch (opcao) {
            case 1:
                // Buscar todas os Episódios pelo nome
                eps = buscarEpisodioNome();

                // Reiniciar variável de controle
                dadosCorretos = false;

                // Testar lista de Episódios encontrados pelo nome
                if (eps == null) {
                    break;
                }

                // Testar se a lista de Episódios tem mais de um Episódio
                if (eps.size() <= 1) {
                    ep = eps.get(0);
                } else {
                    // Reiniciar variável de controle
                    opcao = 0;

                    // Iniciar bloco de seleção
                    do {
                        // Exibir todos os Episódios encontrados pelo nome
                        System.out.println("\n> Escolha um Episódio: \n");

                        int n = 0;

                        for (Episodio i : eps)
                            System.out.println("()" + (n++) + ") " + i.getNome());

                        System.out.print("\nOpção: ");

                        // Tentar ler a opção do console
                        try {
                            opcao = Integer.valueOf(console.nextLine());
                        } catch (NumberFormatException e) {
                            opcao = -1;
                        }

                        // Testar a opção
                        if (0 <= opcao && opcao <= eps.size()) {
                            // Identificar o Episódio selecionado pela sua posição
                            ep = eps.get(opcao);

                            // Atualizar variável de controle
                            dadosCorretos = true;
                        } else {
                            System.err.println("[ERRO]: Episódio não está presente na lista!");
                        }
                    } while (!dadosCorretos);
                }
                // Mostrar o Episódio selecionado
                mostraEpisodio(ep);            
                break;
            case 2:
                // Buscar Episódio pelo ID
                ep = buscarEpisodioID();
                // Mostrar o Episódio selecionado
                mostraEpisodio(ep);
                break;
            default:
                System.err.println("[ERRO]: Opção inválida!");
                break;
        }
        // Retornar o Episódio selecionado
        return ep;
    }

    public List<Episodio> buscarEpisodioNome() {
        System.out.println("\n> Busca de Episódio por Nome");
        System.out.print("\nNome: ");
        String nome = console.nextLine(); // Lê o título digitado pelo usuário
        List<Episodio> episodios = null;
        if (nome.isEmpty())
            return episodios;

        try {
            episodios = controle.buscarEpisodio(nome); // Chama o método de leitura da classe Arquivo
            if (episodios.size() > 0) {
                return episodios;
            } else {
                System.err.println("\n[ERRO]: Nenhum Episódio encontrado!");
                return null;
            }
        } catch (Exception e) {
            System.err.println("\n[ERRO]: " + e.getMessage());
            return null;
        }
    }

    public Episodio buscarEpisodioID() {
        // Definir variáveis auxiliares
        int id = 0;
        boolean dadosCorretos = false;

        // Mostrar cabeçalho
        System.out.println("\n> Busca de Episódio por ID");

        // Ler o ID do Episódio
        do {
            // Ler o ID do Episódio do console
            System.out.print("\nID: ");
            if (console.hasNextInt()) {
                id = console.nextInt();
                // Testar o ID do Episódio
                if (id > 0)
                    dadosCorretos = true;
            } else {
                System.err.println("\n[ERRO]: O ID deve ser maior que 0!");
            }
            // Limpar buffer
            console.nextLine();
        } while (!dadosCorretos);

        // Tentar buscar o Episódio pelo ID
        try {
            // Chama o método de leitura da classe Arquivo
            Episodio Episodio = controle.buscarEpisodio(id);

            // Retornar
            return Episodio;
        } catch(Exception e) {
            System.err.println("\n[ERRO]: " + e.getMessage());
            return null;
        }
    }

    public void buscarEpisodiosTemporada(int temporada) {
        // Iniciar bloco try-catch
        try {
            // Testar serie atual
            if (this.serie == null) return;

            // Mostrar cabeçalho
            System.out.println("\n> Episódios da Temporada\n");

            // Definir ControleSerie
            ControleSerie controleSerie = new ControleSerie();

            // Buscar todos os episódios vinculados à Série
            List<Episodio> episodios = controleSerie.buscarSerieEpisodios(serie.getID());

            // Mostrar as opções de Séries a serem escolhidas
            int i = 1;
            for (Episodio episodio : episodios) {
                if (episodio.getTemporada() == temporada)
                    System.out.println("(" + (i++) + ") - " + episodio.getNome() + " - " + episodio.getTemporada() + " Temporada");
            }

            // Definir variável auxiliar
            int opcao = 0;
            boolean dadosCorretos = false;

            // Tentar ler a opção do console
            do {
                System.out.print("\nOpção: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                    dadosCorretos = true;
                } catch(NumberFormatException e) {
                    System.err.println("\n[ERRO]: Opção inválida!");
                    dadosCorretos = false;
                }
            } while ( !dadosCorretos || !ControleSerie.validarSerie(opcao));
            
            // Identificar Episódio escolhido
            System.out.print(episodios.get(opcao - 1));
        } catch (Exception e) {
            System.out.println("[ERRO]: " + e.getMessage());
        }
    }

    public void incluirEpisodio() {
        if (this.temp > 0) {
            System.out.println("\n> Inclusão de Episódio - " + this.temp + " Temporada\n");
        } else {
            System.out.println("\n> Inclusão de Episodio\n");
        }
        Episodio ep = lerEpisodio();
        System.out.print("\nConfirma a inclusão do Episodio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                controle.incluirEpisodio(ep);
                System.out.println("\nEpisódio incluído com sucesso!");
            } catch (Exception e) {
                System.out.println("\n[ERRO]: Não foi possível incluir o Episódio!");
            }
        }
    }

    public void alterarEpisodio() {
        System.out.println("\n> Alteração de Episodio\n");
        try {
            // Tenta ler o Episodio com o ID fornecido
            Episodio ep = buscarUmEpisodio();
            if (ep != null) {
                System.out.println("\n> Insira os novos dados do Episódio (caso deseje manter os dados originais, apenas tecle Enter): \n");
                Episodio novo = lerEpisodio(ep);
                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = controle.alterarEpisodio(novo);
                    if (alterado) {
                        System.out.println("\nEpisódio alterado com sucesso.");
                    } else {
                        System.err.println("\n[ERRO]: Não foi possível alterar o Episódio.");
                    }
                } else {
                    System.err.println("\nAlterações canceladas.");
                }
                console.nextLine(); // Limpar o buffer
            }
        } catch (Exception e) {
            System.err.println("\n[ERRO]: " + e.getMessage());
        }
    }

    public void excluirEpisodio() {
        System.out.println("\nExclusão de Série");
        try {
            // Tenta ler o Episodio com o ID fornecido
            Episodio ep = buscarUmEpisodio();
            if (ep != null) {
                System.out.print("\nConfirma a exclusão do Episódio? (S/N) ");
                char resp = console.next().charAt(0); // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = controle.excluirEpisodio(ep); // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("\nEpisódio excluído com sucesso.");
                    } else {
                        System.err.println("\n[ERRO]: Não foi possível excluir o Episódio.");
                    }

                } else {
                    System.err.println("\nExclusão cancelada.");
                }
                console.nextLine(); // Limpar o buffer
            } else {
                System.err.println("\n[ERRO]: Episódio não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("\n[ERRO]: " + e.getMessage());
        }
    }

    public void mostraEpisodio(Episodio Episodio) {
        if (Episodio != null) {
            System.out.println(Episodio);
        }
    }

    public void entrarTemporada() {
        int temporada = 0;
        boolean dadosCorretos = false;
        System.out.println("\n> Entrar na Temporada");
        do {
            System.out.print("\nDigite a Temporada: ");
            if (console.hasNextInt()) {
                temporada = console.nextInt();
                if (0 < temporada && temporada <= 127)
                    dadosCorretos = true;
            } else {
                System.err.println("\n[ERRO]: A Temporada deve ser entre 1 e 127!");
            }
            console.nextLine();
        } while (!dadosCorretos);
        try {
            controle.buscarEpisodioTemporada(temporada);
            this.temp = temporada;
            menuTemporada();
        } catch (Exception e) {
            System.err.println("\n[ERRO]: Temporada não existe na Série " + serie.getNome() + "!");
        }
    }
}
