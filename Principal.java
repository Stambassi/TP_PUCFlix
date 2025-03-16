import java.util.Scanner;
import visao.*;

public class Principal {

    public static void main(String[] args) {
        Scanner console;
        String versao;
        try {
            versao = "1.0"
            console = new Scanner(System.in);
            int opcao;
            do {

                System.out.println("\n\ nPUCFlix v"+versao);
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
                        (new VisaoEpisodio()).menu();
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

    public static void povoar(){
        (new VisaoSerie()).povoar()
        (new VisaoEpisodio()).povoar()
    }

}


