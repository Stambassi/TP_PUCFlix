package controle;

import modelo.ArquivoEpisodio;
import modelo.ArquivoSerie;
import entidades.Serie;
import entidades.Episodio;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class ControleEpisodio {
    private ArquivoEpisodio arqEpisodio;
    private Serie serie;

    public ControleEpisodio() throws Exception{
        this.arqEpisodio = new ArquivoEpisodio();
    }
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
        if ( this.serie != null &&  e.getIDSerie() != this.serie.getID() ) 
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
     * @param temporada - Temporada da Série a qual o Episódio pertence
     * @return boolean - True se bem sucedido, False caso contrário
     */
    public boolean excluirEpisodio(Episodio e, int temporada) throws Exception {
        // Testar se o objeto Episódio é válido
        if (e == null) 
            throw new Exception ("Episódio nulo!");

        // Testar se o Episódio pertence à Série da instância atual
        if ( e.getIDSerie() != this.serie.getID() ) 
            throw new Exception ("Episódio não pertence à Série atual!");

        // Testar se o Episódio pertence à temporada correpondente
        if (e.getTemporada() != temporada)
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
        // Ler o Episódio a partir do ArquivoEpisodio usando seu id
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
     * @param temporada - Temporada dos Episódios a serem buscados
     * @return episodios - Lista com os Episódios pertencentes à temporada especificada da Série atual
     */
    public List<Episodio> buscarEpisodioTemporada(int temporada) throws Exception {
        // Definir instância do ArquivoSerie
        ArquivoSerie arqSerie = new ArquivoSerie();

        // Ler todos os Episódios da Série atual a partir do ArquivoSerie
        Episodio[] arrayEpisodios = arqSerie.readEpisodios(serie.getID());

        //Converter Episodio[] para List<Episodio>
        List<Episodio> episodios = new ArrayList<Episodio>( Arrays.asList(arrayEpisodios) );

        // Iterar sobre todos os Episódios encontrados e filtrar apenas os que pertencem à temporada
        int i = 0;
        for (Episodio episodio : episodios) {
            // Testar se o Episódio pertence à temporada especificada
            if (episodio.getTemporada() != temporada)
                episodios.remove(i);
            i++;
        }

        // Retornar os Episódios que pertencem à temporada da Série atual
        return episodios;        
    }

    /*
     * buscarEpisodio - Função para buscar um objeto Episódio pelo ID de uma temporada específica da Série atual 
     * @param id - ID do Episódio a ser buscado
     * @param temporada - Temporada da Série atual
     * @return e - Episódio buscado de uma temporada específica da Série atual
     */
    public Episodio buscarEpisodio(int id, int temporada) throws Exception {
        // ler o episódio a partir do arquivoepisódio usando seu id
        Episodio e = arqEpisodio.read(id);

        // Testar se o Episódio pertence à Série da instância atual
        if ( e.getIDSerie() != this.serie.getID() ) 
            throw new Exception ("Episódio não pertence à Série atual!");

        // Testar se o Episódio pertence à temporada da Série da instância atual
        if (e.getTemporada() != temporada)
            throw new Exception ("Episódio não pertence à temporada!");

        // Retornar Episódio encontrada
        return e;
    }

    /*
     * verificarEpisodiosSerie - Função estática para verificar se existem episódios de uma Série a partir de seu ID
     * @param IDSerie - ID da Série a ser testada
     * @return resposta - True se existir algum Episódio da Série atual, False caso contrário
     */
    public static boolean verificarEpisodiosSerie(int IDSerie) {
        // Definir variável de resposta
        boolean resposta;
        
        // Iniciar bloco try-catch
        try {
        // Definir instância do ArquivoSerie
            ArquivoSerie arqSerie = new ArquivoSerie();

        // Testar se há algum episódio na Série encontrada
            if (arqSerie.readEpisodios(IDSerie) != null)
                resposta = true;
            else
                resposta = false;
        } catch (Exception e) {
            resposta = false;
        }

        // Retornar
        return resposta;
    }

    /*
     * povoar - Função para inicalizar o Banco de Dados com Episódios amostrais
     */
    public void povoar(){
        // Inicar bloco try-catch
        try {
            // Iniciar ArrayList de diretores, pois para inserir é precisso passar um ArrayList como parâmetro
            ArrayList<String> diretores = new ArrayList<String>();

            // Inserir Episódio
            diretores.add("Takahiro Ikezoe");
            diretores.add("Yasuhiro Irie");
            incluirEpisodio(new Episodio(this.serie.getID(), "Hagane no renkinjutsushi", 1, LocalDate.of(2009, 04, 05), 24.0F, 7, diretores));

            // Inserir Episódio
            diretores.clear();
            diretores.add("Hiromu Arakawa");
            diretores.add("Hiroshi Ônogi");
            incluirEpisodio(new Episodio(this.serie.getID(), "Hajimari no hi", 1, LocalDate.of(2009, 04, 12), 24.0F, 8, diretores));

            // Inserir Episódio
            diretores.clear();
            diretores.add("Yasuhiro Irie");
            diretores.add("Masao Ôkubo");
            incluirEpisodio(new Episodio(this.serie.getID(), "Jakyô no machi", 1, LocalDate.of(2009, 04, 19), 24.0F, 7, diretores));

            System.out.println("\nEpisódios povoados!");

        } catch (Exception e){
            System.err.println("[ERRO]: " + e.getMessage());
            e.printStackTrace();
        }
    }

}