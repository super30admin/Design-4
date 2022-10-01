// Time Complexity : O(1)
// Space Complexity: O(NK) where N is num of users and K is number of tweets
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
// Your code here along with comments explaining your approach:
/*
Used a doubly LL to save all the incoming tweets. Insert at head at each point
whenever get tweets method is called return the first 10 tweets of the DLL
*/
public class TwitterDesign {

    class TweetsList {
        int tweetId;
        int userId;
        TweetsList next;
        TweetsList prev;

        TweetsList(int tweetId, int userId) {
            this.tweetId = tweetId;
            this.userId = userId;
        }
    }

    HashMap<Integer, HashSet<Integer>> followersMap;
    TweetsList head;
    TweetsList tail;

    public Twitter() {
        followersMap = new HashMap<>();
        head = new TweetsList(-1, -1);
        tail = new TweetsList(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public void postTweet(int userId, int tweetId) {
        //O(1) - since at max we need to itearte 10 times on listNodes.
        TweetsList tweet = new TweetsList(tweetId, userId);
        addNode(tweet);
        HashSet followingList = followersMap.getOrDefault(userId, new HashSet<>());
        followingList.add(userId);
        followersMap.put(userId, followingList);
    }

    private void addNode(TweetsList node) {
        head.next.prev = node;
        node.next = head.next;
        head.next = node;
        node.prev = head;
    }

    public List<Integer> getNewsFeed(int userId) {
        //O(N) TC
        List<Integer> result = new ArrayList<>();
        TweetsList curr = head.next;
        HashSet followingList = followersMap.get(userId);
        if (followingList == null || followingList.size() == 0) return result;
        int count = 0;
        while (curr != tail) {
            if (curr.userId == userId || followingList.contains(curr.userId)) {
                result.add(curr.tweetId);
                count++;
                if (count == 10) return result;
            }
            curr = curr.next;
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        //O(1)
        HashSet followersList = followersMap.getOrDefault(followerId, new HashSet<>());
        followersList.add(followeeId);
        followersMap.put(followerId, followersList);
    }

    public void unfollow(int followerId, int followeeId) {
        //(1)
        if (followersMap.containsKey(followerId)) {
            HashSet followersList = followersMap.get(followerId);
            if (followersList.contains(followeeId)) {
                followersList.remove(followeeId);
                followersMap.put(followerId, followersList);
            }
        }
    }
}
