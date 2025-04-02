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
import modelo.ArquivoEpisodio;
import controle.ControleEpisodio;

public class VisaoEpisodio {
    
    ControleEpisodio controle;
    Serie serie;
    int temp;
    private static Scanner console = new Scanner(System.in);

    public VisaoEpisodio(Serie s) throws Exception {
        controle = new ControleEpisodio(s);
        serie = s;
        temp = 0;
    }

    public void menu() {

        int opcao;
        if(serie == null){
            System.out.println("ERRO: Série não encontrada");
        } else {
            do {
                System.out.println("\n\nPUCFlix v");
               System.out.println("--------------------------");
               System.out.println("> Início > Episódios > "+serie.getNome());
               System.out.println("\n1 - Incluir");
               System.out.println("2 - Excluir");
               System.out.println("3 - Alterar");
               System.out.println("4 - Buscar");
               System.out.println("5 - Escolher Temporada");
               System.out.println("0 - Sair");
   
               System.out.print("\nOpção: ");
               try {
                   opcao = Integer.valueOf(console.nextLine());
               } catch(NumberFormatException e) {
                   opcao = -1;
               }
   
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
                       System.out.println("Opção inválida!");
                       break;
               }
   
           } while (opcao != 0);
        }
       
    }

    public void menuTemporada() {

        int opcao;
        if(serie == null){
            System.out.println("ERRO: Série não encontrada");
        } else {
            do {
                System.out.println("\n\nPUCFlix v");
               System.out.println("--------------------------");
               System.out.println("> Início > Episódios > "+serie.getNome()+" > Temporada: "+this.temp);
               System.out.println("\n1 - Incluir");
               System.out.println("2 - Buscar");
               System.out.println("3 - Excluir");
               System.out.println("0 - Sair");
   
               System.out.print("\nOpção: ");
               try {
                   opcao = Integer.valueOf(console.nextLine());
               } catch(NumberFormatException e) {
                   opcao = -1;
               }
   
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
                       System.out.println("Opção inválida!");
                       break;
               }
   
           } while (opcao != 0);
        }
       
    }



    public Episodio lerEpisodio(){
        String nome;
        int IDSerie = serie.getID();
        int temporada;
        LocalDate dataLancamento;
        float duracao; // em minutos
        int nota; // 0 a 10
        List<String> diretores = new ArrayList<String>();
        String diretor;
        boolean dadosCorretos = false;
        String regex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(regex);

        do {
            System.out.print("Qual o nome do Episódio? ");
            nome = console.nextLine();
            if(nome.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("O nome deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

        if (this.temp > 0){
            temporada = this.temp;
        } else {
            dadosCorretos = false;
            do {
                System.out.print("Qual a Temporada? ");
                if (console.hasNextInt()) {
                    temporada = console.nextInt();
                    if(0 < temporada && temporada <= 127)
                        dadosCorretos = true;
                }else{
                    System.err.println("A Temporada deve ser entre 1 e 127.");
                }
                console.nextLine();
            } while(!dadosCorretos);
        }
        

        
        do {
            System.out.print("Qual a data de lançamento (yyyy-MM-dd)? ");
            String data = console.nextLine();
            Matcher matcher = pattern.matcher(data);

            if(matcher.matches()){
                dadosCorretos = true;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dataLancamento = LocalDate.parse(data, formatter);
            }
            else
                System.err.println("O formato deve ser (yyyy-MM-dd).");
        } while(!dadosCorretos);


        dadosCorretos = false;
        do {
            System.out.print("Qual a duração (minutos)? ");
            if (console.hasNextInt()) {
                duracao = console.nextFloat();
                if(0 < duracao)
                    dadosCorretos = true;
            }else{
                System.err.println("O episódio deve ter mais que 0 minutos");
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Qual a duração (minutos)? ");
            if (console.hasNextInt()) {
                duracao = console.nextFloat();
                if(0 < duracao)
                    dadosCorretos = true;
            }else{
                System.err.println("O episódio deve ter mais que 0 minutos");
            }
            console.nextLine();
        } while(!dadosCorretos);
        

        dadosCorretos = false;
        do {
            System.out.print("Quais são os diretores (digite FIM para parar)? ");
            diretor = console.nextLine();
            if(diretor.equals("FIM") && diretores.size() > 0){
                dadosCorretos =  true;
            } else {
                if (diretores.size() == 0) {
                    System.err.println("A Série deve conter pelo menos um criador");
                } else if(diretores.size() >=4){
                    diretores.add(diretor);
                } else {
                    System.err.println("O criador deve ter no mínimo 4 caracteres.");
                }
            }
        } while(!dadosCorretos);

       return new Episodio(IDSerie,nome,temporada, dataLancamento, duracao, nota, diretores);
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
                buscarEpisodioNome();
                break;
            case 2: 
                buscarEpisodioID();
                break;
            default:
                break;
        }

    }

    public Episodio buscarUmEpisodio(){
        int opcao;
        Episodio ep = null;
        List<Episodio> episodios;
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
                episodios = buscarEpisodioNome();
                dadosCorretos = false;
                if (episodios.size() > 1){
                    opcao = 0;
                    do{
                        System.out.println("Escolha um Episódio: ");
                        int n = 0;
                        for(Episodio l : episodios) {
                            System.out.println((n++)+" - "+l.getNome());
                        }
                        try {
                            opcao = Integer.valueOf(console.nextLine());
                        } catch(NumberFormatException e) {
                            opcao = -1;
                        }

                        if(0 <= opcao && opcao <= episodios.size()){
                            ep = episodios.get(opcao);
                            mostraEpisodio(ep);
                            dadosCorretos = true;
                        } else {
                            System.out.println("Episódio não está presente na lista");
                        }
                    } while(!dadosCorretos);
                   
                } else {
                    ep = episodios.get(0);
                }
                break;
            case 2: 
                ep = buscarEpisodioID();
                break;
            default:
                break;
        }

        return ep;

    }

    public Episodio buscarEpisodioID() {
        System.out.println("\nBusca de Episódio por ID");
        int id = 0;
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
            Episodio Episodio = controle.buscarEpisodio(id);  // Chama o método de leitura da classe Arquivo
            if (Episodio != null) {
                mostraEpisodio(Episodio);  // Exibe os detalhes do Episodio encontrado
                return Episodio;
            } else {
                System.out.println("Episódio não encontrado.");
                return null;
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar o Episódio!");
            e.printStackTrace();
            return null;
        }
    }   

    public List<Episodio> buscarEpisodioNome() {
        System.out.println("\nBusca de episódio por nome");
        System.out.print("\nNome: ");
        String nome = console.nextLine();  // Lê o título digitado pelo usuário
        List<Episodio> episodios = null;
        if(nome.isEmpty())
            return episodios; 

        try {
            episodios = controle.buscarEpisodio(nome);  // Chama o método de leitura da classe Arquivo
            if (episodios.size()>0) {
                return episodios;
            } else {
                System.out.println("Nenhum Episódio encontrado.");
                return null;
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar os Episódios!");
            e.printStackTrace();
            return null;
        }
    }   


    public void incluirEpisodio() {
        if(this.temp > 0){
            System.out.println("\nInclusão de Episódio - "+this.temp+" Temporada");
        } else {
            System.out.println("\nInclusão de Episodio");
        }
        Episodio ep = lerEpisodio();
        System.out.print("\nConfirma a inclusão do Episodio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                controle.incluirEpisodio(ep);
                System.out.println("Episódio incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o Episódio!");
            }
        }
        
        
    }

    public void alterarEpisodio() {
        System.out.println("\nAlteração de Episodio");
        try {
            // Tenta ler o Episodio com o ID fornecido
            Episodio ep = buscarUmEpisodio();
            if (ep != null) {
                mostraEpisodio(ep);  // Exibe os dados do Episodio para confirmação
                Episodio novo = lerEpisodio();
                novo.setID(ep.getID());
                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = controle.alterarEpisodio(novo);
                    if (alterado) {
                        System.out.println("Episódio alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o Episódio.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
                 console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Episódio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o Episódio!");
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
                mostraEpisodio(ep);  // Exibe os dados do Episodio para confirmação

                System.out.print("\nConfirma a exclusão do Episódio? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = controle.excluirEpisodio(ep);  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Episódio excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o Episódio.");
                    }
                    
                } else {
                    System.out.println("Exclusão cancelada.");
                }
                console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Episódio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o Episódio!");
            e.printStackTrace();
        }
    }

    public void mostraEpisodio(Episodio Episodio) {
        if (Episodio != null) {
            System.out.println(Episodio);
        }
    }

    public void entrarTemporada(){
        int temporada = 0;
        boolean dadosCorretos = false;
        do {
            System.out.print("Qual a Temporada? ");
            if (console.hasNextInt()) {
                temporada = console.nextInt();
                if(0 < temporada && temporada <= 127)
                    dadosCorretos = true;
            }else{
                System.err.println("A Temporada deve ser entre 1 e 127.");
            }
            console.nextLine();
        } while(!dadosCorretos);
        try{
            controle.buscarEpisodioTemporada(temporada);
            this.temp = temporada;
            menuTemporada();
        } catch (Exception e){
            System.out.println("Temporada não existe na Série ("+serie.getNome()+")");
        }
    }

    public void povoar() throws Exception {
        System.out.println("Função povoar em construção..."); 
    //    controle.povoar();
    }

}
