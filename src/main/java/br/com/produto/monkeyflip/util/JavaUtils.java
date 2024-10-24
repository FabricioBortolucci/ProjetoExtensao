package br.com.produto.monkeyflip.util;

public final class JavaUtils {


    public static String formatarCpf(String cpf) {
        if (cpf != null && cpf.length() == 11) {
            return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
        return cpf;
    }

    public static String formatarTelefone(String telefone) {
        if (telefone != null && telefone.length() == 11) {
            return telefone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else if (telefone != null && telefone.length() == 10) {
            return telefone.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }
        return telefone;
    }

}
