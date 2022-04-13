// Time Complexity : postTweet, follow, unfollow = O(1)
                    //getNewsFeed - O(followings) the no. users one is following
// Space Complexity : O(Tweets) no. of tweets
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
// Create user class and tweet class
// We will also maintain a following list 
// To get newsfeed we will create a maxHeap and add head of all the followings
// We will pop ten elements from the heap and add to result list, simultaneously we will 
// add the next of the poped tweet to the heap.
// Finally we will return the neewsfeed result list
class Twitter {
    Map<Integer, User> hm;
    PriorityQueue<Tweet> pq;
    static int timeStamp = 0;
    class User{
        int id;
        Tweet head;
        Set<Integer> following;
        User(int id){
            this.id = id;
            following = new HashSet<>();
            following.add(id);
            //head = new Tweet();
        }
        void follow(int id){
            following.add(id);
        }
        void unfollow(int id){
            following.remove(id);
        }
    }
    class Tweet{
        int tid;
        int ts;
        Tweet next;
        Tweet(int tid){
            this.tid = tid;
            this.ts = timeStamp++;
        }
    }

    public Twitter() {
        hm = new HashMap<>();
        pq = new PriorityQueue<>((a, b) -> b.ts - a.ts );
    }
    
    public void postTweet(int userId, int tweetId) {
        createUserIfNotExist(userId);
        Tweet tw = new Tweet(tweetId);
        User user = hm.get(userId);
        tw.next = user.head;
        user.head = tw;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        if(hm.get(userId) == null){
            return new ArrayList<Integer>();
        }
        List<Integer> newsfeed = new ArrayList<>();
        Set<Integer> hs = hm.get(userId).following;
        for(Integer fol: hs){
            if(hm.get(fol).head != null)
                pq.add(hm.get(fol).head);
        }
        for(int i = 0; i < 10; i++){
            if(!pq.isEmpty()){
            Tweet cur = pq.poll();
            newsfeed.add(cur.tid);
            if(cur.next != null)
                pq.add(cur.next);
            }
        }
        pq.clear();
        return newsfeed;
    }
    
    public void follow(int followerId, int followeeId) {
        createUserIfNotExist(followerId);
        createUserIfNotExist(followeeId);
        hm.get(followerId).follow(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        hm.get(followerId).unfollow(followeeId);
    }
    public void createUserIfNotExist(int id){
        if(!hm.containsKey(id)){
            User user = new User(id);
            hm.put(id, user);
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