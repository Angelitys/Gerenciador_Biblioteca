package br.edu.unisep.bibilioteca.model;

public class LivroFisico extends Livro {
    private int quantidadeExemplares;

    public LivroFisico(String titulo, Autor autor, Genero genero, int quantidadeExemplares) {
        super(titulo, autor, genero);
        this.quantidadeExemplares = quantidadeExemplares;
    }

    public int getQuantidadeExemplares() {
        return quantidadeExemplares;
    }

    public void setQuantidadeExemplares(int quantidadeExemplares) {
        this.quantidadeExemplares = quantidadeExemplares;
    }

    @Override
    public String getTipo() {
        return "Livro Físico";
    }
}
