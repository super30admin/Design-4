
// Time Complexity : O(n + m) as we are traversing throuigh all the values in 2 arrays.
// Space Complexity : O(1) we are not creating any extra space.
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : no issues as of now.. Learning


class Solution {
public:
    void merge(vector<int>& nums1, int m, vector<int>& nums2, int n) {
         int l1=m-1,l2=n-1,f=m+n-1;
            
            while(l1>=0 && l2>=0){
                if(nums1[l1]>nums2[l2])
                    nums1[f--]=nums1[l1--];
                else
                    nums1[f--]=nums2[l2--];
                
            }
        
        if(l2<0)
            while(l1>=0){
                 nums1[f--]=nums1[l1--];
       
            }
        else
            while(l2>=0)
                nums1[f--]=nums2[l2--];
                
                
        
    }
};