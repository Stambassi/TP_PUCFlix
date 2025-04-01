package visao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entidades.Serie;
import modelo.ArquivoSerie;
import controle.ControleSerie;

public class VisaoSerie {
    
    ControleSerie controle;
    private static Scanner console = new Scanner(System.in);

    public VisaoSerie() throws Exception {
        controle = new ControleSerie();
    }

    public void menu() {

        int opcao;
        do {
            System.out.println("\n\nPUCFlix v");
            System.out.println("--------------------------");
            System.out.println("> Início > Séries");
            System.out.println("\n1 - Incluir");
            System.out.println("2 - Excluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Buscar séries");
            System.out.println("5 - Buscar episódios");
            System.out.println("0 - Sair");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

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
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }



    public Serie lerSerie(){
        String nome;
        int anoLancamento = 0;
        String sinopse;
        String streaming;
        int nota = 0;
        ArrayList<String> criadores = new ArrayList<>();
        String criador;
        String genero;
        boolean dadosCorretos = false;
        do {
            System.out.print("Qual o nome da Série? ");
            nome = console.nextLine();
            if(nome.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("O nome deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual o ano de lançamento (yyyy)? ");
            if (console.hasNextInt()) {
                anoLancamento = console.nextInt();
                if(1000 <= anoLancamento && anoLancamento <= 9999)
                    dadosCorretos = true;
            }else{
                System.err.println("O ano deve ser de 4 dígitos.");
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual a sinopse? ");
            sinopse = console.nextLine();
            if(sinopse.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("A sinopse deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual o streaming? ");
            streaming = console.nextLine();
            if(streaming.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("O streaming deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual a nota (0 a 10)? ");
            if (console.hasNextInt()) {
                nota = console.nextInt();
                if(0 <= nota && nota <= 10)
                    dadosCorretos = true;
            }else{
                System.err.println("A nota deve ser entre 0 e 10.");
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Quais são os criadores (digite FIM para parar)? ");
            criador = console.nextLine();
            if(criador.equals("FIM") && criadores.size() > 0){
                dadosCorretos =  true;
            } else {
                if (criadores.size() == 0) {
                    System.err.println("A Série deve conter pelo menos um criador");
                } else if(criador.length()>=4){
                    criadores.add(criador);
                } else {
                    System.err.println("O criador deve ter no mínimo 4 caracteres.");
                }
            }
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual o gênero? ");
            genero = console.nextLine();
            if(genero.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("O gênero deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

       return new Serie(nome, anoLancamento, sinopse, streaming, nota, criadores, genero);
    }

    public void escolhasBusca(){
        int opcao;
        System.out.println("Como deseja fazer a busca? ");
        System.out.println("1 - Buscar por nome");
        System.out.println("2 - Buscar por ID");
        System.out.println("0 - Sair");

        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch(NumberFormatException e) {
            opcao = -1;
        }

        switch(opcao){
            case 1:
                buscarSerieNome();
                break;
            case 2: 
                buscarSerieID();
                break;
            default:
                break;
        }

    }

    public Serie buscarUmaSerie(){
        int opcao;
        Serie s = null;
        List<Serie> series;
        boolean dadosCorretos;
        System.out.println("Como deseja fazer a busca? ");
        System.out.println("1 - Buscar por nome");
        System.out.println("2 - Buscar por ID");
        System.out.println("0 - Sair");

        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch(NumberFormatException e) {
            opcao = -1;
        }

        switch(opcao){
            case 1:
                series = buscarSerieNome();
                dadosCorretos = false;
                if (series.size() > 1){
                    opcao = 0;
                    do{
                        System.out.println("Escolha uma Série: ");
                        int n = 0;
                        for(Serie l : series) {
                            System.out.println((n++)+" - "+l.getNome());
                        }
                        try {
                            opcao = Integer.valueOf(console.nextLine());
                        } catch(NumberFormatException e) {
                            opcao = -1;
                        }

                        if(0 <= opcao && opcao <= series.size()){
                            s = series.get(opcao);
                            mostraSerie(s);
                            dadosCorretos = true;
                        } else {
                            System.out.println("Série não está presente na lista");
                        }
                    } while(!dadosCorretos);
                   
                } else {
                    s = series.get(0);
                }
                break;
            case 2: 
                s = buscarSerieID();
                break;
            default:
                break;
        }

        return s;

    }

    public Serie buscarSerieID() {
        System.out.println("\nBusca de Serie por ID");
        int id = -1;
        boolean dadosCorretos = false;

        do {
            System.out.print("\nID: ");
            if (console.hasNextInt()) {
                id = console.nextInt();
                if(id > 0)
                    dadosCorretos = true;
            }else{
                System.err.println("O ID deve ser maior que 0");
            }
            console.nextLine();
        } while (!dadosCorretos);

        try {
            Serie serie = controle.buscarSerie(id);  // Chama o método de leitura da classe Arquivo
            if (serie != null) {
                mostraSerie(serie);  // Exibe os detalhes do Serie encontrado
                return serie;
            } else {
                System.out.println("Série não encontrada.");
                return null;
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar a Série!");
            e.printStackTrace();
            return null;
        }
    }   

    public List<Serie> buscarSerieNome() {
        System.out.println("\nBusca de série por nome");
        System.out.print("\nNome: ");
        String nome = console.nextLine();  // Lê o título digitado pelo usuário
        List<Serie> series = new ArrayList<>();
        if(nome.isEmpty())
            return series; 
        try {
            series = controle.buscarSerie(nome);  // Chama o método de leitura da classe Arquivo
            if (series.size()>0) {
                return series;
            } else {
                System.out.println("Nenhuma Série encontrado.");
                return series;
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar as Séries!");
            e.printStackTrace();
        }
    }   


    public void incluirSerie() {
        System.out.println("\nInclusão de Serie");
        Serie s = lerSerie();
        System.out.print("\nConfirma a inclusão da Serie? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                controle.incluirSerie(s);
                System.out.println("Serie incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir a Serie!");
            }
        }
    }

    public void alterarSerie() {
        System.out.println("\nAlteração de Serie");
        try {
            // Tenta ler o Serie com o ID fornecido
            Serie s = buscarUmaSerie();
            if (s != null) {
                mostraSerie(s);  // Exibe os dados do Serie para confirmação
                Serie nova = lerSerie();
                nova.setID(s.getID());
                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = controle.alterarSerie(nova);
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
            System.out.println("Erro do sistema. Não foi possível alterar a Série!");
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
                mostraSerie(s);  // Exibe os dados do Serie para confirmação

                System.out.print("\nConfirma a exclusão do Serie? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = controle.excluirSerie(s.getID());  // Chama o método de exclusão no arquivo
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
            System.out.println("Erro do sistema. Não foi possível excluir o Serie!");
            e.printStackTrace();
        }
    }

    public void mostraSerie(Serie serie) {
        if (serie != null) {
            System.out.println(serie);
        }
    }

    public void povoar() throws Exception {
        System.out.println("Função povoar em construção..."); 
       // controle.povoar();
    }

    public void buscarEpisodios() {
        System.out.println("Função Buscar Episódios em construção...");
    }

}
