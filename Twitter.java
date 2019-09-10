public class Twitter {
    
    public class Tweet{
        int tweetId; int userId;
        public Tweet (int tweetId, int userId) 
        { this.tweetId = tweetId; 
         this.userId = userId; }
    }
    
    LinkedList<Tweet> tweetStack= new LinkedList<>(); // Create a stack for getting tweets.
    HashMap<Integer, HashSet<Integer>> users = new HashMap<>();// Create a HashMap for maintaining followers table.

    public void postTweet(int userId, int tweetId) 
    {
        
        if (!users.containsKey(userId)) users.put(userId, new HashSet<>());
        tweetStack.push(new Tweet(tweetId, userId));// Post the tweet onto the stack.
    }

    public List<Integer> getNewsFeed(int userId) {
        if (!users.containsKey(userId)) users.put(userId, new HashSet<>());
        List<Integer> result = new ArrayList<>();
        for (Tweet tweet : tweetStack){
            if (users.get(userId).contains(tweet.userId) || tweet.userId == userId) result.add(tweet.tweetId);
            if (result.size() == 10) break;
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) 
    {
        if (!users.containsKey(followerId)) users.put(followerId, new HashSet<>());
        users.get(followerId).add(followeeId); // Add followee ID to the hash table.
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (!users.containsKey(followerId)) users.put(followerId, new HashSet<>());
        users.get(followerId).remove(followeeId); // remove foloweeID from the hash table.
    }
}