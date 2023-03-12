

# RemoteWinControl
 ### App para controle do PC com o smatphone
 
Através do App é possível:
* Simular o comportamento do mouse
* Manipular o volume do computador
* Agendar o desligamento  
* Gravar e reproduzir ações no computador
*Exemplos de ações para gravar:  pular abertura / ir para o próximo episódio na netflix, abrir e executar tarefas em programas e sites, etc…  Você pode gravar qualquer rotina no computador e reproduzir a partir do dispositivo com até 10x a velocidade original*

-------
**Requisitos para executar:**
- Aparelho com Android 8.1 Oreo (SDK 26) ou superior  
- JDK 17 necessário, tem um link na pasta  do servidor pra faciltar, caso precise.
 - O smartphone e computador devem estar conectados na mesma rede, via cabo ou Wi-fi (não importa)
------
**Como usar**
 
* Baixe o repositório via clone ou download de .zip e extraia em local de sua preferência.
* execute o .jar em `\servidor\out\artifacts\RemoteWinControlServer_jar`.
* envie o apk em `\cliente\app\build\outputs\apk\debug` para o seu aparelho, instale e execute.
* quando o servidor está rodando, um ícone aparece na bandeja do windows (perto do relógio), passe o mouse para ver a porta/ip.
* Abra o menu e selecione a opção `Porta e IP`, insira os dados que o servidor está exibido na bandeja do windows e clique em salvar.


-------
**Capturas**

<p align="center">
<img src="https://github.com/GilianMarques/RemoteWinControl/blob/master/_screenshots/captura_%20(4).png" width="250">
<img src="https://github.com/GilianMarques/RemoteWinControl/blob/master/_screenshots/captura_%20(3).png" width="250">
<img src="https://github.com/GilianMarques/RemoteWinControl/blob/master/_screenshots/captura_%20(2).png" width="250">
<img src="https://github.com/GilianMarques/RemoteWinControl/blob/master/_screenshots/captura_%20(1).png" width="250">
<img src="https://github.com/GilianMarques/RemoteWinControl/blob/master/_screenshots/captura_%20(5).png" width="250">
</p>

-------
**Detalhes técnicos**

>*Esse trecho é um rascunho, em breve vou preencher com detalhes adicionais, no momento planejo uma grande reforma na estrutura do cliente e muito do funcionamento vai ser alterado.*

 - O cliente (App Android) se comunica com o servidor (App Windows) através de Sockets (TCP).
 - Os comandos que  cliente e servidor podem executar são pré estabelecidos em Enums.
 - Os comandos executados pelo usuário são encapsulados em um Dto (Data Transfer Object) junto com os dados adicionais necessários, este é enviado ao servidor que analisa os dados e responde adequadamente.
 

-----

**Info**
* Ambos, cliente e servidor são escritos em kotlin usando Android Studio e Intellij IDEA.
* O servidor não tem interface, toda a configuração e interação é feita pelo cliente (app android)
* Sinta-se livre para me contactar a respeito desse repositório
-------
**Stats**

![GitHub last commit](https://img.shields.io/github/last-commit/gilianmarques/RemoteWinControl?color=green&style=flat-square) ![GitHub commit activity (branch)](https://img.shields.io/github/commit-activity/m/gilianmarques/RemoteWinControl/master?color=green&style=flat-square)

### Metricas:

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GilianMarques_RemoteWinControl&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=GilianMarques_RemoteWinControl) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=GilianMarques_RemoteWinControl&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=GilianMarques_RemoteWinControl) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=GilianMarques_RemoteWinControl&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=GilianMarques_RemoteWinControl) [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=GilianMarques_RemoteWinControl&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=GilianMarques_RemoteWinControl)