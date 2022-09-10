#  355. Design Twitter
'''
Leetcode all test cases passed: Yes
Twitter:
    def postTweet(self,matrix,i,j):
        Space Complexity: O(1)
        Time Complexity: O(1)
    def getNewsFeed(self, userId: int) -> List[int]: 
        n is average number of followers
        k is no of newsfeed
        Space Complexity: O(1)
        Time Complexity: O(n log k)
    def follow(self, followerId: int, followeeId: int) -> None:
        Space Complexity: O(1)
        Time Complexity: O(1)
    def unfollow(self, followerId: int, followeeId: int) -> None:
        Space Complexity: O(1)
        Time Complexity: O(1)
'''
from collections import defaultdict
from collections import deque
from heapq import heapify,heappush as hpush,heappop as hpop

class reverse_Iter():
    def __init__(self,l):
        self.next = None
        self.nextPointer = len(l)  
        self.input = l
        self.next_ele()
    
    def next_ele(self):
        val = self.next
        temp = self.nextPointer - 1
        if temp >= 0:
            self.next = self.input[temp]
        self.nextPointer = temp
        return val
        
    def has_next(self):
        if self.nextPointer >= 0:
            return True
        return False

class Node(object):
    def __init__(self, val):
        self.val =val
    def __lt__(self,other):
        return self.val[0][0] > other.val[0][0]

class Twitter:

    def __init__(self):
        self.follow_graph = defaultdict(set)
        self.tweet_graph = defaultdict(list)
        self.count = 0
    def postTweet(self, userId: int, tweetId: int) -> None: #O(1)
        self.tweet_graph[userId].append([self.count,tweetId])
        self.count += 1
    def getNewsFeed(self, userId: int) -> List[int]: #O(averagenumber of followers log no of newsfeed)
        self.follow_graph[userId].add(userId)
        heap = []
        for follower in self.follow_graph[userId]:
            ri = reverse_Iter(self.tweet_graph[follower])
            if ri.has_next():
                hpush(heap,Node([ri.next_ele(),ri]))
        result = deque()
        while heap and len(result) < 10:
            curr = hpop(heap)
            value = curr.val[0][1]
            iterator = curr.val[1]
            if value != None:
                result.append(value)
            if iterator.has_next():
                hpush(heap,Node([iterator.next_ele(),iterator]))
        
        return result
            
            
            
    def follow(self, followerId: int, followeeId: int) -> None: #O(1)
         
        self.follow_graph[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None: #O(1)
        if followeeId in self.follow_graph[followerId]:
            self.follow_graph[followerId].remove(followeeId)
