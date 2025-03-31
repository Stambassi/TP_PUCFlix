package controle;

import modelo.ArquivoEpisodio;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import modelo.ArquivoSerie;

import entidades.Serie;
import entidades.Episodio;

public class ControleEpisodio {
    private ArquivoEpisodio arqEpisodio;
    private Serie serie;

    public ControleEpisodio(Serie serie) throws Exception {
        if (serie == null)
            throw new Exception ("Série nula!");
            
        if (!ControleSerie.validarSerie(serie.getID()))
            throw new Exception ("Série inválida!");

        this.arqEpisodio = new ArquivoEpisodio();
        this.serie = serie;
    }

    public int incluirEpisodio(Episodio e) throws Exception {
        if (e == null)
            throw new Exception ("Episódio nulo!");

        int id = arqEpisodio.create(e);

        return id;
    }

    public boolean excluirEpisodio(int id) throws Exception {
        Episodio e = arqEpisodio.read(id);

        if ( !ControleSerie.validarSerie(e.getIDSerie()) ) 
            throw new Exception ("Série do episódio inválida!");

        return arqEpisodio.delete(id);
    }

    public boolean excluirEpisodio(Episodio e) throws Exception {
        if (e == null) 
            throw new Exception ("Episódio nulo!");

        if ( !ControleSerie.validarSerie(e.getIDSerie()) )
            throw new Exception ("Série do episódio inválida!");

        return arqEpisodio.delete(e.getID());
    }

    public boolean excluirEpisodio(Episodio e, int temp) throws Exception {
        if (e == null) 
            throw new Exception ("Episódio nulo!");

        if ( !ControleSerie.validarSerie(e.getIDSerie()) )
            throw new Exception ("Série do episódio inválida!");

        if (e.getTemporada() != temp)
            throw new Exception ("Episódio não pertence à temporada!");

        return arqEpisodio.delete(e.getID());
    }

    public boolean alterarEpisodio(Episodio e) throws Exception {
        if (e == null) 
            throw new Exception ("Episódio nulo!");

        if ( !ControleSerie.validarSerie(e.getIDSerie()) )
            throw new Exception ("Série do episódio inválida!");

        return arqEpisodio.update(e);
    }

    public List<Episodio> buscarEpisodio() throws Exception {
        ArquivoSerie arqSerie = new ArquivoSerie();

        //Converter Episodio[] para List<Episodio>
        Episodio[] arrayEpisodios = arqSerie.readEpisodios(serie.getID());
        List<Episodio> episodios = new ArrayList<Episodio>( Arrays.asList(arrayEpisodios) );

        return episodios;
    }

    public Episodio buscarEpisodio(int id) throws Exception {
        Episodio e = arqEpisodio.read(id);

        if (e.getIDSerie() != serie.getID())
            throw new Exception("Episódio não pertence à série!");

        return e;
    }

    public List<Episodio> buscarEpisodio(String entrada) throws Exception {
        Episodio episodios[] = arqEpisodio.readNome(entrada);
        List<Episodio> episodiosValidos = new ArrayList<Episodio>();

        for (Episodio episodio : episodios) {
            if (episodio.getIDSerie() == serie.getID())
                episodiosValidos.add(episodio);
        }

        
        return episodiosValidos;
    }

    public List<Episodio> buscarEpisodioTemporada(int temp) throws Exception {
        ArquivoSerie arqSerie = new ArquivoSerie();

        //Converter Episodio[] para List<Episodio>
        Episodio[] arrayEpisodios = arqSerie.readEpisodios(serie.getID());
        List<Episodio> episodios = new ArrayList<Episodio>( Arrays.asList(arrayEpisodios) );

        int i = 0;
        for (Episodio episodio : episodios) {
            if (episodio.getIDSerie() != serie.getID())
                episodios.remove(i);
            i++;
        }

        return episodios;        
    }

    public Episodio buscarEpisodio(int id, int temporada) throws Exception {
        Episodio e = arqEpisodio.read(id);

        if (e.getIDSerie() != this.serie.getID())
            throw new Exception ("Episódio não percente à série!");

        if (e.getTemporada() != temporada)
            throw new Exception ("Episódio não pertence à temporada!");

        return e;
    }

    public static boolean verificarEpisodiosSerie(int IDSerie) {
        boolean resposta;
        
        try {
            ArquivoSerie arqSerie = new ArquivoSerie();

            if (arqSerie.readEpisodios(IDSerie) != null)
                resposta = true;
            else
                resposta = false;
        } catch (Exception e) {
            resposta = false;
        }

        return resposta;
    }
}