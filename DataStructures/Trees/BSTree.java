import java.util.Random;

public class BSTree {
    private static Node root = null;

    public static class Node {
        // 父结点
        Node p;
        // 左孩子
        Node left;
        // 右孩子
        Node right;
        // 关键字
        int key;

        Node(Node p, Node left, Node right, int key) {
            this.p = p;
            this.left = left;
            this.right = right;
            this.key = key;
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        // 结点数组
        Node[] node = new Node[10];
        int i;
        System.out.println("生成二叉树结点并插入树中：");
        for (i = 0; i < node.length; i++) {
            node[i] = new Node(null, null, null, rand.nextInt(100) + 1);
            System.out.print(node[i].key + "\t");
            insert(node[i]);
        }
        // 中序遍历
        System.out.println("\n" + "中序遍历二叉搜索树：");
        inorderTreeWalk(root);
        // 查找指定结点
        Node x = iterativeSearch(root, node[5].key);
        System.out.println("\n" + "查找结果：");
        System.out.println("自身关键字：" + x.key + "\t" + "父结点的关键字：" + x.p.key);
        // 具有最小关键字的结点
        x = minimum(root);
        System.out.println("树中最小关键字：" + x.key);
        // 具有最大关键字的结点
        x = maximum(root);
        System.out.println("树中最大关键字：" + x.key);
        // x的后继
        x = predecessor(node[5]);
        System.out.println("前驱的关键字：" + x.key);
        // x的前驱
        x = successor(node[5]);
        System.out.println("后继的关键字：" + x.key);
        // 删除结点，并中序输出观看结果
        delete(node[5]);
        System.out.println("删除结点：\n");
        inorderTreeWalk(root);

    }

    private static void inorderTreeWalk(Node x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.print(x.key + "\t");
            inorderTreeWalk(x.right);
        }
    }

    private static Node search(Node x, int k) {
        if (x == null || k == x.key) {
            return x;
        }
        if (k < x.key) {
            return search(x.left, k);
        } else return search(x.right, k);
    }

    private static Node iterativeSearch(Node x, int k) {
        while (x != null && k != x.key) {
            if (k < x.key) {
                x = x.left;
            } else x = x.right;
        }
        return x;
    }

    private static Node minimum(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    private static Node maximum(Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    private static Node successor(Node x) {
        if (x.right != null) return minimum(x.right);
        else {
            Node y = x.p;
            while (y != null && x == y.right) {
                x = y;
                y = y.p;
            }
            return y;
        }
    }

    private static Node predecessor(Node x) {
        if (x.left != null) return maximum(x.left);
        else {
            Node y = x.p;
            while (y != null && x == y.left) {
                x = y;
                y = y.p;
            }
            return y;
        }
    }

    private static void insert(Node z) {
        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else x = x.right;
        }
        z.p = y;
        if (y == null) {
            root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else y.right = z;
    }

    private static void transplant(Node root, Node u, Node v) {
        if (u.p == null) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else u.p.right = v;
        if (v != null) {
            v.p = u.p;
        }
    }

    private static void delete(Node z) {
        if (z.left == null) {
            transplant(root, z, z.right);
        } else if (z.right == null) {
            transplant(root, z, z.left);
        } else {
            Node y = minimum(z.right);
            if (y.p != z) {
                transplant(root, y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(root, z, y);
            y.left = z.left;
            y.left.p = y;
        }

    }
}
