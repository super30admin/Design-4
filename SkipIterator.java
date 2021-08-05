/*
Time Complexity : 
next() : O(n)
hasnext() : O(1)
skip : O(n)
Space Complexity : O(n)
*/

class SkipIterator implements Iterator<Integer> {

    Map<Integer,Integer> map;
    Iterator<Integer> it;
    Integer cur;

    public SkipIterator(Iterator<Integer> it) {

        map = new HashMap<>();
        this.it = it;
        helper();
    }

    public boolean hasNext() {
        return cur != null;
    }

    public Integer next() {
        Integer res = cur;
        helper();
        return res;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(Integer val) {

        // If we have to skip the current element
        if(cur == val){
            helper();
        }
        // Store the number to be skipped in a hashmap
        else{
            map.put(val,map.getOrDefault(val,0)+1);
        }
    }

    public void helper(){

        cur = null;

        // get the next element
        while(cur == null && it.hasNext()){

            Integer temp = it.next();
            if(map.containsKey(temp)){
                map.put(temp,map.get(temp)-1);

                if(map.get(temp) == 0){
                    map.remove(temp);
                }
            }
            else{
                cur = temp;
            }
        }
    }
}