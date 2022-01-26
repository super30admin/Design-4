// Time Complexity : O(1)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode : Yes
// Three line explanation of solution in plain english
// Take a hashmap of userid and list of tweets to store the tweets. and a hashmap of userid who is following all the user stored in HashSet of users, int the tweet along with ID store the timer count so that we can set priority based on that
// Your code here along with comments explaining your approach
class Twitter {
    class Tweet{
        int id;
        int tCount;
        public Tweet(int id, int tCount){
            this.id = id;
            this.tCount = tCount;
        }
    }

    HashMap<Integer, List<Tweet>> hmT;
    HashMap<Integer, HashSet<Integer>> hmU;
    int time;

    public Twitter() {
        hmT = new HashMap<>();
        hmU = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!hmT.containsKey(userId)){
            hmT.put(userId, new ArrayList<>());
        }
        hmT.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        // System.out.println("Call for User id "+userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.tCount - b.tCount);
        HashSet<Integer> hs = hmU.get(userId);
        // System.out.println("Users we have "+hs);
        List<Integer> res = new ArrayList<>();
        if(hs != null){
            for(Integer u : hs){
                // System.out.println("User id "+u);
                List<Tweet> ls = hmT.get(u);
                if(ls != null){
                    for(Tweet t: ls){
                        // System.out.println("Twi id "+t.id);
                        pq.add(t);
                        if(pq.size()>10){
                            pq.poll();
                            //System.out.println("Twi id removed"+pq.poll().id);
                        }
                    }
                }
            }
        }
        while(!pq.isEmpty()){
            res.add(0,pq.poll().id);
        }
        return res;
    }

    public void follow(int followerId, int followeeId) {
        if(!hmU.containsKey(followerId)){
            hmU.put(followerId, new HashSet <>());
        }
        hmU.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(hmU.containsKey(followerId) && followerId != followeeId){
            hmU.get(followerId).remove(followeeId);
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