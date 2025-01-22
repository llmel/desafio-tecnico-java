# desafio-tecnico-java
Detalhes gerais, tanto para ambiente local quanto para ambientes publicados na internet:
- Endpoint de health check: /actuator/health
- Para acessar o swagger nesses ambientes, é só adicionar no final da URL a URI: /swagger-ui/index.html 
- A aplicação está configurada com autenticação básica, então ao tentar acessar, irá pedir usuário e senha:
	- usuário: desafio
	- senha: 12345

Instruções para rodar a aplicação localmente:
- Programas:
	- Instale o Intellij Community
	- Instale o SQL Server Express 2022
		- Caso o instalador peça, instale o OleDB Driver 18.7
	- Instale o SSMS (SQL Server Management Studio)

- Scripts SQL:
	- Conecte-se ao banco de dados via SSMS (via autenticação do windows mesmo) e rode os scripts 1 e 2 localizados no repositório
		- Caso tenha problemas em se conectar ao banco de dados, tente essas soluções:
			- criar regras de firewall (liberar portas 1433 e 1434 via powershell)
			- habilitar o serviço sql server browser (necessário para se conectar em instâncias nomeadas, que é o caso do SQLEXPRESS)
			- habilitar conexão via tcp na porta 1433 (com o sql server configuration manager, pois às vezes não vem configurado por default)

- API:
	- Rode a API com .properties default, não utilize os properties dos ambientes (dev, qas e prod).
	- Aplicações spring boot rodam por padrão na porta 8080.

Instruções para utilizar a aplicação publicada na internet:
	- Criei o ambiente público da aplicação na nuvem Azure
	- São 3 serviços de aplicativo, um para cada ambiente
	- Para utilizar a API em ambientes de dev, qas ou prod, seguem links base da aplicação publicada em cada ambiente:
		- Ambiente dev: https://desafiojavadev-g7a5e3ddfbeddbcz.brazilsouth-01.azurewebsites.net 
		- Ambiente qas: https://desafiojavaqas-dyaaedfrdrg8d8gq.brazilsouth-01.azurewebsites.net
		- Ambiente prod: https://desafiojavaprod-bhbxega9fmexc4cz.brazilsouth-01.azurewebsites.net		

		- Observação importante: Esses ambientes no Azure estão em conta gratuita, então ao tentar acessar via navegador ou fazer alguma requisição via postman, insomnia etc, vai demorar alguns minutos a primeira resposta porque é como se ele fosse inicializar a aplicação.

	- Em relação ao banco de dados, eu tive que usar schemas diferentes para cada ambiente, pois só é possível criar um servidor e uma base de dados gratuita por conta de usuário.
	- A construção e implantação (build e deploy) da API nos ambientes foi automatizada via Github Actions.

