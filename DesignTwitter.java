// 355.

//space - O(n)
public class Tweet {
    int id; //primary key in tweet table
    int createdAt; 
    
    public Tweet(int id, int createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}

class Twitter {
    HashMap<Integer, Set<Integer>> following; // u1 -> [u2, u3] -> u1 follows u2 and u3
    HashMap<Integer, List<Tweet>> tweets; // u1 -> [t1, t2, t3] -> u1 has posted tweets t1, t2, t3
    int timeStamp;
    int feedSize;
    
    /** Initialize your data structure here. */
    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
        timeStamp = 0;
        feedSize = 10;
    }
    
    //time - O(1)
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!following.containsKey(userId)) //new user
        {
            following.put(userId, new HashSet<>());
        }
        following.get(userId).add(userId); //the user follows himself when he posts a tweet
        if(!tweets.containsKey(userId)) //new user in tweets table
        {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++)); //create an entry for tweet in tweets hashmap with id = tweetId and created at = timestamp++
        return;
    }
    
    //get the tweets posted by all people who the user follows(including the user's slef tweet)
    //return the most recent 10 tweets using a pq
    //time - O(nlog(feedSize))
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        //build min heap based on created at
        PriorityQueue<Tweet> support = new PriorityQueue<>((t1, t2) -> t1.createdAt - t2.createdAt); 
        Set<Integer> people = following.get(userId); //list of people who userId follows
        if(people != null)
        {
            for(Integer user : people)
            {
                List<Tweet> fTweets = tweets.get(user); //get the tweet posted by each person
                if(fTweets != null)
                {
                    for(Tweet tweet : fTweets) //update in pq accordingly
                    {
                        if(support.size() < feedSize)
                        {
                            support.add(tweet);
                        }
                        else
                        {
                            if(support.peek().createdAt < tweet.createdAt)
                            {
                                support.poll();
                                support.add(tweet);
                            }
                        }
                        
                    }
                }
            }
        }
        
        List<Integer> feedIds = new ArrayList<>();
        while(!support.isEmpty())
        {
            feedIds.add(0, support.poll().id);
        }
        
        return feedIds;
    }
    
    //time - O(1)
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!following.containsKey(followerId)) 
        {
            following.put(followerId, new HashSet<>());
        }
        following.get(followerId).add(followeeId);
        return;
    }
    
    //time - O(1)
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        //followerId must exist in the map and follower != followee as we cant unfollowe ourselves after tweeting
        if(following.containsKey(followerId) && followerId != followeeId)
        {
            following.get(followerId).remove(followeeId); 
        }
        return;
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
