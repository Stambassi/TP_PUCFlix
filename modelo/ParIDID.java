package modelo;

import java.io.*;

import aeds3.RegistroHashExtensivel;

public class ParIDID implements RegistroHashExtensivel {
    private int IDSerie;
    private int IDEpisodio;
    private final short TAMANHO = 8;

    /**
     * Construtor padrão, inicializa com valores -1
     */
    public ParIDID() {
        this.IDSerie = -1;
        this.IDEpisodio = -1;
    }

    /**
     * Construtor que recebe os IDs da série e do episódio
     * @param idS (int): ID da série
     * @param idEp (int): ID do episódio
     */
    public ParIDID(int idS, int idEp) {
        this.IDSerie = idS;
        this.IDEpisodio = idEp;
    }

    /**
     * Retorna o ID da série
     * @return (int) ID da série
     */
    public int getIDSerie() {
        return IDSerie;
    }

    /**
     * Retorna o ID do episódio
     * @return (int) ID do episódio
     */
    public int getIDEpisodio() {
        return IDEpisodio;
    }

    /**
     * Retorna o tamanho do registro
     * @return (short) Tamanho em bytes
     */
    public short size() {
        return TAMANHO;
    }

    /**
     * Retorna um código hash para o objeto, baseado nos IDs
     * @return (int) Código hash
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(IDSerie) ^ Integer.hashCode(IDEpisodio);
    }

    /**
     * Converte o objeto para um array de bytes
     * @return (byte[]) Representação serializada do objeto
     */
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(IDSerie);
        dos.writeInt(IDEpisodio);
        return baos.toByteArray();
    }

    /**
     * Preenche o objeto a partir de um array de bytes
     * @param ba (byte[]): Array de bytes contendo os dados
     */
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.IDSerie = dis.readInt();
        this.IDEpisodio = dis.readInt();
    }

    /**
     * Representação textual do objeto
     * @return (String) Representação formatada do par de IDs
     */
    @Override
    public String toString() {
        return "(Série: " + IDSerie + ", Episódio: " + IDEpisodio + ")";
    }
}
