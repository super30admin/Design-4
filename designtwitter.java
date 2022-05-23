// TC getNewsFeed O(No of tweets) 
class Twitter {
    class Tweet{
        int id;
        int createdAt; 
        public Tweet(int id, int timestamp){
            this.id = id;
            this.createdAt = timestamp;
        }
    }
    int timestamp; 
    HashMap<Integer, HashSet<Integer>> userTable;
    HashMap<Integer, List<Tweet>> tweetTable;
    int newsfeedLimit;
    public Twitter() {
        this.timestamp =0; 
        this.newsfeedLimit =10;
        userTable = new HashMap<>();
        tweetTable = new HashMap<>();
        
        
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweetTable.containsKey(userId)){
            tweetTable.put(userId, new ArrayList<>());
        }
        tweetTable.get(userId).add(new Tweet(tweetId, timestamp++));
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followeeList = userTable.get(userId);
        if(followeeList != null){
            for(Integer followee:followeeList){
                List<Tweet> TweetList = tweetTable.get(followee);
                if(TweetList != null){
                    for(Tweet eachTweet:TweetList){
                        pq.add(eachTweet);
                        if(pq.size() > newsfeedLimit){
                            pq.poll();
                        }
                    }
                }
                
            }
            
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
        return result;
        
    }
    
    public void follow(int followerId, int followeeId) {
        if(!userTable.containsKey(followerId)){
            userTable.put(followerId, new HashSet<>());
        }
        userTable.get(followerId).add(followeeId);
        
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(userTable.containsKey(followerId)){
            userTable.get(followerId).remove(followeeId);
        }
        
        
    }
}











/*
Two approached Strategical -> SYStem level approach 
Tactical approach -> grass root level approach 

First Question to ask interviewer : could you elaborate on the features/ requirements when designing twitter/ 
Ask interviewer where should I start : at backend or at database 
// At database level : Object Oriented Design level : if interview is asking to start at database its object oriented design 
// Practice writing functions according to the requirement 
// 







*/



