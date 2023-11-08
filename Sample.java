// # Design-4

// ## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : I didnt face any problem while coding this.

class Twitter {
    class Tweet{
        int tid; int created_at;
        public Tweet(int id, int created_at){
            this.tid = id;
            this.created_at = time;
        }
    }
    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    public Twitter() {
       this.userMap = new HashMap<>();
       this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)){
            follow(userId, userId);
            tweets.put(userId, new ArrayList<>());
        }
        time++;
        tweets.get(userId).add(new Tweet(tweetId, time));
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.created_at - b.created_at);
        List<Integer> result = new ArrayList<>();
        if(userMap.containsKey(userId)){
            HashSet<Integer> followed = userMap.get(userId);
            for(int fid : followed){
                List<Tweet> ftweets = tweets.get(fid);
                if(ftweets != null){
                    for(Tweet t : ftweets){
                        pq.add(t);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tid);
        }
        return result;
        }
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }
    
    
    
    public void unfollow(int followerId, int followeeId) {
        if(followerId != followeeId && userMap.containsKey(followerId)){
            userMap.get(followerId).remove(followeeId);
        }
    }
}



// ## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

// "static void main" must be defined in a public class.


class SkipIterator implements Iterator<Integer> {
   HashMap<Integer, Integer> skipMap;
   Integer nextEl;
   Iterator<Integer> nit;
   public SkipIterator(Iterator<Integer> it) {
        this.skipMap = new HashMap<>(;
        this.it = it;
        advance();
    }
    private void advance(){ //O(n)
        // it is taking the pointer of skip iterator to next valid location;
        nextEl = null;
        while(nextEl != null && nit.hasNext()){
            Integer el = nit.next();
            if(skipMap.containsKey(el)){
                skipMap.put(el.skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            }else{
                nextEl = el;
            }
        }
 
    }
    public void skip(int num) {  //O(n)
        if(num!=nextEl){
            skipMap.put(num.skipMap.getOrDefault(num, 0) + 1);
        }else{
            advance();
        }
    }
   @Override
     public boolean hasNext() { //O(1)
         return nextEl != null;
     }

   @Override
     public Integer next() {  //O(n)
        Integer result = nextEl;
        return result;
     }

  
}

public class Main {

         public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(it.hasNext());// true // nextEl = 5
        it.skip(5);//  nextEl = 6
         System.out.println(it.next()); //6   nextEl = 7
         it.skip(5); 
             System.out.println(it.next());// 7 nextEl = 6
             System.out.println(it.next()); // 6nextEl = 6
          it.skip(8); // 
        it.skip(9); //
             
        System.out.println(it.next()); // 5
             
         System.out.println(it.next()); //5
         System.out.println(it.next());//6 
             System.out.println(it.hasNext());// true
         System.out.println(it.next());// 8
         // it.skip(1);

//          it.skip(3);

         System.out.println(it.hasNext()); //false 

     }

 }