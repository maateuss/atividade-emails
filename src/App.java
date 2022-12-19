import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class App {

    static MailBox mailBox = new MailBox();
    static MailService servicoDeMensagens = new MailService();
    public static void main(String[] args) throws Exception {
        Instant start = Instant.now();
        List<Mail> novosEmails = servicoDeMensagens.criarListaComVariosEmails("destinatario@teste.com");

        for (Mail mail : novosEmails) {
            mailBox.processarEmailRecebidoUsandoBuscaBinaria(mail);
        }
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
        //2022-12-19T22:22:22.9993


        // System.out.println(String.format("Agora a caixa possui %d emails",mailBox.numeroDeEmails()));
        // Integer numeroDeElementosDoCentrodoJapao = mailBox.numeroDeEmailsPorEndereco(new Endereco("Japão", "Centro"));
        // System.out.println("numero de elementos do centro do japão: " + numeroDeElementosDoCentrodoJapao);
        // Integer numeroDeEnderecosDistintos = mailBox.numeroDeEnderecosDistintos();
        // System.out.println(String.format("numero de endereços distintos: %d", numeroDeEnderecosDistintos));
        // mailBox.ordenarEmails();
        // for (Mail mail : mailBox.emails) {
        //     System.out.println(mail);
        // }
    }
}
