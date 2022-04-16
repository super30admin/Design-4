type tweet struct {
    t int // time tweeted at
    id int
}

type Twitter struct {
    followMap map[int]*set
    tweetMap map[int][]*tweet
    globalTime int
}


func Constructor() Twitter {
    return Twitter{
        followMap: map[int]*set{},
        tweetMap: map[int][]*tweet{},
    }
}

// time: o(1)
// space: o(1)
func (this *Twitter) PostTweet(userId int, tweetId int)  {
    // when this user posts a tweet, 
    // we will make this user follow itself so we can 
    // get this users tweet when this user calls getNewsFeed()
    if this.followMap[userId] == nil {
        this.followMap[userId] = newSet()
    }
    this.followMap[userId].add(userId)
    this.tweetMap[userId] = append(this.tweetMap[userId], &tweet{t: this.globalTime, id: tweetId})
    this.globalTime++
}

// time: o(n)
// space: o(1)
func (this *Twitter) GetNewsFeed(userId int) []int {
    // get users this userId is following
    userSet := this.followMap[userId]
    if userSet == nil {return []int{}}
    
    // this user may be following n users
    mh := &minheap{items: []*tweet{}}
    for userKey, _ := range userSet.items {
        // get ONLY the latest 10 tweets from this nth user and push it to heap
        tweetsFromThisUser := this.tweetMap[userKey]
        for i := len(tweetsFromThisUser)-1; i >= 0 && i >= len(tweetsFromThisUser)-11; i-- {
            // o(klogk) - here k is constant and it is 10
            heap.Push(mh, tweetsFromThisUser[i])
            if len(mh.items) > 10 {
                // o(klogk) - here k is constant and it is 10
                heap.Pop(mh)
            }
        }
    }
    
    // at worse this loop runs 10 times which is constant
    out := make([]int, len(mh.items))
    for i := len(mh.items)-1; i>=0; i-- {
        out[i] = heap.Pop(mh).(*tweet).id
    }
    return out
}

// time: o(1)
// space: o(1)
func (this *Twitter) Follow(followerId int, followeeId int)  {   
    if this.followMap[followerId] == nil {
        this.followMap[followerId] = newSet()
    }
    this.followMap[followerId].add(followeeId)
}

// time: o(1)
// space: o(1)
func (this *Twitter) Unfollow(followerId int, followeeId int)  {
    if this.followMap[followerId] == nil {
        return
    }
    this.followMap[followerId].remove(followeeId)
}


/**
 * Your Twitter object will be instantiated and called as such:
 * obj := Constructor();
 * obj.PostTweet(userId,tweetId);
 * param_2 := obj.GetNewsFeed(userId);
 * obj.Follow(followerId,followeeId);
 * obj.Unfollow(followerId,followeeId);
 */


// heap implementation ( sort by tweet.time )
// we will use min heap
type minheap struct {
    items []*tweet
}
func (m *minheap) Len() int {return len(m.items)}
func (m *minheap) Less(i, j int) bool {return m.items[i].t < m.items[j].t}
func (m *minheap) Swap(i, j int) { m.items[i],m.items[j] = m.items[j], m.items[i]}
func (m *minheap) Push(x interface{}) {m.items = append(m.items, x.(*tweet))}
func (m *minheap) Pop() interface{} {
	i := m.items[len(m.items)-1]
	m.items = m.items[:len(m.items)-1]
	return i
}

// set implementation ( since there is no native set )
type set struct {
    items map[int]struct{}
}
func newSet() *set {
    return &set{items:map[int]struct{}{}}
}
func (s *set) add(item int) {
    s.items[item] = struct{}{}
}
func (s *set) remove(item int){
    delete(s.items, item)
}
