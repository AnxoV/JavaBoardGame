public class BFSMain {
    public static void main(String[] args) throws Exception {
        char[][] board = new char[][]{
            {' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' '},
            {' ', ' ', 'X', ' ', ' '},
            {' ', ' ', ' ', ' ', ' '},
            {' ', '0', ' ', ' ', ' '}
        };

        BFS bfs = new BFS(board);
        bfs.path();
    }
}
