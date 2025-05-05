import javax.swing.SwingUtilities;

/**
 * カウントダウンタイマーアプリケーションを起動するクラス。
 */
public class CountdownTimerApp {

    public static void main(String[] args) {
        // Swingコンポーネントの作成と更新はイベントディスパッチスレッド(EDT)で行う
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame(); // メインフレームを作成して表示
            }
        });
    }
}