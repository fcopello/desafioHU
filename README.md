# Desafio HU

## API

A API foi desenvolvida utilzando sailsJS um framework WebMVC NodeJS. O storage de dados, está utilzando a configuração default do framework, sendo assim, não será necessário plugar em nenhum banco. Não é a melhor implementação para performance mas permite que o serviço tenha uma solução standalone. 


##### * Importante: 
Ao parsear as datas do arquivo disp.csv no `config/bootsrap.js` inseri o ano estático como <strong>2016</strong> para que a busca no app funcionasse corretamente ao se utilizar o calendário com data atual.

##### * Performance e outros: 
Os dados de cidade não foram desnormalizados o que pode afetar o requisito performance do teste.
<br/>Não realizei um teste de carga mas não acredito que o serivço aguente 1000 requisições/segundo (conforme estava na descrição do desafio) mesmo com todos os dados em memória.



#### Comandos para instalar as dependências da API

###### Para instalar as dependências

````code
sudo npm -g install sails
npm install fast-csv
npm install grunt-npm-install --save-dev
npm install ejs
npm install include-all --save
````

###### Para rodar a aplicação
````code
cd sailsHU
sails lift
````
> Se aparecer a seguinte mensagem do Sails pode utilizar a opção <strong>3</strong>, drop.

````
warn: ** DO NOT CHOOSE "2" or "3" IF YOU ARE WORKING WITH PRODUCTION DATA **
prompt: ?:  
````

###### * NOTE
Após o comando `sails lift` a aplicação carregarar todos os dados do CSV da pasta `sailsHU/artefatos` no banco de dados.
Essa operação poderá levar alguns minutos principalmente dado ao grande número de registros do arquivo disp.csv. 
Dependendo da máquina pode ocorrer algum problema de timeout no ORM do Sails, por isso os registros são totalmente removidos a cada `lift`.

#### Controllers

`api/controllers/PlaceController`<br/>
`api/controllers/HotelController`<br/>
`api/controllers/DispController`

###### Blueprints
`'GET /place/search': 'PlaceController.search'`<br/>
`'GET /hotel/search': 'HotelController.search'`<br/>
`'GET /hotel/check': 'HotelController.check'`<br/>
`'GET /disp/check': 'DispController.check'`<br/>


#### Resources

#### `'GET /place/search'`<br/>
> Método utilizado pelo app para a busca de cidades ou hotéis.

<table>
<tr>
<th>Parameter</th>
<th>Type</th>
<th>Data</th>
</tr>
<tr>
<td>query</td>
<td>text</td>
<td>nome ou parte do nome da cidade ou do hotel</td>
</tr>
</table>

Exemplo:

````code
curl --data "query=Araruama" http://localhost:1337/place/search
````


####### Response
````json
[
  {
    "key": 0,
    "name": "Araruama",
    "type": 1,
    "createdAt": "2016-04-09T08:01:06.104Z",
    "updatedAt": "2016-04-09T08:01:06.104Z",
    "id": 3841
  }
]
````


#### `'GET /hotel/search'`<br/>

<table>
<tr>
<th>Parameter</th>
<th>Type</th>
<th>Data</th>
</tr>
<tr>
<td>query</td>
<td>text</td>
<td>nome ou parte do nome do hotel</td>
</tr>
</table>

Exemplo:

````code
curl --data "query=Mer" http://localhost:1337/hotel/search
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
  }
]

````

#### `'GET /hotel/check'`<br/>
> Método utilizado pelo app para checar a disponibilidade. A aplicação verifica a disponibilidade do intervalo de datas através de consulta e map/reduce no controller. Só serão retornados hotéis que possuem disponibilidade em todas as datas contidas no invervalo selecionado pelo usuário. Ou seja, se o usuário informar um período entre os dias 4 e 6 e o dia 5 não tiver disponibilidade, o hotel não será listado.

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

##### Exemplo

````code
curl --data "city=Araruama&startDate=5/3/2016&endDate=5/4/2016" http://localhost:1337/hotel/check
````



##### Response

````json
[
  {
    "id": 27,
    "name": "Mercure Grand Jebel Hafeet ",
    "city": "Rio De Janeiro",
    "createdAt": "2016-04-09T01:45:38.443Z",
    "updatedAt": "2016-04-09T01:45:38.443Z"
  }
]
````


#### `'GET /disp/check'`<br/>
Este recurso não está sendo utilizado pelo app mas serve para listar os dados do model Disp. Este recurso não checa a disponibilidade, apenas lista os registros dentro dos intervalos de datas.

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
<td>data de entrada</td>
</tr>
<tr>
<td>endDate</td>
<td>date (M/d/yyyy)</td>
<td>data de saída</td>
</tr>
</table>

* é necessário informar ou o `param` `ids` ou o `city`

##### Exemplo

````code
curl --data "ids=1,2&startDate=5/3/2016&endDate=5/4/2016" http://localhost:1337/disp/check
````

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
  }
]
````


## Android

Implementei a tela da pasta artefato do desafio seguindo as convenções para mobile e principalmente o app atual do Hotel Urbano.


##### * Importante: 
> É necessário configurar o IP do serviço (ip da máquina na rede ou do servidor) na classe `com.hotelurbano.desafio.api.HotelUrbanoAPI` na constante `API_ENDPOINT` para funcionar corretamente.

##### O app possui três telas: 
<ul>
<li>Tela principal com o formulário de busca.</li>
<li>Tela de busca de cidades ou hotéis.</li>
<li>Tela de resultado de hotéis com disponibildade.</li>
</ul>

##### Toolkit:
<ul>
<li>Square Retrofit</li>
<li>Material Design</li>
<li>RecyclerView</li>
<li>CardView</li>
</ul>

##### Screenshots

###### Tela principal
![alt tag](https://s3-sa-east-1.amazonaws.com/desafiohu/print1.png)
###### Tela de busca
![alt tag](https://s3-sa-east-1.amazonaws.com/desafiohu/print0.png)
###### Date picker
![alt tag](https://s3-sa-east-1.amazonaws.com/desafiohu/print3.png)
###### Tela de resultado
![alt tag](https://s3-sa-east-1.amazonaws.com/desafiohu/print4.png)




