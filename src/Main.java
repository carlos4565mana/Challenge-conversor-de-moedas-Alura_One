import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "bed0e0628f7fb5f8a5b4b89a";

    public static void main(String[] args) throws IOException {


        Scanner sc = new Scanner(System.in);

        HashMap<Integer, String> codigos = new HashMap<Integer, String>();

        codigos.put(1, "USD");
        codigos.put(2, "BRL");
        codigos.put(3, "ARS");
        codigos.put(4, "COP");
        codigos.put(5, "EUR");
        codigos.put(6, "JPY");
        codigos.put(7, "PYG");

        int from = 0,to = 0;
        String codigoOrigem, codigoDestino;
        double quantidade = 0;
        boolean continua = true;



        System.out.println("-------------------------------------------------------------------");
        System.out.println("**************** Bem Vindo ao Conversor de Moedas ****************");
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
        System.out.println();

        System.out.println("    *************** Escoha a Moeda de Origem  ( 1 - 7 )**********");
        System.out.println("\t\t\t 1 - Dollar");
        System.out.println("\t\t\t 2 - Real Brasileiro");
        System.out.println("\t\t\t 3 - Peso Argentino");
        System.out.println("\t\t\t 4 - Peso Colombiano");
        System.out.println("\t\t\t 5 - Euro");
        System.out.println("\t\t\t 6 - Iene Japones");
        System.out.println("\t\t\t 7 - Guarani Paraguaio");
        do{
            try{
                System.out.println(" Escoha um numero de  ( 1 - 7 ) ");
                from = sc.nextInt();
                continua = false;

            }catch (InputMismatchException err){
                System.err.println("Não é permitido inserir letras, informe apenas números inteiros de 1 - 7 !");
                sc.nextLine();
            }
        }while(continua || from < 1 ||from > 7);

        codigoOrigem = codigos.get(from);
        System.out.println("codigo de Origem = "+codigoOrigem);

        continua=true;

        System.out.println("    *************** Escoha a Moeda de Destino  ( 1 - 7 )**********");
        System.out.println("\t\t\t 1 - Dollar");
        System.out.println("\t\t\t 2 - Real Brasileiro");
        System.out.println("\t\t\t 3 - Peso Argentino");
        System.out.println("\t\t\t 4 - Peso Colombiano");
        System.out.println("\t\t\t 5 - Euro");
        System.out.println("\t\t\t 6 - Iene Japones");
        System.out.println("\t\t\t 7 - Guarani Paraguaio");

        do{
            try{
                System.out.println(" Escoha um numero de  ( 1 - 7 ) ");
                to = sc.nextInt();
                if(to == from){
                    System.out.println("A moeda de destino é igual a moeda de origem ");
                    System.out.println(" Escoha um numero diferente de "+ from);

                }else{
                continua = false;}

            }catch (InputMismatchException err){
                System.err.println("Não é permitido inserir letras, informe apenas números inteiros de 1 - 7 !");
                sc.nextLine();
            }
        }while(continua || to < 1 || to > 7);

        codigoDestino = codigos.get(to);

        System.out.println("codigo de Destino = "+codigoDestino);

        continua = true;
        do{
            try{
                System.out.println("Digite a quantidade que deseja converter :");
                quantidade = sc.nextFloat();
                continua = false;

            }catch (InputMismatchException err){
                System.err.println("Não é permitido inserir letras, informe apenas números separados por .!");
                sc.nextLine();
            }
        }while(continua);

        quantidade = quantidade;

        sendHttpGETRequest(codigoOrigem, codigoDestino, quantidade);

    }

    private static void  sendHttpGETRequest(String codigoOrigem, String codigoDestino,double quantidade) throws IOException {
        DecimalFormat f = new DecimalFormat("00.00");

        String GET_URL = "https://v6.exchangerate-api.com/v6/"+API_KEY+"/pair/"+codigoOrigem+"/"+codigoDestino+"/"+quantidade;

        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();
       // System.out.println(responseCode);

        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);

            }
            in.close();
            JSONObject obj = new JSONObject(response.toString());
            var con =  obj.getDouble("conversion_result");
            System.out.println("Valor de "+ f.format(quantidade) +" " + codigoOrigem + " = " + f.format(con) +" " + codigoDestino);

        }else{
            System.out.println("Houve uma falha durante a reguisição!!");
        }


    }
}