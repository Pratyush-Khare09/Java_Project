import javax.swing.*; 
import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.util.Random; 
public class ColorMatchGame extends JFrame { 
    // --- Constants --- 
    private static final String[] COLOR_NAMES = {"Red", "Yellow", "Blue", "Green"};     private static final Color[] INK_COLORS = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN}; 
    private static final int GAME_DURATION_SECONDS = 60; 
    // --- UI Components --- 
    private final JLabel promptLabel = new JLabel("Press Restart to Begin", SwingConstants.CENTER); 
    private final JButton redBtn = new JButton("Red");     private final JButton yellowBtn = new JButton("Yellow");     private final JButton blueBtn = new JButton("Blue");     private final JButton greenBtn = new JButton("Green"); 
    
 private final JButton restartBtn = new JButton("Restart Game");     private final JLabel timeLabel = new JLabel("Time: 60");     private final JLabel scoreLabel = new JLabel("Score: 0");     private final JLabel correctLabel = new JLabel("Correct: 0");     private final JLabel wrongLabel = new JLabel("Wrong: 0");     private final JLabel totalLabel = new JLabel("Total: 0"); 
    // --- Game State --- 
    private final Random rng = new Random(); 
    private int remainingSeconds = GAME_DURATION_SECONDS; 
    private int score = 0, correct = 0, wrong = 0, total = 0;     private int currentInkIndex = -1;     private boolean running = false; 
    // --- Timer for countdown --- 
    private final Timer timer = new Timer(1000, (ActionEvent e) -> {         if (!running) return;         remainingSeconds--;         if (remainingSeconds <= 0) {             remainingSeconds = 0;             setRunning(false); 
        } 
        updateStatus(); 
    }); 
    public ColorMatchGame() {         super("Color Match Game"); 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);         setLayout(new BorderLayout(10, 10));         getContentPane().setBackground(Color.WHITE); 
        // Central Label 
        promptLabel.setFont(promptLabel.getFont().deriveFont(Font.BOLD, 48f));         promptLabel.setOpaque(true); 
        promptLabel.setBackground(Color.WHITE);         add(promptLabel, BorderLayout.CENTER); 
        // Top Status Bar 
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));         statusPanel.setBackground(Color.WHITE); 
        for (JLabel lbl : new JLabel[]{timeLabel, scoreLabel, correctLabel, wrongLabel, totalLabel}) {             lbl.setFont(lbl.getFont().deriveFont(Font.PLAIN, 16f));             statusPanel.add(lbl); 
        } 
        add(statusPanel, BorderLayout.NORTH); 
        // Bottom Buttons 
        JPanel controls = new JPanel(new BorderLayout(10, 10));         controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));         controls.setBackground(Color.WHITE); 
        JPanel colorGrid = new JPanel(new GridLayout(2, 2, 10, 10));         colorGrid.setBackground(Color.WHITE); 
        JButton[] colorButtons = {redBtn, yellowBtn, blueBtn, greenBtn}; 
        for (int i = 0; i < colorButtons.length; i++) {             final int idx = i; 
            JButton b = colorButtons[i];             b.setFocusable(false); 
            b.setFont(b.getFont().deriveFont(Font.BOLD, 18f)); 
            b.addActionListener(evt -> handleSelection(idx));             colorGrid.add(b); 
        } 
        controls.add(colorGrid, BorderLayout.CENTER); 
         
        JPanel restartPanel = new JPanel(new GridBagLayout());         restartPanel.setBackground(Color.WHITE); 
        restartBtn.setFont(restartBtn.getFont().deriveFont(Font.BOLD, 16f));         restartBtn.setFocusable(false); 
        restartBtn.addActionListener(evt -> restartGame());         restartPanel.add(restartBtn); 
        controls.add(restartPanel, BorderLayout.EAST); 
        add(controls, BorderLayout.SOUTH); 
        setRunning(false);         timer.setRepeats(true);         pack();         setSize(820, 360);         setLocationRelativeTo(null);         setVisible(true); 
    } 
    private void nextRound() { 
        String wordText = COLOR_NAMES[rng.nextInt(COLOR_NAMES.length)];         currentInkIndex = rng.nextInt(INK_COLORS.length);         promptLabel.setText(wordText); 
        promptLabel.setForeground(INK_COLORS[currentInkIndex]); 
    } 
    private void handleSelection(int idx) {         if (!running) return; 
        boolean isCorrect = (idx == currentInkIndex); 
        if (isCorrect) { 
score++; correct++; 
} 
else { 
score--; wrong++; 
}         
total++;         
updateStatus(); 
        nextRound() ;
} 
    private void setRunning(boolean shouldRun) {         running = shouldRun;         redBtn.setEnabled(shouldRun);         yellowBtn.setEnabled(shouldRun);         blueBtn.setEnabled(shouldRun);         greenBtn.setEnabled(shouldRun);         if (shouldRun) { if (!timer.isRunning()) timer.start(); }         else { if (timer.isRunning()) timer.stop(); } 
    } 
    private void restartGame() { 
        remainingSeconds = GAME_DURATION_SECONDS; 
        score = correct = wrong = total = 0;         updateStatus();         setRunning(true);         nextRound(); 
    } 
    private void updateStatus() { 
        timeLabel.setText("Time: " + remainingSeconds);         scoreLabel.setText("Score: " + score);         correctLabel.setText("Correct: " + correct);         wrongLabel.setText("Wrong: " + wrong);         totalLabel.setText("Total: " + total);         if (!running && remainingSeconds == 0) {             promptLabel.setText("Time Up!"); 
        } 
    } 
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(ColorMatchGame::new); 
    } } 
