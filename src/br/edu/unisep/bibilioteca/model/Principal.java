package br.edu.unisep.bibilioteca.model;
import javax.swing.*;
import java.util.ArrayList;

public class Principal {
    private static ArrayList<Livro> livros = new ArrayList<>();
    private static ArrayList<Autor> autores = new ArrayList<>();
    private static ArrayList<Genero> generos = new ArrayList<>();
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Emprestimo> emprestimos = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;

        do {
            String menu = """
                    Biblioteca - Escolha uma opção:
                    1. Cadastrar Livro
                    2. Cadastrar Autor
                    3. Cadastrar Gênero
                    4. Cadastrar Usuário
                    5. Realizar Empréstimo
                    6. Devolver Livro
                    7. Consultar Livros Disponíveis
                    8. Sair
                    """;

            String input = JOptionPane.showInputDialog(menu);

            if (input == null || input.isEmpty()) break;

            try {
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1 -> cadastrarLivro();
                    case 2 -> cadastrarAutor();
                    case 3 -> cadastrarGenero();
                    case 4 -> cadastrarUsuario();
                    case 5 -> realizarEmprestimo();
                    case 6 -> devolverLivro();
                    case 7 -> consultarLivrosDisponiveis();
                    case 8 -> JOptionPane.showMessageDialog(null, "Saindo...");
                    default -> JOptionPane.showMessageDialog(null, "Opção inválida.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida.");
            }
        } while (true);
    }

    private static void cadastrarLivro() {
        String titulo = JOptionPane.showInputDialog("Título do Livro:");
        if (titulo == null || titulo.isEmpty()) return;

        Autor autor = selecionarAutor();
        if (autor == null) {
            JOptionPane.showMessageDialog(null, "Nenhum autor disponível. Cadastre autores primeiro.");
            return;
        }

        Genero genero = selecionarGenero();
        if (genero == null) {
            JOptionPane.showMessageDialog(null, "Nenhum gênero disponível. Cadastre gêneros primeiro.");
            return;
        }

        String tipo = JOptionPane.showInputDialog("Tipo de Livro (1 - Físico, 2 - Digital):");

        if ("1".equals(tipo)) {
            int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Exemplares:"));
            livros.add(new LivroFisico(titulo, autor, genero, quantidade));
        } else if ("2".equals(tipo)) {
            double tamanho = Double.parseDouble(JOptionPane.showInputDialog("Tamanho do Arquivo (MB):"));
            livros.add(new LivroDigital(titulo, autor, genero, tamanho));
        } else {
            JOptionPane.showMessageDialog(null, "Tipo inválido. Livro não cadastrado.");
        }
    }

    private static void cadastrarAutor() {
        String nome = JOptionPane.showInputDialog("Nome do Autor:");
        if (nome != null && !nome.isEmpty()) {
            autores.add(new Autor(nome));
            JOptionPane.showMessageDialog(null, "Autor cadastrado com sucesso!");
        }
    }

    private static void cadastrarGenero() {
        String nome = JOptionPane.showInputDialog("Nome do Gênero:");
        if (nome != null && !nome.isEmpty()) {
            generos.add(new Genero(nome));
            JOptionPane.showMessageDialog(null, "Gênero cadastrado com sucesso!");
        }
    }

    private static void cadastrarUsuario() {
        String nome = JOptionPane.showInputDialog("Nome do Usuário:");
        if (nome != null && !nome.isEmpty()) {
            usuarios.add(new Usuario(nome));
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
        }
    }

    private static Autor selecionarAutor() {
        if (autores.isEmpty()) return null;

        String[] nomes = autores.stream().map(Autor::getNome).toArray(String[]::new);
        String selecionado = (String) JOptionPane.showInputDialog(null, "Selecione um Autor:",
                "Autor", JOptionPane.QUESTION_MESSAGE, null, nomes, nomes[0]);
        return autores.stream().filter(a -> a.getNome().equals(selecionado)).findFirst().orElse(null);
    }

    private static Genero selecionarGenero() {
        if (generos.isEmpty()) return null;

        String[] nomes = generos.stream().map(Genero::getNome).toArray(String[]::new);
        String selecionado = (String) JOptionPane.showInputDialog(null, "Selecione um Gênero:",
                "Gênero", JOptionPane.QUESTION_MESSAGE, null, nomes, nomes[0]);
        return generos.stream().filter(g -> g.getNome().equals(selecionado)).findFirst().orElse(null);
    }

    private static void realizarEmprestimo() {
        if (livros.isEmpty() || usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum livro ou usuário disponível para empréstimo.");
            return;
        }

        String[] titulos = livros.stream().filter(Livro::isDisponivel).map(Livro::getTitulo).toArray(String[]::new);
        if (titulos.length == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum livro disponível para empréstimo.");
            return;
        }

        String tituloSelecionado = (String) JOptionPane.showInputDialog(null, "Selecione um Livro:",
                "Empréstimo", JOptionPane.QUESTION_MESSAGE, null, titulos, titulos[0]);

        Livro livro = livros.stream().filter(l -> l.getTitulo().equals(tituloSelecionado)).findFirst().orElse(null);
        String[] usuariosNomes = usuarios.stream().map(Usuario::getNome).toArray(String[]::new);

        String usuarioSelecionado = (String) JOptionPane.showInputDialog(null, "Selecione um Usuário:",
                "Empréstimo", JOptionPane.QUESTION_MESSAGE, null, usuariosNomes, usuariosNomes[0]);

        Usuario usuario = usuarios.stream().filter(u -> u.getNome().equals(usuarioSelecionado)).findFirst().orElse(null);

        if (livro != null && usuario != null) {
            livro.setDisponivel(false);
            emprestimos.add(new Emprestimo(livro, usuario));
            JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
        }
    }

    private static void devolverLivro() {
        if (emprestimos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum empréstimo registrado.");
            return;
        }

        String[] emprestados = emprestimos.stream()
                .filter(e -> e.getDataDevolucao() == null)
                .map(e -> e.getLivro().getTitulo() + " - " + e.getUsuario().getNome())
                .toArray(String[]::new);

        if (emprestados.length == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum livro pendente para devolução.");
            return;
        }

        String selecionado = (String) JOptionPane.showInputDialog(null, "Selecione um Empréstimo para Devolução:",
                "Devolução", JOptionPane.QUESTION_MESSAGE, null, emprestados, emprestados[0]);

        Emprestimo emprestimo = emprestimos.stream()
                .filter(e -> (e.getLivro().getTitulo() + " - " + e.getUsuario().getNome()).equals(selecionado))
                .findFirst()
                .orElse(null);

        if (emprestimo != null) {
            emprestimo.devolver();
            JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso!");
        }
    }

    private static void consultarLivrosDisponiveis() {
        StringBuilder sb = new StringBuilder("Livros Disponíveis:\n");
        livros.stream().filter(Livro::isDisponivel).forEach(l -> sb.append(l).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
