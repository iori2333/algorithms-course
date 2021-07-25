public class PathCompressionUF implements UnionFind {
    private final int[] id;

    public PathCompressionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    @Override
    public void union(int p, int q) {
        int pid = root(p);
        int qid = root(q);
        if (pid != qid) {
            id[pid] = qid;
        }
    }
}
