package modelo;

import java.io.*;

import aeds3.RegistroArvoreBMais;

public class ParNomeID implements RegistroArvoreBMais<ParNomeID> {
    private String nome;
    private int ID;
    private final short TAMANHO = 34;
    private final short TAMANHO_NOME = 30;

    /**
     * Construtor padrão, inicializa com valores vazios
     */
    public ParNomeID() {
        this.nome = "";
        this.ID = -1;
    }

    /**
     * Construtor que recebe nome e ID, truncando o nome se necessário
     * @param nome (String): Nome da série (máx. 30 caracteres)
     * @param ID (int): Identificador único
     */
    public ParNomeID(String nome, int ID) {
        this.nome = (nome.length() > TAMANHO_NOME) ? nome.substring(0, TAMANHO_NOME) : nome;
        this.ID = ID;
    }

    public String getNome() {
        return this.nome;
    }

    public int getID() {
        return this.ID;
    }

    /**
     * Retorna o tamanho do registro
     * @return (short) Tamanho em bytes
     */
    public short size() {
        return TAMANHO;
    }

    /**
     * Converte o objeto para um array de bytes
     * @return (byte[]) Representação serializada do objeto
     */
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeBytes(String.format("%-30s", nome)); // Garante que o nome tenha 30 bytes
        dos.writeInt(ID);
        return baos.toByteArray();
    }

    /**
     * Preenche o objeto a partir de um array de bytes
     * @param ba (byte[]): Array de bytes contendo os dados
     */
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] b = new byte[TAMANHO_NOME];
        dis.readFully(b);
        this.nome = new String(b).trim();
        this.ID = dis.readInt();
    }

    /**
     * Compara este objeto com outro ParNomeID baseado no nome
     * @param obj (ParNomeID): Objeto a ser comparado
     * @return (int) Valor negativo, zero ou positivo conforme a ordem lexicográfica
     */
    public int compareTo(ParNomeID obj) {
        return this.nome.compareTo(obj.nome);
    }

    /**
     * Retorna uma cópia do objeto
     * @return (ParNomeID) Clone do objeto atual
     */
    public ParNomeID clone() {
        return new ParNomeID(this.nome, this.ID);
    }

    /**
     * Representação textual do objeto
     * @return (String) Nome e ID formatados
     */
    @Override
    public String toString() {
        return "(" + this.nome + "; " + this.ID + ")";
    }
}
