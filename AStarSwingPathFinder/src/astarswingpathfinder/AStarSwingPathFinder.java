import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class AStarSwingPathFinder extends JFrame {
    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 30;
    private boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
    private Point start = null, end = null;
    private ArrayList<Point> path = new ArrayList<>();
    private JPanel gridPanel;
    private int currentPathIndex = 0;
    private Timer animationTimer;
    private JLabel stepLabel; // Added for step count display

    public AStarSwingPathFinder() {
        setTitle("A* Shortest Path Finder (Swing)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Paths
                g2d.setColor(new Color(50, 50, 50));
                for (int i = 0; i < GRID_SIZE; i++) {
                    for (int j = 0; j < GRID_SIZE; j++) {
                        if (grid[i][j]) {
                            g2d.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                            g2d.setColor(new Color(70, 70, 70));
                            for (int x = 0; x < CELL_SIZE; x += 5) {
                                for (int y = 0; y < CELL_SIZE; y += 5) {
                                    if (Math.random() > 0.7)
                                        g2d.fillRect(j * CELL_SIZE + x, i * CELL_SIZE + y, 2, 2);
                                }
                            }
                        }
                    }
                }

                // Buildings
                for (int i = 0; i < GRID_SIZE; i++) {
                    for (int j = 0; j < GRID_SIZE; j++) {
                        if (!grid[i][j]) {
                            int height = 10;
                            GradientPaint gp = new GradientPaint(j * CELL_SIZE, i * CELL_SIZE, Color.GRAY,
                                    j * CELL_SIZE, i * CELL_SIZE + height * 2, Color.DARK_GRAY);
                            g2d.setPaint(gp);
                            g2d.fillRect(j * CELL_SIZE, i * CELL_SIZE - height, CELL_SIZE, height * 2);
                            g2d.setColor(Color.BLACK);
                            g2d.drawRect(j * CELL_SIZE, i * CELL_SIZE - height, CELL_SIZE, height * 2);
                        }
                    }
                }

                // Start and end points
                if (start != null) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(start.y * CELL_SIZE, start.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                if (end != null) {
                    g2d.setColor(Color.RED);
                    g2d.fillRect(end.y * CELL_SIZE, end.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }

                // Human icon (blue ball)
                if (!path.isEmpty() && currentPathIndex < path.size()) {
                    Point currentPos = path.get(currentPathIndex);
                    g2d.setColor(Color.BLUE);
                    g2d.fillOval(currentPos.y * CELL_SIZE + CELL_SIZE / 4,
                            currentPos.x * CELL_SIZE + CELL_SIZE / 4,
                            CELL_SIZE / 2, CELL_SIZE / 2);
                }
            }
        };
        gridPanel.setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        add(gridPanel, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Select Start Point");
        JButton endButton = new JButton("Select End Point");
        JButton findPathButton = new JButton("Find Path");
        JButton resetButton = new JButton("Reset");
        stepLabel = new JLabel("Steps: 0"); // Initialize step label
        controlPanel.add(startButton);
        controlPanel.add(endButton);
        controlPanel.add(findPathButton);
        controlPanel.add(resetButton);
        controlPanel.add(stepLabel); // Add step label to control panel
        add(controlPanel, BorderLayout.SOUTH);

        generateRandomBuildings();

        final boolean[] selectingStart = {true};
        gridPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;
                if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && grid[row][col]) {
                    if (selectingStart[0]) {
                        start = new Point(row, col);
                    } else {
                        end = new Point(row, col);
                    }
                    selectingStart[0] = !selectingStart[0];
                    gridPanel.repaint();
                }
            }
        });

        startButton.addActionListener(e -> selectingStart[0] = true);
        endButton.addActionListener(e -> selectingStart[0] = false);
        findPathButton.addActionListener(e -> {
            if (start != null && end != null) {
                path = findPath(start, end);
                if (!path.isEmpty()) {
                    currentPathIndex = 0;
                    stepLabel.setText("Steps: " + (path.size() - 1)); // Display step count
                    if (animationTimer != null) animationTimer.stop();
                    animationTimer = new Timer(500, evt -> {
                        if (currentPathIndex < path.size() - 1) {
                            currentPathIndex++;
                            gridPanel.repaint();
                        } else {
                            ((Timer) evt.getSource()).stop();
                        }
                    });
                    animationTimer.start();
                } else {
                    JOptionPane.showMessageDialog(null, "No path found!");
                    stepLabel.setText("Steps: 0");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select start and end points!");
            }
        });
        resetButton.addActionListener(e -> {
            start = null;
            end = null;
            path.clear();
            currentPathIndex = 0;
            stepLabel.setText("Steps: 0"); // Reset step count
            if (animationTimer != null) animationTimer.stop();
            generateRandomBuildings();
            gridPanel.repaint();
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generateRandomBuildings() {
        Random rand = new Random();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = rand.nextDouble() > 0.3;
            }
        }
    }

    private ArrayList<Point> findPath(Point start, Point end) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fCost));
        boolean[][] closedList = new boolean[GRID_SIZE][GRID_SIZE];
        Node[][] nodes = new Node[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                nodes[i][j] = new Node(i, j);
            }
        }

        Node startNode = nodes[start.x][start.y];
        Node endNode = nodes[end.x][end.y];
        startNode.gCost = 0;
        startNode.hCost = calculateHeuristic(start, end);
        startNode.fCost = startNode.hCost;
        openList.add(startNode);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            int x = current.x, y = current.y;
            closedList[x][y] = true;

            if (x == end.x && y == end.y) {
                ArrayList<Point> path = new ArrayList<>();
                Node node = current;
                while (node != null) {
                    path.add(new Point(node.x, node.y));
                    node = node.parent;
                }
                Collections.reverse(path);
                return path;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0], newY = y + dir[1];
                if (newX >= 0 && newX < GRID_SIZE && newY >= 0 && newY < GRID_SIZE &&
                        !closedList[newX][newY] && grid[newX][newY]) {
                    Node neighbor = nodes[newX][newY];
                    double newGCost = current.gCost + 1;
                    if (newGCost < neighbor.gCost) {
                        neighbor.parent = current;
                        neighbor.gCost = newGCost;
                        neighbor.hCost = calculateHeuristic(new Point(newX, newY), end);
                        neighbor.fCost = neighbor.gCost + neighbor.hCost;
                        openList.add(neighbor);
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    private double calculateHeuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private static class Node {
        int x, y;
        double gCost = Double.MAX_VALUE, hCost, fCost;
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Node)) return false;
            Node other = (Node) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AStarSwingPathFinder::new);
    }
}