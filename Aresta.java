

public class Aresta {
    private final int menorPeso = 1;
    private Vertice Vertice1, Vertice2;
    private float Peso;

    public Aresta(Vertice nomeVertice1, Vertice nomeVertice2, float Peso) {
        this.Vertice1 = nomeVertice1;
        this.Vertice2 = nomeVertice2;
        if (Peso < menorPeso)
        	this.Peso = menorPeso;
        else
        	this.Peso = Peso;
    }
    
    public Aresta(Vertice nomeVertice1, Vertice nomeVertice2) {
        this.Vertice1 = nomeVertice1;
        this.Vertice2 = nomeVertice2;
        this.Peso = menorPeso;
    }
    
    public Vertice getVertice1() {
        return Vertice1;
    }

    public Vertice getVertice2() {
        return Vertice2;
    }
    
    public float getPeso() {
        return Peso;
    }
    
    public void setPeso(float peso) {
        if (peso < menorPeso) //se o peso que quiser ser inserido for menor que o mínimo possível
        	this.Peso = menorPeso;
        else
        	this.Peso = peso;
    }

    public void setNomeVertice1(String nome) {
        this.Vertice1.setNome(nome);
    }

    public void setNomeVertice2(String nome) {
        this.Vertice2.setNome(nome);
    }
}
