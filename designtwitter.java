
time complexity: O(n)
space complexity: O(n)

class Twitter {
    
private int postCount;    
private Map<Integer, Integer> tweetCountMap;
private Map<Integer, List<Integer>> tweetIdMap;
private Map<Integer, Integer> tweetOwnerMap;
private Map<Integer, Set<Integer>> followeeMap;
    
    public Twitter() {
        tweetCountMap = new HashMap<Integer, Integer>();
        tweetIdMap = new HashMap<Integer, List<Integer>>();
        tweetOwnerMap = new HashMap<Integer, Integer>();
        followeeMap = new HashMap<Integer, Set<Integer>>();
    }
    
    public void postTweet(int userId, int tweetId) {
        tweetCountMap.put(tweetId, ++postCount);
        tweetOwnerMap.put(tweetId, userId);
        getTweetIdList(userId).add(tweetId);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<Integer>();
        Set<Integer> followeeSet = getFolloweeSet(userId);
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>(){
            @Override
            public int compare(Integer a, Integer b) {
                return tweetCountMap.get(b) - tweetCountMap.get(a);
            }
        });
        Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
        for (Integer followeeId : followeeSet) {
            List<Integer> tweetIdList = getTweetIdList(followeeId);
            int idx = tweetIdList.size() - 1;
            if (idx >= 0) {
                idxMap.put(followeeId, idx - 1);
                pq.add(tweetIdList.get(idx));
            }
        }
        while (result.size() < 10 && !pq.isEmpty()) {
            Integer topTweetId = pq.poll();
            result.add(topTweetId);
            Integer ownerId = tweetOwnerMap.get(topTweetId);
            int idx = idxMap.get(ownerId);
            if (idx >= 0) {
                List<Integer> tweetIdList = getTweetIdList(ownerId);
                pq.add(tweetIdList.get(idx));
                idxMap.put(ownerId, idx - 1);
            }
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        getFolloweeSet(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (followerId != followeeId) {
         getFolloweeSet(followerId).remove(followeeId);
        }
    }


    private Set<Integer> getFolloweeSet(int userId) {
        Set<Integer> followeeSet = followeeMap.get(userId);
        if (followeeSet == null) {
            followeeSet = new HashSet<Integer>();
            followeeSet.add(userId);
            followeeMap.put(userId, followeeSet);
        }
        return followeeSet;
    }
    
    private List<Integer> getTweetIdList(int userId) {
        List<Integer> tweetIdList = tweetIdMap.get(userId);
        if (tweetIdList == null) {
            tweetIdList = new ArrayList<Integer>();
            tweetIdMap.put(userId, tweetIdList);
        }
        return tweetIdList;
    }
}