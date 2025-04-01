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

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceNome.create(new ParNomeID(s.getNome(), id));
        return id;
    }

    public List<Episodio> readEpisodios(int IDSerie) throws Exception {
        List<Episodio> episodios = new ArrayList<Episodio>();
        if (!ControleSerie.validarSerie(IDSerie))
            throw new Exception("IDSerie inválido");
        ParIDID pii = indiceSerieEpisodio.read();
        while (pii != null){
            episodios.add(pii);
            pii = indiceSerieEpisodio.read();
        }
        return episodios;
    }
    
    public Serie[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;

        ArrayList<ParNomeID> pnis = indiceNome.read(new ParNomeID(nome, -1));

        if (pnis.size() > 0) {
            Serie[] Series = new Serie[pnis.size()];
            int i = 0;
            for(ParNomeID pni: pnis) 
                Series[i++] = read(pni.getID());
            return Series;
        }
        else 
            return null;
    }

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
