import java.time.LocalDateTime;
import java.util.*;

public class MailBox {

    public List<Mail> emails = new ArrayList<>();
    public String[] proibicoesAssunto = {"aleatório"};
    private Integer numeroDeEmails = 0;
    
    public Integer numeroDeEmailsPorPais(String pais) {
        return 0;
    }

    public Integer numeroDeEmailsPorEndereco(Endereco endereco){
        return (int) emails.stream().filter(map -> endereco.equals(map.endereco)).count();   
    }

    public void processarEmailRecebido(Mail novoEmail){

        System.out.println(novoEmail);
        for (String palavraProibida: proibicoesAssunto) {
            if (novoEmail.assunto.contains(palavraProibida)){
                novoEmail.blacklisted = true;
                break;
            }
        }

        if (numeroDeEmails == 0) {
            adicionarEmail(0, novoEmail);
            return;
        }

        for (int i = 0; i < numeroDeEmails; i++) {
            if (novoEmail.horarioEnvio.isAfter(emails.get(i).horarioEnvio)) {
                adicionarEmail(i, novoEmail);
                return;
            }
        }

        adicionarEmail(numeroDeEmails, novoEmail);
    }

    public void processarEmailRecebidoUsandoBuscaBinaria(Mail novoEmail) {
        System.out.println(novoEmail);
        for (String palavraProibida: proibicoesAssunto) {
            if (novoEmail.assunto.contains(palavraProibida)){
                novoEmail.blacklisted = true;
                break;
            }
        }

        if (numeroDeEmails == 0) {
            adicionarEmail(0, novoEmail);
            return;
        }

        adicionarEmail(buscarIndicePorHorario(novoEmail.horarioEnvio), novoEmail);
    }


    private void adicionarEmail(int posicao, Mail novoEmail) {
        emails.add(posicao, novoEmail);
        numeroDeEmails++;
    }

    public Integer numeroDeEnderecosDistintos() {
        Set<Endereco> enderecosDistintos = new HashSet<Endereco>();
        for (Mail mail : emails) {
           enderecosDistintos.add(mail.endereco);
        }

        return enderecosDistintos.size();
    }

    public List<Endereco> buscarEnderecosPorAssunto(String[] filtroDePalavrasAssunto) {
        
        List<Endereco> enderecosEncontrados = new ArrayList<>();

        for (Mail mail : emails) {
            if (mail.assuntoContemPalavra(filtroDePalavrasAssunto)) {
                enderecosEncontrados.add(mail.endereco);
            }
        }
        
        return enderecosEncontrados;
    }


    private List<Mail> filtrarPorPalavrasNoAssunto(String[] filtroDePalavrasAssunto) {
        List<Mail> emailsFiltrados = new ArrayList<>();

        for (Mail mail : emails) {
            if (mail.assuntoContemPalavra(filtroDePalavrasAssunto)) {
                emailsFiltrados.add(mail);
            }
        }
        
        return emailsFiltrados;
    }
    
    public void deletarEmailsAnteriorAData(LocalDateTime dataLimite) {

    }

    public List<Endereco> emailsRecebidosHoje() {
        return new ArrayList<>();
    }

    public void deletarEmailsPorParametroDeUmEndereco(Endereco endereco, String[] filtros) {

    }

    public void deletarEmailsAnteriorADataPorEndereco(LocalDateTime dataLimite, Endereco endereco) {

    }

    public List<Endereco> buscarPorPais(String pais) {
        return new ArrayList<>();
    }

    public Integer numeroDeEmails() {
        return emails.size();
    }

    public Integer buscarIndicePorHorario(LocalDateTime horario) {
        if(numeroDeEmails == 0) {
            return 0;
        }
        Integer step = 1;
        Integer inicio = 0;
        Integer fim = numeroDeEmails - 1;
        Integer pontoMedio = fim/2;
        Boolean encontrou = false;
        while(!encontrou) {
            
            if (emails.get(pontoMedio).horarioEnvio.isAfter(horario)) {
                inicio = pontoMedio;
                pontoMedio = (inicio + fim) / 2;
                encontrou = pontoMedio.equals(inicio);
               
            } else {
                fim = pontoMedio;
                pontoMedio = (inicio + fim) / 2;
                encontrou = pontoMedio.equals(fim);
               
            }

            step++;
        }

        if(emails.get(pontoMedio).horarioEnvio.isAfter(horario)){
            return pontoMedio + 1;
        }

        return pontoMedio;
    }

}
