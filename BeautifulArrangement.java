class Solution {
    int count;
    List<List<Integer>> res;
    public int countArrangement(int N) {
        res = new ArrayList<>();
        backtrack(N, 1, new ArrayList<>(), new boolean[N]);
        return count;
    }
    private void backtrack(int N, int index, List<Integer> temp, boolean[]used ){
        //goal case
        if(index > N)
            count++;
        for(int i=1; i<=N; i++){
            //consitions given and we 
            //maintaining a used array so that I dont repeat numbers in the perticular arrangement.
            //all numbers should be unique.
            if(!used[i-1] && (i % index ==0 || index % i == 0 )){
                //action, making current number as used for the current arrangement.
                used[i-1] = true;
                //recursion
                backtrack(N, index+1, temp, used);
                //backtrack
                //setting the number as unused again so that I would be able to consider the number 
                //for the next possible arrangement.
                used[i-1] = false;
            }
        }
    }
}
