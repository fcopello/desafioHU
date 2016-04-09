# Desafio HU

### API

A API foi desenvolvida utilzando sailsJS um framework WebMVC NodeJS. O storage de dados, está utilzando a configuração default do framework, sendo assim, não será necessário plugar em nenhum banco. Não é o melhor aproach mas para uma solução standalone funcionou melhor. 


##### * Importante: 
Ao parsear as datas do arquivo disp.csv no `config/bootsrap.js` inseri o ano estático como <strong>2016</strong> para que a busca no app pudesse fazer sentido ao abrir o calendário com data atual.

##### * Performance e outros: 
Os dados de cidade não foram desnormalizados o que pode afetar o requisito performance do teste. Além disso o meu resultado de busca é impreciso uma vez que trago cidades ou hotéis naquela cidade (caso a busca seja por cidade).
<br/>Não realizei um teste de carga mas não acredito que o serivço aguente 1000 requisições/segundo (conforme estava na descrição do desafrio) mesmo com todos os dados em memória.



#### Comandos para isntalar as dependências da API

###### Para instalar as dependências

````code
sudo npm -g install sails
npm install fast-csv
````

###### Para rodar a aplicação
````code
cd sailsHU
sails lift
````

###### * NOTE
Após o comando `sails lift` a aplicação carregarar todos os dados do CSV da pasta `sailsHU/artefatos` no banco de dados.
Essa operação poderá levar alguns minutos principalmente dado ao grande número de registros do arquivo disp.csv. 
Dependendo da máquina pode ocorrer algum problema de timeout no ORM do Sails, por isso os registros são totalmente removidos a cada `lift`.

#### Resources


##### `'GET /hotel/search'`<br/>

<table>
<tr>
<th>Parameter</th>
<th>Type</th>
<th>Data</th>
</tr>
<tr>
<td>query</td>
<td>text</td>
<td>nome da cidade ou do hotel</td>
</tr>
</table>

Exemplo:

````code
curl --data "query=Araruama" http://localhost:1337/hotel/search
````





####### Response
````json

[
  {
    "id": 27,
    "name": "Mercure Grand Jebel Hafeet ",
    "city": "Rio De Janeiro",
    "createdAt": "2016-04-09T01:45:38.443Z",
    "updatedAt": "2016-04-09T01:45:38.443Z"
  },
  .....
]

````

##### `'GET /hotel/check'`<br/>

<table>
<tr>
<th>Parameter</th>
<th>Type</th>
<th>Data</th>
</tr>
<tr>
<td>id</td>
<td>number</td>
<td>id do hotel</td>
</tr>
<tr>
<td>city</td>
<td>text</td>
<td>nome da cidade</td>
</tr>
<tr>
<td>startDate</td>
<td>date (M/d/yyyy)</td>
<td>data de entrada</td>
</tr>
<tr>
<td>endDate</td>
<td>date (M/d/yyyy)</td>
<td>data de saída</td>
</tr>
</table>

* é necessário informar ou o `param` `id` ou o `city`

##### Response
O mesmo response do recurso anterior. A API verifica a disponibilidade do intervalo de datas através de consulta e map/reduce no controller e o response final são registro de hotéis.


##### `'GET /disp/check'`<br/>
Este recurso não está sendo utilizado pelo app mas server para listar os dados do model Disp. Este recurso não checa a disponibilidade, apenas lista os registros dentro dos intervalos de datas.

<table>
<tr>
<th>Parameter</th>
<th>Type</th>
<th>Data</th>
</tr>
<tr>
<td>ids</td>
<td>number</td>
<td>ids de hotéis separados por virgula</td>
</tr>
<tr>
<td>city</td>
<td>text</td>
<td>nome da cidade</td>
</tr>
<tr>
<td>startDate</td>
<td>date (M/d/yyyy)</td>
<td>nome da cidade</td>
</tr>
<tr>
<td>endDate</td>
<td>date (M/d/yyyy)</td>
<td>nome da cidade</td>
</tr>
</table>

* é necessário informar ou o `param` `ids` ou o `city`

##### Response
````json
[
  {
    "hotelId": 27,
    "date": "2015-05-01T03:00:00.000Z",
    "available": true,
    "createdAt": "2016-04-09T00:29:04.274Z",
    "updatedAt": "2016-04-09T00:29:04.274Z",
    "id": 77392
  },
  .....
]
````





### Android

Implementei a tela da pasta artefato do desafio seguindo as convenções para mobile e principalmente o app atual do Hotel Urbano.


##### * Importante: 
É necessário configurar o IP do serviço (ip da rede ou do servicor) na classe `com.hotelurbano.desafio.api.HotelUrbanoAPI` para funcionar corretamente.

##### O app consiste possui três telas. 
<ul>
<li>Tela principal com o formulário de busca.</li>
<li>Tela de busca de cidades ou hotéis.</li>
<li>Tela de resultado de hotéis com disponibildade.</li>
</ul>

##### Toolkit
<ul>
<li>Square Retrofit</li>
<li>Material Design</li>
<li>RecyclerView</li>
<li>CardView</li>
</ul>

##### Screenshots

<table border="0">
<tr>
<td>
![alt tag](https://s3-sa-east-1.amazonaws.com/desafiohu/print1.png)
</td>
<td>
![alt tag](https://s3-sa-east-1.amazonaws.com/desafiohu/print2.png)
</td>
</tr>
<tr>
<td>
![alt tag](https://s3-sa-east-1.amazonaws.com/desafiohu/print3.png)
</td>
<td>
![alt tag](https://s3-sa-east-1.amazonaws.com/desafiohu/print4.png)
</td>
</tr>
</table>

![alt tag](https://s3-sa-east-1.amazonaws.com/desafiohu/print4.png)


