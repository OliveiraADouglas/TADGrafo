

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
	éDirigido?
    nomeVertice1 nomeVertice2 ... nomeVerticeN 
    ;
    nomeVertice1 nomeVertice2 peso
    nomeVerticeN nomeVerticeN peso

	Primeira linha deve ser true/false que indica se o grafo é dirigido ou não
    Segunda linha contém os nomes dos vértices
    Terceira linha contém os pares para as arestas   
    Eles são divididos pelo caractere ";"
    TEM QUE EXISTIR PESO, MESMO QUE SEJA IRRELEVANTE
*/

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Scanner;

public final class Grafo{
    private ArrayList<Vertice> vertices; //guarda os vértices
    private ArrayList<Aresta> arestas; //guarda as arestas
    boolean isDirigido; //marca se o grafo é dirigido ou não
   
    public Grafo(){ //construtor sem arquivo
        vertices = new ArrayList<Vertice>(); 
        arestas = new ArrayList<Aresta>();
        isDirigido = false;
    }
    
    public Grafo(boolean d){
        vertices = new ArrayList<Vertice>(); 
        arestas = new ArrayList<Aresta>();
        isDirigido = d;
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
    
	private int getIVertice(Vertice v){ //retorna a posição do vértice na lista de vértices
		for(int i = 0; i < this.vertices.size(); ++i)
			if (this.vertices.get(i).getNome().equals(v.getNome()))
				return i;		
		
		return -1;
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
    
    public int[][] getMatrizAdjacencia(boolean imprime){
    	//conta com um flag que diz que a matriz deve ser imprimida depois do cálculo
        Aresta a;
        int quantVertice = this.vertices.size();
        int matrizAdjacencia [][] = new int[quantVertice][quantVertice];
        
        for(int i = 0; i < quantVertice; ++i){
            for(int i2 = 0; i2 < quantVertice; ++i2){
                a = new Aresta(this.vertices.get(i), this.vertices.get(i2));
                int quant = 0;
                
                if(arestaExist(a)){
                    quant = 1;
                }
                
                matrizAdjacencia[i][i2] = quant;
            }
        }
        
        if(imprime)
	        for(int i = 0; i < quantVertice; ++i){
	            System.out.print(this.vertices.get(i).getNome() + " ");
	            for(int i2 = 0; i2 < quantVertice; ++i2){
	                System.out.printf("%d ", matrizAdjacencia[i][i2]);
	            }
	            System.out.println();
	        }

        return matrizAdjacencia;
    }
    
    public int[][] getMatrizAdjacencia(){
    	//Somente calcula e retorna a matriz
        Aresta a;
        int quantVertice = this.vertices.size();
        int matrizAdjacencia [][] = new int[quantVertice][quantVertice];
        
        for(int i = 0; i < quantVertice; ++i){
            for(int i2 = 0; i2 < quantVertice; ++i2){
                a = new Aresta(this.vertices.get(i), this.vertices.get(i2));
                int quant = 0;
                
                if(arestaExist(a)){
                    quant = 1;
                }
                
                matrizAdjacencia[i][i2] = quant;
            }
        }
        
        return matrizAdjacencia;
    }
    
    public int[][] getWarshall(boolean imprime){
    	int M[][] = this.getMatrizAdjacencia();
    	int n = this.vertices.size();
    	
    	for (int k = 0; k < n ;k++){
    		for (int i = 0; i < n; i++){
    			for (int j = 0; j < n; j++ ){
    				M[i][j] = opOr(M[i][j], opAnd(M[i][k], M[k][j]));
    			}
    		}
    	}
        
        if (imprime)//imprime a matriz de resultado
           	for (int i = 0; i < n; ++i){
           		System.out.print(this.vertices.get(i).getNome() + " ");
           		for (int j = 0; j < n; ++j)
           			System.out.print(M[i][j] + " ");
           		System.out.println();
           	}
    	
		return M;
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
   
    public float[][] getMatrizPeso(boolean imprime){
        int quantVertice = this.vertices.size();
    	float mp [][] = new float [quantVertice][quantVertice];
    	Aresta a;
    	
    	for (int i = 0; i < quantVertice; ++i)
    		for (int j = 0; j < quantVertice; ++j){
    			a = new Aresta (this.vertices.get(i), this.vertices.get(j));
    			
    			mp[i][j] = this.getPesoAresta(a); 
    		}
    	
    	if(imprime)
	        for(int i = 0; i < quantVertice; ++i){
	            System.out.print(this.vertices.get(i).getNome() + " ");
	            for(int i2 = 0; i2 < quantVertice; ++i2){
	                System.out.printf("%.1f ", mp[i][i2]);
	            }
	            System.out.println();
	        }

    	return mp;
    }
    
    public float[][] getMatrizPeso(){
        int quantVertice = this.vertices.size();
    	float mp [][] = new float [quantVertice][quantVertice];
    	Aresta a;
    	
    	for (int i = 0; i < quantVertice; ++i)
    		for (int j = 0; j < quantVertice; ++j){
    			a = new Aresta (this.vertices.get(i), this.vertices.get(j));
    			
    			mp[i][j] = this.getPesoAresta(a); 
    		}
       	return mp;
    }

    private int posicaoMenorValor(ArrayList<Float> l){ //retorna a posicao do menor valor da lista
    	int posicao = 0;
    	float menorValor = 1;
    	
//    	//põe o primeiro menor valor encontrado na lista
//    	while (menor <= 1){
//    		menor = l.get(posicao);
//    		++posicao;
//    	}
    	
    	//calcula o menor valor
    	for(int i = 0; i < l.size(); ++i){
    		if(l.get(i) <= menorValor && l.get(i) >= 1.0){ 
    			menorValor = l.get(i);
    			posicao = i;
    		}    			
    	}
    	
    	return posicao;
    }
    
    public ArrayList<Vertice> getCaminhoMinimo(Vertice vInicio, Vertice vDestino){
    	//utiliza o algoritmo de Dijkstra

//    	Inicializa o conjunto IN e os vetores distancias e caminho
    	ArrayList<Float> distancias = new ArrayList<Float>();
    	ArrayList<Vertice> caminho = new ArrayList<Vertice>(),
    			   IN = new ArrayList<Vertice>();
    	int //quantVerticesForaDeIN = this.vertices.size() - 1, //guarda a quantidade de vértices que estão fora da lista IN
    		posicaoVNaMatriz, //guarda a posiçao da linha da matriz das adjacencias de um vertice
    		quantVGrafo = this.vertices.size(); //guarda a quantidade de vertices no grafo
    	float matrizPeso[][] = this.getMatrizPeso();
    	
    	IN.add(vInicio);
    	posicaoVNaMatriz = this.getIVertice(vInicio);
    	
    	for(int i = 0; i < quantVGrafo; ++i){
    		distancias.add(matrizPeso[posicaoVNaMatriz][i]);
    		caminho.add(vInicio);
    	}
    	
//    	Enquanto vDestino não pertence a IN faça	// loop enquanto recalcula o vetor distancias    	
    	while(!this.existe(vDestino, IN)){

//    		p = calcula o vértice de distancia mínima onde p  IN
//    		IN = IN  {p}
                int p = posicaoMenorValor(distancias);
    		IN.add(this.vertices.get(p));
    		//--quantVerticesForaDeIN;
    		
//    		Para todos os vértices z não pertencentes a IN faça
//    		for(int i = 0; i < quantVerticesForaDeIN; ++i){
                for(int i = 0; i < quantVGrafo; ++i){
                    if (!this.existe(this.vertices.get(i), IN)){
//    			distanciaAnterior = d[z]
                        float distanciaAnterior = distancias.get(p),
                              novaDistancia = matrizPeso[p][i] + distancias.get(p);                        
                        distancias.set(i, novaDistancia);
                        
//    			d[z] = min(d[z], d[p] + A[p,z])
                        if (distanciaAnterior > novaDistancia){
                            caminho.set(i, IN.get(IN.size() - 1));
                        }
//    			Se d[z]  distanciaAnterior então s[z] = p
                    }// Fim if de todos os vertices não pertencentes a IN
    		}//Fim para
    	}//Fim Enquanto

    	
//    	Imprime o Caminho
    	for(int i = 0; i < caminho.size(); ++i)
    		System.out.print(caminho.get(i).getNome() + "    ");
    	
    	return IN;
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
	
	//Algoritmo de Ford(matriz booleana nxn mp,v1){
/*public int[][] getFord(){	
	
        ArrayList<Float> distancias = new ArrayList<Float>();
    	ArrayList<Vertice> caminho = new ArrayList<Vertice>(),
        IN = new ArrayList<Vertice>();
        
	float A[][] = this.getMatrizPeso();
	int n = this.vertices.size();
	
	d[x] = 0;
	v = vDestino;
for (z = 1; z < n; z++)
{
	if (z = x)
	{
		d[z] = A[x,z];
	}else
			d[z] = A[x,z];
}
	for (i=1;i<n-1;;i++)
	{
		for (u =1;u<n;u++)
		{
			for (v=1;v<v++;
			{
				distanciaAnterior = d[v];
				d[v] = min(d[v], d[u] + A[u,v]);
				if(d[v]!= distanciaAnterior 
				s[v]=u;
			}
		}
	}
	
}*/

    public float[][] getFloyd(){	
		float A[][] = this.getMatrizPeso();
		int n = this.vertices.size();
                
		for (int k = 1; k < n; k++)
		{
			for (int i = 1; i < n; i++)
			{
				for (int j = 1; j < n; j++)
				{
					if(A[i][k] + A[k][j] < A[i][j])
					{
						A[i][j] = A[i][k] + A[k][j];
					}
			    }
			}	
		}
		return A;
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
    
    public ArrayList<Aresta> imprimeAdjacentes(Vertice v){
        ArrayList<Aresta> va = this.getAdjacente(v);
        
        for(int i = 0; i < va.size(); ++i){
            if(v.getNome().equals(va.get(i).getVertice1().getNome())) //se o primeiro vértice da aresta tiver o mesmo nome que o vértice pedido
                System.out.println(va.get(i).getVertice2().getNome()); //imprime o adjacente a ele
            
            else //caso contrário
                System.out.println(va.get(i).getVertice1().getNome()); //imprime o adjacente a ele
        }
        
        return va;
    }
    
    public boolean verticeExist (Vertice v){
        for(int i = 0; i < this.vertices.size(); ++i)
            if(this.vertices.get(i).getNome().equals(v.getNome())) //se o objeto requerido for o mesmo que foi recebido da lista
                return true; //retorna verdadeiro
            
        return false; //se não existir, retorna falso
    }
    
    private boolean existe(Vertice v, ArrayList<Vertice> l){
        //testa se o vertice existe na lista
        
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
    	if (isDirigido){
    		for(int i = 0; i < this.arestas.size(); ++i)
	            if(a.getVertice1().getNome().equals(this.arestas.get(i).getVertice1().getNome()) && 
	               a.getVertice2().getNome().equals(this.arestas.get(i).getVertice2().getNome())){ 
	               //se o nome do vertice for igual a um dos dois vertices da aresta
	                        this.arestas.remove(i);
	                        System.out.println(a.getVertice1().getNome() + " ---- " + a.getVertice2().getNome() + " removida");
	                        return; //força a saída do método
	            }
    		
    	}
    	
    	//se o grafo não for dirigido
	    else
	        for(int i = 0; i < this.arestas.size(); ++i){
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
	                        System.out.println(a.getVertice1().getNome() + " ---- " + a.getVertice2().getNome() + " removida");
	                        return; //força a saída do método
	            }
	        }

    	System.out.println(a.getVertice1().getNome() + " ---- " + a.getVertice2().getNome() + " não existe");
    }
    
    public void removeAresta(int i){
        this.arestas.remove(i);
    }
    
    public void imprimeArestas(){
        for(int i = 0; i < this.arestas.size(); ++i)
            System.out.println(this.arestas.get(i).getVertice1().getNome() + " ---- " + this.arestas.get(i).getVertice2().getNome());  
    }
    
    public float getPesoAresta(Aresta a){
    	//se a aresta não existe, retorna -1
    	//se é uma aresta de um vértice com ele mesmo, então o peso é 0
    	
    	if(a.getVertice1().getNome().equals(a.getVertice2().getNome()))
    		return 0;
    	
    	if (isDirigido){
    		for(int i = 0; i < this.arestas.size(); ++i)
    			if(a.getVertice1().getNome().equals(this.arestas.get(i).getVertice1().getNome()) && 
	               a.getVertice2().getNome().equals(this.arestas.get(i).getVertice2().getNome())){ 
	                        return this.arestas.get(i).getPeso(); //força a saída do método
	            }
    		
    	}
    	
    	//se o grafo não for dirigido
	    else
	        for(int i = 0; i < this.arestas.size(); ++i){
	            if(a.getVertice1().getNome().equals(this.arestas.get(i).getVertice1().getNome()) && 
	               a.getVertice2().getNome().equals(this.arestas.get(i).getVertice2().getNome())){ 
	               //se o nome do vertice for igual a um dos dois vertices da aresta
	            		return this.arestas.get(i).getPeso(); //força a saída do método
	            }	
	            //testa as arestas com as posições invertidas
	            else if(a.getVertice1().getNome().equals(this.arestas.get(i).getVertice2().getNome()) && 
	                    a.getVertice2().getNome().equals(this.arestas.get(i).getVertice1().getNome())){
	            			return this.arestas.get(i).getPeso(); //força a saída do método
	            }
	        }

    	return -1;
    }
    public boolean arestaExist (Aresta a){
    	if(this.isDirigido){ //se o grafo for dirigido
        	for(int i = 0; i < this.arestas.size(); ++i){
	            //verifica vertice1 da aresta com vertice1 da lista e vertice 2 da aresta com vertice2 da lista
	            if(this.arestas.get(i).getVertice1().getNome().equals(a.getVertice1().getNome()) &&
	               this.arestas.get(i).getVertice2().getNome().equals(a.getVertice2().getNome())) //se o objeto requerido for o mesmo que foi recebido da lista
	                            return true; //retorna verdadeiro
        	}
        	
        	return false; //se não encontrou a aresta, retorna falso
        }
    	
	    else
	    	//se o grafo não for dirigido, vai testar ambas as posições da aresta
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
    public boolean isDirigido() {
		return isDirigido;
	}
    
    public void setDirigido(boolean isDirigido) {

    	try{
    		this.isDirigido = isDirigido;
    	}catch(Exception e){
    		
    	}
	}

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
                this.isDirigido = arqGrafoIn.nextBoolean(); //lê o flag que diz se o grafo é dirigido
                System.out.println("É dirigido: " + isDirigido);
                
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
            System.out.println("Arquivo não existe");
        } catch (InputMismatchException ex){
            //Permite que o programa não trave caso haja alguma inconsistencia no arquivo
            System.out.println("Arquivo com formato incorreto");            
        } catch (Exception ex){
            //Caso haja algum outro erro
            System.out.println("Erro: " + ex);
        }
        
    }//fim da função carregarArquivo

    }//fim da classe