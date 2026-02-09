//
//  DesignTwitter.swift
//  DSA-Practice
//
//  Created by Paridhi Malviya on 2/7/26.
//

/*
 To take the latest 10 news feed.
 bruteforce - combine all the tweets of my followees. sort it and take the top 10 latest. O(N log N) - N - no of all elements combined
 approach 2 - combine all tweets from all followees (whom I am following). and put it in min heap according to timestamps. Bceuase we want to see the latest k news feeds from my followees. O(N log k). N = no of followees * no of tweets by them
 k is very small. So, O(n) linear. Many design problems end up being solved by heap. Earlier all followees * no of tweets by each and everybody
 
 approach 3 - Take k elements from the end for all followees (latest 10 tweets) and put it in heap. O(n) - no of people that I am following.
 If followees - 500. 500 is tkind of constant then going towards cnostant time complexity. -> O(1)
 
 post twit - O(1)
 follow - O(1)
 unfollow - O(1)
 */

private class PQ<T> {
    var elements: [T] = []
    
    var sort: (T, T) -> Bool
    init(sort: @escaping (T, T) -> Bool) {
        self.sort = sort
    }
    
    var isEmpty: Bool {
        return elements.count == 0
    }
    
    var count: Int {
        return elements.count
    }
    
    var peek: T? {
        return elements.first
    }
    
    func enqueue(element: T) {
        
        elements.append(element)
        heapifyUp(from: elements.count - 1)
    }
    
    func heapifyUp(from index: Int) {
        
        var childIndex = index
        var parentIndex = (childIndex - 1) / 2
        
        while(childIndex > 0 && sort(elements[childIndex], elements[parentIndex])) {
            elements.swapAt(childIndex, parentIndex)
            childIndex = parentIndex
            parentIndex = (childIndex - 1) / 2
        }
    }
    
    func dequeue() -> T? {
        if (elements.count == 0) {
            return nil
        }
        elements.swapAt(0, elements.count - 1)
        let removedOne = elements.removeLast()
        heapifyDown(from: 0)
        
        return removedOne
    }
    
    func heapifyDown(from index: Int) {
        var parentIndex = index
        while true {
            var childIndex = 2 * parentIndex + 1
            if (childIndex >= count) {
                break
            }
            
            if (childIndex + 1 < count && sort(elements[childIndex + 1], elements[childIndex])) {
                childIndex += childIndex
            }
            
            if (sort(elements[parentIndex], elements[childIndex])) {
               break
            }
            elements.swapAt(childIndex, parentIndex)
            parentIndex = childIndex
        }
    }
}
class Twitter {
    
    class Tweet {
        var tweetId: Int
        var createdAt: Int
        
        init(tweetId: Int, createdAt: Int) {
            self.tweetId = tweetId
            self.createdAt = createdAt
        }
    }
    
    var followedsMap = [Int: Set<Int>]() //userdId mapped to a set of userIds whom all userId is following
    var tweetsMap = [Int: Array<Tweet>]() //user Id mapped to array of tweets
    var time = 0
    
    func follow(followerId: Int, followeeId: Int) {
        if (followedsMap[followerId] == nil) {
            followedsMap[followerId] = Set<Int>() //If the
        }
        followedsMap[followerId]?.insert(followeeId)
    }
    
    func unfollow(followerId: Int, followeeId: Int) {
        //don't want to unfollow myself
        if (followedsMap[followerId] != nil && followerId != followeeId) {
            followedsMap[followerId]?.remove(followeeId)
        }
    }
    
    func postTweet(userId: Int, tweetId: Int) {
        //check if the userId entry is present in tweets map. If no entry, then put an entry
        if (tweetsMap[userId] == nil) {
            tweetsMap[userId] = []
        }
        follow(followerId: userId, followeeId: userId)
        let tweetObj = Tweet(tweetId: tweetId, createdAt: time)
        time += 1
        tweetsMap[userId]?.append(tweetObj)
    }
    
    func getNewsFeed(userId: Int) -> [Int] {
        //have priority queue
        let followeesList = followedsMap[userId]
        guard let followeesList = followeesList else {
            return []
        }
        //use min heap to expel the low priority elements. ie.e. with lower timestamps.
        let pq = PQ<Tweet>(sort: { $0.createdAt < $1.createdAt})

        for uid in followeesList {
            let uidTweets = tweetsMap[uid] ?? []
            for tweet in uidTweets {
                pq.enqueue(element: tweet)
                if (pq.count > 10) {
                    let r = pq.dequeue()
                }
            }
        }
        
        var result = [Int]()
        while (!pq.isEmpty) {
            let r = pq.dequeue()?.tweetId
            if let r = r {
                result.append(r)
            }
        }
        return result
    }
}
