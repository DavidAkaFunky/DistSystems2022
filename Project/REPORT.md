Turmas - Projeto de Sistemas Distribuídos - 3º Período 2021/2022 - Grupo A08
Realizado por:
- David Belchior - 95550
- Mariana Charneca - 95635
- Pedro Severino - 96904

A nossa 3ª entrega tem como foco a implementação de um mecanismo de coerência entre servidores, recorrendo ao protocolo de "gossip", também conhecido como propagação epidémica.

Na nossa implementação, cada servidor, seja ele primário ou secundário, possui:
- Uma turma "principal", que recebe indiscriminadamente os pedidos dos vários clientes (podendo, ou não, aceitá-los, da mesma forma que nas primeiras 2 entregas);
- Uma lista de comandos de escrita sobre o servidor (os de leitura não são incluídos, por não gerarem incoerências), cada um contendo um timestamp de quando o comando foi recebido e a string enviada pelo cliente ao efetuar o comando;
- Uma réplica da turma, a qual mantém o estado da turma obtido no último "gossip" (que se assume coerente entre todos os servidores ativos) e que é usada para simular os comandos recebidos por todos os servidores desde esse último "gossip", sempre que o servidor respetivo iniciar um "gossip";
- Um relógio vetorial "principal", que inclui o número do comando mais recente de cada servidor que a turma "principal" possui;
- Uma réplica do relógio vetorial, idêntico ao "principal", mas associado ao estado da réplica da turma;
- Um vetor com o número do comando mais recente do servidor que cada um dos servidores existentes possui (confirmado através do OK de cada servidor no final do "gossip"): este vetor permite que todos os comandos do servidor com id <= min{entradas do vetor} sejam removidos.

De cada vez que um servidor inicia um "gossip", este envia a cada servidor (incluindo ele próprio, por motivos de simplificação da implementação) a entrada do relógio vetorial da réplica que lhe corresponde, permitindo ao recetor comparar o valor recebido com o que possui e enviar apenas os comandos que possuem id superior.
Ao receber todos os comandos (e incrementando cada entrada da réplica do relógio vetorial com o número de comandos recebidos do servidor respetivo), estes são ordenados temporalmente, do mais antigo para o mais recente, e simulados sobre a réplica, criando um estado consistente com todos os comandos existentes.
Por fim, as réplicas da turma e do relógio vetorial (que refletem, agora, o estado conciliado de todos os servidores) são enviados para todos os servidores (novamente, incluindo ele próprio), substituindo a turma e o relógio vetorial "principais".
Para evitar que, durante uma operação de "gossip", ocorram operações de escrita entre o fim do envio dos comandos e a receção do estado conciliado (o que implicaria que essa operação seria perdida), usamos uma "flag" que indica que há um "gossip" a decorrer, não permitindo operações durante esse período (enviando uma resposta similar à de um servidor inativo).

Como forma de assegurar leituras coerentes, usamos 2 mecanismos adicionais:
- Quando um professor abre ou encerra as inscrições de alunos, o servidor que recebeu o pedido (na nossa implementação, o servidor principal) inicia automaticamente um "gossip", assegurando que todos os servidores ativos estão em condições de (não) aceitar inscrições;
- Quando um aluno se consegue inscrever com sucesso, este ativa uma "flag", a qual é enviada no pedido de listar a turma, para que o servidor que o recebe só responda com o estado da turma caso o aluno em causa esteja lá presente (seja na lista de inscritos, seja na lista de alunos com a inscrição revogada).