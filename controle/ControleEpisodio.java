package controle;

import modelo.ArquivoEpisodio;
import modelo.ArquivoSerie;
import entidades.Serie;
import entidades.Episodio;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class ControleEpisodio {
    private ArquivoEpisodio arqEpisodio;
    private Serie serie;

    /*
     * Construtor da classe ControleEpisodio
     * @param serie - Série referente à classe atual
     */
    public ControleEpisodio(Serie serie) throws Exception {
        // Testar se o objeto Série é válido
        if (serie == null)
            throw new Exception ("Série nula!");
           
        // Testar se o ID da Série é válido
        if (!ControleSerie.validarSerie(serie.getID()))
            throw new Exception ("Série inválida!");

        // Definir atributos da instância
        this.arqEpisodio = new ArquivoEpisodio();
        this.serie = serie;
    }

    /*
     * incluirEpisodio - Função para adicionar um Episódio ao Banco de Dados
     * @param e - Episódio a ser inserido
     * @param id - ID do Episódio inserido
     */
    public int incluirEpisodio(Episodio e) throws Exception {
        // Testar se o objeto Episódio é válido
        if (e == null)
            throw new Exception ("Episódio nulo!");

        // Testar se o Episódio pertence à Série da instância atual
        if ( e.getIDSerie() != this.serie.getID() ) 
            throw new Exception ("Episódio não pertence à Série atual!");

        // Criar o Episódio a partir do ArquivoEpisódio
        int id = arqEpisodio.create(e);

        // Retornar
        return id;
    }

    /*
     * excluirEpisodio - Função para excluir um Episódio a partir do seu ID
     * @param id - ID do Episódio a ser excluído
     * @return boolean - True se bem sucedido, False caso contrário
     */
    public boolean excluirEpisodio(int id) throws Exception {
        // Ler o Episódio a partir do ArquivoEpisódio usando seu ID
        Episodio e = arqEpisodio.read(id);

        // Testar se o Episódio pertence à Série da instância atual
        if ( e.getIDSerie() != this.serie.getID() ) 
            throw new Exception ("Episódio não pertence à Série atual!");

        // Excluir o Episódio a partir do ArquivoEpisódio e retornar o seu status
        return arqEpisodio.delete(id);
    }

    /*
     * excluirEpisodio - Função para excluir um Episódio a partir do seu objeto
     * @param e - Episódio a ser excluído
     * @return boolean - True se bem sucedido, False caso contrário
     */
    public boolean excluirEpisodio(Episodio e) throws Exception {
        // Testar se o objeto Episódio é válido
        if (e == null) 
            throw new Exception ("Episódio nulo!");

        // Testar se o Episódio pertence à Série da instância atual
        if ( e.getIDSerie() != this.serie.getID() ) 
            throw new Exception ("Episódio não pertence à Série atual!");

        // Excluir o Episódio a partir do ArquivoEpisodio e retornar o seu status
        return arqEpisodio.delete(e.getID());
    }

    /*
     * excluirEpisodio - Função para excluir o Episódio de uma determinada temporada
     * @param e - Episódio a ser excluído
     * @param temp - Temporada da Série a qual o Episódio pertence
     * @return boolean - True se bem sucedido, False caso contrário
     */
    public boolean excluirEpisodio(Episodio e, int temp) throws Exception {
        // Testar se o objeto Episódio é válido
        if (e == null) 
            throw new Exception ("Episódio nulo!");

        // Testar se o Episódio pertence à Série da instância atual
        if ( e.getIDSerie() != this.serie.getID() ) 
            throw new Exception ("Episódio não pertence à Série atual!");

        // Testar se o Episódio pertence à temporada correpondente
        if (e.getTemporada() != temp)
            throw new Exception ("Episódio não pertence à temporada!");

        // Excluir o Episódio a partir do ArquivoEpisodio e retornar o seu status
        return arqEpisodio.delete(e.getID());
    }

    /*
     * alterarEpisodio - Função para alterar um Episódio
     * @param e - Objeto já alterado a ser inserido no Banco de Dados
     * @return boolean - True se bem sucedido, False caso contrário
     */
    public boolean alterarEpisodio(Episodio e) throws Exception {
        // Testar se o objeto Episódio é válido
        if (e == null) 
            throw new Exception ("Episódio nulo!");

        // Testar se o Episódio pertence à Série da instância atual
        if ( e.getIDSerie() != this.serie.getID() ) 
            throw new Exception ("Episódio não pertence à Série atual!");

        // Atualizar o Episódio a partir do ArquivoEpisodio e retornar o seu status
        return arqEpisodio.update(e);
    }

    /*
     * buscarEpisodio - Função para buscar todos os Episódios da Série da instância atual
     * @return episodios - Lista com todos os Epitódios pertencentes à Série atual
     */
    public List<Episodio> buscarEpisodio() throws Exception {
        // Definir instância do ArquivoSerie
        ArquivoSerie arqSerie = new ArquivoSerie();

        // Ler todos os Episódios da Série atual a partir do ArquivoSerie
        Episodio[] arrayEpisodios = arqSerie.readEpisodios(serie.getID());

        //Converter Episodio[] para List<Episodio>
        List<Episodio> episodios = new ArrayList<Episodio>( Arrays.asList(arrayEpisodios) );

        // Retornar lista de Episódios
        return episodios;
    }

    /*
     * buscarEpisodio - Função para buscar um Episódio a partir do seu ID
     * @param id - ID do Episódio a ser buscado
     * @return e - Objeto do Episódio buscado
     */
    public Episodio buscarEpisodio(int id) throws Exception {
        // Ler o Episódio a partir do ArquivoEpisódio usando seu ID
        Episodio e = arqEpisodio.read(id);

        // Testar se o Episódio pertence à Série da instância atual
        if ( e.getIDSerie() != this.serie.getID() ) 
            throw new Exception ("Episódio não pertence à Série atual!");

        // Retornar Episódio
        return e;
    }

    /*
     * buscarEpisodio - Função para buscar um ou mais episódios a partir de uma String
     * @param entrada - String a ser buscada
     * @return episodiosValidos - Lista dos Episódios encontrados que pertencem à Série atual
     */
    public List<Episodio> buscarEpisodio(String entrada) throws Exception {
        // Ler todos os Episódios que têm o nome iniciando com a String correspondente
        Episodio episodios[] = arqEpisodio.readNome(entrada);

        // Definir lista de Episódios
        List<Episodio> episodiosValidos = new ArrayList<Episodio>();

        // Iterar pela lista de Episódios
        for (Episodio episodio : episodios) {
            // Testar se o Episódio pertence à Série atual
            if (episodio.getIDSerie() == serie.getID())
                episodiosValidos.add(episodio); // Inserir na lista
        }

        // Retornar lista de Episódios válidos
        return episodiosValidos;
    }

    /*
     * buscarEpisodioTemporada - Função para buscar todos os Episódios de uma temporada da Série atual
     */
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