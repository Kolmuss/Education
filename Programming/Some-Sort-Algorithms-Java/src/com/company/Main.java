package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    void BubbleSort(int arr[]){
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            boolean sorted = true;
            for (int j = 0; j < n - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    sorted = false;
                }
            }
            if (sorted) {
                break;
            }
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tot = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tot;
                }
            }
        }
    }

    void InsertSort(int arr[]){
        int n = arr.length;
        for (int i = n-2; i >= 0; i--) {
            int key = arr[i];
            int j = i + 1;

            while (j < n && arr[j] < key) {
                arr[j - 1] = arr[j];
                j = j + 1;
            }
            arr[j - 1] = key;
        }
    }

    void SelectSort(int arr[], int i) {
        int n = arr.length;
        if (n <= i) {
            return;
        }
        int min_idx = i;
        for (int j = i + 1; j < n; j++)
            if (arr[j] < arr[min_idx])
                min_idx = j;

        int temp = arr[min_idx];
        arr[min_idx] = arr[i];
        arr[i] = temp;
        SelectSort(arr, ++i);
    }

    void CountSort(int arr[]) {
        int min = arr[0];
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }else
            if(min > arr[i]){
                min = arr[i];
            }
        }
        int r = max - min + 1;
        int count[] = new int[r];
        int tot[] = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i] - min]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            tot[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = tot[i];
        }
    }

    void HeapSort(int arr[]){
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heap(arr, n, i);
        }
        for (int i=n-1; i>=0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heap(arr, i, 0);
        }
    }

    void heap(int arr[], int n, int i) {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heap(arr, n, largest);
        }
    }

    int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = (low-1);
        for (int j=low; j<high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        return i+1;
    }

    void QuickSort(int arr[], int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            QuickSort(arr, low, pi-1);
            QuickSort(arr, pi+1, high);
        }
    }

    void ShellSort(int arr[]) {
        int n = arr.length;
        for (int gap = n/2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
        }
    }

    void MergeSort(int arr[], int l, int r) {
        if (l < r) {
            int m = (l+r)/2;
            MergeSort(arr, l, m);
            MergeSort(arr , m+1, r);

            int n1 = m - l + 1;
            int n2 = r - m;
            int L[] = new int [n1];
            int R[] = new int [n2];
            for (int i=0; i<n1; ++i)
                L[i] = arr[l + i];
            for (int j=0; j<n2; ++j)
                R[j] = arr[m + 1+ j];
            int i = 0, j = 0;
            int k = l;
            while (i < n1 && j < n2) {
                if (L[i] <= R[j]) {
                    arr[k] = L[i];
                    i++;
                }
                else {
                    arr[k] = R[j];
                    j++;
                }
                k++;
            }
            while (i < n1){
                arr[k] = L[i];
                i++;
                k++;
            }
            while (j < n2) {
                arr[k] = R[j];
                j++;
                k++;
            }
        }
    }

    void RadixSort(int arr[]) {
        int n = arr.length;
        int max = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > max)
                max = arr[i];

        for (int exp = 1; max/exp > 0; exp *= 10) {
            int output[] = new int[n];
            int i;
            int count[] = new int[10];
            Arrays.fill(count, 0);
            for (i = 0; i < n; i++)
                count[(arr[i] / exp) % 10]++;
            for (i = 1; i < 10; i++)
                count[i] += count[i - 1];
            for (i = n - 1; i >= 0; i--) {
                output[count[(arr[i] / exp) % 10] - 1] = arr[i];
                count[(arr[i] / exp) % 10]--;
            }
            for (i = 0; i < n; i++)
                arr[i] = output[i];
        }
    }

    void RadixChar(String arr[]){
        int n = arr.length;
        int max = arr[0].length();
        for (String s: arr) {
            if(s.length() > max){
                max = s.length();
            }
        }
        for (int exp = 0; exp < max; exp++) {
            String output[] = new String[n];
            int count[] = new int[27];
            Arrays.fill(count, 0);

            for (int i = 0; i < n; i++) {
                System.out.println(exp);
                if (arr[i].length() < (exp + 1)) {
                    count[0]++;
                } else
                    count[arr[i].charAt(exp) - 'a' + 1]++;
            }

            for (int i = 1; i < 27; i++)
                count[i] += count[i - 1];

            for (int i = n - 1; i >= 0; i--) {
                if (arr[i].length() < exp + 1) {
                    output[0] = arr[i];
                    count[0]--;
                } else {
                    output[count[arr[i].charAt(exp) - 'a' + 1] - 1] = arr[i];
                    count[arr[i].charAt(exp) - 'a' + 1]--;
                }
            }
            for (int i = 0; i < n; i++)
                arr[i] = output[i];
        }
    }

    public static void main(String[] args) {
        Main ob = new Main();
        double time[][] = new double[6][6];
        for (int i = 1; i <= 6; i++) {
            int n = i*60000;
            time[i-1][0] = n;
            int shf_arr[] = new int[n];
            for (int j = 0; j < n; j++) {
                shf_arr[j] = (int) (Math.random() * 1000);
            }
            int arr[] = shf_arr;
            long start = System.nanoTime();
            ob.BubbleSort(arr);
            time[i-1][1] = ((double)(System.nanoTime() - start)/1000000000);

            arr = shf_arr;
            start = System.nanoTime();
            ob.MergeSort(arr, 0, (int)time[i-1][0]-1);
            time[i-1][2] = ((double)(System.nanoTime() - start)/1000000000);

            arr = shf_arr;
            start = System.nanoTime();
            ob.QuickSort(arr, 0, (int)time[i-1][0]-1);
            time[i-1][3] = ((double)(System.nanoTime() - start)/1000000000);

            arr = shf_arr;
            start = System.nanoTime();
            ob.HeapSort(arr);
            time[i-1][4] = ((double)(System.nanoTime() - start)/1000000000);

            arr = shf_arr;
            start = System.nanoTime();
            ob.RadixSort(arr);
            time[i-1][5] = ((double)(System.nanoTime() - start)/1000000000);
        }
        System.out.println("Array size | Bubble | Merge | Quick | Heap | Radix");
        for (int i = 0; i < 6; i++) {
            System.out.println(time[i][0] + " |" + time[i][1] + " |" + time[i][2] + " |" + time[i][3] + " |" + time[i][4] + " |" + time[i][5] + "\n");
        }
    }
}
