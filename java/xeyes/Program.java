package xeyes;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/** XEyes プログラムを実行するメインメソッドを持つクラスです。 */
public class Program {
    /** このクラスのインスタンスが作成できないようデフォルトコンストラクタを抑制します。 */
    private Program() {
        throw new AssertionError();
    }

    /**
     * XEyes プログラムのメインメソッドです。
     * @param args コマンドライン引数。指定できるものは特になし
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /** このプログラムの名前 */
    private static final String NAME = "XEyesJava";

    /** 行区切り文字 */
    private static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

    /** XEyes プログラムを実行します。 */
    private static void createAndShowGUI() {
        MainFrameModel model = new MainFrameModel(NAME + ".xml");
        try {
            model.load();
        } catch (IOException e) {
            showLoadErrorMessage(e);
            System.exit(1);
        }

        JFrame frame = new MainFrame(model);
        frame.setTitle(NAME);
        frame.setVisible(true);
    }

    /**
     * 設定の読み込みに失敗したことを示すダイアログを表示します。
     * @param e エラーオブジェクト
     */
    public static void showLoadErrorMessage(Throwable e) {
        StringBuilder builder = new StringBuilder(64);
        builder.append("設定ファイルの読み込みに失敗しました。");
        builder.append(LINE_SEPARATOR);
        builder.append("現在の設定ファイルを削除するかリネームしてください。");
        showErrorMessage(builder, e);
    }

    /**
     * 設定の書き込みに失敗したことを示すダイアログを表示します。
     * @param e エラーオブジェクト
     */
    public static void showSaveErrorMessage(Throwable e) {
        String text = "設定ファイルの書き込みに失敗しました。";
        showErrorMessage(text, e);
    }

    /**
     * 指定されたテキストとエラーをダイアログに表示します。
     * @param text テキスト
     * @param e エラーオブジェクト
     */
    private static void showErrorMessage(CharSequence text, Throwable e) {
        StringBuilder builder = new StringBuilder(256);
        builder.append(text);
        builder.append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        builder.append("エラーの詳細：");
        builder.append(LINE_SEPARATOR);
        builder.append(e.getMessage());
        String message = builder.toString();

        builder.setLength(0);
        builder.append("エラー - ").append(NAME);
        String title = builder.toString();

        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.ERROR_MESSAGE);
    }
}
