package controle;

import modelo.ArquivoSerie;
import entidades.Serie;
import entidades.Episodio;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ControleSerie {
    private ArquivoSerie arqSerie;

    /*
     * Construtor da classe ControleSerie
     */
    public ControleSerie() throws Exception {
        this.arqSerie = new ArquivoSerie();
    }

    /*
     * incluirSerie - Função para incluir uma Série no Banco de Dados
     * @param s - Objeto da Série a ser incluído
     * @return id - ID da Série incluída
     */
    public int incluirSerie(Serie s) throws Exception {
        // Testar o objeto da Série a ser inserido
        if (s == null)
            throw new Exception ("Série nula!");

        // Criar a Série a partir do ArquivoSerie
        int id = arqSerie.create(s);

        // Retornar ID
        return id;
    }

    /*
     * excluirSerie - Função para excluir uma Série a partir do seu ID
     * @param id  ID da Série a ser excluída
     * @return boolean - True se sucedido, False se contrário
     */
    public boolean excluirSerie(int id) throws Exception {
        // Buscar a Série com o ID especificado
        Serie s = arqSerie.read(id);

        // Verificar se a Série possui Episódios vinculados à ela
        if (! ControleEpisodio.verificarEpisodiosSerie(s.getID())) 
            throw new Exception ("Há episódios vinculados com essa série!");

        // Exlcuir a Série a partir do ArquivoSerie e retornar o seu status
        return arqSerie.delete(id);
    }

    public Serie buscarSerie(int id) throws Exception {
        Serie s = arqSerie.read(id);

        return s;
    }

    public List<Serie> buscarSerie(String entrada) throws Exception {
        Serie[] arraySeries = arqSerie.readNome(entrada);
        List<Serie> series = new ArrayList<Serie>( Arrays.asList(arraySeries) );

        return series;
    }

    public List<Episodio> buscarSerieEpisodios(int id) throws Exception {
        Episodio[] arrayEpisodios = arqSerie.readEpisodios(id); 
        List<Episodio> episodios = new ArrayList<Episodio>( Arrays.asList(arrayEpisodios) );

        return episodios;
    }

    public List<Episodio> buscarSerieEpisodiosPorTemporada(int id, int temporada) throws Exception {
        Episodio[] arrayEpisodios = arqSerie.readEpisodios(id); 
        List<Episodio> episodios = new ArrayList<Episodio>( Arrays.asList(arrayEpisodios) );

        int i = 0;
        for (Episodio e : episodios) {
            if (e.getTemporada() != temporada)
                episodios.remove(i);
            i++;
        }

        return episodios;
    }

    public static boolean validarSerie(int id) {
        boolean resposta;

        try {
            ArquivoSerie arqSerie = new ArquivoSerie();
            if (arqSerie.read(id) != null)
                resposta = true;   
            else
                resposta = false;
        } catch (Exception e) {
            resposta = false;
        }

        return resposta;
    }
}  