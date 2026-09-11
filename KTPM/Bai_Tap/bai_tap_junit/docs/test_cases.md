# Test Cases Documentation

## Function 1: Linear Search — `search(a[], x, n)`

Searches for value `x` in array `a[]` of size `n`. Returns the index if found, `-1` if not found.

| No. | ID   | Path | Input                          | Expected Output | Note              |
|-----|------|------|--------------------------------|-----------------|-------------------|
| 1   | TC01 | ①   | a=[2,1,3], x=2, n=3           | 0               | 1 iteration       |
| 2   | TC02 | ②   | a=[2,1,3], x=1, n=3           | 1               | 2 iterations      |
| 3   | TC03 | ③   | a=[], x=2, n=0                 | -1              | 0 iterations      |
| 4   | TC04 | —   | a=[2,1,5,3,6,8,9], x=3, n=7   | 3               | 4 iterations      |
| 5   | TC05 | —   | a=[2,1,5,3,6,8,9], x=8, n=7   | 5               | 6 iterations      |
| 6   | TC06 | —   | a=[2,1,5,3,6,8,9], x=9, n=7   | 6               | n-1 iterations    |

### Test Case Details

**TC01** — Element found at first position
- Input: `a = [2, 1, 3]`, `x = 2`, `n = 3`
- Expected Output: `0`
- Loop executes **1 time** (element found immediately at index 0)

**TC02** — Element found at second position
- Input: `a = [2, 1, 3]`, `x = 1`, `n = 3`
- Expected Output: `1`
- Loop executes **2 times**

**TC03** — Empty array
- Input: `a = []`, `x = 2`, `n = 0`
- Expected Output: `-1` (not found)
- Loop executes **0 times** (boundary case)

**TC04** — Element found in the middle
- Input: `a = [2, 1, 5, 3, 6, 8, 9]`, `x = 3`, `n = 7`
- Expected Output: `3`
- Loop executes **4 times**

**TC05** — Element found near the end
- Input: `a = [2, 1, 5, 3, 6, 8, 9]`, `x = 8`, `n = 7`
- Expected Output: `5`
- Loop executes **6 times**

**TC06** — Element found at last position
- Input: `a = [2, 1, 5, 3, 6, 8, 9]`, `x = 9`, `n = 7`
- Expected Output: `6`
- Loop executes **n-1 times** (worst case)

---

## Function 2: Merge Sorted Arrays — `cal(a[], n, b[], m, c[])`

Merges two sorted arrays `a[]` (size `n`) and `b[]` (size `m`) into output array `c[]`.

| No. | ID   | Input                                          | Expected Output       | Note                                    |
|-----|------|------------------------------------------------|-----------------------|-----------------------------------------|
| 7   | TC07 | a=[3], n=1, b=[1], m=1                         | c=[1,3]               | 1 iter L1, 0 iter L3                    |
| 8   | TC08 | a=[2,3], n=2, b=[0,1], m=2                     | c=[0,1,2,3]           | 2 iter L1, 0 iter L3                    |
| 9   | TC09 | a=[0,1,2,3], n=4, b=[5,6,7,9], m=4            | c=[0,1,2,3,5,6,7,9]   | 4 iter L1, 0 iter L2                    |
| 10  | TC10 | a=[0,1,2], n=3, b=[3,4], m=3                   | c=[0,1,2,3,4]         | 3 iter L1, 0 iter L2, 2 iter L3        |

> **L1** = Loop 1 (while i < n && j < m), **L2** = Loop 2 (while i < n), **L3** = Loop 3 (while j < m)

### Test Case Details

**TC07** — Single-element arrays, b element smaller
- Input: `a = [3]`, `n = 1`, `b = [1]`, `m = 1`
- Expected Output: `c = [1, 3]`
- Loop 1 runs **1 time**, Loop 2 runs **0 times**, Loop 3 runs **0 times**

**TC08** — All elements of b are smaller than all elements of a
- Input: `a = [2, 3]`, `n = 2`, `b = [0, 1]`, `m = 2`
- Expected Output: `c = [0, 1, 2, 3]`
- Loop 1 runs **2 times**, Loop 2 runs **0 times**, Loop 3 runs **0 times**

**TC09** — All elements of a are smaller than all elements of b
- Input: `a = [0, 1, 2, 3]`, `n = 4`, `b = [5, 6, 7, 9]`, `m = 4`
- Expected Output: `c = [0, 1, 2, 3, 5, 6, 7, 9]`
- Loop 1 runs **4 times**, Loop 2 runs **0 times**, Loop 3 runs **0 times**

**TC10** — a exhausts first, remaining elements in b are appended
- Input: `a = [0, 1, 2]`, `n = 3`, `b = [3, 4]`, `m = 3`
- Expected Output: `c = [0, 1, 2, 3, 4]`
- Loop 1 runs **3 times**, Loop 2 runs **0 times**, Loop 3 runs **2 times**
