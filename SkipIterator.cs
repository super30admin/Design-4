using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace Design
{
    //C# doesnot support inbuilt iterator
    public  class SkipIterator : Iterator<int>
    {
        private Iterator<int> it;
        Dictionary<int, int> map;
        int nextEl;

        public SkipIterator(Iterator<int> it)
        {
            this.map = new Dictionary<int, int>();
            this.it = it;
            advance();
        }
        public SkipIterator() { }

        public bool HasNext()
        {
            return nextEl != Int32.MinValue;
        }

        public int Next()
        {
            int temp = nextEl;
            advance();
            return temp;
        }

        public void advance()
        {
            nextEl = Int32.MinValue;
            while (nextEl == Int32.MinValue && it.ToString() != "") //it.HasNext())
            {
                int el = 1; //it.Next();
                if (!map.ContainsKey(el))
                {
                    nextEl = el;
                }
                else
                {
                    map.Add(el, map[el] - 1);
                    if(map[el] == 0)
                       map.Remove(el);
                }
            }
        }
        public void Skip(int val)
        {
            if (val == nextEl)
            {
                advance();
            }
            else
            {
                map.Add(val, map.GetValueOrDefault(val, 0) + 1);
            }
        }

        public static void Test()
        {
            ArrayList arlist = new ArrayList(){5, 6, 7, 7, 7, 8, 9, 10, 11};
            //Iterator<int> inp = new Iterator<int> { 5, 6, 7, 7, 7, 8, 9, 10, 11 };
            SkipIterator it = new SkipIterator();
            Console.WriteLine(it.Next());
            it.Skip(7);
            it.Skip(7);
            it.Skip(7);
            Console.WriteLine(it.Next());
            Console.WriteLine(it.HasNext());
            Console.WriteLine(it.Next());
            Console.WriteLine(it.Next());
        }
    }

    public class Iterator<T> : IEnumerable<T>
    {
        IEnumerator<T> IEnumerable<T>.GetEnumerator()
        {
            throw new NotImplementedException();
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            throw new NotImplementedException();
        }
    }
}
