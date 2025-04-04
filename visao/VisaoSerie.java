package visao;

import entidades.Serie;
import entidades.Episodio;
import controle.ControleSerie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VisaoSerie {
    
    ControleSerie controleSerie;

    private static Scanner console = new Scanner(System.in);

    /*
     * Construtor da classe VisaoSerie
     */
    public VisaoSerie() throws Exception {
        controleSerie = new ControleSerie();
    }

    public void menu() {
        // Definir variável de controle
        int opcao;

        // Iniciar bloco de seleção
        do {
            System.out.println("\n\nPUCFlix v");
            System.out.println("--------------------------");
            System.out.println("> Início > Séries\n");
            System.out.println("1 - Incluir");
            System.out.println("2 - Excluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Buscar séries");
            System.out.println("5 - Buscar episódios");
            System.out.println("0 - Sair");

            System.out.print("\nOpção: ");

            // Tentar ler um inteiro do Scanner
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            // Testar a opção escolhida
            switch (opcao) {
                case 1:
                    incluirSerie();
                    break;
                case 2:
                    excluirSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    buscarUmaSerie();
                    break;
                case 5:
                    buscarEpisodios();
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
     * incluirSerie - Função para interagir e confirmar a inclusão de uma Série
     */
    public void incluirSerie() {
        // Exibir título da ação
        System.out.println("\nInclusão de Serie");

        // Ler dados da Série a partir do terminal
        Serie s = lerSerie();

        // Confirmar a inclusão da Série
        System.out.print("\nConfirma a inclusão da Serie? (S/N) ");

        // Identificar escolha
        char resp = console.nextLine().charAt(0);

        // Testar escolha
        if (resp == 'S' || resp == 's') {
            // Tentar incluir a Série a partir do ControleSerie
            try {
                controleSerie.incluirSerie(s);
                System.out.println("Serie incluído com sucesso.");
            } catch(Exception e) {
                System.err.println("[ERRO]: " + e.getMessage());
            }
        }
    }

    /*
     * lerSerie - Função para iniciar a operação de leitura de uma Série
     * @return
     */
    public Serie lerSerie() {
        // Definir atributos de uma série
        String nome;
        int anoLancamento = 0;
        String sinopse;
        String streaming;
        int nota = 0;
        ArrayList<String> criadores = new ArrayList<String>();
        String criador;
        String genero;

        // Definir variável de controle
        boolean dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler o nome da Série
            System.out.print("Qual o nome da Série? ");

            nome = console.nextLine();

            // Testar se a entrada é válida
            if (nome.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: O nome deve ter no mínimo 4 caracteres!");
        } while (!dadosCorretos);

        // Reiniciar variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler o ano de lançamento da Série
            System.out.print("Qual o ano de lançamento (yyyy)? ");

            if (console.hasNextInt()) {
                anoLancamento = console.nextInt();

                // Testar se o ano é válido
                if(1000 <= anoLancamento && anoLancamento <= 9999)
                    dadosCorretos = true;
            } else {
                System.err.println("[ERRO]: O ano deve ser de 4 dígitos!");
            }

            // Limpar o buffer
            console.nextLine();
        } while (!dadosCorretos);

        // Reiniciar variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler a sinopse da Série
            System.out.print("Qual a sinopse? ");

            sinopse = console.nextLine();

            // Testar o tamanho da sinopse da Série
            if(sinopse.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: A sinopse deve ter no mínimo 4 caracteres!");
        } while(!dadosCorretos);

        // Reiniciar a variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler o streaming da Série
            System.out.print("Qual o streaming? ");
            streaming = console.nextLine();

            // Testar o tamanho do streaming da Série
            if(streaming.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: O streaming deve ter no mínimo 4 caracteres!");
        } while(!dadosCorretos);

        // Reiniciar a variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler a nota da Série 
            System.out.print("Qual a nota (0 a 10)? ");

            if (console.hasNextInt()) {
                nota = console.nextInt();

                // Testar nota da Sèrie
                if (0 <= nota && nota <= 10)
                    dadosCorretos = true;
            } else {
                System.err.println("[ERRO]: A nota deve ser entre 0 e 10!");
            }

            // Limpar o buffer
            console.nextLine();
        } while(!dadosCorretos);

        // Reiniciar a variável de controle
        dadosCorretos = false;
        do {
            // Ler os criadores da Série
            System.out.print("Quais são os criadores? (digite FIM para parar) ");

            criador = console.nextLine();

            // Testar a leitura dos criadores
            if (criador.equals("FIM") && criadores.size() > 0){
                dadosCorretos =  true;
            } else {
                // Testar se os criadores da série são válidos
                if (criador.length() >= 4) {
                    criadores.add(criador);
                } else if(criador.length() == 0) {
                    System.err.println("[ERRO]: A Série deve conter pelo menos um criador!");
                } else {
                    System.err.println("[ERRO]: O criador deve ter no mínimo 4 caracteres.");
                }
            }
        } while(!dadosCorretos);

        // Reiniciar a variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler o gênero da Série
            System.out.print("Qual o gênero? ");

            genero = console.nextLine();

            // Testar o gênero da Série
            if (genero.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: O gênero deve ter no mínimo 4 caracteres!");
        } while(!dadosCorretos);

        // Retornar objeto da Série preenchido com as informações!
        return new Serie(nome, anoLancamento, sinopse, streaming, nota, criadores, genero);
    }

    /*
     * lerSerieAlterar - Função para iniciar a operação de leitura de uma Série
     * @return
     */
    public Serie lerSerie(Serie antiga) {
        // Definir atributos de uma série
        String nome;
        int anoLancamento = 0;
        String sinopse;
        String streaming;
        int nota = 0;
        ArrayList<String> criadores = new ArrayList<String>();
        String criador;
        String genero;
        String aux;

        // Definir variável de controle
        boolean dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler o nome da Série
            System.out.print("Qual o nome da Série? ");

            nome = console.nextLine();

            // Testar se a entrada é válida
            if (nome.length() == 0){
                nome = antiga.getNome();
                dadosCorretos = true;
            }
            if (nome.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: O nome deve ter no mínimo 4 caracteres!");
        } while (!dadosCorretos);

        // Reiniciar variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler o ano de lançamento da Série
            System.out.print("Qual o ano de lançamento (yyyy)? ");
            aux = console.nextLine();
            if(aux.length() == 0){
                anoLancamento = antiga.getAnoLancamento();
                dadosCorretos = true;
            } else {
                try{
                    anoLancamento = Integer.parseInt(aux);
                    // Testar se o ano é válido
                    if(1000 <= anoLancamento && anoLancamento <= 9999) {
                        dadosCorretos = true;
                    } else {
                        System.err.println("[ERRO]: O ano deve ser de 4 dígitos!");
                    }
                } catch (NumberFormatException e){
                    dadosCorretos = false;
                    System.err.println("[ERRO]: Digite um número válido!");
                }
            }
        } while (!dadosCorretos);

        // Reiniciar variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler a sinopse da Série
            System.out.print("Qual a sinopse? ");

            sinopse = console.nextLine();
            if (sinopse.length() == 0){
                sinopse = antiga.getSinopse();
                dadosCorretos = true;
            }
            // Testar o tamanho da sinopse da Série
            if(sinopse.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: A sinopse deve ter no mínimo 4 caracteres!");
        } while(!dadosCorretos);

        // Reiniciar a variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler o streaming da Série
            System.out.print("Qual o streaming? ");
            streaming = console.nextLine();

            if (streaming.length() == 0){
                streaming = antiga.getStreaming();
                dadosCorretos = true;
            }
            // Testar o tamanho do streaming da Série
            if(streaming.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: O streaming deve ter no mínimo 4 caracteres!");
        } while(!dadosCorretos);

        // Reiniciar variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler o ano de lançamento da Série
            System.out.print("Qual a nota (0 a 10)? ");
            aux = console.nextLine();
            if(aux.length() == 0){
                nota = antiga.getNota();
                dadosCorretos = true;
            } else {
                try{
                    anoLancamento = Integer.parseInt(aux);
                    // Testar se o ano é válido
                    if(0 <= anoLancamento && anoLancamento <= 10) {
                        dadosCorretos = true;
                    } else {
                        System.err.println("[ERRO]: A nota deve ser entre 0 e 10!");
                    }
                } catch (NumberFormatException e){
                    dadosCorretos = false;
                    System.err.println("[ERRO]: Digite um número válido!");
                }
            }
        } while (!dadosCorretos);

        // Reiniciar a variável de controle
        dadosCorretos = false;
        do {
            // Ler os criadores da Série
            System.out.print("Quais são os criadores? (digite FIM para parar) ");
            criador = console.nextLine();
            if (criador.length() == 0){
                criadores = antiga.getCriadores();
                dadosCorretos = true;
            } else if (criador.equals("FIM") && criadores.size() > 0){ 
                // Testar a leitura dos criadores
                dadosCorretos =  true;
            } else {
                // Testar se os criadores da série são válidos
                if (criador.length() >= 4) {
                    criadores.add(criador);
                } else if(criador.length() == 0) {
                    System.err.println("[ERRO]: A Série deve conter pelo menos um criador!");
                } else {
                    System.err.println("[ERRO]: O criador deve ter no mínimo 4 caracteres.");
                }
            }
        } while(!dadosCorretos);

        // Reiniciar a variável de controle
        dadosCorretos = false;

        // Iniciar bloco de seleção
        do {
            // Ler o gênero da Série
            System.out.print("Qual o gênero? ");

            genero = console.nextLine();
            if (genero.length() == 0){
                genero = antiga.getGenero();
                dadosCorretos = true;
            }
            // Testar o gênero da Série
            if (genero.length() >= 4)
                dadosCorretos = true;
            else
                System.err.println("[ERRO]: O gênero deve ter no mínimo 4 caracteres!");
        } while(!dadosCorretos);

        // Retornar objeto da Série preenchido com as informações!
        return new Serie(nome, anoLancamento, sinopse, streaming, nota, criadores, genero);
    }


    /*
     * buscarUmaSerie - Função para iniciar a operação de seleção do método de busca de uma Série (nome ou ID)
     * @return s - Série selecionada
     */
    public Serie buscarUmaSerie() {
        // Definir variável de controle
        int opcao;

        // Definir a variável Série a ser retornada
        Serie s = null;

        // Definir lista auxiliar de Séries
        List<Serie> series;

        // Definir variável de controle
        boolean dadosCorretos;

        // Exibir opções de seleção
        System.out.println("\n> Como deseja fazer a busca da Série?\n");
        System.out.println("1 - Buscar por Nome");
        System.out.println("2 - Buscar por ID");
        System.out.println("0 - Sair");
        System.out.print("\nOpção: ");

        // Tentar ler a opção do console
        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch(NumberFormatException e) {
            opcao = -1;
        }

        // Testar a opção
        switch (opcao){
            case 1:
                // Buscar todas as Séries pelo nome
                series = buscarSerieNome();

                // Reiniciar variável de controle
                dadosCorretos = false;

                // Testar lista de Séries encontradas pelo nome
                if(series != null){
                    if (series.size() <= 1) {
                        s = series.get(0);
                    } else {
                        // Reiniciar variável de controle  
                        opcao = 0;
    
                        // Iniciar bloco de seleção
                        do {
                            // Exibir todas as Séries encontradas pelo nome
                            System.out.println("Escolha uma Série: ");
    
                            int n = 0;
    
                            for (Serie l : series) 
                                System.out.println((n++) + " - " + l.getNome());
    
                            // Tentar ler a opção do console
                            try {
                                opcao = Integer.valueOf(console.nextLine());
                            } catch(NumberFormatException e) {
                                opcao = -1;
                            }
    
                            // Testar a opção
                            if (0 <= opcao && opcao <= series.size()) {
                                // Identificar a Série selecionada pela sua posição
                                s = series.get(opcao);
    
    
    
                                // Atualizar variável de controle
                                dadosCorretos = true;
                            } else {
                                System.err.println("[ERRO]: Série não está presente na lista!");
                            }
                        } while(!dadosCorretos);        
                    }
                } 
                break;
            case 2: 
                // Buscar Série pelo ID
                s = buscarSerieID();

                
                break;
            default:
                System.err.println("[ERRO]: Opção inválida!");
                break;
        }

        // Mostrar a Série selecionada
        mostraSerie(s);
        // Retornar Série selecionada
        return s;

    }

    public Serie buscarSerieID() {
        System.out.println("\n> Busca de Serie por ID");
        int id = -1;
        boolean dadosCorretos = false;

        do {
            System.out.print("\nID: ");
            if (console.hasNextInt()) {
                id = console.nextInt();
                if(id > 0)
                    dadosCorretos = true;
                else 
                    System.err.println("O ID deve ser maior que 0");
            }
            console.nextLine();
        } while (!dadosCorretos);

        try {
            Serie serie = controleSerie.buscarSerie(id);  // Chama o método de leitura da classe Arquivo
            if (serie != null) {
                //mostraSerie(serie);  // Exibe os detalhes do Serie encontrado
                return serie;
            } else {
                System.out.println("Série não encontrada.");
                return null;
            }
        } catch(Exception e) {
            System.err.println("[ERRO]: " + e.getMessage());
            //e.printStackTrace();
            return null;
        }
    }   

    public List<Serie> buscarSerieNome() {
        System.out.println("\n> Busca de Série por Nome");
        System.out.print("\nNome: ");
        String nome = console.nextLine();  // Lê o título digitado pelo usuário
        List<Serie> series = new ArrayList<Serie>();
        if(nome.isEmpty())
            return series; 
        try {
            series = controleSerie.buscarSerie(nome);  // Chama o método de leitura da classe Arquivo
            if (series.size()>0) {
                return series;
            } else {
                System.out.println("Nenhuma Série encontrada.");
                return series;
            }
        } catch(Exception e) {
            System.err.println("[ERRO]: " + e.getMessage());
            return null;
        }
    }   

    public void alterarSerie() {
        System.out.println("\nAlteração de Serie");
        try {
            // Tenta ler o Serie com o ID fornecido
            Serie s = buscarUmaSerie();
            if (s != null) {
                //mostraSerie(s);  // Exibe os dados do Serie para confirmação
                Serie nova = lerSerie(s);
                nova.setID(s.getID());
                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = controleSerie.alterarSerie(nova);
                    if (alterado) {
                        System.out.println("Série alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar a Série.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
                 console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Serie não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("[ERRO]: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

    public void excluirSerie() {
        System.out.println("\nExclusão de Série");
        try {
            // Tenta ler o Serie com o ID fornecido
            Serie s = buscarUmaSerie();
            if (s != null) {
                System.out.println("Serie encontrada:");
                //mostraSerie(s);  // Exibe os dados do Serie para confirmação

                System.out.print("\nConfirma a exclusão do Serie? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = controleSerie.excluirSerie(s.getID());  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Serie excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o Serie.");
                    }
                    
                } else {
                    System.out.println("Exclusão cancelada.");
                }
                console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Serie não encontrado.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
    }

    public void mostraSerie(Serie serie) {
        if (serie != null) {
            System.out.println(serie);
        }
    }

    public void buscarEpisodios() {
        // Iniciar bloco try-catch
        try {
            // Tentar ler o Serie com o ID fornecido
            Serie s = buscarUmaSerie();

            // Mostrar cabeçalho
            System.out.println("\n> Episódios da Série\n");

            // Buscar todos os episódios vinculados à Série
            List<Episodio> episodios = controleSerie.buscarSerieEpisodios(s.getID());

            // Mostrar as opções de Séries a serem escolhidas
            int i = 1;
            for (Episodio episodio : episodios) {
                System.out.println("(" + (i++) + ") - " + episodio.getNome() + " " + episodio.getTemporada() + " - Temporada");
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
}
