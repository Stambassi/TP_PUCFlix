package controle;

import modelo.ArquivoSerie;
import entidades.Serie;
import entidades.Episodio;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ControleSerie {
    private ArquivoSerie arqSerie;

    public ControleSerie() throws Exception {
        this.arqSerie = new ArquivoSerie();
    }

    public int incluirSerie(Serie s) throws Exception {
        if (s == null)
            throw new Exception ("Série nula!");

        int id = arqSerie.create(s);

        return id;
    }

    public boolean excluirSerie(int id) throws Exception {
        Serie s = arqSerie.read(id);

        if (! ControleEpisodio.verificarEpisodiosSerie(s.getID())) 
            throw new Exception ("Há episódios vinculados com essa série!");

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