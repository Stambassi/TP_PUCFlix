import modelo.ArquivoEpisodio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

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

        if (e == null) 
            throw new Exception ("Episódio inválido!");

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

        List<Episodio> episodios = arqSerie.readEpisodios(serie.getID());

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

    public List<Episodio> buscarEpisodioTemporada(int temp) {
        ArquivoSerie arqSerie = new ArquivoSerie();

        List<Episodio> episodios = arqSerie.readEpisodios(serie.getID());

        int i = 0;
        for (Episodio episodio : episodios) {
            if (episodio.getIDSerie() != serie.getID())
                episodios.remove(i);
            i++;
        }

        return episodios;        
    }
}

/*

#### Atributos

+ ArquivoEpisodio arqEpisodio
+ Serie serie

#### Funções

+ Construtor: Pede uma Série válida como parâmetro

+ incluirEpisodio(Episodio e): Função para insirir Episódio e utilizando os métodos de ArquivoEpisodio 

+ excluirEpisodio(Episodio e): Função para excluir Episodio por ID. Testar se o episódio é válido para remoção(existe no bd e o id pertence a série)

+ excluirEpisodio(Episodio e, int temp): Função para excluir Episodio por ID e uma temporada. Testar se o episódio é válido para remoção(existe no bd, pertence a série e está na temporada especificada)

+ alterarEpisodio(Episodio e): Função para alterar algum valor da Episodio.

+ buscarEpisodio(): Função que retorna todos os episódios da série

+ buscarEpisodio(int id): Função que busca um objeto Episódio pelo ID e retorna caso esteja na série.

+ buscarEpisodio(String entrada): Função que le um nome e retorna um episódio que contém a sequência inserida que está na série especificada. Pode receber mais de um objeto da funcao do arqEpisodio.

+ buscarEpisodioTemporada(int temp): Função que retorna uma lista de episódios que estão na sérieAtual e presentes na temporada temp.

+ buscarEpisodio(int id, int temp): Função que busca um objeto Episódio pelo ID e retorna caso esteja na série e na temporada.

+ buscarEpisodio(String entrada, int temp): Função que le um nome e retorna um episódio que contém a sequência inserida que está na série e na temporada especificada. Pode receber mais de um objeto da funcao do arqEpisodio.

+ verificarEpisodiosSerie( ): Função estática que, com um ID de Série, retorna verdadeiro ou falso se tiver um ou mais episódios atrelados a essa série.

+ povoar( ): Primeiro carregamento de dados para o sistema.

*/