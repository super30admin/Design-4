# Time Complexity: getting newsfeed is O(1)
# Space Complexity: saving tweets: O(n) where n is number of tweets
#				    saving followers: O(n) where n is the number of followers
# 					Getting newsfeed: O(k) where k is the number of followers for the user.
# Approach: For getting latest news feed do algorithm similar to merge k sorted lists

import heapq
from collections import defaultdict
class Twitter:

    class UserNode:
        def __init__(self):
            self._followers = set()
            self._tweets = []
            
        def add_tweet(self, tweet_id, timestamp):
            self._tweets.append((tweet_id, timestamp))
        
        def fetch_tweets(self):
            return self._tweets
            
        def add_follower(self, user_id):
            self._followers.add(user_id)
        
        def remove_follower(self, user_id):
            if user_id in self._followers:
                self._followers.remove(user_id)
            
        def fetch_followers(self):
            return self._followers

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self._user_map = defaultdict(self.UserNode)
        self._timestamp = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self._user_map[userId].add_tweet(tweetId, self._timestamp)
        self._timestamp += 1
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        follower_tweets = []
        recent_tweets_ptr = []
        top_10_tweets = []
        tweet_heap = []
        count = 0

        number_of_t_lists = len(recent_tweets_ptr)
        for i in self._user_map[userId].fetch_followers().union(set([userId])):
            tweet_list = self._user_map[i].fetch_tweets()
            if len(tweet_list) != 0:
                follower_tweets.append(tweet_list)
                recent_tweets_ptr.append(len(tweet_list)-1)
                
        for idx,i in enumerate(recent_tweets_ptr):
            heapq.heappush(tweet_heap, (-follower_tweets[idx][i][1], follower_tweets[idx][i][0], idx))
            
        while len(top_10_tweets) != 10 and len(tweet_heap) != 0:
            tweet = heapq.heappop(tweet_heap)
            top_10_tweets.append(tweet[1])
            recent_tweets_ptr[tweet[2]] -= 1
            if recent_tweets_ptr[tweet[2]] != -1:
                heapq.heappush(tweet_heap, (-follower_tweets[tweet[2]][recent_tweets_ptr[tweet[2]]][1], follower_tweets[tweet[2]][recent_tweets_ptr[tweet[2]]][0], tweet[2]))

        return top_10_tweets        
        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self._user_map[followerId].add_follower(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        self._user_map[followerId].remove_follower(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)