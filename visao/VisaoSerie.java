package visao;

import java.util.Scanner;
import controle.ControleSerie;
import entidades.Serie;

/**
 * Classe VisaoSerie: Responsável pela interação com o usuário para operações com séries.
 */
public class VisaoSerie {

    private static Scanner entrada = new Scanner(System.in);
    private ControleSerie gerenciadorSerie;

    /**
     * Construtor: Inicializa o controlador de séries.
     */
    public VisaoSerie() {
        gerenciadorSerie = new ControleSerie();
    }

    /**
     * Método principal para execução da interface textual.
     */
    public void iniciar() {
        int escolha;
        do {
            escolha = exibirMenu();
            switch (escolha) {
                case 1 -> incluirNovaSerie();
                case 2 -> removerSeriePorID();
                case 3 -> removerSeriePorNome();
                case 4 -> modificarSerie();
                case 5 -> procurarSeriePorID();
                case 6 -> procurarSeriePorNome();
                case 7 -> buscarTodosEpisodios();
                case 8 -> buscarEpisodiosPorTemporada();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 0);
    }

    /**
     * Exibe o menu e retorna a opção escolhida pelo usuário.
     * @return (int): opção selecionada
     */
    private int exibirMenu() {
        System.out.println("\nPUCFLIX - Sistema de Séries");
        System.out.println("----------------------------");
        System.out.println("1 - Adicionar série");
        System.out.println("2 - Remover por ID");
        System.out.println("3 - Remover por nome");
        System.out.println("4 - Editar série");
        System.out.println("5 - Localizar por ID");
        System.out.println("6 - Localizar por nome");
        System.out.println("7 - Listar episódios da série");
        System.out.println("8 - Episódios por temporada");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
        return Integer.parseInt(entrada.nextLine());
    }

    /**
     * Realiza o cadastro de uma nova série.
     */
    private void incluirNovaSerie() {
        try {
            Serie nova = ((Object) gerenciadorSerie).criarSerieViaEntrada(entrada);
            gerenciadorSerie.registrarSerie(nova);
            System.out.println("Série cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    /**
     * Remove uma série com base no ID informado.
     */
    private void removerSeriePorID() {
        System.out.print("Informe o ID da série: ");
        int id = Integer.parseInt(entrada.nextLine());
        if (gerenciadorSerie.excluirSerie(id))
            System.out.println("Série excluída.");
        else
            System.out.println("Série não encontrada.");
    }

    /**
     * Remove uma série com base no nome informado.
     */
    private void removerSeriePorNome() {
        System.out.print("Digite o nome da série: ");
        String nome = entrada.nextLine();
        if (gerenciadorSerie.excluirSerie(nome))
            System.out.println("Série removida.");
        else
            System.out.println("Série não localizada.");
    }

    /**
     * Permite alterar os dados de uma série existente.
     */
    private void modificarSerie() {
        try {
            Serie modificada = ((Object) gerenciadorSerie).criarSerieViaEntrada(entrada);
            if (gerenciadorSerie.atualizarSerie(modificada))
                System.out.println("Série atualizada com sucesso.");
            else
                System.out.println("Falha na atualização.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Exibe os dados de uma série com base no ID.
     */
    private void procurarSeriePorID() {
        System.out.print("Informe o ID da série: ");
        int id = Integer.parseInt(entrada.nextLine());
        Serie s = (Serie) gerenciadorSerie.buscarSeriesPorPrefixo(id);
        if (s != null)
            System.out.println(s);
        else
            System.out.println("Série não encontrada.");
    }

    /**
     * Exibe os dados de uma série com base no nome.
     */
    private void procurarSeriePorNome() {
        System.out.print("Nome da série: ");
        String nome = entrada.nextLine();
        Serie s = gerenciadorSerie.buscarSeriesPorPrefixo(nome);
        if (s != null)
            System.out.println(s);
        else
            System.out.println("Série não localizada.");
    }

    /**
     * Lista todos os episódios de uma determinada série.
     */
    private void buscarTodosEpisodios() {
        System.out.print("Informe o ID da série: ");
        int id = Integer.parseInt(entrada.nextLine());
        gerenciadorSerie.listarEpisodiosPorSerie(id);
    }

    /**
     * Lista os episódios de uma série por temporada.
     */
    private void buscarEpisodiosPorTemporada() {
        System.out.print("ID da série: ");
        int id = Integer.parseInt(entrada.nextLine());
        System.out.print("Número da temporada: ");
        int temporada = Integer.parseInt(entrada.nextLine());
        gerenciadorSerie.filtrarEpisodiosPorTemporada(id, temporada);
    }
}
