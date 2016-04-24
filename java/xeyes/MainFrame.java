package xeyes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import xeyes.layout.GridCell;
import xeyes.layout.NonUniformGridLayout;

/** XEyes のメインウィンドウです。 */
public final class MainFrame extends JFrame {
    /** このクラスのシリアルバージョン ID です。 */
    private static final long serialVersionUID = 1L;

    /** このウィンドウの設定を保持するデータモデルです。 */
    private final MainFrameModel model;

    /** XEyes の左側の目 */
    private final EyeComponent leftEye = new EyeComponent();

    /** XEyes の右側の目 */
    private final EyeComponent rightEye = new EyeComponent();

    /**
     * XEyes のメインウィンドウを作成します。
     * @param model 設定を保持するデータモデル
     */
    public MainFrame(MainFrameModel model) {
        super();
        if (model == null)
            throw new NullPointerException("model is null.");
        this.model = model;

        JCheckBoxMenuItem topMostMenuItem = new JCheckBoxMenuItem();
        topMostMenuItem.setAction(topMostMenuItemAction);
        JPopupMenu mainPopupMenu = new JPopupMenu();
        mainPopupMenu.setLabel("メニュー");
        mainPopupMenu.add(topMostMenuItem);
        mainPopupMenu.add(optionsMenuItemAction);
        mainPopupMenu.addSeparator();
        mainPopupMenu.add(saveMenuItemAction);
        mainPopupMenu.addSeparator();
        mainPopupMenu.add(exitMenuItemAction);
        leftEye.setInheritsPopupMenu(true);
        rightEye.setInheritsPopupMenu(true);
        float[] rowWeights = { 1.0f, 10.0f, 1.0f };
        float[] columnWeights = { 1.0f, 10.0f, 2.0f, 10.0f, 1.0f };
        JComponent contentPane = (JComponent) this.getContentPane();
        contentPane.setBackground(new Color(0xA55A4A));
        contentPane.setComponentPopupMenu(mainPopupMenu);
        contentPane.setLayout(new NonUniformGridLayout(rowWeights,
                columnWeights));
        contentPane.add(leftEye, new GridCell(1, 1, true, true));
        contentPane.add(rightEye, new GridCell(1, 3, true, true));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(180, 180));
        // this.setSize(200, 200);
        this.addWindowListener(windowListener);

        setIrisSizeRatio(model.getIrisSizeRatio());
        setTopMost(model.isTopMost());
        setFrameState(model);

        Timer mainTimer = new Timer(50, mainTimerListener);
        mainTimer.start();
    }

    /** このウィンドウを閉じます。 */
    public void close() {
        this.getToolkit().getSystemEventQueue()
                .postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /** オプションダイアログを表示します。 */
    private void showOptionsDialog() {
        JDialog optionsDialog = new OptionsDialog(this, model);
        optionsDialog.setTitle(this.getTitle());
        optionsDialog.setVisible(true);
    }

    /**
     * このウィンドウのコンポーネントに瞳の大きさの値を設定します。
     * @param irisSizeRatio 瞳の大きさ
     */
    private void setIrisSizeRatio(float irisSizeRatio) {
        leftEye.setIrisSizeRatio(irisSizeRatio);
        rightEye.setIrisSizeRatio(irisSizeRatio);
    }

    /**
     * このウィンドウに常に最前面にウィンドウを表示するかどうかを設定します。
     * @param topMost 常に最前面にウィンドウを表示するかどうか
     */
    private void setTopMost(boolean topMost) {
        this.setAlwaysOnTop(topMost);
        topMostMenuItemAction.putValue(Action.SELECTED_KEY, topMost);
    }

    /**
     * このウィンドウの状態を指定されたデータモデルから設定します。
     * @param model ウィンドウの状態をもつデータモデル
     */
    private void setFrameState(MainFrameModel model) {
        setExtendedState(model.getFrameState());
        if (MainFrameModel.frameBoundsIsValid(getExtendedState()))
            setBounds(model.getFrameX(), model.getFrameY(),
                    model.getFrameWidth(), model.getFrameHeight());
    }

    /** データモデルのプロバティーをファイルへ書き込みます。 */
    private void saveModel() {
        int frameState = getExtendedState();
        model.setFrameState(frameState);
        if (MainFrameModel.frameBoundsIsValid(frameState)) {
            model.setFrameX(getX());
            model.setFrameY(getY());
            model.setFrameWidth(getWidth());
            model.setFrameHeight(getHeight());
        }

        try {
            model.save();
        } catch (IOException ex) {
            Program.showSaveErrorMessage(ex);
        }
    }

    /** このウィンドウによって呼び出されるウィンドウリスナー */
    private final WindowListener windowListener = new WindowAdapter() {
        /*
         * (非 Javadoc)
         * 
         * @see
         * java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
         */
        @Override
        public void windowOpened(WindowEvent e) {
            assert e.getWindow() == MainFrame.this;
            model.addPropertyChangeListener(modelPropertyChangeListener);
        }

        /*
         * (非 Javadoc)
         * 
         * @see
         * java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent
         * )
         */
        @Override
        public void windowClosing(WindowEvent e) {
            if (model.isSaveOnExit())
                saveModel();
        }

        /*
         * (非 Javadoc)
         * 
         * @see
         * java.awt.event.WindowAdapter#windowClosed(java.awt.event.WindowEvent)
         */
        @Override
        public void windowClosed(WindowEvent e) {
            assert e.getWindow() == MainFrame.this;
            model.removePropertyChangeListener(modelPropertyChangeListener);

            System.exit(0);
        }
    };

    /** データモデルのプロパティが変更されたときに呼び出されるリスナー */
    private final PropertyChangeListener modelPropertyChangeListener =
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    assert evt.getSource() == model;
                    String propertyName = evt.getPropertyName();
                    if (MainFrameModel.IRIS_SIZE_RATIO_PROPERTY
                            .equals(propertyName)) {
                        float irisSizeRatio = (Float) evt.getNewValue();
                        setIrisSizeRatio(irisSizeRatio);
                    } else if (MainFrameModel.TOP_MOST_PROPERTY
                            .equals(propertyName)) {
                        boolean topMost = (Boolean) evt.getNewValue();
                        setTopMost(topMost);
                    }
                }
            };

    /** メインタイマーによって一定時間ごとに呼び出されるリスナー */
    private final ActionListener mainTimerListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Point focus = MouseInfo.getPointerInfo().getLocation();
            Point leftEyeFocus = new Point(focus);
            Point rightEyeFocus = new Point(focus);
            SwingUtilities.convertPointFromScreen(leftEyeFocus, leftEye);
            SwingUtilities.convertPointFromScreen(rightEyeFocus, rightEye);
            leftEye.lookAt(leftEyeFocus);
            rightEye.lookAt(rightEyeFocus);
        }
    };

    /** 常に最前面にウィンドウを表示するかどうかを選択するメニュー項目のアクション */
    private final TopMostMenuItemAction topMostMenuItemAction =
            new TopMostMenuItemAction();

    /** 常に最前面にウィンドウを表示するかどうかを選択するメニュー項目のアクションです。 */
    private class TopMostMenuItemAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public TopMostMenuItemAction() {
            super("常に前面(A)");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
            putValue(Action.SELECTED_KEY, false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean topMost = (Boolean) getValue(Action.SELECTED_KEY);
            model.setTopMost(topMost);
        }
    }

    /** オプションダイアログを開くメニュー項目のアクション */
    private final Action optionsMenuItemAction = new OptionsMenuItemAction();

    /** オプションダイアログを開くメニュー項目のアクションです。 */
    private class OptionsMenuItemAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public OptionsMenuItemAction() {
            super("オプション(O)...");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showOptionsDialog();
        }
    }

    /** 現在の設定をファイルへ保存するメニュー項目のアクション */
    private final Action saveMenuItemAction = new SaveMenuItemAction();

    /** 現在の設定をファイルへ保存するメニュー項目のアクションです。 */
    private class SaveMenuItemAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public SaveMenuItemAction() {
            super("現在の設定をファイルへ保存(S)");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            saveModel();
        }
    }

    /** ウィンドウを閉じる操作を行うメニュー項目のアクション */
    private final Action exitMenuItemAction = new ExitMenuItemAction();

    /** ウィンドウを閉じる操作を行うメニュー項目のアクションです。 */
    private class ExitMenuItemAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public ExitMenuItemAction() {
            super("終了(X)");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            close();
        }
    }
}
