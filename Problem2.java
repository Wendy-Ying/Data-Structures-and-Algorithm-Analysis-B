
import edu.princeton.cs.algs4.StdOut;
import java.util.Scanner;

public class Main {
    public int[] id;
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Main g = new Main();
        g.id = new int[N + 1];
        for(int i = 0; i < N + 1; i++)
            g.id[i] = i;

        while(sc.hasNext())
        {
            String next = sc.next();
            if(next == null)
                break;
            if(next.equals("C"))
            {
                g.id[g.find(sc.nextInt())] = g.find(sc.nextInt());
            }
            if(next.equals("Q"))
            {
                if(g.find(sc.nextInt())==g.find(sc.nextInt())) StdOut.print("Yes\n");
                else StdOut.print("No\n");
            }
        }
    }
    public int find( int p ) {
        return (p==id[p]) ? p : (id[p]=find(id[p]));
    }
}