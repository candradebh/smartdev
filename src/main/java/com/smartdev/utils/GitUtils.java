package com.smartdev.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GitUtils {

    // Método para criar uma pasta
    public static void createDirectory(String directoryName) throws IOException {
        Path path = Paths.get(directoryName);

        // Verifica se o diretório já existe
        if (Files.exists(path)) {
            throw new IOException("A pasta já existe: " + directoryName);
        }

        // Cria o diretório
        Files.createDirectories(path);
        System.out.println("Pasta criada com sucesso: " + directoryName);
    }

    // Método para iniciar um repositório Git
    public static void initGitRepository(String directoryName) throws IOException, InterruptedException {
        File dir = new File(directoryName);

        // Verifica se o diretório existe
        if (!dir.exists()) {
            throw new IOException("Diretório não existe: " + directoryName);
        }

        // Comando para inicializar o repositório Git
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(dir); // Define o diretório onde o comando será executado
        processBuilder.command("git", "init");

        // Executa o comando
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        // Verifica se o processo foi bem-sucedido
        if (exitCode == 0) {
            System.out.println("Repositório Git iniciado com sucesso em: " + directoryName);
        } else {
            throw new IOException("Falha ao iniciar o repositório Git.");
        }
    }
}
