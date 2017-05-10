import java.util.Scanner;

public class TrabalhoGrafos {
    //constantes para facilitar a organização do menu
    static final int sair = 0,
                     carregarArquivo = 1,
                     adicionarVertice = carregarArquivo + 1,
                     mostrarVertices = adicionarVertice + 1,
                     mostrarAdjacentes = mostrarVertices + 1,
                     
                     adicionarAresta = mostrarAdjacentes + 1,
                     verificarAresta = adicionarAresta + 1,
                     mostrarArestas = verificarAresta + 1,
                     removerAresta = mostrarArestas + 1,
            
                     grau = removerAresta + 1,
                     grauMedio = grau + 1,
                     grauMinimo = grauMedio + 1,
                     grauMaximo = grauMinimo + 1,
            
                     isConexo = grauMaximo + 1,
                     isCompleto = isConexo + 1,
                     matrizAdjacencias = isCompleto + 1,
                     caminhoEuler = matrizAdjacencias + 1,
					 warshall = caminhoEuler + 1;
                     
    static String caminhoGrafo = "/home/douglas/.workspace/TrabalhoGrafos/src/Exemplos Grafos/grafoConexo3Vertices";

	private static Scanner entrada;
    
    public static void main(String[] args) {
        Grafo g = new Grafo(caminhoGrafo);
        Vertice v, v2;
        Aresta a;
        int opcao = 1;
        String nome;
        entrada = new Scanner(System.in);
        
        menu();
        while (opcao != 0) {
            System.out.print("\nDigite sua opção: ");
            opcao = entrada.nextInt();
            System.out.println();
            
            switch (opcao) {
                default:
                    System.out.println("Opção inválida");
                    break;
                    
                case carregarArquivo: //carregar um novo arquivo
                    System.out.print("Digite o caminho do arquivo sem espaços: ");
                    nome = entrada.next();
                    g.carregarArquivo(nome);
                    break;

                case adicionarVertice: //adicionar vertices
                    System.out.print("Digite o nome do vertice: ");
                    nome = entrada.next();
                    v = new Vertice(nome);
                    g.addVertice(v);
                    break;
                      
                case mostrarVertices: //mostrar todos os vértices
                    g.imprimeVertices();
                    break;
                    
                case grau:
                    System.out.print("Digite o nome do vertice: ");
                    nome = entrada.next();
                    v = new Vertice(nome);
                    System.out.println(g.getGrau(v));
                    break;
                    
                case grauMedio:
                    System.out.println(g.getGrauMedio());
                    break;

                case grauMaximo:
                    g.getGrauMaximo().getNome();
                    break;

                case grauMinimo:
                    g.getGrauMinimo().getNome();
                    break;

                case isConexo: //conexo
                    System.out.println("Conexo? " + g.isConexo());
                    break;

                case isCompleto:
                    System.out.println("Completo? " + g.isCompleto());
                    break;
                    
                case adicionarAresta:
                    System.out.print("Digite o nome do vertice 1: ");
                    nome = entrada.next();
                    v = new Vertice(nome);
                    
                    System.out.print("Digite o nome do vertice 2: ");
                    nome = entrada.next();
                    v2 = new Vertice(nome);
                    
                    a = new Aresta(v,v2);
                    g.addAresta(a);
                    break;
                
                case removerAresta:
                    System.out.print("Digite o nome do vertice 1: ");
                    nome = entrada.next();
                    v = new Vertice(nome);
                    
                    System.out.print("Digite o nome do vertice 2: ");
                    nome = entrada.next();
                    v2 = new Vertice(nome);
                    
                    a = new Aresta(v,v2);
                    g.removeAresta(a);
                    break;
                    
                case verificarAresta:
                    System.out.print("Digite o nome do vertice 1: ");
                    nome = entrada.next();
                    v = new Vertice(nome);
                    
                    System.out.print("Digite o nome do vertice 2: ");
                    nome = entrada.next();
                    v2 = new Vertice(nome);
                    
                    a = new Aresta(v,v2);
                    System.out.println(g.arestaExist(a));
                    break;
                    
                case mostrarAdjacentes:
                    System.out.print("Digite o nome do vertice: ");
                    nome = entrada.next();
                    v = new Vertice(nome);
                    g.imprimeAdjacentes(v);
                    break;
                    
                case mostrarArestas:
                    g.imprimeArestas();
                    break;
                
                case caminhoEuler:
                    g.getEuler();
                    break;
                
                case matrizAdjacencias:
                    g.getMatrizAdjacencia();
                    break;
                    
                case warshall:
                    g.getWarshall();
                    break;
                    
                case sair:
                    System.out.println("Sair");
                    break;
            }
        }
    }

    private static void menu() {
        System.out.println(carregarArquivo + " - Carregar novo grafo");
        System.out.println(adicionarVertice + " - Inserir um novo vertice");
        System.out.println(mostrarVertices + " - Mostrar vertices");        
        System.out.println(mostrarAdjacentes + " - Mostrar vertices adjacentes:");
        System.out.println(adicionarAresta + " - Inserir aresta");
        System.out.println(verificarAresta + " - Verificar aresta");
        System.out.println(mostrarArestas + " - Mostrar arestas");
        System.out.println(removerAresta + " - Excluir aresta");
        System.out.println(grau + " - Grau de um vértice");
        System.out.println(grauMedio + " - Grau medio");
        System.out.println(grauMinimo + " - Grau minimo");
        System.out.println(grauMaximo + " - Grau maximo");
        System.out.println(isConexo + " - Verificar se é conexo");
        System.out.println(isCompleto + " - Verificar se é completo");
        System.out.println(matrizAdjacencias + " - Mostrar matriz de adjacências");
        System.out.println(caminhoEuler + " - Mostrar caminho de Euler");
        System.out.println(warshall + " - Aplicar Algoritmo de Warshall na matriz de adjacências");
        System.out.println(sair + " - Sair ");
    }
}
