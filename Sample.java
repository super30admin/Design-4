//Twitter
//Time complexity : O(1)
//Space complexity : O(N)


class Twitter {

    private Map<Integer, Deque<Integer[]>> tweets;
    private Map<Integer, Set<Integer>> users;
    private int nextTweetID;
    
    public Twitter() {
        this.tweets = new HashMap<>();
        this.users = new HashMap<>();
        this.nextTweetID = 1;
    }
    
    public void postTweet(int userId, int tweetId) {
        tweets.putIfAbsent(userId, new LinkedList<>());
        tweets.get(userId).add(new Integer[] {tweetId, nextTweetID++});
        if (tweets.get(userId).size() > 10) {
            tweets.get(userId).pollFirst();
        }
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Integer[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        
        addTweetsByUserId(pq, userId);
        
        for (Integer followeeId : users.getOrDefault(userId, new HashSet<>())) {
            addTweetsByUserId(pq, followeeId);
        }
        
        int size = pq.size();
        List<Integer> feed = new ArrayList<>();
        while (!pq.isEmpty()) {
            feed.add(pq.poll()[0]);
        }
        Collections.reverse(feed);
        return feed;
    }
    
    public void follow(int followerId, int followeeId) {
        if (followerId != followeeId) {
            users.putIfAbsent(followerId, new HashSet<>());
            users.get(followerId).add(followeeId);
        }
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (users.containsKey(followerId)) {
            users.get(followerId).remove(followeeId);
        }
    }
    
    private void addTweetsByUserId(PriorityQueue<Integer[]> pq, int userId) {
        LinkedList<Integer[]> tweetList = (LinkedList<Integer[]>)tweets.getOrDefault(userId, new LinkedList<>());
        for (Integer[] tweet : tweetList) {
            pq.add(tweet);
            if (pq.size() > 10) {
                pq.poll();
            }
        }
    }
    
}



Skip Iterator
//Time complexity : O(N)
//Space complexity : O(N)

class SkipIterator {
	private int index = 0;
	private int[] nums = null;
	private Map<Integer, Integer> map = null;
	public SkipIterator(int[] nums) {
		this.nums = nums;
		map = new HashMap<>();
	}

	/**
	* Returns true if the iteration has more elements.
	*/
	public boolean hasNext() {
		return index < nums.length;
	}

	/**
	* Returns the next element in the iteration.
	*/
	public Integer next() {
		Integer value = nums[index++];
		checkSkipped();
		return value;
	}
	
	private void checkSkipped() {
		while(index < nums.length && map.containsKey(nums[index])) {
			if(map.get(nums[index]) == 1) map.remove(nums[index]);
			else map.put(nums[index], map.get(nums[index])-1);
			++index;
		}
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'num' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int num) {
		map.put(num, map.getOrDefault(num, 0)+1);
		checkSkipped();
	}
}
`
