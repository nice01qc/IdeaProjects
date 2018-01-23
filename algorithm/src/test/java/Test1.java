import java.io.*;
import java.util.*;

import org.junit.Test;

public class Test1 {


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode removeElements(ListNode head, int val) {
        if(head ==null)return null;
        ListNode cur = head;
        ListNode temp =null;

        while(cur!=null){
            temp=cur;

            if(cur.val==val){
                if(cur.next==null){
                    temp.next=null;
                    cur=null;
                }else{
                    cur.val=cur.next.val;
                    cur.next=cur.next.next;
                }
            }else{
                cur=cur.next;
            }
        }

        return head;
    }

    public static void main(String[] args) {
        int a=2;
        double b=2.2;
        System.out.println(a>=b);
    }

}
