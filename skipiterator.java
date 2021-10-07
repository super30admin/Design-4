
time complexity: O(k)
space complexity: O(n)

public class SkipIterator {
Map<Integer, Integer> hm;
Long isNext;
Iterator itr;

SkipIterator(Iterator<Integer> itr){
    hm = new HashMap<>();
    isNext = Long.MAX_VALUE;
    this.itr = itr;
}

public boolean hasNext(){
    if(isNext != Long.MAX_VALUE){
        return true;
    }
    else {
        try{
        isNext = next();
        hasNext();
        }
        catch(Exception e){
            return false;
        }
    }
    return false;
}

public int next(){
    if(isNext != Long.MAX_VALUE){
        int temp = isNext;
        isNext = Long.MAX_VALUE;
        return temp;
    }
    else if(isNext == Long.MAX_VALUE){
        if(itr.hasNext()){
            int current = itr.next();
            if(hm.containsKey(current) && hm.get(current) != 0){
                hm.put(current, hm.get(current)-1);
                next();
            }
            return current;
        }
    }
    else {
        return new IndexOutOfBound();
    }
}

public void skipValue(int val){
    hm.put(val, hm.getOrDefault(val, 0)+1);
}
}
