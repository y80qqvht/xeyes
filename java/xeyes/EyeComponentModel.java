package xeyes;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** XEyes のひとつの目を表すデータモデルです。 */
public class EyeComponentModel {
    /** プロパティの変更通知を管理するオブジェクト */
    private final PropertyChangeSupport propertyChangeSupport;

    /** 新しいデータモデルを作成します。 */
    public EyeComponentModel() {
        super();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /** 目の幅を表すプロパティの名前です。 */
    public static final String EYE_WIDTH_PROPERTY = "eyeWidth";

    /** 目の幅 */
    private double eyeWidth = 90.0;

    /**
     * 目の幅を取得します。
     * @return 目の幅
     */
    public double getEyeWidth() {
        return eyeWidth;
    }

    /**
     * 目の幅を設定します。
     * @param eyeWidth 目の幅
     * @throws IllegalArgumentException 指定された値が正でない場合
     */
    public void setEyeWidth(double eyeWidth) {
        if (Double.isNaN(eyeWidth) || eyeWidth <= 0.0) {
            throw new IllegalArgumentException(EYE_WIDTH_PROPERTY + " = "
                    + eyeWidth);
        }
        double oldEyeWidth = this.eyeWidth;
        if (oldEyeWidth != eyeWidth) {
            double oldIrisWidth = getIrisWidth();
            this.eyeWidth = eyeWidth;
            firePropertyChange(EYE_WIDTH_PROPERTY, oldEyeWidth, eyeWidth);
            firePropertyChange(IRIS_WIDTH_PROPERTY, oldIrisWidth,
                    getIrisWidth());
        }
    }

    /** 目の高さを表すプロパティの名前です。 */
    public static final String EYE_HEIGHT_PROPERTY = "eyeHeight";

    /** 目の高さ */
    private double eyeHeight = 160.0;

    /**
     * 目の高さを取得します。
     * @return 目の高さ
     */
    public double getEyeHeight() {
        return eyeHeight;
    }

    /**
     * 目の高さを設定します。
     * @param eyeHeight 目の高さ
     * @throws IllegalArgumentException 指定された値が正でない場合
     */
    public void setEyeHeight(double eyeHeight) {
        if (Double.isNaN(eyeHeight) || eyeHeight <= 0.0) {
            throw new IllegalArgumentException(EYE_HEIGHT_PROPERTY + " = "
                    + eyeHeight);
        }
        double oldEyeHeight = this.eyeHeight;
        if (oldEyeHeight != eyeHeight) {
            double oldIrisHeight = getIrisHeight();
            this.eyeHeight = eyeHeight;
            firePropertyChange(EYE_HEIGHT_PROPERTY, oldEyeHeight, eyeHeight);
            firePropertyChange(IRIS_HEIGHT_PROPERTY, oldIrisHeight,
                    getIrisHeight());
        }
    }

    /** 目のサイズに対する瞳のサイズの比率を表すプロパティの名前です。 */
    public static final String IRIS_SIZE_RATIO_PROPERTY = "irisSizeRatio";

    /** 目のサイズに対する瞳のサイズの比率 */
    private float irisSizeRatio = 0.4f;

    /**
     * 目のサイズに対する瞳のサイズの比率を取得します。
     * @return 目のサイズに対する瞳のサイズの比率
     */
    public float getIrisSizeRatio() {
        return irisSizeRatio;
    }

    /**
     * 目のサイズに対する瞳のサイズの比率を設定します。
     * @param irisSizeRatio 目のサイズに対する瞳のサイズの比率
     * @throws IllegalArgumentException 指定された値が 0 以下または 1 以上の場合
     */
    public void setIrisSizeRatio(float irisSizeRatio) {
        if (Float.isNaN(irisSizeRatio) || irisSizeRatio <= 0.0f
                || irisSizeRatio >= 1.0f) {
            throw new IllegalArgumentException(IRIS_SIZE_RATIO_PROPERTY + " = "
                    + irisSizeRatio);
        }
        float oldIrisSizeRatio = this.irisSizeRatio;
        if (oldIrisSizeRatio != irisSizeRatio) {
            double oldIrisWidth = getIrisWidth();
            double oldIrisHeight = getIrisHeight();
            this.irisSizeRatio = irisSizeRatio;
            firePropertyChange(IRIS_SIZE_RATIO_PROPERTY, oldIrisSizeRatio,
                    irisSizeRatio);
            firePropertyChange(IRIS_WIDTH_PROPERTY, oldIrisWidth,
                    getIrisWidth());
            firePropertyChange(IRIS_HEIGHT_PROPERTY, oldIrisHeight,
                    getIrisHeight());
        }
    }

    /** 瞳の幅を表すプロパティの名前です。 */
    public static final String IRIS_WIDTH_PROPERTY = "irisWidth";

    /**
     * 瞳の幅を取得します。
     * @return 瞳の幅
     */
    public double getIrisWidth() {
        return getEyeWidth() * getIrisSizeRatio();
    }

    /** 瞳の高さを表すプロパティの名前です。 */
    public static final String IRIS_HEIGHT_PROPERTY = "irisHeight";

    /**
     * 瞳の高さを取得します。
     * @return 瞳の高さ
     */
    public double getIrisHeight() {
        return getEyeHeight() * getIrisSizeRatio();
    }

    /** 瞳の X 座標を表すプロパティの名前です。 */
    public static final String IRIS_X_PROPERTY = "irisX";

    /** 瞳の X 座標 */
    private double irisX;

    /**
     * 瞳の X 座標を取得します。
     * @return 瞳の X 座標
     */
    public double getIrisX() {
        return irisX;
    }

    /**
     * 瞳の X 座標を設定します。
     * @param irisX 瞳の X 座標
     */
    private void setIrisX(double irisX) {
        assert !Double.isNaN(irisX) : IRIS_X_PROPERTY + " is NaN";
        double oldIrisX = this.irisX;
        if (oldIrisX != irisX) {
            this.irisX = irisX;
            firePropertyChange(IRIS_X_PROPERTY, oldIrisX, irisX);
        }
    }

    /** 瞳の Y 座標を表すプロパティの名前です。 */
    public static final String IRIS_Y_PROPERTY = "irisY";

    /** 瞳の Y 座標 */
    private double irisY;

    /**
     * 瞳の Y 座標を取得します。
     * @return 瞳の Y 座標
     */
    public double getIrisY() {
        return irisY;
    }

    /**
     * 瞳の Y 座標を設定します。
     * @param irisY 瞳の Y 座標
     */
    private void setIrisY(double irisY) {
        assert !Double.isNaN(irisY) : IRIS_Y_PROPERTY + " is NaN";
        double oldIrisY = this.irisY;
        if (oldIrisY != irisY) {
            this.irisY = irisY;
            firePropertyChange(IRIS_Y_PROPERTY, oldIrisY, irisY);
        }
    }

    /** X 座標と Y 座標をカプセル化するクラスです。 */
    private static class Point {
        private final double x;

        private final double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * 瞳が指定された位置を向くようにします。
     * @param focusX 視線の先の X 座標
     * @param focusY 視線の先の Y 座標
     * @throws IllegalArgumentException 指定された値が {@code NaN} の場合
     */
    public void lookAt(double focusX, double focusY) {
        if (Double.isNaN(focusX))
            throw new IllegalArgumentException("focusX is NaN");
        if (Double.isNaN(focusY))
            throw new IllegalArgumentException("focusY is NaN");
        Point irisPosition = calcIrisPosition(focusX, focusY);
        setIrisX(irisPosition.x);
        setIrisY(irisPosition.y);
    }

    /**
     * 指定された視線の位置から瞳の位置を計算します。
     * @param focusX 視線の先の X 座標
     * @param focusY 視線の先の Y 座標
     * @return 計算された瞳の位置
     */
    private Point calcIrisPosition(double focusX, double focusY) {
        double irisRadiusX = getIrisWidth() / 2.0;
        double irisRadiusY = getIrisHeight() / 2.0;
        double irisX = focusX - irisRadiusX;
        double irisY = focusY - irisRadiusY;

        double centerX = getEyeWidth() / 2.0;
        double centerY = getEyeHeight() / 2.0;
        double cFocusX = focusX - centerX;
        double cFocusY = focusY - centerY;

        if (cFocusX == 0.0) {
            double irisTopMin = 0.0;
            double irisTopMax = getEyeHeight() - getIrisHeight();
            if (irisY < irisTopMin)
                irisY = irisTopMin;
            else if (irisY > irisTopMax)
                irisY = irisTopMax;
        } else {
            double tan = cFocusY / cFocusX;
            double a = centerX - irisRadiusX;
            double b = centerY - irisRadiusY;
            double tan2 = tan * tan;
            double a2 = a * a;
            double b2 = b * b;
            double cX2 = (a2 * b2) / (a2 * tan2 + b2);
            if (cX2 < cFocusX * cFocusX) {
                double cX = Math.sqrt(cX2);
                if (cFocusX < 0.0)
                    cX = -cX;
                double cY2 = cX2 * tan2;
                double cY = Math.sqrt(cY2);
                if (cFocusY < 0.0)
                    cY = -cY;
                irisX = cX + centerX - irisRadiusX;
                irisY = cY + centerY - irisRadiusY;
            }
        }

        return new Point(irisX, irisY);
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
}
