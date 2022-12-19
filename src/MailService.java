import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MailService {
    public List<Mail> criarListaComVariosEmails(String destinatario) {
        List<Mail> lista = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            lista.add(new Mail(destinatario, String.format("remetenteN%d@teste.com", i), "Assunto aleatório", obterEnderecoAleatorio(), "bla teste batata futebol", obterData(i)));
        }

        return lista;
    }

    private LocalDateTime obterData(Integer i) {
        return LocalDateTime.parse(String.format("2022-12-%02dT22:22:22.%04d", ((1+i)%25+1), i));
    }

    private Endereco obterEnderecoAleatorio() {

        String[] paises = {"Brasil", "Estados Unidos", "Argentina", "Espanha", "Japão"};

        String[] cidades = {"Centro", "Capital", "Interior", "Litoral", "Ponto Turistico"};
        Random r = new Random();

        Integer indice = r.nextInt(5);
        String  pais = paises[indice];
        indice = r.nextInt(5);
        String cidade = cidades[indice];

        return new Endereco(pais, cidade);
    }
}
