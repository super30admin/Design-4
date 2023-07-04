# // Time Complexity :O(n)
# // Space Complexity :O(n)
# // Did this code successfully run on Leetcode :Yes
# // Any problem you faced while coding this :no
class Twitter:

    def __init__(self):
        self.users=collections.defaultdict(list)
        self.time=1
        self.followers=collections.defaultdict(set)
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.users[userId].append((self.time,tweetId))
        self.time += 1   

    def getNewsFeed(self, userId: int) -> List[int]:
        news=list(self.users[userId])
        for user in self.followers[userId]:
            news.extend(self.users[user])
        news.sort(reverse=True,key=lambda x:x[0])
        res=[]
        for i in range(len(news)):
            if i == 10: break
            res.append(news[i][1])
        return res

    def follow(self, followerId: int, followeeId: int) -> None:
         if followerId != followeeId:
            self.followers[followerId].add(followeeId)


    def unfollow(self, followerId: int, followeeId: int) -> None:
         if followeeId in self.followers[followerId]:
            self.followers[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)