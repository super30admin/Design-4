//                      follow        unfollow          postTweet
// Time Complexity:      O(1)           O(1)              O(n)
// Space Complexity:     O(1)           O(1)              O(n)
// where n is number of tweets

class Twitter {

    Map<Integer, List<Integer>> followerToFollowees;
    Map<Integer, List<Integer>> userToTweets;
    Node dummyHead;

    class Node {
        int tweet;
        Node next;
        Node(int t) { tweet = t; }
    }

    public Twitter() {
        followerToFollowees = new HashMap<>();
        userToTweets = new HashMap<>();
        dummyHead = new Node(-1);
    }
    
    public void postTweet(int userId, int tweetId) {
        if(userToTweets.containsKey(userId)) {
            userToTweets.get(userId).add(tweetId);
        }
        else {
            userToTweets.put(userId, new ArrayList(Arrays.asList(tweetId)));
        }
        Node newNode = new Node(tweetId);
        newNode.next = dummyHead.next;
        dummyHead.next = newNode;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        
        List<Integer> feed = new ArrayList<>();
        Set<Integer> tweets = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> followeeQueue = new LinkedList<>();
        
        followeeQueue.add(userId);
        while(!followeeQueue.isEmpty()) {

            // get user
            int user = followeeQueue.remove().intValue();

            // process
            if(userToTweets.containsKey(user)) {
                tweets.addAll(userToTweets.get(user));
            }

            // dependencies
            if(followerToFollowees.containsKey(user) && !visited.contains(user)) {
                for(Integer followee : followerToFollowees.get(user)) {
                    followeeQueue.add(followee);
                    visited.add(followee);
                }
            }
            visited.add(user);
        }

        Node node = dummyHead.next;

        while(node != null) {
            System.out.println(node.tweet);
            if(tweets.contains(node.tweet)) {
                feed.add(node.tweet);
                if(feed.size() == 10)
                    break;
            }
            node = node.next;
        }
        
        return feed;
    }
    
    public void follow(int followerId, int followeeId) {
        if(followerToFollowees.containsKey(followerId)) {
            followerToFollowees.get(followerId).add(followeeId);
        }
        else {
            followerToFollowees.put(followerId, new ArrayList(Arrays.asList(followeeId)));
        }
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followerToFollowees.containsKey(followerId)) {
            followerToFollowees.get(followerId).remove(Integer.valueOf(followeeId));
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



// ******************** Another Approach ********************

// class Twitter {
    
//     private static final int TEN = 10;
//     private static int seq = 0;
//     private Map<Integer, User> usersMap;
    
//     class User {
        
//         int userId;
//         Tweet head;
//         Set<Integer> following;
        
//         User(int userId) {
//             this.userId = userId;
//             following = new HashSet<>();
//             following.add(userId);
//         }
        
//         void follow(int followeeId) {
//             following.add(followeeId);
//         }
        
//         void unfollow(int followeeId) {
//             following.remove(followeeId);
//         }
        
//         void postTweet(int tweetId) {
//             Tweet tweet = new Tweet(tweetId);
//             if(head == null) {
//                 head = tweet;
//             }
//             else {
//                 tweet.next = head;
//                 head = tweet;
//             } 
//         }
        
//     }
    
//     class Tweet {
        
//         int tweetId;
//         int ts;
//         Tweet next;
        
//         Tweet(int tweetId) {
//             this.tweetId = tweetId;
//             this.ts = seq++;
//         }

//     }

//     public Twitter() {
//         usersMap = new HashMap<>();
//     }
    
//     public void postTweet(int userId, int tweetId) {
//         createUserIFNotExists(userId);
//         usersMap.get(userId).postTweet(tweetId);
//     }
    
//     public List<Integer> getNewsFeed(int userId) {
        
//         Set<Integer> followingSet = usersMap.getOrDefault(userId, new User(-1)).following;
//         Queue<Tweet> queue = new PriorityQueue<>((a, b) -> b.ts - a.ts);
//         List<Integer> newsFeed = new ArrayList<>();
        
//         for(int followeeId : followingSet) {
//             User user = usersMap.get(followeeId);
//             if(user != null && user.head != null) {
//                queue.add(user.head); 
//             }
//         }
        
//         while(newsFeed.size() < TEN && !queue.isEmpty()) {
//             Tweet tweet = queue.remove();
//             newsFeed.add(tweet.tweetId);
//             if(tweet.next != null) {
//                 queue.add(tweet.next);
//             }
//         }
        
//         return newsFeed;
        
//     }
    
//     public void follow(int followerId, int followeeId) {
//         createUserIFNotExists(followerId);
//         createUserIFNotExists(followeeId);
//         usersMap.get(followerId).follow(followeeId);
//     }
    
//     public void unfollow(int followerId, int followeeId) {
//         usersMap.get(followerId).unfollow(followeeId);
//     }
    
//     private void createUserIFNotExists(int userId) {
//         if(!usersMap.containsKey(userId)) {
//             usersMap.put(userId, new User(userId));
//         }
//     }
// }

// /**
//  * Your Twitter object will be instantiated and called as such:
//  * Twitter obj = new Twitter();
//  * obj.postTweet(userId,tweetId);
//  * List<Integer> param_2 = obj.getNewsFeed(userId);
//  * obj.follow(followerId,followeeId);
//  * obj.unfollow(followerId,followeeId);
//  */
