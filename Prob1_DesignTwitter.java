// Time Complexity : O(M*T) // M users and T tweets
// Space Complexity : O(M*T)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach


class Twitter {
    class Tweet{ //Tweet object which is having it's ID and time when it created
        int tid;
        int created;
        public Tweet(int tid, int created){
            this.tid = tid;
            this.created = time;
        }
    }
    
    HashMap<Integer, HashSet<Integer> > followed; //Mapping of User to set of followings of that User
    HashMap<Integer, List<Tweet> > tweets; // Mapping of User to tweets done by that User
    int time;
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) { 
        if(!tweets.containsKey(userId)){ //If any User didn't posted tweet yet, then add list to store Tweets in Map
            tweets.put(userId, new ArrayList<>());
        }
        follow(userId, userId);//Add User with UserID in followings HashMap
        tweets.get(userId).add(new Tweet(tweetId, time++)); // Add tweet object into User's tweet into Tweets Map
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> queue = new PriorityQueue<>((a,b) -> a.created - b.created); //Storing Tweets objects
        
        HashSet<Integer> followings = followed.get(userId); //Fetched followings of User with userId
        
        if(followings == null)  return new ArrayList<>();
        
        for(Integer fId : followings){ // For each following of User
            List<Tweet> fIdTweets = tweets.get(fId); //Retrieved tweets for that User with fId
            
            if(fIdTweets != null){
                int len = fIdTweets.size();
                
                for(int i = len - 1; i>= 0 && i >= len - 11; i--){ //We want at most 10 most tweets of any User
                    queue.add(fIdTweets.get(i)); //Adding those 10 tweets of user into Priority Queue
                    
                    if(queue.size() > 10){ // Priority queue will be maintaining only 10 tweets which will be based on time(Comparator was tweet created)
                        queue.poll();
                    }
                }
            }
        }
        
        List<Integer> data = new ArrayList<>();
        while(!queue.isEmpty()){
            Tweet temp = queue.poll(); //Fetching tweets and adding it's ID in result list
            data.add(0, temp.tid);
        }
        
        return data;
        
    }
    
    public void follow(int followerId, int followeeId) { // If user with followerId is not in followeed map, add that user 
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        } 
        followed.get(followerId).add(followeeId);//Add user with followeeId for User with followerId
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId){ // If user with followerId is followeed map and both IDs are different 
            followed.get(followerId).remove(followeeId);
        }
    }
}

