
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleAlchemy {

    private static File diretorioAtual = new File(
        System.getProperty("user.dir")
    );

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("          CONSOLE ALCHEMY         ");
        System.out.println("==================================");

        System.out.println("Digite 'ajuda' para ver os comandos.");

        while (true) {

            System.out.print("\n" + diretorioAtual.getAbsolutePath() + " > ");

            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                continue;
            }

            String[] partes = entrada.split("\\s+", 2);

            String comando = partes[0];
            String argumento = partes.length > 1 ? partes[1] : "";

            switch (comando) {

                case "liste":
                    listarArquivos();
                    break;

                case "crie":
                    criarDiretorio(argumento);
                    break;

                case "texto":
                    criarArquivo(argumento);
                    break;

                case "remover":
                    removerArquivo(argumento);
                    break;

                case "acesse":
                    mudarDiretorio(argumento);
                    break;

                case "caminho":
                    mostrarCaminho();
                    break;

                case "ajuda":
                    mostrarAjuda();
                    break;

                case "sair":
                    System.out.println("Encerrando ConsoleAlchemy...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Comando não reconhecido.");
                    System.out.println("Digite 'ajuda' para consultar os comandos.");
            }
        }
    }

    // LISTAR ARQUIVOS E PASTAS
    private static void listarArquivos() {

        File[] arquivos = diretorioAtual.listFiles();

        if (arquivos == null) {
            System.out.println("Não foi possível acessar o diretório.");
            return;
        }

        if (arquivos.length == 0) {
            System.out.println("Diretório vazio.");
            return;
        }

        for (File arquivo : arquivos) {

            if (arquivo.isDirectory()) {
                System.out.println("[PASTA] " + arquivo.getName());
            } else {
                System.out.println("[ARQUIVO] " + arquivo.getName());
            }
        }
    }

    // CRIAR DIRETÓRIO
    private static void criarDiretorio(String nome) {

        if (nome.isEmpty()) {
            System.out.println("Informe o nome do diretório.");
            return;
        }

        File novoDiretorio = new File(diretorioAtual, nome);

        if (novoDiretorio.mkdir()) {
            System.out.println("Diretório criado com sucesso!");
        } else {
            System.out.println("Não foi possível criar o diretório.");
        }
    }

    // CRIAR ARQUIVO DE TEXTO
    private static void criarArquivo(String nome) {

        if (nome.isEmpty()) {
            System.out.println("Informe o nome do arquivo.");
            return;
        }

        File novoArquivo = new File(diretorioAtual, nome);

        try {

            if (novoArquivo.createNewFile()) {
                System.out.println("Arquivo criado com sucesso!");
            } else {
                System.out.println("O arquivo já existe.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo: " + e.getMessage());
        }
    }

    // REMOVER ARQUIVO
    private static void removerArquivo(String nome) {

        if (nome.isEmpty()) {
            System.out.println("Informe o nome do arquivo.");
            return;
        }

        File arquivo = new File(diretorioAtual, nome);

        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado.");
            return;
        }

        if (arquivo.isDirectory()) {
            System.out.println("O comando remover não remove diretórios.");
            return;
        }

        if (arquivo.delete()) {
            System.out.println("Arquivo removido com sucesso!");
        } else {
            System.out.println("Não foi possível remover o arquivo.");
        }
    }

    // ACESSAR DIRETÓRIO
    private static void mudarDiretorio(String caminho) {

        if (caminho.isEmpty()) {
            System.out.println("Informe um diretório.");
            return;
        }

        File novoDiretorio;

        if (caminho.equals("..")) {
            novoDiretorio = diretorioAtual.getParentFile();

        } else if (caminho.equals("/")) {
            novoDiretorio = diretorioAtual.toPath().getRoot().toFile();

        } else if (new File(caminho).isAbsolute()) {
            novoDiretorio = new File(caminho);

        } else {
            novoDiretorio = new File(diretorioAtual, caminho);
        }

        if (novoDiretorio != null &&
            novoDiretorio.exists() &&
            novoDiretorio.isDirectory() &&
            novoDiretorio.canRead()) {

            try {
                diretorioAtual = novoDiretorio.getCanonicalFile();
                System.out.println("Diretório acessado com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao acessar diretório.");
            }

        } else {
            System.out.println("Diretório não encontrado ou inacessível.");
        }
    }

    // MOSTRAR CAMINHO ABSOLUTO
    private static void mostrarCaminho() {

        System.out.println("Caminho atual: "
            + diretorioAtual.getAbsolutePath());
    }

    // MOSTRAR AJUDA
    private static void mostrarAjuda() {

        System.out.println("\n===== COMANDOS DO CONSOLE ALCHEMY =====");

        System.out.println("liste          - Lista arquivos e pastas");
        System.out.println("crie nome      - Cria um diretório");
        System.out.println("texto nome     - Cria um arquivo de texto");
        System.out.println("remover nome   - Remove um arquivo");
        System.out.println("acesse pasta   - Acessa um diretório");
        System.out.println("acesse ..      - Retorna ao diretório anterior");
        System.out.println("caminho        - Mostra o caminho absoluto");
        System.out.println("ajuda          - Mostra os comandos disponíveis");
        System.out.println("sair           - Encerra o programa");

        System.out.println("======================================");
    }
}
