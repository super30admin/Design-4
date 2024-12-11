class TwitterHeap {
  items: Tweet[];
  comparator: (a: Tweet, b: Tweet) => number;

  constructor(comparator: (a: Tweet, b: Tweet) => number) {
    this.items = [];
    this.comparator = comparator;
  }

  size(): number {
    return this.items.length;
  }

  insert(item: Tweet) {
    this.items.push(item);
    let index = this.items.length - 1;
    while (index > 0) {
      let parentIdx = this.parentIndexOf(index);
      let comparision = this.comparator(
        this.items[parentIdx],
        this.items[index]
      );
      if (comparision > 0) {
        this.swap(index, parentIdx);
        index = parentIdx;
      } else {
        break;
      }
    }
  }

  poll(): Tweet | null {
    let result = this.items[0];
    this.swap(0, this.items.length - 1);
    this.items.pop();
    let index = 0;
    while (index < this.items.length) {
      let leftIdx = this.leftIndexOf(index);
      let rightIdx = this.rightIndexOf(index);
      let smallestValueAtIndex = leftIdx;
      if (
        rightIdx < this.items.length &&
        this.comparator(this.items[leftIdx], this.items[rightIdx]) > 0
      ) {
        smallestValueAtIndex = rightIdx;
      }
      if (
        smallestValueAtIndex < this.items.length &&
        this.comparator(this.items[index], this.items[smallestValueAtIndex]) > 0
      ) {
        this.swap(smallestValueAtIndex, index);
        index = smallestValueAtIndex;
      } else {
        break;
      }
    }
    return result;
  }

  private parentIndexOf(index: number): number {
    return Math.floor((index - 1) / 2);
  }
  private leftIndexOf(index: number) {
    return index * 2 + 1;
  }
  private rightIndexOf(index: number) {
    return index * 2 + 2;
  }
  private swap(first: number, second: number) {
    let temp = this.items[first];
    this.items[first] = this.items[second];
    this.items[second] = temp;
  }
}

class Tweet {
  tweetId: number;
  createdAt: number;
  constructor(tweetId: number, createdAt: number) {
    this.tweetId = tweetId;
    this.createdAt = createdAt;
  }
}

class Twitter {
  followeeMap: Map<number, Set<number>>;
  tweetsMap: Map<number, Tweet[]>;
  timestamp: number;
  constructor() {
    this.followeeMap = new Map();
    this.tweetsMap = new Map();
    this.timestamp = 1;
  }

  postTweet(userId: number, tweetId: number): void {
    if (!this.followeeMap.has(userId)) {
      this.followeeMap.set(userId, new Set());
    }
    this.followeeMap.get(userId)?.add(userId);

    // Tweets
    if (!this.tweetsMap.has(userId)) {
      this.tweetsMap.set(userId, []);
    }
    this.tweetsMap.get(userId)?.push(new Tweet(tweetId, this.timestamp));
    this.timestamp++;
  }

  getNewsFeed(userId: number): number[] {
    let heap = new TwitterHeap((a, b) => a.createdAt - b.createdAt);
    let result: number[] = [];

    if (this.followeeMap.has(userId)) {
      let followee = Array.from(this.followeeMap.get(userId)!);
      for (let index = 0; index < followee.length; index++) {
        let user = followee[index];
        if (this.tweetsMap.has(user)) {
          let tweets = this.tweetsMap.get(user)!;
          for (let index = 0; index < tweets.length; index++) {
            heap.insert(tweets[index]);
            if (heap.size() > 10) {
              heap.poll();
            }
          }
        }
      }
      while (heap.size() > 0) {
        result.unshift(heap.poll()!.tweetId)
      }
    }

    return result;
  }

  follow(followerId: number, followeeId: number): void {
    if (!this.followeeMap.has(followerId)) {
      this.followeeMap.set(followerId, new Set());
    }
    this.followeeMap.get(followerId)?.add(followeeId);
  }

  unfollow(followerId: number, followeeId: number): void {
    if (!this.followeeMap.has(followerId)) {
      return;
    }
    this.followeeMap.get(followerId)?.delete(followeeId);
  }
}

describe("Design Twitter", () => {
  it("Tweeter Demo", () => {
    let tweeter = new Twitter();
    tweeter.postTweet(1, 1);
    tweeter.postTweet(1, 2);
    tweeter.postTweet(1, 3);
    tweeter.postTweet(2, 4);
    tweeter.postTweet(2, 5);
    tweeter.postTweet(2, 6);
    tweeter.follow(1, 2);
    expect(tweeter.getNewsFeed(1)).toStrictEqual([6, 5, 4, 3, 2, 1]);
  });

  it("Tweeter Demo Small Test", () => {
    let tweeter = new Twitter();
    tweeter.postTweet(1, 5);
    tweeter.postTweet(1, 3);
    expect(tweeter.getNewsFeed(1)).toStrictEqual([3, 5]);
  });

  it("Tweeter Demo Big Test", () => {
    let tweeter = new Twitter();
    tweeter.postTweet(1, 5);
    tweeter.postTweet(1, 3);
    tweeter.postTweet(1, 101);
    tweeter.postTweet(1, 13);
    tweeter.postTweet(1, 10);
    tweeter.postTweet(1, 2);
    tweeter.postTweet(1, 94);
    tweeter.postTweet(1, 505);
    tweeter.postTweet(1, 333);
    tweeter.postTweet(1, 22);
    tweeter.postTweet(1, 11);
    expect(tweeter.getNewsFeed(1)).toStrictEqual([
      11, 22, 333, 505, 94, 2, 10, 13, 101, 3,
    ]);
  });
});
