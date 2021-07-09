
// Time complexity for posttweet, follow and unfollow is O(1), for getNewsFeed is O(N)

// Space complexity - O(N)


class Twitter {

    /** Initialize your data structure here. */

    class Tweet {

        int id;
        int createdAt;

        public Tweet(int tid, int createdAt) {

            this.id = tid;
            this.createdAt = createdAt;

        }

    }

    // Userid with List of tweets
    HashMap<Integer,List<Tweet>> tweets;

    // followeid with no of followeeid's
    HashMap<Integer,Set<Integer>> followed;

    // increase the time by 1 when put in hashmap tweets
    int time;



    public Twitter() {
        tweets = new HashMap<>();
        followed = new HashMap<>();

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {

        follow(userId,userId);  // follow yourself while you post the first tweet


        // if it does not contain userId, add the userId
        if(!tweets.containsKey(userId)) {

            tweets.put(userId, new ArrayList<>());

        }

        //if it contains userId update the arraylist with new tweet and increase the timestamp
        tweets.get(userId).add(new Tweet(tweetId, time++));



    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {

        // add a priority queue of tweet sorted with the timestamp
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);

        // Get the users list for tweets

        Set<Integer> fids = followed.get(userId);

        // iterate over userslist
        if(fids != null) {

            for(int fid: fids) {

                // get each users list of tweets
                List<Tweet> ftweets = tweets.get(fid);

                if(ftweets != null) {

                    // iterate over each users tweet
                    for(Tweet ftweet: ftweets ) {


                        pq.add(ftweet);
                        if(pq.size() > 10) {   // add 10 most recent tweets to the priority queue
                            pq.poll();
                        }

                    }

                }


            }

        }

        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {

            // add the most recent at the top
            result.add(0,pq.poll().id);


        }

        return result;



    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {

        // if followed hashmap does not contain key
        if(!followed.containsKey(followerId)) {


            // put the followerId in the hashmap
            followed.put(followerId, new HashSet<>());

        }

        // get the reference of the followerId and put new followeeId in the set
        followed.get(followerId).add(followeeId);

    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {

        // if it contains follower id and also check that the case where we do not remove ourselves
        if(followed.containsKey(followerId) && followerId != followeeId) {

            // get the set of all followeeid
            Set<Integer> set = followed.get(followerId);

            // if that followeeId is found remove it
            if(set.contains(followeeId)) {

                set.remove(followeeId);

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

