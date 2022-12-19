import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mail implements Comparable {
    public String destinatario;
    public String remetente;
    public LocalDateTime horarioEnvio;
    public String assunto;
    public Endereco endereco;
    public String mensagem;
    public Boolean blacklisted = false;

    public Mail(String destinatario, String remetente, String assunto, Endereco endereco, String mensagem, LocalDateTime horario){
        this.destinatario = destinatario;
        this.remetente = remetente;
        this.assunto = assunto;
        this.horarioEnvio = horario;
        this.endereco = endereco;
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //sb.append("Destinatario: " + this.destinatario);
        //sb.append("\nRemetente: " + this.remetente);
        sb.append("\nHorario: " + this.horarioEnvio.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        //sb.append("\nAssunto: " + this.assunto);
        //sb.append("\nEndereco: " + this.endereco.cidade + " de " + this.endereco.pais);
        //sb.append("\nMensagem: " + this.mensagem);
        //sb.append("\nProibida?: " + (this.blacklisted ? "sim" : "não")) ;


        return sb.toString();
    }

    public boolean assuntoContemPalavra(String[] palavras) {
        for (String palavra : palavras) {
            if (assunto.contains(palavra)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int compareTo(Object obj) {
        if (this == obj) return 0;
        if (obj == null || getClass() != obj.getClass()) return 0;
        Mail that = (Mail) obj;
        return that.horarioEnvio.compareTo(this.horarioEnvio);
    }
}
