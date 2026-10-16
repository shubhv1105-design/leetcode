import java.util.HashMap;
import java.util.Map;

class Solution {
    private static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        SegmentTree tree = new SegmentTree(n);
        Map<Integer, Integer> lastSeen = new HashMap<>();
        long answer = 0;

        for (int right = 0; right < n; right++) {
            int value = nums[right];
            int left = lastSeen.getOrDefault(value, -1) + 1;

            // Distinct count increases for starts in [left, right].
            tree.add(left, right, 1);
            lastSeen.put(value, right);

            // Sum of squared distinct counts for subarrays ending at right.
            answer = (answer + tree.sumSquares(0, right)) % MOD;
        }

        return (int) answer;
    }

    private static class SegmentTree {
        private final long[] sum;
        private final long[] sumSquares;
        private final long[] lazy;
        private final int n;

        SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            sumSquares = new long[4 * n];
            lazy = new long[4 * n];
        }

        void add(int ql, int qr, long delta) {
            add(1, 0, n - 1, ql, qr, delta);
        }

        private void add(int node, int left, int right,
                         int ql, int qr, long delta) {
            if (ql <= left && right <= qr) {
                apply(node, left, right, delta);
                return;
            }

            push(node, left, right);
            int mid = (left + right) / 2;

            if (ql <= mid) {
                add(node * 2, left, mid, ql, qr, delta);
            }
            if (qr > mid) {
                add(node * 2 + 1, mid + 1, right, ql, qr, delta);
            }

            pull(node);
        }

        long sumSquares(int ql, int qr) {
            return sumSquares(1, 0, n - 1, ql, qr);
        }

        private long sumSquares(int node, int left, int right,
                                int ql, int qr) {
            if (ql <= left && right <= qr) {
                return sumSquares[node];
            }

            push(node, left, right);
            int mid = (left + right) / 2;
            long result = 0;

            if (ql <= mid) {
                result += sumSquares(node * 2, left, mid, ql, qr);
            }
            if (qr > mid) {
                result += sumSquares(node * 2 + 1, mid + 1, right, ql, qr);
            }

            return result;
        }

        private void apply(int node, int left, int right, long delta) {
            long length = right - left + 1L;
            sumSquares[node] += 2 * delta * sum[node] + delta * delta * length;
            sum[node] += delta * length;
            lazy[node] += delta;
        }

        private void push(int node, int left, int right) {
            if (lazy[node] == 0 || left == right) {
                return;
            }

            int mid = (left + right) / 2;
            apply(node * 2, left, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, right, lazy[node]);
            lazy[node] = 0;
        }

        private void pull(int node) {
            sum[node] = sum[node * 2] + sum[node * 2 + 1];
            sumSquares[node] = sumSquares[node * 2] + sumSquares[node * 2 + 1];
        }
    }
}