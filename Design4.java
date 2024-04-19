//Space O(N) and Time O(1)
class Twitter {

    private Map<Integer, HashSet<Integer>> followers;
    private Map<Integer, List<Tweet>> tweetsMap;
    private int time;
    class Tweet {
        int id;
        int createdAt;
        public Tweet(int id, int time) {
            this.id = id;
            this.createdAt = time;
        }
    }

    public Twitter() {
        this.followers = new HashMap<>();
        this.tweetsMap = new HashMap<>();
        
    }
    //O(1)
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId,new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId,time));
        time++;  
    }
    
    //O(1)
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt- b.createdAt);
        List<Integer> result = new ArrayList<>();

        HashSet<Integer> userList = followers.get(userId);
        if(userList!=null){
                    for(int user:userList){
                        List<Tweet> tweets = tweetsMap.get(user);
                        if(tweets!=null){
                            for(Tweet tweet:tweets){
                                pq.add(tweet);
                                if(pq.size()>10){
                                    pq.poll();
                                }
                            }  
                        }  
                    }
        }


        while(!pq.isEmpty()){
            result.add(0,pq.poll().id);
        }

        return result;
        
    }
    
    //O(1)
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)){
            followers.put(followerId,new HashSet<>());
        }
        followers.get(followerId).add(followeeId); 
    }
    
    //O(1)
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId!=followeeId){
            followers.get(followerId).remove(followeeId);
        }
    }
}


//time O(1) and space O(N)
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit;
    Integer nextEl;
    Map<Integer,Integer> skipMap;

	public SkipIterator(Iterator<Integer> it) {
        this.it = nit;
        skipMap = new HashMap<>();
        advance();
	}
    
    public void advance(){
        this.nextEl = null;
        while(nit.hasNext() && nextEl==null){
            Integer el = nit.next();
            if(skipMap.containsKey(nextEl)){
                skipMap.get(el)-1;
                skipMap.remove(el,0);
            }else{
                nextEl = el;
            }
        }
        
    }

	public boolean hasNext() {
        return nextEl!=null;
	}

	public Integer next() {
        Integer re = nextEl;
        advance();
        return re;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val!=nextEl){
            skipMap.put(val,getOrDefault(val,0)+1);
        }else{
            advance();
        }
	}
}
