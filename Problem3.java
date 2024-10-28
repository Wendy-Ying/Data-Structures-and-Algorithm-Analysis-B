
import edu.princeton.cs.algs4.StdOut;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //读入日期
        Scanner sc = new Scanner(System.in);
        String line = null;
        String[] num = null;
        int[] year = new int[4];
        int[] month = new int[4];
        int[] day = new int[4];
        int i = 0;
        for(i = 0; i < 4; i++)
        {
            line = sc.nextLine();
            num = line.split("-");
            year[i] = Integer.parseInt(num[0]);
            month[i] = Integer.parseInt(num[1]);
            day[i] = Integer.parseInt(num[2]);
            if(year[i] > 2024 || year[i] < 2010) return;
            if(month[i] > 12 || month[i] < 1) return;
            if(day[i] > 31 || day[i] < 1) return;
        }

        //找到开始和结束计时的flag
        int startflag = -1;
        if(year[0] < year[2]) startflag = 2;
        else if(year[0] > year[2]) startflag = 0;
        else if(year[0] == year[2])
        {
            if(month[0] < month[2]) startflag = 2;
            else if(month[0] > month[2]) startflag = 0;
            else if(month[0] == month[2])
            {
                if(day[0] < day[2]) startflag = 2;
                else startflag = 0;
            }
        }
        int endflag = 0;
        if(year[1] < year[3]) endflag = 1;
        else if(year[1] > year[3]) endflag = 3;
        else if(year[1] == year[3])
        {
            if(month[1] < month[3]) endflag = 1;
            else if(month[1] > month[3]) endflag = 3;
            else if(month[1] == month[3])
            {
                if(day[1] < day[3]) endflag = 1;
                else endflag = 3;
            }
        }
        int qualify = 1;

        //计算重叠天数
        int[] pingnian = {0,31,28,31,30,31,30,31,31,30,31,30,31};
        int[] runnian = {0,31,29,31,30,31,30,31,31,30,31,30,31};
        int sum = 0;
        int daycounttype = -1;
        if(year[startflag] > year[endflag]) qualify = 0;
        if(year[startflag] < year[endflag])
        {
            if(year[endflag] - year[startflag] > 2)
                for(i = year[startflag] + 1; i < year[endflag] - 1; i++)
                {
                    if((i % 4) == 0) sum += 366;
                    if((i % 4) != 0) sum += 365;
                }
            daycounttype = 1;
            if((year[startflag] % 4) == 0)
                for(i = month[startflag] + 1; i <= 12; i++)
                    sum += runnian[i];
            else
                for(i = month[startflag] + 1; i <= 12; i++)
                    sum += pingnian[i];
            if((year[endflag] % 4) == 0)
                for(i = 1; i <= month[endflag] - 1; i++)
                    sum+= runnian[i];
            else
                for(i = 1; i <= month[endflag] - 1; i++)
                    sum+= pingnian[i];
        }
        else if(year[startflag] == year[endflag])
        {
            if(month[startflag] > month[endflag]) qualify = 0;
            if((year[startflag] % 4) == 0)
                for(i = month[startflag] + 1; i <= month[endflag] - 1; i++)
                    sum += runnian[i];
            else
                for(i = month[startflag] + 1; i <= month[endflag] - 1; i++)
                    sum += pingnian[i];
            if(month[startflag] == month[endflag])
            {
                daycounttype = 0;
                if(day[startflag] > day[endflag]) qualify = 0;
            }
            else daycounttype = 1;
        }
        if(daycounttype == 0)
            sum += day[endflag] - day[startflag] + 1;
        else {
            if ((year[startflag] % 4) == 0) sum += runnian[month[startflag]] - day[startflag] + 1;
            else sum += pingnian[month[startflag]] - day[startflag] + 1;
            sum += day[endflag];
        }
        if(qualify == 0) sum = 0;
        StdOut.println(sum);
    }
}