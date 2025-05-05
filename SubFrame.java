import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 残り時間を表示するサブ画面。
 */
public class SubFrame extends JFrame {

    private long remainingSeconds;
    private JLabel timeLabel;
    private Timer timer; // javax.swing.Timerを使用

    public SubFrame(long initialSeconds) {
        super("残り時間");
        this.remainingSeconds = initialSeconds;

        // --- ウィンドウ設定 ---
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // これだとサブ画面閉じたらアプリ終了
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // サブ画面だけ閉じる
        setAlwaysOnTop(true); // 最前面表示
        setResizable(false); // サイズ変更不可
        // setUndecorated(true); // タイトルバーなどを非表示にする場合

        // --- コンポーネント作成 ---
        timeLabel = new JLabel(formatTime(remainingSeconds));
        timeLabel.setFont(new Font("Helvetica", Font.BOLD, 36));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 余白

        add(timeLabel);
        pack(); // コンポーネントに合わせてウィンドウサイズを調整

        // --- 画面右下に配置 ---
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int screenWidth = (int) rect.getMaxX();
        int screenHeight = (int) rect.getMaxY();
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        // タスクバーの高さを考慮しない単純な右下配置 (少しオフセット)
        int x = screenWidth - windowWidth - 10;
        int y = screenHeight - windowHeight - 50;
        setLocation(x, y);

        // --- タイマー設定 (1000ms = 1秒ごと) ---
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        timer.start();

        // --- ウィンドウクローズ時の処理 ---
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitTimer(); // タイマー停止処理
            }
        });

        // --- 表示 ---
        setVisible(true);
    }

    /**
     * タイマーを更新する処理。
     */
    private void updateTimer() {
        if (remainingSeconds > 0) {
            remainingSeconds--;
            timeLabel.setText(formatTime(remainingSeconds));
        } else {
            timerFinished();
        }
    }

    /**
     * 秒数を HH:MM:SS 形式の文字列に変換する。
     * 
     * @param totalSeconds 秒数
     * @return フォーマットされた文字列
     */
    private String formatTime(long totalSeconds) {
        if (totalSeconds < 0)
            totalSeconds = 0;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * タイマーが終了したときの処理。
     */
    private void timerFinished() {
        quitTimer(); // タイマー停止とフレーム破棄

        // 終了メッセージを表示（最前面になるように親を指定したいが、
        // このフレームは破棄するのでnullで良い。自動的に最前面になるはず）
        // 必要であれば一時的な非表示JFrameを親にするテクニックも使える
        JOptionPane.showMessageDialog(null, // 親フレームなし
                "時間だよ！",
                "タイマー終了",
                JOptionPane.INFORMATION_MESSAGE);

        // メッセージボックスが閉じられた後、アプリケーションを終了
        System.exit(0);
    }

    /**
     * タイマーを停止し、ウィンドウを破棄する。
     */
    private void quitTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        dispose(); // このフレームを破棄
    }
}