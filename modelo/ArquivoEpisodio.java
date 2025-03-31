package modelo;
import entidades.Episodio;

import java.util.ArrayList;
import java.util.List;

import aeds3.*;

public class ArquivoEpisodio extends Arquivo<Episodio> {
    Arquivo<Episodio> arqEpisodio;
    HashExtensivel<ParIDID> indiceSerieEpisodio
    ArvoreBMais<ParNomeID> indiceNome;

    public ArquivoEpisodio() throws Exception {
        super("episodio", Episodio.class.getConstructor());

        indiceSerieEpisodio = new HashExtensivel<>(
            ParIDID.class.getConstructor(),
            4,
            "./dados/"+nomeEntidade+"/indiceSerieEpisodio.d.db",
            "./dados/"+nomeEntidade+"/indiceSerieEpisodio.c.db"
            );

        indiceNome = new ArvoreBMais<>(
            ParNomeID.class.getConstructor(), 
            5, 
            "./dados/"+nomeEntidade+"/indiceNome.db");
    }

    @Override
    public int create(Episodio e) throws Exception {
        int id = super.create(e);

        indiceSerieEpisodio.create(new ParIDID(e.getIDSerie(), id));
        indiceNome.create(new ParNomeID(e.getNome(), id));

        return id;
    }

    public List<Episodio> readIDSerie(int IDSerie) throws Exception {
        if( !ControleSerie.validarSerie(IDSerie) )
            throw new Exception("IDSerie inválido");

        List<ParIDID> piis = indiceSerieEpisodio.read(ParIDID.hash(IDSerie));

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

    public Episodio readEpisodios(int IDSerie) throws Exception {
        if (!ControleSerie.validarSerie(IDSerie))
            throw new Exception("IDSerie inválido");
        ParIDID pii = indiceSerieEpisodio.read();
    }

    public Episodio[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;

        ArrayList<ParNomeID> pnis = indiceNome.read(new ParNomeID(nome, -1));

        if (pnis.size() > 0) {
            Episodio[] episodios = new Episodio[pnis.size()];
            int i = 0;
            for(ParNomeID pni: pnis) 
                episodios[i++] = read(pni.getId());
            return episodios;
        }
        else 
            return null;
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
                    indiceSerieEpisodio.delete( ParIDID.hash(e.getID()) );
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

}
