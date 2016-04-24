/**
 *
 */
package xeyes;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import xeyes.layout.GridCell;
import xeyes.layout.NonUniformGridLayout;

/** XEyes の設定ダイアログです。 */
public class OptionsDialog extends JDialog {
    /** このクラスのシリアルバージョン ID です。 */
    private static final long serialVersionUID = 1L;

    /** このウィンドウの設定を保持するデータモデルです。 */
    private final MainFrameModel model;

    /** 瞳の大きさを設定するスライダー */
    private final JSlider irisSizeRatioSlider = new JSlider();

    /** 瞳の大きさを数値で表示するラベル */
    private final JLabel irisSizeRatioLabel = new JLabel();

    /** 瞳の大きさの値を文字列化する際に使用するフォーマッター */
    private final NumberFormat irisSizeRatioLabelFormat = NumberFormat
            .getInstance();

    /**
     * XEyes の設定ダイアログを作成します。
     * @param owner ダイアログの所有者
     * @param model 設定を保持するデータモデル
     * @throws NullPointerException 指定されたデータモデルが {@code null} の場合
     */
    public OptionsDialog(Window owner, MainFrameModel model) {
        super(owner, ModalityType.DOCUMENT_MODAL);
        if (model == null)
            throw new NullPointerException("model is null.");
        this.model = model;

        irisSizeRatioSlider.setOrientation(SwingConstants.HORIZONTAL);
        irisSizeRatioSlider.setMinimum(10);
        irisSizeRatioSlider.setMaximum(90);
        irisSizeRatioSlider.setMinorTickSpacing(10);
        irisSizeRatioSlider.setPaintTicks(true);
        irisSizeRatioSlider
                .addChangeListener(irisSizeRatioSliderChangeListener);
        irisSizeRatioLabelFormat.setMinimumFractionDigits(2);
        irisSizeRatioLabelFormat.setMaximumFractionDigits(2);
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border, "瞳の大きさ");
        JPanel irisSizeRatioPanel = new JPanel();
        irisSizeRatioPanel.setLayout(new BoxLayout(irisSizeRatioPanel,
                BoxLayout.LINE_AXIS));
        irisSizeRatioPanel.setBorder(border);
        irisSizeRatioPanel.add(irisSizeRatioSlider);
        irisSizeRatioPanel.add(irisSizeRatioLabel);
        JCheckBox saveOnExitCheckBox = new JCheckBox();
        saveOnExitCheckBox.setAction(saveOnExitCheckBoxAction);
        saveOnExitCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton okButton = new JButton();
        okButton.setAction(okButtonAction);
        okButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        float[] rowWeights = { 2.0f, 1.0f, 1.0f };
        float[] columnWeights = { 1.0f };
        JComponent contentPane = (JComponent) this.getContentPane();
        // contentPane.setBackground(new Color(0xA55A4A));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new NonUniformGridLayout(rowWeights,
                columnWeights));
        contentPane.add(irisSizeRatioPanel);
        contentPane.add(saveOnExitCheckBox, new GridCell(1, 0));
        contentPane.add(okButton, new GridCell(2, 0));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(owner);
        this.setResizable(false);
        this.addWindowListener(windowListener);

        setIrisSizeRatio(model.getIrisSizeRatio());
        setSaveOnExit(model.isSaveOnExit());

        this.pack();
    }

    /** このダイアログを閉じます。 */
    public void close() {
        this.getToolkit().getSystemEventQueue()
                .postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * このダイアログのコンポーネントに瞳の大きさの値を設定します。
     * @param irisSizeRatio 瞳の大きさ
     */
    private void setIrisSizeRatio(float irisSizeRatio) {
        irisSizeRatioSlider.setValue(Math.round(irisSizeRatio * 100));
        irisSizeRatioLabel.setText(irisSizeRatioLabelFormat
                .format(irisSizeRatio));
    }

    /**
     * このダイアログのコンポーネントに終了時に設定をファイルへ保存するかを設定します。
     * @param saveOnExit 終了時に設定をファイルへ保存するか
     */
    private void setSaveOnExit(boolean saveOnExit) {
        saveOnExitCheckBoxAction.putValue(Action.SELECTED_KEY, saveOnExit);
    }

    /** このダイアログによって呼び出されるウィンドウリスナー */
    private final WindowListener windowListener = new WindowAdapter() {
        /*
         * (非 Javadoc)
         * 
         * @see
         * java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
         */
        @Override
        public void windowOpened(WindowEvent e) {
            assert e.getWindow() == OptionsDialog.this;
            model.addPropertyChangeListener(modelPropertyChangeListener);
        }

        /*
         * (非 Javadoc)
         * 
         * @see
         * java.awt.event.WindowAdapter#windowClosed(java.awt.event.WindowEvent)
         */
        @Override
        public void windowClosed(WindowEvent e) {
            assert e.getWindow() == OptionsDialog.this;
            model.removePropertyChangeListener(modelPropertyChangeListener);
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
                    } else if (MainFrameModel.SAVE_ON_EXIT_PROPERTY
                            .equals(propertyName)) {
                        boolean saveOnExit = (Boolean) evt.getNewValue();
                        setSaveOnExit(saveOnExit);
                    }
                }
            };

    /** スライダーの値が変化した時に呼び出されるリスナー */
    private final ChangeListener irisSizeRatioSliderChangeListener =
            new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    assert e.getSource() == irisSizeRatioSlider;
                    float irisSizeRatio =
                            irisSizeRatioSlider.getValue() / 100.0f;
                    model.setIrisSizeRatio(irisSizeRatio);
                }
            };

    /** 終了時に設定をファイルへ保存するかどうかを選択するチェックボックスのアクション */
    private final Action saveOnExitCheckBoxAction =
            new SaveOnExitCheckBoxAction();

    /** 終了時に設定をファイルへ保存するかどうかを選択するチェックボックスのアクションです。 */
    private class SaveOnExitCheckBoxAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public SaveOnExitCheckBoxAction() {
            super("終了時に設定をファイルへ保存(S)");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
            putValue(Action.SELECTED_KEY, false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean saveOnExit = (Boolean) getValue(Action.SELECTED_KEY);
            model.setSaveOnExit(saveOnExit);
        }
    }

    /** このダイアログを終了するボタンのアクション */
    private final Action okButtonAction = new OkButtonAction();

    /** このダイアログを終了するボタンのアクションです。 */
    private class OkButtonAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public OkButtonAction() {
            super("OK");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            close();
        }
    }
}
