package modelo;

import entidades.Episodio;
import controle.ControleSerie;
import aeds3.*;

import java.util.ArrayList;
import java.util.List;

public class ArquivoEpisodio extends Arquivo<Episodio> {
    Arquivo<Episodio> arqEpisodio;
    HashExtensivel<ParIDID> indiceSerieEpisodio;
    ArvoreBMais<ParNomeID> indiceNome;

    /*
     * Construtor da classe ArquivoEpisodio
     */
    public ArquivoEpisodio() throws Exception {
        // Chamar o construtor da classe herdada
        super("episodio", Episodio.class.getConstructor());

        // Chamar o construtor do índice de Série e Episódio
        indiceSerieEpisodio = new HashExtensivel<>(
            ParIDID.class.getConstructor(),
            4,
            "./dados/indices/indiceSerieEpisodio.d.db",
            "./dados/indices/indiceSerieEpisodio.c.db"
            );

        // Chamar o construtor do índice de Nome do episódio e seu ID
        indiceNome = new ArvoreBMais<>(
            ParNomeID.class.getConstructor(), 
            5, 
            "./dados/"+nomeEntidade+"/indiceNome.db");
    }

    /*
     * create - Função para criar um Episódio no Banco de Dados
     * @param e - Episódio a ser inserido (sem o ID)
     * @return id - ID do Episódio inserido
     */
    @Override
    public int create(Episodio e) throws Exception {
        // Criar o Episódio 
        int id = super.create(e);

        // Criar os índices Serie-Episodio e Nome-Episodio
        indiceSerieEpisodio.create(new ParIDID(e.getIDSerie(), id));
        indiceNome.create(new ParNomeID(e.getNome(), id));

        // Retornar o ID
        return id;
    }

    /*
     * read - Função para ler um Episódio do Banco de Dados a partir do seu ID
     * @param id - ID do Episódio a ser lido
     * @return episodio - Episódio encontrado
     */
    @Override
    public Episodio read(int id) throws Exception {
        // Ler o episódio a partir do ArquivoEpisodio
        Episodio episodio = arqEpisodio.read(id);

        // Retornar o Episódio buscado
        return episodio;
    }

    /*
     * readNome - Função para buscar todos os Episódios cujo nome inicia com uma String especificada
     * @param nome - String a ser buscada
     * @return episodios - Array de Episódios encontrados
     */
    public Episodio[] readNome(String nome) throws Exception {
        // Testar se o nome é válido
        if (nome.length() == 0)
            throw new Exception("Nome inválido!");

        // Definir Lista de Par Nome-ID que possuem a String especificada
        ArrayList<ParNomeID> pnis = indiceNome.read(new ParNomeID(nome, -1));

        // Testar se há algum Par encontrado
        if ( !(pnis.size() > 0) )
            throw new Exception ("Não foi encontrado nenhum Episódio com o nome buscado!");
        
        // Definir array de Episódios com o tamanho do número de pares
        Episodio[] episodios = new Episodio[pnis.size()];

        // Iterar sobre a lista de Pares Nome-ID a adicionar os Episódios correspondentes ao array de Episódios
        int i = 0;
        for(ParNomeID pni: pnis) 
            episodios[i++] = read(pni.getID());

        // Retornar
        return episodios;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Episodio e = super.read(id);   // na superclasse
        if(e != null) {
            if(super.delete(id))
                return indiceSerieEpisodio.delete(ParIDID.hash(e.getID())) && indiceNome.delete(new ParNomeID(e.getNome(), id));
        }
        return false;
    }

    @Override
    public boolean update(Episodio novoEpisodio) throws Exception {
        Episodio e = super.read(novoEpisodio.getID());    // na superclasse
        if(e != null) {
            if(super.update(novoEpisodio)) {
                if( e.getID() != novoEpisodio.getID() ) {
                    indiceSerieEpisodio.delete( ParIDID.hashCode(e.getID()) );
                    indiceSerieEpisodio.create( new ParIDID(novoEpisodio.getID(), e.getID()) );
                }
                if(!e.getNome().equals(novoEpisodio.getNome())) {
                    indiceNome.delete(new ParNomeID(e.getNome(), e.getID()));
                    indiceNome.create(new ParNomeID(novoEpisodio.getNome(), novoEpisodio.getID()));
                }
                return true;
            }
        }
        return false;
    }

    /*
     * readIDSerie - Função para ler todos os Episódios vinculados a uma Série
     * @param IDSerie - ID da Série dos episódios a serem procurados
     * @return episodios - Lista de Episódios que pertencem à Série especificada
     */
    public List<Episodio> readIDSerie(int IDSerie) throws Exception {
        if( !ControleSerie.validarSerie(IDSerie) )
            throw new Exception("IDSerie inválido");

        List<ParIDID> piis = indiceSerieEpisodio.read(ParIDID.hashCode(IDSerie));

        if(piis != null) {
            List<Episodio> episodios = new ArrayList<Episodio>();
            for (ParIDID pii : piis) {
                episodios.add( super.read(pii.getID()) );
            }
            return episodios;
        }
        else 
            return null;
    }

}