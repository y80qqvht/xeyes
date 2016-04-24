package xeyes;

import java.awt.Frame;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import xeyes.util.MyProperties;

/** XEyes のメインウィンドウで使用されるデータモデルです。 */
public class MainFrameModel {
    /** このモデルのプロパティを管理するオブジェクト */
    private final MyProperties properties = new MyProperties();

    /** このモデルのプロパティを保存するファイル */
    private final File propertiesFile;

    /** プロパティの変更通知を管理するオブジェクト */
    private final PropertyChangeSupport propertyChangeSupport;

    /**
     * 指定されたファイルのパスに設定を読み書きするデータモデルを作成します。
     * @param propertiesPath モデルのプロパティを保存するファイル
     * @throws NullPointerException 指定されたファイルのパスが {@code null} の場合
     */
    public MainFrameModel(String propertiesPath) {
        if (propertiesPath == null)
            throw new NullPointerException("propertiesPath is null.");
        this.propertiesFile = new File(propertiesPath);
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * ファイルからプロパティを読み込みます。
     * @throws InvalidPropertiesFormatException ファイルが有効な XML ドキュメントではない場合
     * @throws IOException 入出力例外が発生した場合
     */
    public void load() throws InvalidPropertiesFormatException, IOException {
        if (propertiesFile.exists()) {
            properties.loadFromXMLFile(propertiesFile);
            firePropertyChange(null, null, null);
        }
    }

    /**
     * ファイルへプロパティを書き込みます。
     * @throws IOException 入出力例外が発生した場合
     */
    public void save() throws IOException {
        properties.storeToXMLFile(propertiesFile, null);
    }

    /** 目のサイズに対する瞳のサイズの比率を表すプロパティの名前 */
    public static final String IRIS_SIZE_RATIO_PROPERTY = "irisSizeRatio";

    /** 目のサイズに対する瞳のサイズの比率のデフォルト値 */
    public static final float IRIS_SIZE_RATIO_DEFAULT_VALUE = 0.4f;

    /**
     * 指定された目のサイズに対する瞳のサイズの比率が有効な値であるかどうかを調べます。
     * @param irisSizeRatio 目のサイズに対する瞳のサイズの比率
     * @return 目のサイズに対する瞳のサイズの比率が有効な値である場合は {@code true}
     */
    public static boolean isValidIrisSizeRatio(float irisSizeRatio) {
        return irisSizeRatio > 0.0f && irisSizeRatio < 1.0f;
    }

    /**
     * 目のサイズに対する瞳のサイズの比率を取得します。
     * @return 目のサイズに対する瞳のサイズの比率
     */
    public float getIrisSizeRatio() {
        float irisSizeRatio =
                properties.getFloatProperty(IRIS_SIZE_RATIO_PROPERTY,
                        IRIS_SIZE_RATIO_DEFAULT_VALUE);
        if (!isValidIrisSizeRatio(irisSizeRatio))
            irisSizeRatio = IRIS_SIZE_RATIO_DEFAULT_VALUE;
        return irisSizeRatio;
    }

    /**
     * 目のサイズに対する瞳のサイズの比率を設定します。
     * @param irisSizeRatio 目のサイズに対する瞳のサイズの比率
     * @throws IllegalArgumentException 指定された値が有効でない場合
     */
    public void setIrisSizeRatio(float irisSizeRatio) {
        if (!isValidIrisSizeRatio(irisSizeRatio)) {
            throw new IllegalArgumentException(IRIS_SIZE_RATIO_PROPERTY + " = "
                    + irisSizeRatio);
        }
        float oldIrisSizeRatio = getIrisSizeRatio();
        if (oldIrisSizeRatio != irisSizeRatio) {
            properties
                    .setFloatProperty(IRIS_SIZE_RATIO_PROPERTY, irisSizeRatio);
            firePropertyChange(IRIS_SIZE_RATIO_PROPERTY, oldIrisSizeRatio,
                    irisSizeRatio);
        }
    }

    /** 終了時に設定をファイルへ保存するかどうかを表すプロパティの名前 */
    public static final String SAVE_ON_EXIT_PROPERTY = "saveOnExit";

    /** 終了時に設定をファイルへ保存するかどうかのデフォルト値 */
    public static final boolean SAVE_ON_EXIT_DEFAULT_VALUE = true;

    /**
     * 終了時に設定をファイルへ保存するかどうかを取得します。
     * @return 終了時に設定をファイルへ保存するかどうか
     */
    public boolean isSaveOnExit() {
        return properties.getBooleanProperty(SAVE_ON_EXIT_PROPERTY,
                SAVE_ON_EXIT_DEFAULT_VALUE);
    }

    /**
     * 終了時に設定をファイルへ保存するかどうかを設定します。
     * @param saveOnExit 終了時に設定をファイルへ保存するかどうか
     */
    public void setSaveOnExit(boolean saveOnExit) {
        boolean oldSaveOnExit = isSaveOnExit();
        if (oldSaveOnExit != saveOnExit) {
            properties.setBooleanProperty(SAVE_ON_EXIT_PROPERTY, saveOnExit);
            firePropertyChange(SAVE_ON_EXIT_PROPERTY, oldSaveOnExit, saveOnExit);
        }
    }

    /** 常に最前面にウィンドウを表示するかどうかを表すプロパティの名前 */
    public static final String TOP_MOST_PROPERTY = "topMost";

    /** 終了時に設定をファイルへ保存するかどうかのデフォルト値 */
    public static final boolean TOP_MOST_DEFAULT_VALUE = false;

    /**
     * 常に最前面にウィンドウを表示するかどうかを取得します。
     * @return 常に最前面にウィンドウを表示するかどうか
     */
    public boolean isTopMost() {
        return properties.getBooleanProperty(TOP_MOST_PROPERTY,
                TOP_MOST_DEFAULT_VALUE);
    }

    /**
     * 常に最前面にウィンドウを表示するかどうかを設定します。
     * @param topMost 常に最前面にウィンドウを表示するかどうか
     */
    public void setTopMost(boolean topMost) {
        boolean oldTopMost = isTopMost();
        if (oldTopMost != topMost) {
            properties.setBooleanProperty(TOP_MOST_PROPERTY, topMost);
            firePropertyChange(TOP_MOST_PROPERTY, oldTopMost, topMost);
        }
    }

    /** ウィンドウの状態を表すプロパティの名前 */
    public static final String FRAME_STATE_PROPERTY = "frameState";

    /** ウィンドウの状態のデフォルト値 */
    public static final int FRAME_STATE_DEFAULT_VALUE = Frame.NORMAL;

    /**
     * 指定されたウィンドウの状態が有効な値であるかどうかを調べます。
     * @param frameState ウィンドウの状態
     * @return ウィンドウの状態が有効な値である場合は {@code true}
     */
    public static boolean isValidFrameState(int frameState) {
        switch (frameState) {
            case Frame.NORMAL:
            case Frame.ICONIFIED:
            case Frame.MAXIMIZED_HORIZ:
            case Frame.MAXIMIZED_VERT:
            case Frame.MAXIMIZED_BOTH:
                return true;
            default:
                return false;
        }
    }

    /**
     * ウィンドウの状態を取得します。
     * @return ウィンドウの状態
     */
    public int getFrameState() {
        int frameState =
                properties.getIntegerProperty(FRAME_STATE_PROPERTY,
                        FRAME_STATE_DEFAULT_VALUE);
        if (!isValidFrameState(frameState))
            frameState = FRAME_STATE_DEFAULT_VALUE;
        return frameState;
    }

    /**
     * ウィンドウの状態を設定します。
     * @param frameState ウィンドウの状態
     * @throws IllegalArgumentException 指定された値が有効でない場合
     */
    public void setFrameState(int frameState) {
        if (!isValidFrameState(frameState)) {
            throw new IllegalArgumentException(FRAME_STATE_PROPERTY + " = "
                    + frameState);
        }
        int oldFrameState = getFrameState();
        if (oldFrameState != frameState) {
            properties.setIntegerProperty(FRAME_STATE_PROPERTY, frameState);
            firePropertyChange(FRAME_STATE_PROPERTY, oldFrameState, frameState);
            if (!frameBoundsIsValid(frameState)) {
                properties.removeProperty(FRAME_X_PROPERTY);
                properties.removeProperty(FRAME_Y_PROPERTY);
                properties.removeProperty(FRAME_WIDTH_PROPERTY);
                properties.removeProperty(FRAME_HEIGHT_PROPERTY);
            }
        }
    }

    /**
     * ウィンドウの位置やサイズの取得・設定が有効な状態であるかどうかを調べます。
     * @param frameState ウィンドウの状態
     * @return ウィンドウの位置やサイズの取得・設定が有効な状態である場合は {@code true}
     */
    public static boolean frameBoundsIsValid(int frameState) {
        return frameState == Frame.NORMAL;
    }

    /**
     * ウィンドウの位置やサイズの取得・設定が有効な状態であることを確認します。
     * @throws IllegalStateException ウィンドウの位置やサイズの取得・設定が有効な状態でない場合
     */
    private void checkFrameBoundsIsValid() {
        int frameState = getFrameState();
        if (!frameBoundsIsValid(frameState)) {
            throw new IllegalStateException(FRAME_STATE_PROPERTY + " = "
                    + frameState);
        }
    }

    /** ウィンドウの {@code x} 座標を表すプロパティの名前 */
    public static final String FRAME_X_PROPERTY = "frameX";

    /** ウィンドウの {@code x} 座標のデフォルト値 */
    public static final int FRAME_X_DEFAULT_VALUE = 0;

    /**
     * ウィンドウの {@code x} 座標を取得します。
     * @return ウィンドウの {@code x} 座標
     * @throws IllegalStateException ウィンドウの位置の取得が有効な状態でない場合
     */
    public int getFrameX() {
        checkFrameBoundsIsValid();
        return properties.getIntegerProperty(FRAME_X_PROPERTY,
                FRAME_X_DEFAULT_VALUE);
    }

    /**
     * ウィンドウの {@code x} 座標を設定します。
     * @param x ウィンドウの {@code x} 座標
     * @throws IllegalStateException ウィンドウの位置の設定が有効な状態でない場合
     */
    public void setFrameX(int x) {
        checkFrameBoundsIsValid();
        int oldFrameX = getFrameX();
        if (oldFrameX != x) {
            properties.setIntegerProperty(FRAME_X_PROPERTY, x);
            firePropertyChange(FRAME_X_PROPERTY, oldFrameX, x);
        }
    }

    /** ウィンドウの {@code y} 座標を表すプロパティの名前 */
    public static final String FRAME_Y_PROPERTY = "frameY";

    /** ウィンドウの {@code y} 座標のデフォルト値 */
    public static final int FRAME_Y_DEFAULT_VALUE = 0;

    /**
     * ウィンドウの {@code y} 座標を取得します。
     * @return ウィンドウの {@code y} 座標
     * @throws IllegalStateException ウィンドウの位置の取得が有効な状態でない場合
     */
    public int getFrameY() {
        checkFrameBoundsIsValid();
        return properties.getIntegerProperty(FRAME_Y_PROPERTY,
                FRAME_Y_DEFAULT_VALUE);
    }

    /**
     * ウィンドウの {@code y} 座標を設定します。
     * @param y ウィンドウの {@code y} 座標
     * @throws IllegalStateException ウィンドウの位置の設定が有効な状態でない場合
     */
    public void setFrameY(int y) {
        checkFrameBoundsIsValid();
        int oldFrameY = getFrameY();
        if (oldFrameY != y) {
            properties.setIntegerProperty(FRAME_Y_PROPERTY, y);
            firePropertyChange(FRAME_Y_PROPERTY, oldFrameY, y);
        }
    }

    /** ウィンドウの幅を表すプロパティの名前 */
    public static final String FRAME_WIDTH_PROPERTY = "frameWidth";

    /** ウィンドウの幅のデフォルト値 */
    public static final int FRAME_WIDTH_DEFAULT_VALUE = 200;

    /**
     * ウィンドウの幅を取得します。
     * @return ウィンドウの幅
     * @throws IllegalStateException ウィンドウのサイズの取得が有効な状態でない場合
     */
    public int getFrameWidth() {
        checkFrameBoundsIsValid();
        return properties.getIntegerProperty(FRAME_WIDTH_PROPERTY,
                FRAME_WIDTH_DEFAULT_VALUE);
    }

    /**
     * ウィンドウの幅を設定します。
     * @param width ウィンドウの幅
     * @throws IllegalStateException ウィンドウのサイズの設定が有効な状態でない場合
     */
    public void setFrameWidth(int width) {
        checkFrameBoundsIsValid();
        int oldFrameWidth = getFrameWidth();
        if (oldFrameWidth != width) {
            properties.setIntegerProperty(FRAME_WIDTH_PROPERTY, width);
            firePropertyChange(FRAME_WIDTH_PROPERTY, oldFrameWidth, width);
        }
    }

    /** ウィンドウの高さを表すプロパティの名前 */
    public static final String FRAME_HEIGHT_PROPERTY = "frameHeight";

    /** ウィンドウの高さのデフォルト値 */
    public static final int FRAME_HEIGHT_DEFAULT_VALUE = 200;

    /**
     * ウィンドウの高さを取得します。
     * @return ウィンドウの高さ
     * @throws IllegalStateException ウィンドウのサイズの取得が有効な状態でない場合
     */
    public int getFrameHeight() {
        checkFrameBoundsIsValid();
        return properties.getIntegerProperty(FRAME_HEIGHT_PROPERTY,
                FRAME_HEIGHT_DEFAULT_VALUE);
    }

    /**
     * ウィンドウの高さを設定します。
     * @param height ウィンドウの高さ
     * @throws IllegalStateException ウィンドウのサイズの設定が有効な状態でない場合
     */
    public void setFrameHeight(int height) {
        checkFrameBoundsIsValid();
        int oldFrameHeight = getFrameHeight();
        if (oldFrameHeight != height) {
            properties.setIntegerProperty(FRAME_HEIGHT_PROPERTY, height);
            firePropertyChange(FRAME_HEIGHT_PROPERTY, oldFrameHeight, height);
        }
    }

    /**
     * {@code PropertyChangeListener} をリスナーリストに追加します。
     * リスナーは、すべてのプロパティーに対して登録されます。
     * @param listener 追加する {@code PropertyChangeListener}
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * {@code PropertyChangeListener} をリスナーリストから削除します。 すべてのプロパティーで登録された
     * {@code PropertyChangeListener} を削除します。
     * @param listener 削除する {@code PropertyChangeListener}
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * 特定のプロパティーの {@code PropertyChangeListener} を追加します。
     * @param propertyName 待機しているプロパティーの名前
     * @param listener 追加する {@code PropertyChangeListener}
     */
    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * 特定のプロパティーの {@code PropertyChangeListener} を削除します。
     * @param propertyName 待機していたプロパティーの名前
     * @param listener 削除する {@code PropertyChangeListener}
     */
    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName,
                listener);
    }

    /**
     * 登録されているすべてのリスナーにバウンドプロパティーの更新を通知します。
     * @param propertyName 変更されたプロパティーのプログラム名
     * @param oldValue プロパティーの古い値
     * @param newValue プロパティーの新しい値
     */
    protected void firePropertyChange(String propertyName, Object oldValue,
            Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue,
                newValue);
    }

    /**
     * 登録されているすべてのリスナーに {@code int} バウンドプロパティーの更新を通知します。
     * @param propertyName 変更されたプロパティーのプログラム名
     * @param oldValue プロパティーの古い値
     * @param newValue プロパティーの新しい値
     */
    protected void firePropertyChange(String propertyName, int oldValue,
            int newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue,
                newValue);
    }

    /**
     * 登録されているすべてのリスナーに {@code boolean} バウンドプロパティーの更新を通知します。
     * @param propertyName 変更されたプロパティーのプログラム名
     * @param oldValue プロパティーの古い値
     * @param newValue プロパティーの新しい値
     */
    protected void firePropertyChange(String propertyName, boolean oldValue,
            boolean newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue,
                newValue);
    }
}
