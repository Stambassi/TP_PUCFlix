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
            System.out.println("\n\nPUCFlix v");
            System.out.println("--------------------------");
            System.out.println("> Início > Episódios > " + serie.getNome());
            System.out.println("\n1 - Incluir");
            System.out.println("2 - Excluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Buscar");
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
            System.out.println("\n\nPUCFlix v");
            System.out.println("--------------------------");
            System.out.println("> Início > Episódios > " + serie.getNome() + " > Temporada " + this.temp);
            System.out.println("\n1 - Incluir");
            System.out.println("2 - Buscar");
            System.out.println("3 - Excluir");
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
                    buscarUmEpisodio();
                    break;
                case 3:
                    excluirEpisodio();
                    break;
                case 0:
                    this.temp = 0;
                    break;
                default:
                    System.err.println("[ERRO]: Opção inválida!");
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
        String regex = "^\\d{4}-\\d{2}-\\d{2}$";
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
                } else {
                    System.err.println("[ERRO]: A Temporada deve ser entre 1 e 127!");
                }

                // Limpar o buffer
                console.nextLine();
            } while (!dadosCorretos);
        }

        // Ler a data de lançamento do Episódio
        do {
            System.out.print("Qual a data de lançamento (dd/MM/yyyy)? > ");
            String data = console.nextLine();
            Matcher matcher = pattern.matcher(data);

            if (matcher.matches()) {
                dadosCorretos = true;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataLancamento = LocalDate.parse(data, formatter);
            } else
                System.err.println("[ERRO]: O formato deve ser (dd/MM/yyyy)!");
        } while (!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual a duração (minutos)? ");
            if (console.hasNextInt()) {
                duracao = console.nextFloat();
                if (0 < duracao)
                    dadosCorretos = true;
            } else {
                System.err.println("[ERRO]: O episódio deve ter mais que 0 minutos!");
            }
            console.nextLine();
        } while (!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual a nota (0 a 10)? ");
            if (console.hasNextInt()) {
                duracao = console.nextInt();
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
            System.out.print("Qual a data de lançamento (dd/MM/yyyy)? > ");
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
        System.out.print("\nOpção ");

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
                        System.out.println("> Escolha um Episódio: ");

                        int n = 0;

                        for (Episodio i : eps)
                            System.out.println((n++) + " - " + i.getNome());

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
                break;
            case 2:
                // Buscar Episódio pelo ID
                ep = buscarEpisodioID();
                break;
            default:
                System.err.println("[ERRO]: Opção inválida!");
                break;
        }

        // Mostrar o Episódio selecionado
        mostraEpisodio(ep);
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
                System.err.println("[ERRO]: Nenhum Episódio encontrado!");
                return null;
            }
        } catch (Exception e) {
            System.err.println("[ERRO]: Não foi possível buscar os Episódios!");
            e.printStackTrace();
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
                System.err.println("[ERRO]: O ID deve ser maior que 0!");
            }

            // Limpar buffer
            console.nextLine();
        } while (!dadosCorretos);

        // Tentar buscar o Episódio pelo ID
        try {
            // Chama o método de leitura da classe Arquivo
            Episodio Episodio = controle.buscarEpisodio(id);

            // Exibe os detalhes do Episodio encontrado
            // mostraEpisodio(Episodio);

            // Retornar
            return Episodio;
        } catch(Exception e) {
            System.err.print("\n[ERRO]: " + e.getMessage());
            return null;
        }
    }

    public void incluirEpisodio() {
        if (this.temp > 0) {
            System.out.println("\nInclusão de Episódio - " + this.temp + " Temporada");
        } else {
            System.out.println("\nInclusão de Episodio");
        }
        Episodio ep = lerEpisodio();
        System.out.print("\nConfirma a inclusão do Episodio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                controle.incluirEpisodio(ep);
                System.out.println("Episódio incluído com sucesso.");
            } catch (Exception e) {
                System.out.println("[ERRO]: Não foi possível incluir o Episódio!");
            }
        }
    }

    public void alterarEpisodio() {
        System.out.println("\nAlteração de Episodio");
        try {
            // Tenta ler o Episodio com o ID fornecido
            Episodio ep = buscarUmEpisodio();
            if (ep != null) {
                // mostraEpisodio(ep); // Exibe os dados do Episodio para confirmação
                Episodio novo = lerEpisodio(ep);
                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = controle.alterarEpisodio(novo);
                    if (alterado) {
                        System.out.println("Episódio alterado com sucesso.");
                    } else {
                        System.err.println("[ERRO]: Não foi possível alterar o Episódio.");
                    }
                } else {
                    System.err.println("Alterações canceladas.");
                }
                console.nextLine(); // Limpar o buffer
            } else {
                System.err.println("[ERRO]: Episódio não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("[ERRO]: Não foi possível alterar o Episódio!");
            e.printStackTrace();
        }

    }

    public void excluirEpisodio() {
        System.out.println("\nExclusão de Série");
        try {
            // Tenta ler o Episodio com o ID fornecido
            Episodio ep = buscarUmEpisodio();
            if (ep != null) {
                System.out.println("Episódio encontrado:");
                // mostraEpisodio(ep); // Exibe os dados do Episodio para confirmação

                System.out.print("\nConfirma a exclusão do Episódio? (S/N) ");
                char resp = console.next().charAt(0); // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = controle.excluirEpisodio(ep); // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Episódio excluído com sucesso.");
                    } else {
                        System.err.println("[ERRO]: Não foi possível excluir o Episódio.");
                    }

                } else {
                    System.err.println("Exclusão cancelada.");
                }
                console.nextLine(); // Limpar o buffer
            } else {
                System.err.println("[ERRO]: Episódio não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("[ERRO]: Não foi possível excluir o Episódio!");
            e.printStackTrace();
        }
    }

    public void mostraEpisodio(Episodio Episodio) {
        if (Episodio != null) {
            System.out.print(Episodio);
        }
    }

    public void entrarTemporada() {
        int temporada = 0;
        boolean dadosCorretos = false;
        do {
            System.out.print("Qual a Temporada? ");
            if (console.hasNextInt()) {
                temporada = console.nextInt();
                if (0 < temporada && temporada <= 127)
                    dadosCorretos = true;
            } else {
                System.err.println("[ERRO]: A Temporada deve ser entre 1 e 127!");
            }
            console.nextLine();
        } while (!dadosCorretos);
        try {
            controle.buscarEpisodioTemporada(temporada);
            this.temp = temporada;
            menuTemporada();
        } catch (Exception e) {
            System.err.println("[ERRO]: Temporada não existe na Série (" + serie.getNome() + ")!");
        }
    }
}
