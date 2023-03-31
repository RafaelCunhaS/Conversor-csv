package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe principal do projeto.
 *
 */
public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados na pasta de
   * saídas, deixando os arquivos originais inalterados.
   *
   * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página web.
   * @param pastaDeSaidas Pasta em que serão colocados os arquivos gerados no formato requerido pelo
   *        subsistema.
   *
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {
    pastaDeSaidas.mkdir();
    if (pastaDeEntradas.isDirectory() && pastaDeEntradas.canRead()) {
      for (File arquivoDeEntrada : pastaDeEntradas.listFiles()) {
        ArrayList<String> conteudo = this.lerConteudoArquivo(arquivoDeEntrada);
        String nomeArquivo = arquivoDeEntrada.getName();
        File arquivoDeSaida = new File(pastaDeSaidas + File.separator + nomeArquivo);
        this.escreverConteudoNoArquivo(arquivoDeSaida, conteudo);
      }
    }
  }

  /**
   * Lê conteúdo do arquivo.
   * 
   * @param caminhoArquivo Caminho do arquivo a ser lido.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public ArrayList<String> lerConteudoArquivo(File caminhoArquivo) throws IOException {
    FileReader leitorArquivo = null;
    BufferedReader bufferedLeitor = null;

    leitorArquivo = new FileReader(caminhoArquivo);
    bufferedLeitor = new BufferedReader(leitorArquivo);
    ArrayList<String> linhasLidas = new ArrayList<String>();

    String conteudoLinha = bufferedLeitor.readLine();

    while (conteudoLinha != null) {
      linhasLidas.add(conteudoLinha);
      conteudoLinha = bufferedLeitor.readLine();
      if (conteudoLinha != null) {
        conteudoLinha = this.formartarLinha(conteudoLinha);
      }
    }
    leitorArquivo.close();
    bufferedLeitor.close();

    return linhasLidas;
  }

  /**
   * Escreve conteúdo em um novo arquivo.
   * 
   * @param caminhoArquivo Caminho do arquivo em que será escrito o conteúdo.
   * @param conteudo Conteúdo que será escrito no arquivo.
   * 
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public void escreverConteudoNoArquivo(File caminhoArquivo, ArrayList<String> conteudo)
      throws IOException {
    FileWriter escritorArquivo = null;
    BufferedWriter bufferedEscritor = null;
    if (!caminhoArquivo.exists()) {
      caminhoArquivo.createNewFile();
    }

    escritorArquivo = new FileWriter(caminhoArquivo, true);
    bufferedEscritor = new BufferedWriter(escritorArquivo);
    for (String linha : conteudo) {
      bufferedEscritor.write(linha);
      bufferedEscritor.newLine();
      bufferedEscritor.flush();
    }

    escritorArquivo.close();
    bufferedEscritor.close();
  }

  /**
   * Formata conteúdo.
   * 
   * @param conteudo Conteúdo a ser formatado.
   * @return Retorna a string formatada.
   */
  public String formartarLinha(String conteudo) {
    String[] informacoes = conteudo.split(",");
    String nomeCompleto = informacoes[0];
    String dataNascimento = informacoes[1];
    String email = informacoes[2];
    String cpf = informacoes[3];

    String[] data = dataNascimento.split("/");
    String dia = data[0];
    String mes = data[1];
    String ano = data[2];
    String dataFormatada = String.join("-", ano, mes, dia);

    String cpfFormatado = cpf.substring(0, 3) + '.' + cpf.substring(3, 6) + '.'
        + cpf.substring(6, 9) + '-' + cpf.substring(9);

    return String.join(",", nomeCompleto.toUpperCase(), dataFormatada, email, cpfFormatado);
  }
}
