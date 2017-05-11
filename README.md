# TADGrafo
Tipo abstrato de dados para controlar um grafo

Autor: Douglas Almeida de Oliveira
Última atualização: 11/05/2017 11:51

Aqui estão informadas todas as funções desse projeto e a data que foram adicionadas a ele

Update log:
* Classe Grafo:
	- Carrega as informações do disco. Veja ajuda para saber o padrão do arquivo para leitura correta
	- Adiciona e remove um vértice
	- Adiciona e remove uma aresta
	- Se o grafo for dirigido, a aresta se comporta de forma diferente. Para grafos não dirigidos, é possível ir de um vertice a outro pela mesma aresta.
	- Retorna grau de um vértice
	- Retorna o grau maior, grau menor e grau médio de todo o grafo
	- Sabe se existe um caminho de Euler
	- Imprima e retorna matriz de adjacencia
	- Algoritmo de Warshall (10/05/2017)
	- Atributo que diz se o grafo é dirigido (11/05/2017)
	- Método arestaExist é relacionado com o tipo do grafo. Se for dirigido, usa um código diferente do de grafo não dirigido (11/05/2017)
	
* Classe Vértice:
	- Nome do vértice que é o que o identifica

* Classe Aresta:
	- Nome dos dois vértices
	- Peso da aresta
	
	


Ajuda:
* Para carregar o grafo de um arquivo:
	- O arquivo deve ter esse padrão:
		true
		nomeVertice1 nomeVertice2 nomeVertice3
		;
		nomeVertice1 nomeVertice2 pesoDaAresta
	
	- Na primeira linha é se o grafo é dirigido (1) ou não-dirigido (0)
	- Na segunda linha são os nomes de vértices
	- ";" define o ponto final para declaração dos vértices e início da leitura das arestas
	- Na terceira linha são os pares de vértices que formam uma aresta com o peso(pesoDaAresta) que deve ser um número inteiro ou decimal