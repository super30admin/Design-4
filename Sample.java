//Problem 1: Design Twitter
// Time Complexity : O(10n)
// Space Complexity : O(n) 
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
// Your code here along with comments explaining your approach
//Create a custom class and maps for storing followees and tweets, maintain a global timestamp for timestamps, and use min heap to put out top 10 tweets
class Twitter {
    class Tweet{
        int tid;
        int time;
        public Tweet(int tid, int time){
            this.tid=tid;
            this.time=time;
        }
    }
    
    HashMap<Integer,HashSet<Integer>> followedMap; //map for people user follows
    HashMap<Integer, List<Tweet>> tweetMap; //map for tweets
    int timestamp;
    
    public Twitter() {
       this.followedMap=new HashMap<>();
       this.tweetMap= new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) { //O(1)
        if(!followedMap.containsKey(userId))
            follow(userId,userId); //self follow

        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId,new ArrayList<>());//add tweet
        }
        tweetMap.get(userId).add(new Tweet(tweetId, timestamp++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq=new PriorityQueue<>((a,b)->a.time-b.time); //min heap based on timestamp
        HashSet<Integer> followeds = followedMap.get(userId); 
        if(followeds!=null){
            for(int uid: followeds){
                List<Tweet> userTweets=tweetMap.get(uid); //get tweets of userids

                if(userTweets!=null){
                    int k=Math.min(userTweets.size(),11);
                    for(int i=k-1;i>=0 && k>=0;i--,k--){ //add all user tweets until k
                         pq.add(userTweets.get(i));
                        if(pq.size()>10) //if size greater than 10(global k) then poll
                            pq.poll();
                    }
                
                    // for(Tweet t: userTweets){
                    //     pq.add(t);
                    //     if(pq.size()>10)
                    //         pq.poll();
                    // }
                }
            }
        }
        List<Integer> res= new ArrayList<>();
        while(!pq.isEmpty()){//O(k)
            res.add(0,pq.poll().tid);//O(k)
        }
        return res;
    }
    
    public void follow(int followerId, int followeeId) { //O(1)
        if(!followedMap.containsKey(followerId)){
            followedMap.put(followerId, new HashSet<>()); //create a new hashset 
            followedMap.get(followerId).add(followerId);//follow urself
        }
        followedMap.get(followerId).add(followeeId); //follow followee
    }
    
    public void unfollow(int followerId, int followeeId) { //O(1)
        if(followedMap.containsKey(followerId) && followerId!=followeeId){ //check if follower exists, then unless we unfollow self, do an unfollow
            followedMap.get(followerId).remove(followeeId);
        }
    }
}


//Problem 2: Skip Iterator
// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach
//Maintain a native operator and enter into skip map only if element is in future,(can be done without too but not efficient)
//if skipping element is next element, dont add to map and just move pointers
//TC:O(n) SC: O(n)
class SkipIterator implements Iterator<Integer> {

    private HashMap<Integer, Integer> skipMap;
    private Integer nextEl;
    Iterator<Integer> nit;
   public SkipIterator(Iterator<Integer> it) { 
       this.skipMap = new HashMap<>(); 
       this.nit = it; //native iterator
       advance();
    }
    
    private void advance(){  //O(n)
       nextEl = null; 
        while(nit.hasNext() && nextEl == null){ //check for next element
            Integer el = nit.next();
            if(skipMap.containsKey(el)){ //check if next element is skipping element
                skipMap.put(el,skipMap.get(el) - 1); //reduce from skipmap
                skipMap.remove(el, 0); //if el is 0 remove, this wont remove anything if el is not zero
            } else {
                nextEl = el; //if not assign element to nextelement.
            }
        }
    }
   @Override
     public boolean hasNext() {  //O(1)
        return nextEl != null; //check if nextElement is assigned 
     }

   @Override
     public Integer next() { //O(n)
        Integer result = nextEl; //nextElement
        advance(); //iterate to next in iterator
        return result;
     }

    public void skip(int num) { //O(n)
       if(num == nextEl){ //check if nextElement is the skiping number
           advance(); //just iterate to next pointer
       } else {
           skipMap.put(num, skipMap.getOrDefault(num, 0) + 1); //if it is future element, add it in skipmap
       }
    }
}

