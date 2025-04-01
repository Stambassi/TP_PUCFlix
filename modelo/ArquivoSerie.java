package modelo;

import entidades.Serie;
import entidades.Episodio;
import controle.ControleSerie;

import java.util.ArrayList;
import java.util.List;

import aeds3.*;

public class ArquivoSerie extends Arquivo<Serie> {
    Arquivo<Serie> arqSerie;
    HashExtensivel<ParIDID> indiceSerieEpisodio;
    ArvoreBMais<ParNomeID> indiceNome;

    /*
     * Construtor da classe ArquivoSerie
     */
    public ArquivoSerie() throws Exception {
        super("serie", Serie.class.getConstructor());

        indiceSerieEpisodio = new HashExtensivel<>(
            ParIDID.class.getConstructor(),
            4,
            "./dados/indices/indiceSerieEpisodio.d.db",
            "./dados/indices/indiceSerieEpisodio.c.db"
            );

        indiceNome = new ArvoreBMais<>(
            ParNomeID.class.getConstructor(), 
            5, 
            "./dados/"+nomeEntidade+"/indiceNome.db");
    }

    /*
     * create - Função para criar um objeto Série junto com os seus índices
     * @param s - Objeto da Série a ser inserido
     * @return id - ID da Série inserida
     */
    @Override
    public int create(Serie s) throws Exception {
        // Criar a Série
        int id = super.create(s);

        // Criar o índice Nome-Série
        indiceNome.create(new ParNomeID(s.getNome(), id));

        // Retornar o ID da Série
        return id;
    }

    /*
     * readEpisodios - Função para retornar todos os Episódios que pertencem à Série especificada
     * @param id - ID da Série
     * @return episodios - Array dos Episódios que pertencem à Série especificada
     */
    public Episodio[] readEpisodios(int id) throws Exception {
        // Definir ArquivoEpisodio
        ArquivoEpisodio arqEpisodio = new ArquivoEpisodio();

        // Testar se o ID existe no Banco de Dados
        if (!ControleSerie.validarSerie(id))
            throw new Exception("ID da Série inválido");

        // Buscar o ParIDID de Série-Episódio a partir do ID da Série
        List<ParIDID> piis = indiceSerieEpisodio.read();

        // Definir array de Episódios com o tamanho da lista de Pares Série-Episódio
        Episodio[] episodios = new Episodio[piis.size()];

        // Iterar sobre a lista de Par Série-Episódio
        int i = 0;

        for (ParIDID pii : piis) {
            // Buscar Episódio referente ao Par Série-Episódio
            Episodio e = arqEpisodio.read(pii.getIDEpisodio());

            // Adicionar o episódio encontrado no array de Episódios
            episodios[i++] = e;
        }

        // Retornar lista de Episódios
        return episodios;
    }
    
    /*
     * readNome - Função para retornar uma array de Séries cujo nome começa com determinada String
     * @param nome - String a ser testada
     * @return series - Array de Séries cujo nome começa com determinada String
     */
    public Serie[] readNome(String nome) throws Exception {
        // Testar se o nome é válido
        if (nome.length() == 0)
            throw new Exception("Nome inválido!");

        // Definir lista de Par Nome-ID que possuem a String especificada
        ArrayList<ParNomeID> pnis = indiceNome.read(new ParNomeID(nome, -1));

        // Testar se há algum Par encontrado
        if ( !(pnis.size() > 0) )
            throw new Exception ("Não foi encontrado nenhuma Série com o nome buscado!");

        // Definir array de Séries com o tamanho do número de pares
        Serie[] series = new Serie[pnis.size()];

        // Iterar sobre a lista de Pares Nome-ID a adicionar as Séries correspondentes ao array de Séries
        int i = 0;
        for(ParNomeID pni: pnis) 
            series[i++] = this.read(pni.getID());

        // Retornar
        return series;
    }

    /*
     * delete - Função para excluir uma Série a partir de um ID
     * @param id - ID da Série a ser excluída
     * @return boolean - True se sucedido, False se contrário
     */
    @Override
    public boolean delete(int id) throws Exception {
        Serie e = super.read(id);   // na superclasse
        if(e != null) {
            if(super.delete(id)){
                ParIDID pii = indiceSerieEpisodio.read(id); // ter certeza que a série não possui episodios
                if(pii != null){
                    return indiceSerieEpisodio.delete(ParIDID.hash(e.getID())) && indiceNome.delete(new ParNomeID(e.getNome(), id));
                }
            }
                
        }
        return false;
    }

    /*
     * update - Função para atualizar uma Série
     * @param novaSerie - Objeto já alterado da Série
     * @return boolean - True se sucedido, False se contrário
     */
    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie e = super.read(novaSerie.getID());    // na superclasse
        if(e != null) {
            if(super.update(novaSerie)) {
                if( e.getID() != novaSerie.getID() ) {
                    indiceSerieEpisodio.delete( ParIDID.hash(e.getID()) );
                    indiceSerieEpisodio.create( new ParIDID(novaSerie.getID(), e.getID()) );
                }
                if(!e.getNome().equals(novaSerie.getNome())) {
                    indiceNome.delete(new ParNomeID(e.getNome(), e.getID()));
                    indiceNome.create(new ParNomeID(novaSerie.getNome(), novaSerie.getID()));
                }
                return true;
            }
        }
        return false;
    }

}
