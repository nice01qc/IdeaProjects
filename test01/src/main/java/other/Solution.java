package other;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for ListNode.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int val) {
 *         this.val = val;
 *         this.next = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     */
    public ListNode mergeKLists(List<ListNode> lists) {
        ListNode[] data=new ListNode[lists.size()];
        ListNode result1 = new ListNode(0);
        ListNode result = result1;
        int i=0;
        for (ListNode x : lists) {
            data[i] = x;
            i++;
        }


        int length = 0;
        for (int k=0;k<data.length;k++){
            if (data[k]!=null)length++;
        }
        deleteNull(data);
        ListNode temp = null;


        while(data[0]!=null){
            sort(data,length);
            int tempnum = length;
            for (int j=0;j<tempnum;j++){
                if (data[j]!=null){
                    temp = data[j].next;
                    data[j].next=null;
                    result.next = data[j];
                    data[j] = temp;
                    result=result.next;
                }else{
                    length--;
                }
            }
            deleteNull(data);

        }

        return result1.next;
    }
    private void deleteNull(ListNode[] a){
        for (int jj=0;jj<a.length-1;jj++){
            if (a[jj]==null){
                a[jj] = a[jj+1];
                a[jj+1]=null;
            }
        }
    }
    private void sort(ListNode[] a,int length){
        ListNode temp =null;

        for (int i=1;i<length;i++){
            for (int j=i;j>0;j--){
                if (a[j].val<a[j-1].val){
                    temp = a[j];
                    a[j]=a[j-1];
                    a[j-1]=temp;
                }else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<ListNode> aa = new ArrayList<>();
        aa.add(((new ListNode(2)).next=new ListNode(3)));
        aa.add(new ListNode(0));
        aa.add(null);
        Solution s = new Solution();
        ListNode dd = s.mergeKLists(aa);
        while(dd!=null){
            System.out.println(dd.val);
            dd=dd.next;
        }
    }
}




class ListNode {
    int val;
    ListNode next;
    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}









