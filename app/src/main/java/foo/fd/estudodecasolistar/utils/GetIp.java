package foo.fd.estudodecasolistar.utils;

public class GetIp {

    String fora = "10.0.0.104";
    String ipCasa = "192.168.1.108";

    String caminho = ipCasa;
    public String getListarCidade(){
        return  "http://"+caminho+"/curso_udemy/exer/APIListarCidades.php";

    }

    public String getDeleteCidade(){
        http://localhost/curso_udemy/exer/APIDeleteCidade.php
      // return "http://"+caminho+"/cursoudemy/exer/APIDeletarCidade.php";
        return "http://"+caminho+"/curso_udemy/exer/APIDeleteCidade.php";

    }

    public String getDeleteEstado(){
        return "http://"+caminho+"/curso_udemy/exer/DELETE_ESTADOj.php";
    }

}
