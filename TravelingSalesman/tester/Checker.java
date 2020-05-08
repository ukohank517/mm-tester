import java.util.Scanner;

public class Checker
{
    static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        boolean[] used = new boolean[id.N];
        for (int i = 0; i < id.N; i++) {
            if (od.v[i] < 0 || od.v[i] >= id.N) {
                System.err.println(
                    "All elements of your return must be " +
                    "between 0 and " + (id.N - 1) + ", but " +
                    "your return contained " + od.v[i] + ".");
                return false;
            }
            if (used[od.v[i]]) {
                System.err.println(
                    "All elements of your return must be " +
                    "unique, but your return contained " +
                    od.v[i] + " two or more times.");
                return false;
            }
            used[od.v[i]] = true;
        }

        return true;
    }

    static double calcScore (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        if (!isValid(id, od)) {
            return -1.0;
        }

        double score = 0.0;
        for (int i = 0; i < id.N; i++) {
            double dx = id.x[od.v[i]] - id.x[od.v[(i + 1) % id.N]];
            double dy = id.y[od.v[i]] - id.y[od.v[(i + 1) % id.N]];
            score += Math.sqrt(dx * dx + dy * dy);
        }

        return score;
    }

    static OutputData runCommand (
        final String exec,
        final InputData id)
        throws Exception
    {
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();
        proc.getOutputStream().write(id.toString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());

        OutputData od = new OutputData();
        od.v = new int[id.N];
        for (int i = 0; i < id.N; i++) {
            od.v[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}