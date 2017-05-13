# TADGrafo
Tipo abstrato de dados para controlar um grafo

Autor: Douglas Almeida de Oliveira
Última atualização: 13/05/2017 20:40

Update log:
* Classe Grafo:
	- Uma lista que armazena os vértices.
	- Uma lista que armazena as arestas.
	- Atributo que diz se o grafo é dirigido. (11/05/2017)
		- set e get.
	- Carrega as informações do disco. Veja ajuda para saber o padrão do arquivo para leitura correta.
	- Sabe se existe um caminho de Euler.
	- Calcula matriz de adjacência. (04/05/2017)
		- Se for passado "true" no argumento, a matriz também será impressa. (13/05/2017)
		- False é argumento padrão. (13/05/2017)
	- Aplica o Algoritmo de Warshall na matriz de adjacências. (10/05/2017)
		- Se for passado "true" no argumento, a matriz calculada também será impressa. (13/05/2017)
		- False é argumento padrão. (13/05/2017)
	- Calcula a matriz de pesos. (13/05/2017)
		- Se for passado "true" no argumento, a matriz também será impressa. (13/05/2017)
		- False é argumento padrão. (13/05/2017)
	- Método privado que faz operação and lógico entre duas variáveis numéricas sendo que devem ter valor 1 ou 0. (11/05/2017)
	- Método privado que faz operação or lógico entre duas variáveis numéricas sendo que devem ter valor 1 ou 0. (11/05/2017)
	- Método privado que põe 0 em todas as posições de uma matriz. (11/05/2017)

	- Métodos relacionados a Vértices:
		- Adiciona.
		- Retorna por nome ou por posição. 
		- Retorna a quantidade de vértices.
		- getGrau. Retorna o grau de um vértice passado por parametro.
		- grau maior, grau menor e grau médio de todo o grafo.
		- Imprime a lista de vértices.
		- Imprime os vértices adjacentes ao passado por parâmentro e retorna uma lista com as aresta conectadas a ele.
		- Diz se vértice passado por parametro existe no grafo.
		- Método privado que testa se um vértice existe numa lista de vértices, ambos passados por parâmetros.

	- Métodos relacionados a Aresta:
		- Se o grafo for dirigido, a aresta se comporta de forma diferente. Para grafos não dirigidos, é possível ir de um vertice a outro pela mesma aresta. (11/05/2017)
		- Adiciona e remove uma aresta passada por parametro ou pela posicao na lista.
		- Diz se aresta passada por parâmetro existe no grafo.
		- Retorna o peso de uma aresta. Se a aresta não existir, retorna 0. (13/05/2017)
	
* Classe Vértice:
	- Nome do vértice que é o que o identifica.

* Classe Aresta:
	- Nome dos dois vértices
	- Peso da aresta
	- O menor peso possível é 1. Não é possível setar um valor menor que esse. (13/05/2017)	


Ajuda:
* Para carregar o grafo de um arquivo:
	- O arquivo deve ter esse padrão:
		true
		nomeVertice1 nomeVertice2 nomeVertice3
		;
		nomeVertice1 nomeVertice2 pesoDaAresta
	
	- Na primeira linha é se o grafo é dirigido (true) ou não-dirigido (false)
	- Na segunda linha são os nomes de vértices
	- ";" define o ponto final para declaração dos vértices e início da leitura das arestas
	- Na terceira linha são os pares de vértices que formam uma aresta com o peso(pesoDaAresta) que deve ser um número inteiro ou decimal
