//postTweet : we append the new tweet to the end of list of tweets for the user and increment time. Also call follow to make sure user follows themselves. Time Complexity : O(1) Space Complexity : O(1)
//getNewsFeed : we are using a min heap to keep track of the 10 most recent tweets. We iterate through the followees of the user and add their tweets to the min heap. If the size of the min heap exceeds 10, we remove
// the least recent tweet. Finally, we extract the tweet ids from the min heap and reverse the list to get the most recent tweets first. Time Complexity : O(N log 10) Space Complexity : O(10)
// follow : we add the followee to the set of followees for the follower. Time Complexity : O(1) Space Complexity : O(1)
// unfollow : we remove the followee from the set of followees for the follower. Time Complexity : O(1) Space Complexity : O(1)


class Twitter() {
    private val followeesMap : MutableMap<Int, MutableSet<Int>> = mutableMapOf()
    private val tweetsMap : MutableMap<Int, MutableList<Tweet>> = mutableMapOf()

    private var time = 0

    private data class Tweet(val tweetId : Int, val timeStamp: Int)

    fun postTweet(userId: Int, tweetId: Int) {
        val list = tweetsMap.getOrPut(userId) {mutableListOf()}
        list.add(Tweet(tweetId, time))
        time++

        follow(userId, userId)
    }

    fun getNewsFeed(userId: Int): List<Int> {
        val pq = PriorityQueue<Tweet>(compareBy {it.timeStamp})

        val followees = followeesMap[userId]

        if(followees != null) {
            for(followee in followees) {
                val tweets = tweetsMap[followee] ?: continue

                val start = tweets.size - 1
                val end = maxOf(0, tweets.size - 10)

                for(i in start downTo end) {
                    pq.add(tweets[i])
                    if(pq.size > 10) {
                        pq.poll()
                    }
                }
            }
        }

        var result = mutableListOf<Int>()
        while(pq.isNotEmpty()) {
            result.add(pq.poll().tweetId)
        }
        result.reverse()
        return result   
    }

    fun follow(followerId: Int, followeeId: Int) {
        followeesMap.getOrPut(followerId) {mutableSetOf()}.add(followeeId)
    }

    fun unfollow(followerId: Int, followeeId: Int) {
        followeesMap[followerId]?.remove(followeeId)
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * var obj = Twitter()
 * obj.postTweet(userId,tweetId)
 * var param_2 = obj.getNewsFeed(userId)
 * obj.follow(followerId,followeeId)
 * obj.unfollow(followerId,followeeId)
 */