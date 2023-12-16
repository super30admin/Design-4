// Time Complexity : O(f) where f is the number of followers the user has
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :no

class Twitter {

    Map<Integer,User> map ;
    static final int k = 10;
    static int time = 0; // time variable for timestamp


    public Twitter() {
        map = new HashMap<>();
    }
    // Time Complexity : O(1)
    public void postTweet(int userId, int tweetId) {
        createIfNotPresent(userId); // if user is not already present then create it
        map.get(userId).postTweet(new Tweet(tweetId , time++));
    }
    // Time Complexity : O(f) where f is the number of followers the user has
    public List<Integer> getNewsFeed(int userId) {
        /*
        1:We will maintain a min heap which will contain the tweet ids with which are smaller on the top (oldest ones), so that the larger ones are in the bottom
        2: We will fetch the folowing from the set and then for each followee, we will put the recent 10 tweets in the heap , as soon as the size goes above 10 we will remove the min item from the heap , in this way , we will only be doing it for a constant number of time k for each followee
        3: Since the size of the heap is constant (k) the insertion is also constant 
        */
        PriorityQueue<Tweet> queue = new PriorityQueue<>((a,b) -> a.time - b.time); // min heap
        List<Integer> result = new ArrayList<>();
        User currentUser = map.get(userId);
        if(currentUser!=null && !currentUser.following.isEmpty()){
            for(Integer followee : currentUser.following){
                if(map.containsKey(followee)){
                    Tweet currentTweet = map.get(followee).head;
                    int c = k;
                    while(currentTweet!=null && c>0){ // k = 10 , so constant
                        queue.add(currentTweet);
                        if(queue.size() > 10){ // so that we always have top 10 elements
                            queue.remove(); // remove the min item
                        }
                        c--;
                        currentTweet = currentTweet.next;
                    }
                }
            }
        }

        Stack<Integer> resultStack = new Stack<Integer>();
        while(!queue.isEmpty()){
            Tweet tweet = queue.remove();
            resultStack.push(tweet.id);
        }

        while(!resultStack.isEmpty()){
            result.add(resultStack.pop());
        }
        return result;

        
    }
    // Time Complexity : O(1)
    public void follow(int followerId, int followeeId) { // this can be called even if the user is not created
        createIfNotPresent(followerId);
        createIfNotPresent(followeeId);
        map.get(followerId).following.add(followeeId);

    }
    // Time Complexity : O(1)
    public void unfollow(int followerId, int followeeId) {
        map.get(followerId).following.remove(followeeId);
    }
    // Time Complexity : O(1)
    private void createIfNotPresent(int userId){
        if(!map.containsKey(userId)){
            map.put(userId , new User(userId));
        }
    }
}

class Tweet{
    int id;
    int time;
    Tweet next; // next Tweet

    public Tweet(int i, int t){
        id = i;
        time = t;
    }

}

class User{
    int id;
    Set<Integer> following;
    Tweet head; // head of tweet list

    public User(int i){
        id = i;
        following = new HashSet<>();
        following.add(id); // user will follow itself
    }

    public void postTweet(Tweet newTweet){
        if(head != null){ // appending new tweet at the head of the list , so most recent ones are on the front
            newTweet.next = head; // point new tweet next to where the head is pointing
            head = newTweet; // point head to new tweet
        }
        else{
            head = newTweet;
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