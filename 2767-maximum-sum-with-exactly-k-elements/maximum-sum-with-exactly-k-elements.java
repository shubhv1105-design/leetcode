class Solution {
    public int maximizeSum(int[] nums, int k) {
        int max = 0;
        int sum=0;
        for (int i = 0;i<nums.length;i++){
         if(nums[i]>max){
            max = nums[i];
         }
        }
        while (k!=0){
            sum += max ;
            max++;
            k--;
        }
        return sum;
    }
}