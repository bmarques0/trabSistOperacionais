# Formula

Trabalho Sistemas Operacionais – Corrida de F1 
• Exercitar o desenvolvimento de sistemas multithread 
 
Funcionamento: 
N (2 <= N <= 8) carros de F1, numerados de 1 a N, devem disputar uma corrida. A corrida possui um total de 10 voltas, sendo que os carros levam aleatoriamente de 40 a 50 segundos para completar uma volta. A cada volta os carros a ordem crescente de sua numeração vão contabilizando seus tempos. Ao final das 10 voltas os carros com menor tempo são os vencedores. Cada carro é uma thread. • Características de um carro: • Possui um número de 1 a N; • A cada volta, percorre a pista em um tempo aleatório de 40 a 50 segundos; • A cada volta dada deve-se guarda o tempo levado e imprimir esta informação na tela, não esquecendo de imprimir a identificação do carro; • Quando a thread carro terminar, ou seja, quando o carro terminar a corrida, a thread deve passar a informação do tempo de cada volta para a classe principal; • Imprimir informando o término do carro. • Características de uma corrida: • Deve ser composta por 10 voltas e garantida a sincronia, ou seja, cada carro aparece apenas uma vez por volta e em ordem crescente, iniciando pelo carro 1, seguindo para o 2, e assim por diante até o N. • Características da classe principal: • Gera os N carros (threads); • Recebe as informações de cada carro em relação ao tempo por volta; 
2 
• Quando tiver recebido todas as informações necessárias do N carros, deve listar a ordem de colocação dos carros, bem como quanto cada carro demorou por volta e o tempo total de corrida. 
 
Exemplo de saída do programa: - Supondo N = 3 e somente 3 voltas Volta 1 Carro 1 – 40 segundos Carro 2 – 42 segundos Carro 3 – 50 segundos Volta 2 Carro 1 – 47 segundos Carro 2 – 41 segundos Carro 3 – 46 segundos Volta 3 Carro 1 – 41 segundos Carro 1 terminou a corrida. Carro 2 – 42 segundos Carro 3 – 42 segundos Carro 2 terminou a corrida. Carro 3 terminou a corrida. 
 
Lista de colocação 1 – Carro 2 – 42 (volta 1), 41 (volta 2), 42 (volta 3) – Total 125 2 – Carro 1 – 40 (volta 1), 47 (volta 2), 41 (volta 3) – Total 128 3 – Carro 3 – 50 (volta 1), 46 (volta 2), 42 (volta 3) – Total 138 
3 
 
Diversos: • O trabalho deve ser apresentado até data prevista no Moodle. • Grupos podem ser compostos por no mínimo 2 alunos e no máximo 3 alunos. • O trabalho tem valor de 30% da nota bimestral. 
