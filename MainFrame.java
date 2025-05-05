import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 時間設定を行うメイン画面。
 */
public class MainFrame extends JFrame {

    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner secondSpinner;

    public MainFrame() {
        super("カウントダウンタイマー設定");

        // --- ウィンドウ設定 ---
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ウィンドウを閉じたらアプリ終了
        setAlwaysOnTop(true); // 最前面表示
        setSize(350, 180);
        setLocationRelativeTo(null); // 画面中央に表示
        setResizable(false); // サイズ変更不可

        // --- コンポーネントの作成 ---
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // コンポーネントが横一行を占める
        gbc.insets = new Insets(0, 0, 10, 0); // 下方向の余白
        gbc.anchor = GridBagConstraints.CENTER; // 中央揃え

        JLabel titleLabel = new JLabel("時間を設定してください:");
        panel.add(titleLabel, gbc);

        // 時間入力用パネル
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        // SpinnerNumberModel(初期値, 最小値, 最大値, ステップ)
        SpinnerNumberModel hourModel = new SpinnerNumberModel(0, 0, 23, 1);
        SpinnerNumberModel minuteModel = new SpinnerNumberModel(1, 0, 59, 1); // 初期値1分
        SpinnerNumberModel secondModel = new SpinnerNumberModel(0, 0, 59, 1);

        hourSpinner = new JSpinner(hourModel);
        minuteSpinner = new JSpinner(minuteModel);
        secondSpinner = new JSpinner(secondModel);

        // スピナーのフォーマット設定 (2桁表示)
        JSpinner.NumberEditor hourEditor = new JSpinner.NumberEditor(hourSpinner, "00");
        JSpinner.NumberEditor minuteEditor = new JSpinner.NumberEditor(minuteSpinner, "00");
        JSpinner.NumberEditor secondEditor = new JSpinner.NumberEditor(secondSpinner, "00");
        hourSpinner.setEditor(hourEditor);
        minuteSpinner.setEditor(minuteEditor);
        secondSpinner.setEditor(secondEditor);

        // スピナーの幅を調整
        Dimension spinnerSize = new Dimension(50, hourSpinner.getPreferredSize().height);
        hourSpinner.setPreferredSize(spinnerSize);
        minuteSpinner.setPreferredSize(spinnerSize);
        secondSpinner.setPreferredSize(spinnerSize);

        timePanel.add(hourSpinner);
        timePanel.add(new JLabel("時"));
        timePanel.add(minuteSpinner);
        timePanel.add(new JLabel("分"));
        timePanel.add(secondSpinner);
        timePanel.add(new JLabel("秒"));

        gbc.insets = new Insets(0, 0, 20, 0); // 下方向の余白を増やす
        panel.add(timePanel, gbc);

        // ボタン用パネル
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startButton = new JButton("開始");
        JButton quitButton = new JButton("終了");

        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);

        gbc.insets = new Insets(0, 0, 0, 0); // 余白なし
        panel.add(buttonPanel, gbc);

        // --- イベントリスナーの設定 ---
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitApp();
            }
        });

        // --- フレームにパネルを追加して表示 ---
        add(panel);
        setVisible(true);
    }

    /**
     * タイマーを開始する処理。
     */
    private void startTimer() {
        int hours = (int) hourSpinner.getValue();
        int minutes = (int) minuteSpinner.getValue();
        int seconds = (int) secondSpinner.getValue();

        long totalSeconds = (long) hours * 3600 + (long) minutes * 60 + seconds;

        if (totalSeconds <= 0) {
            JOptionPane.showMessageDialog(this,
                    "時間を正しく設定してください。",
                    "入力エラー",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // サブフレームを表示
        new SubFrame(totalSeconds);

        // メインフレームを破棄
        dispose();
    }

    /**
     * アプリケーションを終了する処理。
     */
    private void quitApp() {
        dispose(); // フレームを破棄（EXIT_ON_CLOSEなのでアプリも終了）
        // System.exit(0); // これでも可
    }
}