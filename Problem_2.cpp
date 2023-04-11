Google | Skip Iterator

Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.


template<class T>
class SkipIterator {
public:
  SkipIterator(Iterator<T> it) : it(it) {}
  
  bool hasNext() {
    seekNext();
    return buffer.has_value();
  }
  
  T next() {
    seekNext();
    T result = *buffer;
    buffer.reset();
    return result;
  }
  
  void skip(T val) {
    ++to_skip[val];
  }
  
private:
  Iterator<T>           it;
  unordered_map<T, int> to_skip;
  optional<T>           buffer;
  
  void seekNext() {
    while (loadBuffer()) {
      auto skip_it = to_skip.find(*buffer);
      if (skip_it == to_skip.end())
        return;
      buffer.reset();
      if (--skip_it->second == 0)
        to_skip.erase(skip_it);
    }
  }
  
  bool loadBuffer() {
    if (!buffer.has_value() && it.hasNext())
      buffer = it.next();
    return buffer.has_value();
  }
};
