import java.util.Scanner;
import visao.*;
import entidades.*;

public class Principal {
    static String versao = "1.0";
    static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            int opcao;
            do {

                System.out.println("\n\nPUCFlix v"+versao);
                System.out.println( "-----------");
                System.out.println("> Início");
                System.out.println("\n1 - Série");
                System.out.println("2 - Episódio");
                System.out.println("9 - Povoar dados");
                System.out.println("0 - Sair");

                System.out.print("\nOpção: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                } catch(NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        (new VisaoSerie()).menu();
                        break;
                    case 2:
                        episodioEntrarSerie();
                        break;
                    case 9:
                        povoar();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } while (opcao != 0);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void episodioEntrarSerie(){
        int opcao;
        do {
            System.out.println("\n\nPUCFlix v"+versao);
            System.out.println("--------------------------");
            System.out.println("> Início > Episódios");
            System.out.println("\n1 - Escolher Série");
            System.out.println("0 - Sair");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    try{
                        Serie s = (new VisaoSerie()).buscarUmaSerie();
                        (new VisaoEpisodio(s)).menu();
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }
    public static void povoar(){
        try{
            // (new VisaoEpisodio(null)).povoar();
            (new VisaoSerie()).povoar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


