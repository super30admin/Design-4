# Design-4

## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)
class User:
    def __init__(self, userId):
        self.userId = userId
        self.tweets = {}
        self.followers = set()
        self.follows = set()


class Twitter(object):

    def __init__(self):
        self.users = {}
        self.time = 0

    def create(self, userid):
        
        if userid not in self.users:
            self.users[userid] = User(userid)

    def postTweet(self, userId, tweetId):
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        self.create(userId)
        self.users[userId].tweets[tweetId] = self.time
        self.time += 1

    def getNewsFeed(self, userId):
        """
        :type userId: int
        :rtype: List[int]
        """
        feed = []
        temp = []
        self.create(userId)
        for i in self.users[userId].tweets:
            temp.append([i, self.users[userId].tweets[i]])
        for i in self.users[userId].follows:
            print(i.tweets)
            for j in i.tweets:
                temp.append([j, i.tweets[j]])
        temp.sort(key=lambda x:x[1], reverse=True)

        for i in temp:
            feed.append(i[0])
        return feed[:10]

    def follow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        self.create(followerId)
        self.create(followeeId)
        self.users[followerId].follows.add(self.users[followeeId])
        self.users[followeeId].followers.add(self.users[followerId])

    def unfollow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        self.create(followerId)
        self.create(followeeId)
        if self.users[followerId] in self.users[followeeId].followers:
            self.users[followeeId].followers.remove(self.users[followerId])
            self.users[followerId].follows.remove(self.users[followeeId])




## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.

class SkipIterator implements Iterator<Integer> {

	public SkipIterator(Iterator<Integer> it) {
	}

	public boolean hasNext() {
	}

	public Integer next() {
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
	}
}
Example:

SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
itr.hasNext(); // true
itr.next(); // returns 2
itr.skip(5);
itr.next(); // returns 3
itr.next(); // returns 6 because 5 should be skipped
itr.next(); // returns 5
itr.skip(5);
itr.skip(5);
itr.next(); // returns 7
itr.next(); // returns -1
itr.next(); // returns 10
itr.hasNext(); // false
itr.next(); // error

