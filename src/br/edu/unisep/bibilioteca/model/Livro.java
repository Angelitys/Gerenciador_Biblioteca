package br.edu.unisep.bibilioteca.model;

public abstract class Livro {
    private String titulo;
    private Autor autor;
    private Genero genero;
    private boolean disponivel;

    public Livro(String titulo, Autor autor, Genero genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponivel = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public Genero getGenero() {
        return genero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo() + ": " + titulo + " - " + autor.getNome() + " (" + genero.getNome() + ")";
    }
}
