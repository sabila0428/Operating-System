import java.util.*;

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] at = new int[n];
        int[] bt = new int[n];
        int[] ct = new int[n];
        int[] tat = new int[n];
        int[] wt = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Arrival Time of P" + (i + 1) + ": ");
            at[i] = sc.nextInt();

            System.out.print("Burst Time of P" + (i + 1) + ": ");
            bt[i] = sc.nextInt();
        }

        // Sort according to Arrival Time
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (at[i] > at[j]) {
                    int temp = at[i];
                    at[i] = at[j];
                    at[j] = temp;

                    temp = bt[i];
                    bt[i] = bt[j];
                    bt[j] = temp;
                }
            }
        }

        int time = 0;
        double totalWT = 0, totalTAT = 0;

        for (int i = 0; i < n; i++) {
            if (time < at[i])
                time = at[i];

            time += bt[i];
            ct[i] = time;

            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];

            totalWT += wt[i];
            totalTAT += tat[i];
        }

        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");

        for (int i = 0; i < n; i++) {
            System.out.println("P" + (i + 1) + "\t" + at[i] + "\t" + bt[i]
                    + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        System.out.println("\nAverage Waiting Time = " + totalWT / n);
        System.out.println("Average Turnaround Time = " + totalTAT / n);

        sc.close();
    }
}