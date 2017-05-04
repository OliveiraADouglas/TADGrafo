

public class Aresta {
    private final int menorPeso = 0;
    private Vertice Vertice1, Vertice2;
    private float Peso;

    public Aresta(Vertice nomeVertice1, Vertice nomeVertice2, float Peso) {
        this.Vertice1 = nomeVertice1;
        this.Vertice2 = nomeVertice2;
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
    
    public void setPeso(float peso) {
        this.Peso = peso;
//        if(peso < menorPeso) //se o peso que quiser ser inserido for menor que o mínimo possível
//            this.Peso = menorPeso;
    }
    
    public float getPeso() {
        return Peso;
    }

    public void setNomeVertice1(String nome) {
        this.Vertice1.setNome(nome);
    }

    public void setNomeVertice2(String nome) {
        this.Vertice2.setNome(nome);
    }
}
