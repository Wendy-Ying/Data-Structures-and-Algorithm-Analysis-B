import edu.princeton.cs.algs4.StdOut;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] part = line.split("[-\\s:]");
        int month_d = Integer.parseInt(part[0]);
        int day_d = Integer.parseInt(part[1]);
        int hour_d = Integer.parseInt(part[2]);
        int minute_d = Integer.parseInt(part[3]);
        String ampm_d = part[4];
        if((month_d > 12)||(month_d < 1)) return;
        if((day_d > 31)||(day_d < 1)) return;
        if((hour_d > 12)||(hour_d < 1)) return;
        if((minute_d > 59)||(minute_d < 0)) return;
        if((!(ampm_d.equals("AM")))&&(!(ampm_d.equals("PM")))) return;
        if(hour_d == 12) hour_d -= 12;
        if(ampm_d.equals("PM")) hour_d += 12;

        while(sc.hasNext())
        {
            line = sc.nextLine();
            part = line.split("[-\\s:]");
            int month_g = Integer.parseInt(part[0]);
            int day_g = Integer.parseInt(part[1]);
            int hour_g = Integer.parseInt(part[2]);
            int minute_g = Integer.parseInt(part[3]);
            String ampm_g = part[4];
            if((month_g > 12)||(month_g < 1)) continue;
            if((day_g > 31)||(day_g < 1)) continue;
            if((hour_g > 12)||(hour_g < 1)) continue;
            if((minute_g > 59)||(minute_g < 0)) continue;
            if((!(ampm_g.equals("AM")))&&(!(ampm_g.equals("PM")))) continue;
            if(hour_g == 12) hour_g -= 12;
            if(ampm_g.equals("PM")) hour_g += 12;

            boolean judge = false;
            if(month_g < month_d) judge = true;
            else if(month_g == month_d)
            {
                if(day_g < day_d) judge = true;
                else if(day_g == day_d)
                {
                    if(hour_g < hour_d) judge = true;
                    else if(hour_g == hour_d)
                    {
                        if(minute_g < minute_d) judge = true;
                    }
                }
            }
            StdOut.println(judge);
        }
    }
}