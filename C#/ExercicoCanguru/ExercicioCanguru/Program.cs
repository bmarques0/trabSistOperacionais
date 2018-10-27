using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ExercicoCanguru
{
    class Program
    {
        public static bool c1terminou = false;
        public static bool c2terminou = false;
        public static bool c3terminou = false;
        public static bool c4terminou = false;
        public static bool c5terminou = false;

        static void Main(string[] args)
        {
            Corrida c = new Corrida();
            c.IniciarCorrida();

            Console.Write("Finalizado.");
            Console.ReadKey();
        }

    }

    public class Corrida
    {
        Dictionary<Canguru, bool> termino = new Dictionary<Canguru, bool>();
        Dictionary<Canguru, bool> passos = new Dictionary<Canguru, bool>();
        List<Canguru> cangurus = new List<Canguru>();

        public void IniciarCorrida()
        {
            Random r = new Random();
            int totalCorrida = r.Next(80, 101);

            // Cria cangurus com nomes
            Canguru c1 = new Canguru(this, 1, "Felizberto", totalCorrida);
            Canguru c2 = new Canguru(this, 2, "Mimoso", totalCorrida);
            Canguru c3 = new Canguru(this, 3, "Malandro", totalCorrida);
            Canguru c4 = new Canguru(this, 4, "Vendaval", totalCorrida);
            Canguru c5 = new Canguru(this, 5, "Soberano", totalCorrida);
            cangurus.Add(c1);
            cangurus.Add(c2);
            cangurus.Add(c3);
            cangurus.Add(c4);
            cangurus.Add(c5);

            // Inicializa threads e roda elas
            foreach (Canguru c in cangurus)
            {
                Thread cthread = new Thread(new ThreadStart(c.Executa));
                cthread.Start();
               
                // Cria flags de controle de fim de corrida e de fim de passo
                termino.Add(c, false);
                passos.Add(c, false);
            }

            bool isDone = false;
            // Ficamos no loop até a corrida terminar
            while (!isDone)
            {
                lock (termino)
                {
                    // Todos os cangurus notificaram que acabou a corrida pra eles? Se sim, acabou a corrida.
                    isDone = termino.All(i => i.Value == true);
                    if (isDone) continue;
                }
                // Corrida não acabou, precisamos contar essa rodada
                lock (passos)
                {
                    bool allstepsdone = true;
                    // Verificamos se tem algum canguru que ainda não terminou essa rodada
                    for (int i = 0; i < passos.Count; i++)
                    {
                        Canguru c = passos.Keys.ElementAt(i);
                        if (passos[c] == false) allstepsdone = false;
                    }

                    // Se todos terminaram a rodada, podemos marcar a flag como falso pra que uma nova rodada se inicie
                    if (allstepsdone)
                    {
                        for (int i = 0; i < passos.Count; i++)
                        {
                            Canguru c = passos.Keys.ElementAt(i);
                            passos[c] = false;
                        }
                    }
                }
            }

            // Ordena cangurus na ordem de quem pulou menos e chegou mais longe.
            List<Canguru> ordenado = cangurus.OrderBy(i => i.pulos).OrderByDescending(i => i.totalPercorrido).ToList();

            // Imprime na tela a colocação.
            Console.WriteLine();
            int colocacao = 1;
            foreach (Canguru c in ordenado)
            {
                Console.WriteLine("Canguru " + c.Nome + " ficou em "  + colocacao.ToString() + "o. lugar. ");
                colocacao++;
            }
        }

        public void NotificaTermino(Canguru canguru)
        {
            lock (termino)
            {
                if (termino.ContainsKey(canguru)) termino[canguru] = true;
            }
        }

        public void NotificaRodada(Canguru canguru)
        {
            lock (passos)
            {
                if (passos.ContainsKey(canguru)) passos[canguru] = true;
            }
        }

        public bool GetStep(Canguru canguru)
        {
            lock (passos)
            {
                if (passos.ContainsKey(canguru)) return passos[canguru];
                else return false;
            }
        }
    }

    public class Canguru
    {
        public int Numero;
        public string Nome;
        private int MetrosTotais;

        public int totalPercorrido;
        public int pulos = 0;

        private Random r;
        private Corrida corrida;

        public Canguru(Corrida c, int numero, string nome, int metrosTotais)
        {
            this.Numero = numero;
            this.Nome = nome;
            this.MetrosTotais = metrosTotais;
            this.corrida = c;
            
        }

        public void Executa()
        {
            while (true)
            {
                // A rodada atual acabou? False se não, true se sim
                if (!corrida.GetStep(this))
                {
                    // Rodada está pendente, caso canguru não chegou no final ainda, ele dá um pulo
                    if (totalPercorrido < MetrosTotais)
                    {
                        r = r = new Random();
                        int pulo = r.Next(0, 11); // 11 pq é exclusivo, ou seja, 11 não conta, então só vai de 0 a 10.

                        totalPercorrido += pulo;
                        pulos++;

                        // Imprime na tela o pulo do canguru
                        Console.WriteLine("Canguru " + this.Nome + " pulou " + pulo.ToString() + " metros, faltando " + (MetrosTotais - totalPercorrido).ToString() + " para o fim da corrida. ");
                        // Notifica que rodada acabou
                        corrida.NotificaRodada(this);
                    } else
                    {
                        // Canguru já chegou no final, ele encerra a rodada sem fazer nada
                        corrida.NotificaRodada(this);
                    }
                }

                // Caso ele já tenha chego no final, termina a corrida pra ele
                if (totalPercorrido >= MetrosTotais)
                {
                    corrida.NotificaRodada(this);
                    corrida.NotificaTermino(this);
                }

                // Espera um pouco pra dar mais emoção
                System.Threading.Thread.Sleep(30);
            }
        }
    }
}
