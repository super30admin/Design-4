/*

  Time complexity: Skip:O(1); HasNext:O(1); Next:O(S) where S is the maximum number of skips
  Space complexity: O(k) where k is the distinct elements that need to be skipped

*/

public class SkipIterator
{
    private readonly IEnumerator<int> _iterator;
    private readonly Dictionary<int, int> _skipMap;
    private int? _nextEl;

    public SkipIterator(IEnumerable<int> nums)
    {
        _iterator = nums.GetEnumerator();
        _skipMap = new();
        Advance();
    }

    public bool HasNext()
    {
        return _nextEl.HasValue;
    }

    public int Next()
    {
        if (!HasNext())
            throw new InvalidOperationException("No more elements.");

        int result = _nextEl.Value;
        Advance();
        return result;
    }

    public void Skip(int val)
    {
        if (_nextEl.HasValue && _nextEl.Value == val)
        {
            Advance();
        }
        else
        {
            if (!_skipMap.ContainsKey(val))
                _skipMap[val] = 0;

            _skipMap[val]++;
        }
    }

    private void Advance()
    {
        _nextEl = null;

        while (_iterator.MoveNext())
        {
            int el = _iterator.Current;
            if (_skipMap.ContainsKey(el))
            {
                _skipMap[el]--;
                if (_skipMap[el] == 0)
                    _skipMap.Remove(el);
                continue;
            }

            _nextEl = el;
            break;
        }
    }
}
