package com.sort;

/**
 * 排序
 */
public class Sort {

    public static void main(String[] args) {

        int [] arr = {1,2,9,0,8,3,5,4,18,26,17,14,14};

        insertSort(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+",");
        }
    }

    /**
     * 冒泡排序
     * @param source 需要排序的数组
     * 排序思想
     * 将序列中所有元素两两比较，将最大的放在最后面。
     * 将剩余序列中所有元素两两比较(除了最后一个,每趟排序后最大的数在后面)，将最大的放在最后面。
     * 重复第二步，直到只剩下一个数。
     */
    public static void mpSort(int[] source) {

        for (int i = 0; i < source.length - 1; i++) {
            for (int j = 0; j < source.length - 1 -i; j++) {
                if (source[j] > source[j+1]) {
                    source[j] ^= source[j+1];
                    source[j+1] ^= source[j];
                    source[j] ^= source[j+1];
                }
            }
        }
    }

    /**
     * 快速排序
     * @param source
     * @param start
     * @param end
     * 选择第一个数为p，小于p的数放在左边，大于p的数放在右边。
     * 递归的将p左边和右边的数都按照第一步进行，直到不能递归。
     */
    public static void quickSort(int[] source,int start,int end) {

        if (start >= end) {
            return;
        }

        int baseNum = source[start];//基准值
        int low = start;//低位值
        int high = end;//高位值

        while (low < high) {

            for (;;high--) {
                if (high <= low) {
                    break;
                }
                if (source[high] < baseNum) {
                    source[low] = source[high];
                    break;
                }
            }


            for (;;low++) {
                if (high <= low) {
                    break;
                }
                if (source[low] > baseNum) {
                    source[high] = source[low];
                    break;
                }
            }

        }

        if (low == high) {
            source[low] = baseNum;
        }

        quickSort(source,start,low-1);

        quickSort(source,low+1,end);


    }

    /**
     * 选择排序
     * @param source
     * 遍历整个序列，将最小的数放在最前面。
     * 遍历剩下的序列，将最小的数放在最前面。
     * 重复第二步，直到只剩下一个数。
     */
    public static void selectSort(int[] source) {

        for (int i = 0; i < source.length; i++) {

            int min = source[i];
            int flag = i;

            for (int j = i + 1; j < source.length; j++) {

                if (min > source[j]) {
                    min = source[j];
                    flag = j;
                }

            }

            //最左侧值放在最小值处
            source[flag] = source[i];
            //最小值放在本次循环最左侧
            source[i] = min;

        }

    }

    /**
     * 插入排序
     * @param source
     * 把新的数据插入到已经排好的数据列中。将第一个数和第二个数排序，然后构成一个有序序列将第三个数插入进去，
     * 构成一个新的有序序列。对第四个数、第五个数……直到最后一个数，重复第二步。如题所示：
     */
    public static void insertSort(int [] source) {

        for (int i = 1; i < source.length; i++) {

            for (int j = i; j > 0; j--) {
                if (source[j] < source[j-1]) {
                    source[j] ^= source[j-1];
                    source[j-1] ^= source[j];
                    source[j] ^= source[j-1];
                }
            }

        }

    }
}
