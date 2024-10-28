import java.util.Scanner;

public class Problem6 {
    private static int exchange(int[] array, int index) {
        int temp = array[index];
        array[index] = array[index+1];
        array[index+1] = temp;
        return Math.min(array[index], array[index+1]);//cost
    }
    private static long bubble(int[] array, int left, int right, long cost){
        for (int i = 0; i < right - left; i++) {
            int flag = 0;
            for (int j = left; j < right - i; j++) {
                if(array[j] > array[j+1]) {
                    cost += exchange(array, j);
                    flag = 1;
                }
            }
            if(flag == 0)
                break;
        }
        return cost;
    }
    private static long merge(int[] array, int left, int mid, int right, int[] aux, long cost){
        System.arraycopy(array,left,aux,left,right-left+1);
        int i = left;
        int j1 = left;
        int j2 = mid+1;
        while(j1 <= mid && j2 <= right) {
            if (aux[j1] <= aux[j2])
                array[i++] = aux[j1++];
            else {
                array[i++] = aux[j2++];
                cost += (long) (mid + 1 -j1) * aux[j2-1];//simulate it to be bubble
            }
        }
        while (j1 <= mid)
            array[i++] = aux[j1++];
        while (j2 <= right)
            array[i++] = aux[j2++];
        return cost;
    }
    private static long sort(int[] array, int left, int right, int[] aux, long cost){
        if(right-left < 8){
            cost = bubble(array,left,right,cost);
            return cost;
        }
        if(left >= right)
            return cost;
        int mid = left + (right-left)/2;
        cost = sort(array,left,mid,aux,cost);
        cost = sort(array,mid+1,right,aux,cost);
        cost = merge(array,left,mid,right,aux,cost);
        return cost;
    }
    private static long sort(int[] array, long cost){
        cost += sort(array,0, array.length-1, new int[array.length],cost);
        return cost;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int length = sc.nextInt();
            int[] array = new int[length];
            for (int i = 0; i < length; i++)
                array[i] = sc.nextInt();
            long cost = 0;
            cost = sort(array,cost);
            System.out.println(cost);
        }
    }
}
