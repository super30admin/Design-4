// Time Complexity : 
// postTweet -> O(1)
// follow -> O(1)
// unfollow -> O(1)
// getNewsFeed -> O(f * 10 * log 10) where f is number of followed users
//
// Space Complexity : O(total tweets + total follow relationships)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach in three sentences only
// We store follow relationships using a HashMap of HashSets and maintain each user's tweets in chronological order using another HashMap.
// For generating the news feed, we use a Min Heap of size 10 to keep track of the 10 most recent tweets among all followed users.
// Every time a tweet is posted, we assign an increasing timestamp so tweets can be ordered globally by recency.

class Twitter {

    class Tweet {

        int tid;

        int createdAt;

        public Tweet(int tweetId, int time) {

            this.tid = tweetId;

            this.createdAt = time;
        }
    }

    // follower -> followees
    private HashMap<Integer, HashSet<Integer>> followed;

    // user -> list of tweets
    private HashMap<Integer, List<Tweet>> tweetMap;

    // global timestamp
    private int time;

    public Twitter() {

        this.followed = new HashMap<>();

        this.tweetMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {

        // user follows themselves
        follow(userId, userId);

        // initialize tweet list
        if (!tweetMap.containsKey(userId)) {

            tweetMap.put(userId, new ArrayList<>());
        }

        // add tweet with timestamp
        tweetMap.get(userId)
                .add(new Tweet(tweetId, time));

        time++;
    }
    
    public List<Integer> getNewsFeed(int userId) {

        // min heap based on timestamp
        PriorityQueue<Tweet> pq =
            new PriorityQueue<>(
                (a, b) -> a.createdAt - b.createdAt
            );

        HashSet<Integer> followedIds =
            followed.get(userId);

        if (followedIds != null) {

            for (int fId : followedIds) {

                List<Tweet> fTweets =
                    tweetMap.get(fId);

                if (fTweets != null) {

                    int size = fTweets.size();

                    // process latest 10 tweets
                    for (int i = size - 1;
                         i >= 0 && i >= size - 10;
                         i--) {

                        Tweet tweet = fTweets.get(i);

                        pq.add(tweet);

                        // keep only top 10 recent tweets
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();

        // build result in reverse order
        while (!pq.isEmpty()) {

            result.add(0, pq.poll().tid);
        }

        return result;
    }
    
    public void follow(int followerId, int followeeId) {

        // initialize follow set
        if (!followed.containsKey(followerId)) {

            followed.put(
                followerId,
                new HashSet<>()
            );
        }

        followed.get(followerId)
                .add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {

        // user cannot unfollow themselves
        if (followerId != followeeId) {

            if (followed.containsKey(followerId)) {

                followed.get(followerId)
                        .remove(followeeId);
            }
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