package com.wxx.conference.test;

import java.util.*;

/**
 * Created by thinkpad on 2018-3-1.
 */
public class testContains {
    public static void main(String []args){
        int []nums = new int[]{2,5,1,3,4,7};
        shuffle(nums,3);

    }
    public static int[] shuffle(int[] nums, int n) {
        for(int i=1;i<n;i++){
            int tmp = nums[i];
            nums[i] = nums[i+n];
            nums[i+n] = tmp;
        }

        return nums;
    }
    public static void reverseString(char[] s) {
        for(int i=0,j=s.length-1;i<j;i++,j--){
            char tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
    }
    public static String freqAlphabets(String s) {
        char []alArr = new char[27];
        for(int i=1;i<alArr.length;i++){
            alArr[i] = (char)(i+96);
        }
        char []arr = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<arr.length;i++){
            if(i < arr.length-2 && arr[i+2] == '#'){
                sb.append(alArr[(arr[i]-'0')*10+(arr[i+1]-'0')]);
                i+=2;
            }else{
                sb.append(alArr[(arr[i]-'0')]);
            }
        }
        return sb.toString();
    }
}


