

/*
    Contém a lista de todos os vértices e arestas
    Essa classe é que faz o controle de inserção e remoção de vértices e arestas
    O nome do vértice é sua chave, não podem existir dois vértices com o mesmo nome    
    Uma arestas só pode ser inserida se os dois vértices existirem
    Para criar um novo objeto desse tipo, passe o nome do arquivo que deseja ler ou use a função "carregarArquivo" ou insira manualmente cada valor
    Caso o arquivo não exista, não será carregado nenhum dado
*/

/*
    Estrutura do arquivo que guarda os dados dos grafos deve ser a seguinte:

    nomeVertice1 nomeVertice2 ... nomeVerticeN 
    ;
    nomeVertice1 nomeVertice2 peso
    nomeVerticeN nomeVerticeN peso

    Primeira linha contém os nomes dos vértices
    Segunda linha contém os pares para as arestas   
    Eles são divididos pelo caractere ";"
    TEM QUE EXISTIR PESO, MESMO QUE SEJA IRRELEVANTE
*/

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public final class Grafo{
    private ArrayList<Vertice> vertices; //guarda os vértices
    private ArrayList<Aresta> arestas; //guarda as arestas
   
    public Grafo(){ //construtor sem arquivo
        vertices = new ArrayList<Vertice>(); 
        arestas = new ArrayList<Aresta>();
    }
    
    public Grafo(String nomeDoArquivo){ //construtor que carrega os dados do arquivo
        //as listas são iniciadas no método carregarArquivo
        this.carregarArquivo(nomeDoArquivo);
    }
    
//    Getters
     public Vertice getVertice(int posicao){ //retorna vertice pela posição na lista
        return this.vertices.get(posicao);
    }
    
    public Vertice getVertice(String nome){ //retorna vertice pelo nome
        for(int i = 0; i < this.vertices.size(); ++i)
            if(nome.equals(this.vertices.get(i).getNome())) //se o nome requerido for o mesmo que foi recebido da lista
                return this.vertices.get(i); //retorna objeto
        
        return null; //retorna nulo se não achar objeto
    }
    
    public ArrayList<Aresta> getAdjacente(Vertice v){ //retorna uma lista que contém todas as arestas referentes a v
        ArrayList<Aresta> adjacentes = new ArrayList<Aresta>(); //inicia a lista
        
        for(int i = 0; i < this.arestas.size(); ++i)
            if(v.getNome().equals(this.arestas.get(i).getVertice1().getNome()) || 
               v.getNome().equals(this.arestas.get(i).getVertice2().getNome())) //se o nome do vertice for igual a um dos dois vertices da aresta
                    adjacentes.add(this.arestas.get(i)); //adiciona aresta na lista
            
        return adjacentes;
    }
    
    public float getGrauMedio(){
        try{ //se houver algum erro, como divisão por zero
            return (float) (this.arestas.size())/this.vertices.size();
            
        }catch (Exception e){
            return -1; //retorna grau negativo indicando erro
        }            
    }
    
    public Vertice getGrauMinimo(){
        try{ //se houver algum erro na execução, como lista de vértices vazia
            Vertice menor = this.vertices.get(0); //recebe o primeiro vertice da lista

            for(int i = 0; i < this.vertices.size(); ++i)
                if(this.getGrau(menor) > this.getGrau(this.vertices.get(i))) //se for achado outro vertice com grau menor
                    menor = this.vertices.get(i); //então esse vértice é o de menor grau
            
            System.out.println(menor.getNome() + " " + getGrau(menor));
            return menor;

        } catch (Exception e){ //se houver algum erro
            System.out.println(e);
            return null; //retorna nulo
        }
    }
    
    public Vertice getGrauMaximo(){
        try{ //se houver algum erro na execução, como lista de vértices vazia
            Vertice maior = this.vertices.get(0); //recebe o primeiro vertice da lista

            for(int i = 0; i < this.vertices.size(); ++i)
                if(this.getGrau(maior) < this.getGrau(this.vertices.get(i))) //se for achado outro vertice com grau maior
                    maior = this.vertices.get(i); //então esse vértice é o de menor grau
            
            System.out.println(maior.getNome() + " " + getGrau(maior));
            return maior;

        } catch (Exception e){ //se houver algum erro
            System.out.println(e);
            return null; //retorna nulo
        }
    }
    
    public int getGrau(Vertice v){
        boolean existe = this.verticeExist(v);
        
        if(existe){ //só calcula se o vertice exitir
            ArrayList<Aresta> adjacentes = this.getAdjacente(v);
            
            for (int i = 0; i < adjacentes.size(); ++i) //testa se existe algum loop
                if (adjacentes.get(i).getVertice1().getNome().equals(adjacentes.get(i).getVertice1().getNome())) //se achar algum loop
                    return this.getAdjacente(v).size() + 1; //retorna o tamanho da lista de adjacentes a ele mais um loop
                
            return this.getAdjacente(v).size(); //retorna o tamanho da lista de adjacentes a ele se nenhum loop for achado
        } 
        else
            System.out.println("Vértice não existe");
        
        return -1; //se não existir
    }
    
    public int getQuantVertice(){ //retorna a quantidade de vértices do grafo
        return this.vertices.size();
    }
    
//    private int[][] zerarMatriz(int [][] ma, int lin, int col){
//        for (int i = 0; i < lin; ++i)
//            for(int i2 = 0; i2 < col; ++i2)
//                ma[i][i2] = 0;
//        
//        return ma;
//    }
    
    public int[][] getMatrizAdjacencia(){
        Aresta a;
        int quantVertice = this.vertices.size();
        int matrizAdjacencia [][] = new int[quantVertice][quantVertice];
        
        for(int i = 0; i < quantVertice; ++i){
            for(int i2 = 0; i2 < quantVertice; ++i2){
                a = new Aresta(this.vertices.get(i), this.vertices.get(i2));
                int quant = 0;
                
                if(arestaExist(a)){
                    if(this.vertices.get(i).getNome().equals(this.vertices.get(i2).getNome()))
                    //quando os dois vértices são iguais, conta como duas arestas. É um loop
                        quant += 1;
                    
                    quant += 1;
                }
                
                matrizAdjacencia[i][i2] = quant;
            }
        }
        
        for(int i = 0; i < quantVertice; ++i){
            System.out.print(this.vertices.get(i).getNome() + " ");
            for(int i2 = 0; i2 < quantVertice; ++i2){
                System.out.printf("%d ", matrizAdjacencia[i][i2]);
            }
            System.out.println();
        }
        
        return matrizAdjacencia;
    }
    
    public void getCaminhoMinimo(){
    	//utiliza o algoritmo de Dijkstra
    	
    }
    
    
    public int[][] getWarshall(){
    	int M[][] = this.getMatrizAdjacencia();
    	int n = this.vertices.size();
    	
    	for (int k = 0; k < n ;k++){
    		for (int i = 0; i < n; i++){
    			for (int j = 0; j < n; j++ ){
    				M[i][j] = opOr(M[i][j], opAnd(M[i][k], M[k][j]));
    			}
    		}
    	}
        
        //imprime a matriz de resultado
    	System.out.println("\nWarshall");
        for (int i = 0; i < n; ++i){
        	System.out.print(this.vertices.get(i).getNome() + " ");
            for (int j = 0; j < n; ++j)
                System.out.print(M[i][j] + " ");
            System.out.println();
        }
    	
		return M;
    }
    
    private int opAnd (int i, int j){ //faz and lógico entre i e j. Ambos tem que ter valor 0 ou 1
    	int resultado = 0;
    	
    	if(i == 1 && j == 1)
    		resultado = 1;
    	
    	return resultado;
    }
    
    private int opOr (int i, int j){ //faz or lógico entre i e j. Ambos tem que ter valor 0 ou 1
    	int resultado = 1;
    	
    	if(i == 0 && j == 0)
    		resultado = 0;
    	
    	return resultado;
    }
    
    private boolean grauPar(){ //usada em getEuler
        int i;
        
        for (i = 0; i< this.vertices.size(); ++i){
            if(getGrau(this.vertices.get(i)) % 2 != 0){ //se o grau de algum vértice for ímpar
                return false;
            }
        }
        
        return true;
    }
            
            
    public void getEuler(){
        if (!this.isConexo() || !grauPar()){ //se o grafo não é conexo ou se todos os graus não forem par
            System.out.println("Não existe caminho de Euler");
        }
        
        else{
            System.out.println("Existe caminho");
        }
    }
    
//    Métodos de vertices
    public void addVertice(Vertice vertice){ 
        if(!verticeExist(vertice)){ //se o vertice não existe, então pode ser adicionado
            this.vertices.add(vertice);
            System.out.println(vertice.getNome() + " adicionado");
        } else {
            System.out.println(vertice.getNome() + " já existe");
        }
    }
    
    public void imprimeVertices(){
        for(int i = 0;i < this.vertices.size();++i){
            System.out.println(this.vertices.get(i).getNome());
        }
        System.out.println(this.getQuantVertice() + " vertices");
    }
    
    public void imprimeAdjacentes(Vertice v){
        ArrayList<Aresta> va = this.getAdjacente(v);
        
        for(int i = 0; i < va.size(); ++i){
            if(v.getNome().equals(va.get(i).getVertice1().getNome())) //se o primeiro vértice da aresta tiver o mesmo nome que o vértice pedido
                System.out.println(va.get(i).getVertice2().getNome()); //imprime o adjacente a ele
            
            else //caso contrário
                System.out.println(va.get(i).getVertice1().getNome()); //imprime o adjacente a ele
        }
    }
    
    public boolean verticeExist (Vertice v){
        for(int i = 0; i < this.vertices.size(); ++i)
            if(this.vertices.get(i).getNome().equals(v.getNome())) //se o objeto requerido for o mesmo que foi recebido da lista
                return true; //retorna verdadeiro
            

        return false; //se não existir, retorna falso
    }
    
    private boolean existe(Vertice v, ArrayList<Vertice> l){
        //testa se o vertice existe na lista
        //uso no método isConexo
        
        for(int i = 0; i < l.size(); ++i)
            if(l.get(i).getNome().equals(v.getNome())) //se o objeto requerido for o mesmo que foi recebido da lista
                return true; //retorna verdadeiro
            
        return false; //se não existir, retorna falso
    }
        
//    Métodos de aresta
    public void addAresta(Aresta aresta){
        if(verticeExist(aresta.getVertice1()) && verticeExist(aresta.getVertice2())){
            this.arestas.add(aresta);
            System.out.println(aresta.getVertice1().getNome() + " ---- " + aresta.getVertice2().getNome() + " adicionada");
        }
        else
            System.out.println("Um dos vertices não existe");
    }
    
    public void removeAresta(Aresta a){
        for(int i = 0; i < this.arestas.size(); ++i)
            if(a.getVertice1().getNome().equals(this.arestas.get(i).getVertice1().getNome()) && 
               a.getVertice2().getNome().equals(this.arestas.get(i).getVertice2().getNome())){ 
               //se o nome do vertice for igual a um dos dois vertices da aresta
                        this.arestas.remove(i);
                        System.out.println(a.getVertice1().getNome() + " ---- " + a.getVertice2().getNome() + " removida");
                        return; //força a saída do método
            }
            //testa as arestas com as posições invertidas
            else if(a.getVertice1().getNome().equals(this.arestas.get(i).getVertice2().getNome()) && 
                    a.getVertice2().getNome().equals(this.arestas.get(i).getVertice1().getNome())){
                        this.arestas.remove(i);
                        return; //força a saída do método
            }
    }
    
    public void removeAresta(int i){
        this.arestas.remove(i);
    }
    public void imprimeArestas(){
        for(int i = 0; i < this.arestas.size(); ++i)
            System.out.println(this.arestas.get(i).getVertice1().getNome() + " ---- " + this.arestas.get(i).getVertice2().getNome());
        
    }
    
    public boolean arestaExist (Aresta a){
        for(int i = 0; i < this.arestas.size(); ++i){
            //verifica vertice1 da aresta com vertice1 da lista e vertice 2 da aresta com vertice2 da lista
            if(this.arestas.get(i).getVertice1().getNome().equals(a.getVertice1().getNome()) &&
               this.arestas.get(i).getVertice2().getNome().equals(a.getVertice2().getNome())) //se o objeto requerido for o mesmo que foi recebido da lista
                            return true; //retorna verdadeiro
            
            //verifica em posições invertidas
            //vertice1 da aresta com vertice2 da lista e vertice 2 da aresta com vertice1 da lista
            else if(this.arestas.get(i).getVertice1().getNome().equals(a.getVertice2().getNome()) &&
                    this.arestas.get(i).getVertice2().getNome().equals(a.getVertice1().getNome())) //se o objeto requerido for o mesmo que foi recebido da lista
                            return true; //retorna verdadeiro
        }
        return false;
    }//fim do método arestaExist
    
//    Métodos de grafo
    public boolean isConexo(){ 
        //usa algoritmo de busca em nível
        
        ArrayList<Vertice> fila = new ArrayList<Vertice>();
        ArrayList<Vertice> visitados = new ArrayList<Vertice>(); //todos os vertices que foram visitados estarão nessa lista
        ArrayList<Aresta> adjacentes; //armazena temporariamente os adjacentes de um vertice durante a busca
        Vertice va; //armazena temporariamente um vertice adjacente a N para ser tratado com vertice e não como aresta
         
        fila.add(this.vertices.get(0));
        visitados.add(this.vertices.get(0)); //marca que o primeiro vertice foi visitado
        
        while(!fila.isEmpty()){
            adjacentes = this.getAdjacente(fila.get(0));
            
            for(int i = 0; i < adjacentes.size(); ++i){
                
                //pega o vertice da lista de adjacentes e o guarda em um variavel do tipo Vertice
                if (!adjacentes.get(i).getVertice1().getNome().equals(fila.get(0).getNome())) 
                    va = adjacentes.get(i).getVertice1();
                else
                    va = adjacentes.get(i).getVertice2();
                   
                //testa se o vertice foi visitado
                if (!existe(va, visitados)){
                    //se não foi visitado
                    visitados.add(va); //marca como visitado
                    fila.add(va); //adiciona na fila
                } //fim do if
                
            } //fim do for
            
            fila.remove(0);
        }//fim do while
        
        if(visitados.size() == this.vertices.size())
            //se a lista de vertices do grafo tiver a mesma quantidade de vertices da lista de visitados, então o grafo é conexo
            return true;

//        se passar por tudo e não for conexo, retorna falso
        return false;
    }
    
    public boolean isCompleto(){ //verifica se o grafo é conexo
     Aresta a = null;
     
        try{
            for (int primeiro = 0; primeiro < this.vertices.size(); ++primeiro)
                for(int segundo = 0; segundo < this.vertices.size(); ++segundo){ 
                //vai testar todos os pares de arestas possível com o conjunto de vértices
                //se um desses não existir, então o grafo não é conexo
                
                    if (primeiro != segundo){ //só vai testar pares de vertices diferentes
                        a = new Aresta(this.vertices.get(primeiro), this.vertices.get(segundo));
                        
                        if(!this.arestaExist(a))
                            return false; //retorna que o grafo não é conexo
                    }
                }

            //se ao final de tudo não houver nenhum "false", então o grafo é conexo
            return true;
        } catch (Exception e){
            System.out.println(e);
        }   
        
        return false;
    }//fim do método isCompleto
    
    public void carregarArquivo(String nomeDoArquivo){ //reseta todos os dados para um novo arquivo
        System.out.println("\nTentando abrir arquivo: " + nomeDoArquivo);
        
        this.arestas = new ArrayList<Aresta>();
        this.vertices = new ArrayList<Vertice>();
        
        Aresta a;
        Vertice v1, v2;
        Scanner arqGrafoIn; //scanner do arquivo
        
        try { //tenta abrir o arquivo que contém os dados do grafo
            arqGrafoIn = new Scanner(new FileReader(nomeDoArquivo));
            
            //lê do arquivo
            if (arqGrafoIn.hasNext()){ //se houver dados no arquivo
                //lê vértices
                String leitura = arqGrafoIn.next(); //lê o primeiro valor
                while(!leitura.equals(";")){ //enquanto não achar o caractere separador
                    v1 = new Vertice(leitura); //seta o nome do vertice

                    this.addVertice(v1); //adiciona o vértice
                    
                    leitura = arqGrafoIn.next(); //lê o próximo valor
                } //fim da leitura de vértices

                //lê arestas
                //não é permitido criar uma aresta entre dois vértices se um deles não estiver registrado no grafo
                while (arqGrafoIn.hasNext()) {//lê todo o resto até o final do arquivo
                    //lê as arestas
                    v1 = new Vertice (arqGrafoIn.next());
                    v2 = new Vertice (arqGrafoIn.next());
                    float peso = arqGrafoIn.nextFloat();
                    a = new Aresta(v1, v2, peso);

                    this.addAresta(a);
                    
                }//fim da leitura de arestas
            }//fim da carga de arquivos
            
        } catch (FileNotFoundException ex) {
            //Permite que o programa não trave caso o arquivo solicitado não exista
            System.out.println(ex);
        }
    }//fim da função carregarArquivo
        
    
}//fim da classe