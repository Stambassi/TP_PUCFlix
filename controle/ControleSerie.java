package controle;

import modelo.ArquivoSerie;
import entidades.Serie;
import entidades.Episodio;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ControleSerie {

    private ArquivoSerie baseSeries;

    // Construtor
    public ControleGerenciadorSeries() throws Exception {
        this.baseSeries = new ArquivoSerie();
    }

    // Adiciona uma nova série ao sistema
    public int registrarSerie(Serie nova) throws Exception {
        if (nova == null)
            throw new Exception("Série inválida!");

        int identificador = baseSeries.create(nova);
        return identificador;
    }

    // Remove uma série com base no ID, validando dependências
    public boolean removerSerie(int identificador) throws Exception {
        Serie serieLocalizada = baseSeries.read(identificador);

        if (ControleEpisodio.verificarEpisodiosSerie(serieLocalizada.getID()))
            throw new Exception("Impossível excluir: episódios ainda associados!");

        return baseSeries.delete(identificador);
    }

    // Atualiza informações de uma série existente
    public boolean atualizarSerie(Serie modificada) throws Exception {
        if (modificada == null)
            throw new Exception("Objeto de série está nulo!");

        return baseSeries.update(modificada);
    }

    // Consulta série por ID
    public Serie localizarSerie(int identificador) throws Exception {
        if (baseSeries == null)
            System.out.println("[ERRO]: Arquivo de série não inicializado");

        Serie encontrada = baseSeries.read(identificador);
        return encontrada;
    }

    // Retorna uma lista de séries cujo nome começa com o texto fornecido
    public List<Serie> buscarSeriesPorPrefixo(String nome) throws Exception {
        Serie[] encontradas = baseSeries.readNome(nome);
        return new ArrayList<>(Arrays.asList(encontradas));
    }

    // Retorna todos os episódios relacionados a uma série específica
    public List<Episodio> listarEpisodiosPorSerie(int identificador) throws Exception {
        Episodio[] eps = baseSeries.readEpisodios(identificador);
        return new ArrayList<>(Arrays.asList(eps));
    }

    // Lista episódios de uma temporada específica dentro de uma série
    public List<Episodio> filtrarEpisodiosPorTemporada(int idSerie, int temporada) throws Exception {
        Episodio[] eps = baseSeries.readEpisodios(idSerie);
        List<Episodio> listaEps = new ArrayList<>(Arrays.asList(eps));

        listaEps.removeIf(ep -> ep.getTemporada() != temporada);

        return listaEps;
    }

    // Valida existência de uma série pelo ID
    public static boolean serieExiste(int identificador) {
        boolean existe = false;

        try {
            ArquivoSerie verificador = new ArquivoSerie();
            existe = (verificador.read(identificador) != null);
        } catch (Exception e) {
            existe = false;
        }

        return existe;
    }

    // Preenche o sistema com séries de exemplo
        // Preenche o sistema com dados de exemplo para testes e desenvolvimento
        public void carregarDadosIniciais() {
            try {
                ArrayList<String> criadores = new ArrayList<>();
    
                criadores.add("Hwang Dong-hyuk");
                registrarSerie(new Serie("Round 6", 2021,
                    "Pessoas endividadas participam de um jogo mortal por dinheiro.",
                    "Netflix", 8, criadores,
                    "Drama/Thriller"));
    
                criadores.clear();
                criadores.add("Shonda Rhimes");
                registrarSerie(new Serie("Bridgerton", 2020,
                    "Família da elite londrina busca prestígio e romance na era Regencial.",
                    "Netflix", 7, criadores,
                    "Romance/Drama"));
    
                criadores.clear();
                criadores.add("Jon Favreau");
                registrarSerie(new Serie("The Mandalorian", 2019,
                    "Um caçador de recompensas navega pela galáxia após a queda do Império.",
                    "Disney+", 9, criadores,
                    "Ficção Científica"));
    
                criadores.clear();
                criadores.add("Jesse Armstrong");
                registrarSerie(new Serie("Succession", 2018,
                    "Família poderosa disputa controle de império de mídia.",
                    "HBO MAX", 9, criadores,
                    "Drama"));
    
                criadores.clear();
                criadores.add("Steven Knight");
                registrarSerie(new Serie("Peaky Blinders", 2013,
                    "Gangue familiar ascende ao poder na Inglaterra pós-guerra.",
                    "Netflix", 9, criadores,
                    "Drama/Crime"));
    
                criadores.clear();
                criadores.add("Eiichiro Oda");
                registrarSerie(new Serie("One Piece (Live Action)", 2023,
                    "Jovem pirata busca tesouro lendário para se tornar rei dos piratas.",
                    "Netflix", 8, criadores,
                    "Aventura/Anime"));
    
                System.out.println("\n[OK] Séries de teste inseridas com sucesso.\n");
    
            } catch (Exception ex) {
                System.err.println("[ERRO AO INSERIR DADOS DE TESTE]");
            }
        }

        public boolean excluirSerie(String nome) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'excluirSerie'");
        }    
}
