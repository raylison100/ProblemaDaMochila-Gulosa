package main;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Mochila {

    public static void main(String[] args) {

        Scanner imput = new Scanner(System.in);
        System.out.println("Informe a capacidade da mochila: ");

        int capacidade = imput.nextInt();   //Capacidade da mochila.
        int enchendo = 0;   // Variavel auxiliar para verificar o volume atual da mochila.

        ArrayList<String> listItens = null;  // Lista de itens carregada do arquivo de entrada
        ArrayList <String> listValores = null;  // Lista de itens carregada do arquivo de entrada.
        ArrayList<String> listPesos = null;  //Lista de pesos carregada do arquivo de entrada

        //Map de representação da mochila
        TreeMap<String,  TreeMap<Integer, Integer>> mochila = new TreeMap<String,  TreeMap<Integer, Integer>>();

        //Map de representação dos itens fora da mochila
		TreeMap<String, TreeMap<Integer, Integer>> foraDaMochila = new TreeMap<String, TreeMap<Integer, Integer>>();

		int totalFora = 0; //Variavel auxiliar de contagem
		int totalDentro = 0;  //Variavel auxiliar de contagem


        //Inicio da leitura do arquivo de entrada
        FileReader file;
        try {
            file = new FileReader("entrada.txt");
            BufferedReader readFile = new BufferedReader(file);
            String line = readFile.readLine();
            int linha = 1;

            while (line != null) {
                if (linha == 1) {
                    String[] itens = line.split(":");
                    listItens = new ArrayList(Arrays.asList(itens[1].split(",")));
                }
                else if (linha == 2) {
                    String[] itens = line.split(":");
                    listValores = new ArrayList(Arrays.asList(itens[1].split(",")));
                }
                else if (linha == 3) {
                    String[] pesos = line.split(":");
                    listPesos = new ArrayList(Arrays.asList(pesos[1].split(",")));

                }
                linha++;
                line = readFile.readLine();
            }

            readFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Fim da leitura do arquivo de entrada

        int valorGuloso = 0;    //Auxiliar valor guloso na lista
        int index = 0;      //index valor guloso

        //Laço para inserção de itens na mochila
        while (true) {

            //Laço de validação de item da lista de entrada
            for (int item = 0; item < listValores.size(); ) {

                //Se o valor guloso for apto ele é atualizado e fica disponivel pra entrar na mochila
                if (valorGuloso <= Integer.parseInt(listValores.get(item)) && (enchendo + Integer.parseInt(listPesos.get(item))) <= capacidade) {
                    valorGuloso = Integer.parseInt(listValores.get(item));
                    index = item;
                }

                //Se o valor não for apto ele é removido da lista e adiconado ao map dos excluidos
                if ((Integer.parseInt(listPesos.get(item)) + enchendo) > capacidade) {

                    TreeMap<Integer, Integer> valorPesoFora = new TreeMap<>();
                    valorPesoFora.put(Integer.parseInt(listValores.get(item)),Integer.parseInt(listPesos.get(item)));
					foraDaMochila.put(listItens.get(item),valorPesoFora);
					totalFora += Integer.parseInt(listValores.get(item));
					listItens.remove(item);
                    listValores.remove(item);
                    listPesos.remove(item);

                } else {
                    item++;
                }
            }
            if (!listValores.isEmpty()) {
                //insere o valor guloso na mochila
                if ((enchendo + Integer.parseInt(listPesos.get(index))) <= capacidade) {

                    TreeMap<Integer, Integer> valorPesoDentro = new TreeMap<>();
                    valorPesoDentro.put(valorGuloso,Integer.parseInt(listPesos.get(index)));
                    mochila.put(listItens.get(index), valorPesoDentro);
                    totalDentro += valorGuloso;
                    enchendo += Integer.parseInt(listPesos.get(index));
                    listItens.remove(index);
                    listValores.remove(index);
                    listPesos.remove(index);

                } else {
                    break;
                }
            } else {
                break;
            }
            valorGuloso = 0;
            index = 0;
        }

		System.out.println("\n\n******************************************************************************************");
		System.out.println("\nCapacidade da mochila: " + capacidade);
        System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("Itens inclusos na mochila: "+ mochila.toString());
        System.out.println("Total de itens na mochila: "+ mochila.size());
        System.out.println("Valor total da mochila: "+ totalDentro);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("Itens de fora da mochila: "+ foraDaMochila.toString());
        System.out.println("Total de itens fora da mochila: "+ foraDaMochila.size());
        System.out.println("Valor total fora da  mochila: "+ totalFora);
		System.out.println("\n******************************************************************************************");
    }

}
