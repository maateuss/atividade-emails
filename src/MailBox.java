import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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
        Integer indice = buscarIndicePorHorario(dataLimite);

        emails = emails.subList(0, indice - 1);
        numeroDeEmails = emails.size();
    }

    public List<Endereco> emailsRecebidosHoje() {
        List<Endereco> enderecosDeEmailsRecebidosHoje = new ArrayList<>();
        LocalDateTime currentDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        Integer indiceDosEmailsDeHoje = buscarIndicePorHorario(currentDay);

        for(Mail mail : emails.subList(0, indiceDosEmailsDeHoje -1 )) {
            enderecosDeEmailsRecebidosHoje.add(mail.endereco);
        }

        return enderecosDeEmailsRecebidosHoje;
    }

    public void deletarEmailsPorParametroDeUmEndereco(Endereco endereco, String[] filtros) {
        List<Mail> emailsAux = emails;
        for (Mail mail : emailsAux) {
            if (mail.endereco.equals(endereco)){ 
                for (String palavra : filtros) {
                    if (mail.assunto.contains(palavra)) {
                        emails.remove(mail);
                        break;
                    }
                }
            }
        }

        numeroDeEmails = emails.size();
    }

    public void deletarEmailsAnteriorADataPorEndereco(LocalDateTime dataLimite, Endereco endereco) {
        Integer indice = buscarIndicePorHorario(dataLimite);

        List<Mail> listaAuxiliar = emails.subList(indice, numeroDeEmails);

        for(Mail email : listaAuxiliar) {
            if(email.endereco.equals(endereco)) {
                emails.remove(email);
            }
        }

        numeroDeEmails = emails.size();
        
    }

    public List<String> buscarRemetentesPorPais(String pais) {
        List<String> remetentes = new ArrayList<>();

        for (Mail mail: emails){
            if( mail.endereco.pais == pais) {
                remetentes.add(mail.remetente);
            }
        }

        return remetentes;
    }

    public Integer numeroDeEmails() {
        return emails.size();
    }

    // busca binária para acelerar a busca do indice dado que
    // os horarios estão ordenados de forma decrescente
    private Integer buscarIndicePorHorario(LocalDateTime horario) {
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
