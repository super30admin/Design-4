import java.util.*;

class Twitter {
    // --- Variables ---
    private static int timestamp = 0;
    private Map<Integer, User> userMap;

    // --- Inner Class for a Tweet ---
    private class Tweet {
        public int id;
        public int time;

        public Tweet(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    // --- Inner Class for a User ---
    private class User {
        public int id;
        public Set<Integer> followed;
        public LinkedList<Tweet> tweets;

        public User(int id) {
            this.id = id;
            this.followed = new HashSet<>();
            this.followed.add(id); // A user always follows themselves
            this.tweets = new LinkedList<>();
        }

        public void follow(int userId) {
            followed.add(userId);
        }

        public void unfollow(int userId) {
            followed.remove(userId);
        }

        public void post(int tweetId) {
            tweets.addFirst(new Tweet(tweetId, timestamp++));
            if (tweets.size() > 10) {
                tweets.removeLast();
            }
        }
    }

    // --- Main Twitter Class Methods ---

    public Twitter() {
        userMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        // Find the user.
        User user = userMap.get(userId);
        // If the user doesn't exist, create one and put them in the map.
        if (user == null) {
            user = new User(userId);
            userMap.put(userId, user);
        }
        // Now that we're sure the user exists, post the tweet.
        user.post(tweetId);
    }

    public List<Integer> getNewsFeed(int userId) {
        if (!userMap.containsKey(userId)) {
            return new ArrayList<>();
        }

        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>((a, b) -> b.time - a.time);
        Set<Integer> followedUsers = userMap.get(userId).followed;

        for (int followedId : followedUsers) {
            User followedUser = userMap.get(followedId);
            if (followedUser != null) {
                for (Tweet tweet : followedUser.tweets) {
                    maxHeap.add(tweet);
                }
            }
        }

        List<Integer> newsFeed = new ArrayList<>();
        int count = 0;
        while (!maxHeap.isEmpty() && count < 10) {
            newsFeed.add(maxHeap.poll().id);
            count++;
        }
        return newsFeed;
    }

    public void follow(int followerId, int followeeId) {
        // Get or create the follower
        User follower = userMap.get(followerId);
        if (follower == null) {
            follower = new User(followerId);
            userMap.put(followerId, follower);
        }

        // Get or create the person being followed (followee)
        User followee = userMap.get(followeeId);
        if (followee == null) {
            followee = new User(followeeId);
            userMap.put(followeeId, followee);
        }

        // Add the followee to the follower's "followed" set.
        follower.follow(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (userMap.containsKey(followerId) && userMap.containsKey(followeeId)) {
            userMap.get(followerId).unfollow(followeeId);
        }
    }
}
//

class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> iterator;
    private Integer nextElement;
    private Map<Integer, Integer> skipCount;

    public SkipIterator(Iterator<Integer> it) {
        this.iterator = it;
        this.skipCount = new HashMap<>();
        advanceToNext();
    }

    public boolean hasNext() {
        return nextElement != null;
    }

    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Integer result = nextElement;
        advanceToNext();
        return result;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int val) {
        skipCount.put(val, skipCount.getOrDefault(val, 0) + 1);

        // If the current nextElement is the value we want to skip, advance to the next
        // element
        if (nextElement != null && nextElement == val) {
            advanceToNext();
        }
    }

    private void advanceToNext() {
        nextElement = null;

        while (iterator.hasNext()) {
            Integer current = iterator.next();

            // Check if we need to skip this value
            if (skipCount.containsKey(current) && skipCount.get(current) > 0) {
                skipCount.put(current, skipCount.get(current) - 1);
                continue; // Skip this element
            }

            nextElement = current;
            break;
        }
    }
}

// Example usage and test
public class Main {
    public static void main(String[] args) {
        // Create a list for testing
        List<Integer> list = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
        SkipIterator itr = new SkipIterator(list.iterator());

        System.out.println("Initial state:");
        System.out.println("itr.hasNext(): " + itr.hasNext()); // true
        System.out.println("itr.next(): " + itr.next()); // returns 2

        System.out.println("\nAfter skip(5):");
        itr.skip(5);
        System.out.println("itr.next(): " + itr.next()); // returns 3
        System.out.println("itr.next(): " + itr.next()); // returns 6 because 5 should be skipped
        System.out.println("itr.next(): " + itr.next()); // returns 5

        System.out.println("\nAfter skip(5) twice:");
        itr.skip(5);
        itr.skip(5);
        System.out.println("itr.next(): " + itr.next()); // returns 7
        System.out.println("itr.next(): " + itr.next()); // returns -1
        System.out.println("itr.next(): " + itr.next()); // returns 10

        System.out.println("\nFinal state:");
        System.out.println("itr.hasNext(): " + itr.hasNext()); // false

        try {
            itr.next(); // This should throw an error
        } catch (NoSuchElementException e) {
            System.out.println("Error caught: " + e.getMessage());
        }
    }
}