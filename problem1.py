#time O(N)
#space O(N)

class Twitter:

    def __init__(self):
        self.user_to_tweets_map = collections.defaultdict(list) # userId -> list of [tweetIds]
        self.follower_to_followee_map = collections.defaultdict(set)  # userId -> set of followeeId
        self.timestamp = 0
        
    def postTweet(self, userId: int, tweetId: int) -> None:
        self.user_to_tweets_map[userId].append((self.timestamp, tweetId))
        self.timestamp += 1

    def getNewsFeed(self, user_id: int) -> List[int]:
        tweet_candidates = self.user_to_tweets_map[user_id][::]
        for followee_id in self.follower_to_followee_map[user_id]:
            tweet_candidates.extend(self.user_to_tweets_map[followee_id]) 
        
        tweet_candidates.sort(reverse=True) # most recent to least recent
        return [tweetId for timestamp, tweetId in tweet_candidates[:10]]
        
    def follow(self, follower_id: int, followee_id: int) -> None:
        self.follower_to_followee_map[follower_id].add(followee_id)
        
    def unfollow(self, follower_id: int, followee_id: int) -> None:
        self.follower_to_followee_map[follower_id].discard(followee_id)