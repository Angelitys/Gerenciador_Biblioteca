package br.edu.unisep.bibilioteca.model;

public class LivroDigital extends Livro {
    private double tamanhoMB;

    public LivroDigital(String titulo, Autor autor, Genero genero, double tamanhoMB) {
        super(titulo, autor, genero);
        this.tamanhoMB = tamanhoMB;
    }

    public double getTamanhoMB() {
        return tamanhoMB;
    }

    @Override
    public String getTipo() {
        return "Livro Digital";
    }
}
