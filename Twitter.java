/*
Time Complexity : 
follow & unfollow : O(1)
getNewsFeed : O(nlogk), n = users * tweets
postTweet : O(1)

 
 */
/* Approach : The Twitter class implementation involves creating a system where users can post tweets, follow/unfollow other users, 
and retrieve a personalized news feed. Tweets are stored with a timestamp, and users automatically follow themselves to simplify tweet 
retrieval. The news feed is generated using a max-heap to prioritize the most recent tweets from followed users, maintaining only 
the top 10 tweets. The follow and unfollow methods manage user relationships, ensuring users cannot unfollow themselves. 
This approach ensures efficient tweet posting and retrieval while maintaining user relationships.
*/
class Twitter {
    // Inner class to represent a Tweet
    class Tweet {
        int tid; // Tweet ID
        int createdAt; // Timestamp of when the tweet was created

        public Tweet(int tid, int time) {
            this.tid = tid;
            this.createdAt = time;
        }
    }

    private HashMap<Integer, HashSet<Integer>> followedMap; // Maps userId to the set of users they follow
    private HashMap<Integer, List<Tweet>> tweetMap; // Maps userId to the list of their tweets
    int time; // Global timestamp to keep track of tweet creation time

    public Twitter() {
        this.followedMap = new HashMap<>(); // Initialize followedMap
        this.tweetMap = new HashMap<>(); // Initialize tweetMap
        this.time = 0; // Initialize time
    }

    // Method to post a tweet
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); // Ensure the user follows themselves
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>()); // Initialize tweet list if not present
        }
        Tweet tw = new Tweet(tweetId, time); // Create a new tweet
        time++; // Increment global timestamp
        tweetMap.get(userId).add(tw); // Add tweet to the user's tweet list
    }

    // Method to get the news feed for a user
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> b.createdAt - a.createdAt); // Max-heap based on tweet creation time
        HashSet<Integer> followedList = followedMap.get(userId); // Get the list of users followed by the user
        if (followedList != null) {
            for (int fId : followedList) {
                List<Tweet> fTweet = tweetMap.get(fId); // Get tweets of followed users
                if (fTweet != null) {
                    for (Tweet t : fTweet) {
                        pq.add(t); // Add tweet to the priority queue
                        if (pq.size() > 10) {
                            pq.poll(); // Maintain only the 10 most recent tweets
                        }
                    }
                }
            }
        }
        List<Integer> tweetIds = new ArrayList<>(); // List to store the tweet IDs for the news feed
        while (!pq.isEmpty()) {
            tweetIds.add(0, pq.poll().tid); // Add tweet IDs to the list in reverse order
        }
        return tweetIds; // Return the list of tweet IDs
    }

    // Method to follow a user
    public void follow(int followerId, int followeeId) {
        if (!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<>()); // Initialize followed set if not present
        }
        followedMap.get(followerId).add(followeeId); // Add followee to the follower's followed set
    }

    // Method to unfollow a user
    public void unfollow(int followerId, int followeeId) {
        if (followedMap.containsKey(followerId) && followerId != followeeId) {
            followedMap.get(followerId).remove(followeeId); // Remove followee from the follower's followed set
        }
    }
}

