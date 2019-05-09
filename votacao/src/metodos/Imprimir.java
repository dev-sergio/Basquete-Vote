package metodos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Imprimir {

    public Imprimir(int numeroTitulo, String grupo) {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        try {
            // Localiza todas as impressoras com suporte a arquivos txt   
            PrintService[] servicosImpressao = PrintServiceLookup.lookupPrintServices(
                    DocFlavor.INPUT_STREAM.AUTOSENSE, null);
            PrintService impressora = PrintServiceLookup.lookupDefaultPrintService();

            // Definição de atributos do conteúdo a ser impresso:   
            DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

            // Atributos de impressão do documento   
            HashDocAttributeSet attributes = new HashDocAttributeSet();

            // InputStream apontando para o conteúdo a ser impresso  
            String caminho = System.getProperty("user.dir") + "\\ultimoTitulo.txt";
            File f = new File(caminho);
            f.delete();
            fileWriter = new FileWriter(caminho);

            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(arrumaTexto("ELEICOES BASQUETE 2017"));
            bufferedWriter.newLine();
            bufferedWriter.write(arrumaTexto("Vila Rosina"));
            bufferedWriter.newLine();
            bufferedWriter.write("------------------------------------------------");
            bufferedWriter.newLine();
            bufferedWriter.write(arrumaTexto("GRUPO DO TITULO"));
            bufferedWriter.newLine();
            bufferedWriter.write(arrumaTexto(grupo));
            bufferedWriter.newLine();
            bufferedWriter.write("");
            bufferedWriter.newLine();
            bufferedWriter.write(arrumaTexto("NUMERO DO TITULO DE ELEITOR"));
            bufferedWriter.newLine();
            bufferedWriter.write(arrumaTexto(Integer.toString(numeroTitulo)));
            bufferedWriter.newLine();
            bufferedWriter.write("------------------------------------------------");
            bufferedWriter.newLine();
            bufferedWriter.write(arrumaTexto("VOTE CONSCIENTE - GOOD GAME") + (char) 27 + 'w');

            bufferedWriter.close();

            FileInputStream fi = new FileInputStream(caminho);

            // Cria um Doc para impressão a partir do arquivo exemplo.txt   
            Doc documentoTexto = new SimpleDoc(fi, docFlavor, attributes);

            // Configura o conjunto de parametros para a impressora   
            PrintRequestAttributeSet printerAttributes = new HashPrintRequestAttributeSet();

            boolean mostrarDialogo = false;  //Decide se mostra ou não a caixa de dialogos
           

            if (mostrarDialogo) {
                // exibe um dialogo de configuracoes de impressao   
                PrintService servico = ServiceUI.printDialog(null, 320, 240,
                        servicosImpressao, impressora, docFlavor, printerAttributes);

                if (servico != null) {
                    DocPrintJob printJob = servico.createPrintJob();
                    printJob.print(documentoTexto, printerAttributes);
                }
            } else {
                // Cria uma tarefa de impressão   
                DocPrintJob printJob = impressora.createPrintJob();

                // Adiciona propriedade de impressão: Imprimir duas cópias   
                printerAttributes.add(new Copies(1));

                // Imprime o documento sem exibir uma tela de dialogo   
                printJob.print(documentoTexto, printerAttributes);
            }
        } catch (IOException e) {
            System.out.println("ERRO IO" + e.getMessage());
        } catch (PrintException ex2) {
            ex2.getMessage();
        }
    }

    private String arrumaTexto(String texto) {
        int quantidade = (48 - texto.length()) / 2;
        
        char a = ' ';
        char[] arrayDeAs = new char[quantidade];
        Arrays.fill(arrayDeAs, a); // preenche arrayDeAs com 'a'
        
        String stringComOsAs = new String(arrayDeAs);
        
        String novo = stringComOsAs + texto;
        
        return novo;
    }

}
