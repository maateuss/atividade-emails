public class Endereco {
    public String pais;
    public String cidade;

    public Endereco(String pais, String cidade){
        this.pais = pais;
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Endereco that = (Endereco) obj;
        return this.pais.equals(that.pais) &&
          this.cidade.equals(that.cidade);
    }

    @Override
    public int hashCode() {
        return pais.hashCode() + cidade.hashCode();
    }
}
