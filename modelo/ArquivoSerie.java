package modelo;

import aeds3.Arquivo;
import aeds3.ArvoreBMais;
import aeds3.HashExtensivel;
import entidades.Serie;

import java.util.*;
import java.io.*;

/**
 * Classe responsável pela manipulação dos dados de séries, extendendo a estrutura genérica de arquivo.
 */
public class ArquivoSerie extends Arquivo<Serie> {

    private Arquivo<Episodio> manipuladorEpisodios;
    private ArvoreBMais<ParNomeID> arvoreIndiceNome;
    private HashExtensivel<ParIDID> tabelaHashSerieEpisodio;

    /**
     * Construtor: Inicializa o arquivo de séries e os índices auxiliares.
     */
    public ArquivoSerie() throws Exception {
        super("dados/series/dados", Serie.class.getConstructor());

        arvoreIndiceNome = new ArvoreBMais<>(ParNomeID.class.getConstructor(), 4, "dados/series/indiceNome.db");

        tabelaHashSerieEpisodio = new HashExtensivel<>(
            ParIDID.class.getConstructor(),
            4,
            "dados/series/hashSerieEp.d.db",
            "dados/series/hashSerieEp.c.db"
        );
    }

    /**
     * Lê todas as séries cujo nome começa com determinado prefixo.
     * @param prefixoNome (String): Parte inicial do nome da série.
     * @return ArrayList<Episodio>: Lista de episódios correspondentes.
     */
    public ArrayList<Episodio> buscarPorPrefixoNome(String prefixoNome) throws Exception {
        ArrayList<ParNomeID> correspondencias = arvoreIndiceNome.readPrefix(prefixoNome);
        ArrayList<Episodio> episodiosEncontrados = new ArrayList<>();
        ArquivoEpisodio leitorEpisodio = new ArquivoEpisodio();

        for (ParNomeID par : correspondencias) {
            episodiosEncontrados.add(leitorEpisodio.read(par.getID()));
        }

        return episodiosEncontrados;
    }

    /**
     * Retorna todos os episódios relacionados a uma série específica.
     * @param idSerie (int): Identificador da série.
     * @return ArrayList<Episodio>: Lista de episódios da série.
     */
    public ArrayList<Episodio> buscarEpisodiosDaSerie(int idSerie) throws Exception {
        ArquivoEpisodio leitorEpisodio = new ArquivoEpisodio();
        ArrayList<Episodio> listaEpisodios = new ArrayList<>();

        for (ParIDID par : tabelaHashSerieEpisodio.readAll()) {
            if (par.getIDSerie() == idSerie) {
                Episodio ep = leitorEpisodio.read(par.getIDEpisodio());
                if (ep != null)
                    listaEpisodios.add(ep);
            }
        }

        return listaEpisodios;
    }

    /**
     * Cria uma nova série, adicionando também ao índice.
     */
    @Override
    public int create(Serie novaSerie) throws Exception {
        int idGerado = super.create(novaSerie);
        arvoreIndiceNome.create(new ParNomeID(novaSerie.getNome(), idGerado));
        return idGerado;
    }

    /**
     * Atualiza uma série existente, sincronizando o índice.
     */
    @Override
    public boolean update(Serie serieAtualizada) throws Exception {
        Serie serieAnterior = super.read(serieAtualizada.getID());
        if (serieAnterior == null) return false;

        boolean atualizado = super.update(serieAtualizada);

        if (atualizado && !serieAtualizada.getNome().equals(serieAnterior.getNome())) {
            arvoreIndiceNome.delete(serieAnterior.getNome());
            arvoreIndiceNome.create(new ParNomeID(serieAtualizada.getNome(), serieAtualizada.getID()));
        }

        return atualizado;
    }

    /**
     * Exclui uma série, apenas se não possuir episódios relacionados.
     */
    @Override
    public boolean delete(int id) throws Exception {
        boolean possuiEpisodios = buscarEpisodiosDaSerie(id).size() > 0;

        if (possuiEpisodios) {
            System.err.println("Erro: A série possui episódios cadastrados e não pode ser excluída.");
            return false;
        }

        Serie serieParaExcluir = super.read(id);
        if (serieParaExcluir == null) return false;

        boolean deletado = super.delete(id);
        if (deletado) {
            arvoreIndiceNome.delete(serieParaExcluir.getNome());
        }

        return deletado;
    }
}
