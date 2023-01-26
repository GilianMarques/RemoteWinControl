
# RemoteWinControl
 ### App para controle do PC com o smatphone
 
Com o app instalado no dispositivo android é possível:
* Simular o comportamento do mouse
* Manipular o volume (Pc)
* Agendar o desligamento (Pc) 
* Gravar e reproduzir ações frequentes no computador

Exemplos de ações para gravar:  pular abertura / ir para o próximo episódio na netflix, abrir e executar tarefas em programas, atalhos tipo CRTL+C + CRTL+V, abrir um site específico no navegador, etc…  Você pode gravar qualquer rotina no computador e reproduzir com dois cliques a partir do dispositivo  com até 10x da velocidade original

-------

**Como usar**
* Baixe o repositório via clone ou download de .zip e extraia em local de sua preferência
* execute o .jar em `\servidor\out\artifacts\RemoteWinControlServer_jar` (JDK 17 necessário)
* quando o servidor está rodando, um ícone aparece na bandeja do windows (perto do relógio), passe o mouse para ver a porta/ip
* envie o apk em `\cliente\app\build\outputs\apk\debug` para o seu aparelho, instale e execute
* Abra o menu e selecione a opção ‘Ip e porta’, insira os dados que o servidor está exibido na bandeja do windows e clique em salvar.


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

**Info**
* Ambos, cliente e servidor são escritos em kotlin usando Android Studio e Intellij IDEA.
* O servidor não tem interface, toda a configuração e interação é feita pelo cliente (app android)
* Senta-se livre para me contactar a respeito desse repositório (meios de contato no perfil)
-------
**Stats**

![GitHub last commit](https://img.shields.io/github/last-commit/gilianmarques/RemoteWinControl?color=green&style=flat-square) ![GitHub commit activity (branch)](https://img.shields.io/github/commit-activity/m/gilianmarques/RemoteWinControl/master?color=green&style=flat-square)

