class Twitter {

    long time = 0l;

    Map<Integer, Set<Integer>> friendList;
    Map<Integer, ListNode> headTweetMap;

    public Twitter() {
        time = 0l;
        friendList = new HashMap<>();
        headTweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {

        ListNode curr = new ListNode(tweetId, time);

        if(headTweetMap.containsKey(userId)){
            ListNode head = headTweetMap.get(userId);
            curr.next = head;
        }

        headTweetMap.put(userId, curr);
        time++;
    }

    public List<Integer> getNewsFeed(int userId) {

        Queue<ListNode> pq = new PriorityQueue<>( (a,b) -> Long.compare(b.time, a.time));

        if(headTweetMap.containsKey(userId)){
            pq.add(headTweetMap.get(userId));
        }

        if(friendList.containsKey(userId)){
            for(int friend : friendList.get(userId) ){

                if(headTweetMap.containsKey(friend)){
                    pq.add(headTweetMap.get(friend));
                }
            }
        }

        List<Integer> res = new ArrayList<>();

        int i=0;
        while(!pq.isEmpty() && i<10){

            ListNode curr = pq.poll();
            res.add(curr.tweetId);

            if(curr.next != null){
                pq.add(curr.next);
            }

            i++;
        }
        return res;
    }

    public void follow(int followerId, int followeeId) {
        Set<Integer> friends = friendList.getOrDefault(followerId, new HashSet<>());
        friends.add(followeeId);
        friendList.put(followerId,friends);
    }

    public void unfollow(int followerId, int followeeId) {
        Set<Integer> friends = friendList.get(followerId);
        if(friends != null){
            friends.remove(followeeId);
        }
    }
}

class ListNode{

    int tweetId;
    long time;
    ListNode next;

    public ListNode(int tweetId, long time){
        this.tweetId = tweetId;
        this.time = time;
        this.next = null;
    }

}
