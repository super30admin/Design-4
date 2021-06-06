//Time complexity O(1)
//Space complexity O(n)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No

// "static void main" must be defined in a public class.
class SkipIterator implements Iterator<Integer> {
    
    Iterator<Integer> it;
    HashMap<Integer,Integer> map1;
    Integer nextEl;
    
    public SkipIterator(Iterator<Integer> it){
    
    this.it = it;
    map1 = new HashMap<>();
    advance();
}
@Override

public boolean hasNext(){
    
    return nextEl != null;
}


@Override

public Integer next(){
    
    Integer result = nextEl;
    advance();
    return result;
    
}

public void skip(int num){
    
    if(num == nextEl){
        
        advance();
    }
    
    else {
        
    
            
            map1.put(num, map1.getOrDefault(num,0) + 1);
        
      
    }
    
}
private void advance() {
    
    nextEl = null;
    
    while(nextEl == null && it.hasNext()){
        
        Integer el = it.next();
        if(map1.containsKey(el)){
            
            map1.put(el, map1.get(el) - 1);
            if(map1.get(el) == 0){
                
                map1.remove(el);
            }
        }
        
        else {
            
            nextEl = el;
        }
    }
}
    
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        SkipIterator it = new SkipIterator(Arrays.asList(2,5,3,6,5,8,7,6,5,4).iterator());
        
        System.out.println(it.hasNext());
        
        it.skip(5);
        
        it.skip(5);
        
        it.skip(6);
        
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.hasNext());
    }
}