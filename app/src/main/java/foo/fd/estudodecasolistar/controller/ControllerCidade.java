package foo.fd.estudodecasolistar.controller;

import java.util.List;

import foo.fd.estudodecasolistar.controller.MODEL2.Cidade;

public class ControllerCidade {
    /**
     * Localizar cidade pelo nome
     * @param cidades
     * @param nome
     * @return
     */
    public Cidade buscarObjeto(List<Cidade> cidades,
                               String nome) {

        for (Cidade obj : cidades) {
            if (obj.getNome().equals(nome)) {
                return obj;
            }
        }
        return null;
    }
}
