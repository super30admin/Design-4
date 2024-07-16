

// Time Complexity : O(nxn -> followers/tweets/users)
// Space Complexity : O(n) -> followers/tweets/users
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


class Twitter {
    class Tweet { // Tweet class, this will be stored in the map. 
        int tweetId; //  tweetId-> to output the tweetId in  getNewsFeed function
        int createdAt; // to arrange tweets in order of occurence

        public Tweet(int id, int time) {
            this.tweetId = id;
            this.createdAt = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> userMap; // stores users and set of followers
    HashMap<Integer, List<Tweet>> tweetMap; // stores tweets and who tweeted them
    int time; // global artificial time variable

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)){ // check if tweet map has this user's entries
            tweetMap.put(userId, new ArrayList<>()); // put its entries
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++)); // create a new tweet object and initialise it with tweet id and time given
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId); // following ourselves so we get our tweets
        PriorityQueue<Tweet> pq = new PriorityQueue<>( (a,b) -> a.createdAt - b.createdAt); // to sort tweets in descending order
        HashSet<Integer> allUsers = userMap.get(userId); // getting all users that the user follows, this will be used to collect all the tweets
        for(Integer user : allUsers){ // iterating over all the users that user follows
            List<Tweet> alltweets = tweetMap.get(user); // get all the tweets of each user
            if(alltweets!=null){ // check if the followed user has tweets
                for(Tweet tw : alltweets){ // if yes, then traverse through it
                    pq.add(tw); // add each tweet to the heap
                    if(pq.size() > 10){ // limiting the size of heap as we only want recent 10 tweets
                        pq.poll(); // removing the oldest one
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>(); // resultant list to store all tweet ids
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId); // adding to list, to the 0th index as it adds from end
        }
        return result;

    }

    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId) ){
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId) || followerId == followeeId){ // 2nd check cause of the getNewsFeed function, not to unfollow ourselves
            return;
        }
        if(userMap.get(followerId).contains(followeeId)){
            userMap.get(followerId).remove(followeeId);
        }
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