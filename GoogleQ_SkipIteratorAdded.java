// "static void main" must be defined in a public class.
//main starts here
public class Main 
{
            public static class SkipIterator implements Iterator<Integer>
            {

                Iterator<Integer> native_iterator;
                Integer nextEl;
                Map<Integer, Integer> skipMap; //key value pair for skip val and it frequency for reference

                public SkipIterator(Iterator iterator)
                {
                    this.native_iterator = iterator;
                    skipMap = new HashMap<>();
                    advance(); //this method helps to move to next element nd also check if we have element to skip!!??
                }
          
                public Integer next()
                {
                    Integer result = nextEl; //once we use the advance method to find the next standing value, advance will reset nextEl to null!
                    advance();
                    return result;
                }

                public boolean hasNext()
                {
                    return nextEl != null; //if it's not null, we have value, so returns true, else false. 
                }

                public void skip(Integer key)
                {
                    if(nextEl == key) //i.e. if we are standing at the value which is equal to kkey, we dont have to put that in map - we just    skip it there
                    {
                        advance(); //this method will take care of the rest
                    }
                    else //value we are standing at is different than key, so we put this key into ma
                    {
                        skipMap.put(key, skipMap.getOrDefault(key,0)+1);
                    }
                }
    
               //advance method
                private void advance()
                {
                    nextEl = null; //we set to null before assigning anything
                    while(nextEl == null && native_iterator.hasNext()) //native iterator check if I have next value!
                    {
                        //we come here only following above conditions where native gets true from hasnext method , so let's assign value to null
                        nextEl = native_iterator.next();
                        //now check if this nextEl - is something exists in map? if not we are good to keep it there and print it, if it does , then 
                        //we decrease the value in map, if it becomes zero, we remove the entire key and also set nextEl == null, as we are not processing value that is asked to skip!

                        if(skipMap.containsKey(nextEl))
                        {
                            int count = skipMap.get(nextEl);
                            count--;
                            if(count ==0) 
                                skipMap.remove(nextEl);
                            else 
                                skipMap.put(nextEl, count);
                            //we are here because nextEl was found in Map so we just set it back to null for new assignment 
                            nextEl = null;
                        }
                    }
                }
        }

    public static void main(String[] args) {
       SkipIterator it = new SkipIterator(Arrays.asList(1,2,3,3,4,5,6,2,7,8,11,23).iterator());
        
        
        it.skip(11); // this will be store in map as we are standing at 1!
        System.out.println(it.next());
        System.out.println(it.next());
        
        it.skip(3);
        it.skip(3);
        it.skip(2);
        
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        
        System.out.println(it.next());
        System.out.println(it.next());
        
        
    }
}