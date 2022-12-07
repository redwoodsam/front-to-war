# Gerador ReactJS/VueJS para war
Aplicação GUI para converter projetos React e Vue 2 para formato .war compatível com servidores de aplicação Java (Tomcat, WebSphere e afins).

OBS: Não compatível com Vite.

<img src="https://i.imgur.com/TcnmuX0.png">

## Requisitos:
- Java Runtime Environment (JRE) 8+
- NodeJS 14+
- Apache Maven 2.6+ (se for realizar seu próprio build)

## Como rodar:

#### Método 1:
- Baixe o arquivo gerador-war.rar e o descompacte em qualquer lugar
- Abra o terminal e rode o comando: "java -jar gerador-war.jar"
- Caso o sistema operacional for Windows, basta dar dois cliques ou clicar com o botão direito > Abrir com > Java(TM) Platform SE Binary

#### Método 2:
- Instale o Apache Maven, caso não o tenha instalado
- Clone o projeto
- Abra o terminal dentro da pasta do projeto e digite o comando "mvn clean package"
- Após o script terminar, vá até a pasta target e execute o arquivo gerador-war-jar-with-dependencies.jar conforme método 1
