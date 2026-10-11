class Solution {
    public int coinChange(int[] coins, int amount) {
      int impossible =  amount+1;
      int [] dp = new int[amount +1 ];
      Arrays.fill(dp,impossible);

      dp[0]= 0;
      for (int current = 1;current<= amount; current ++ ){

        for (int coin:coins){
            if (coin<= current && dp[current- coin] != impossible){
                dp[current] = Math.min(dp[current],
                          dp[current - coin]+1);

            }
        }
      }
     return dp[amount] == impossible ?   -1: dp[amount];


    }
}
